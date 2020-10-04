/*
 * Author: Kun Xie
 */

USE qacommunity;

-- -- DROP tables in a reversing order
-- DROP TABLE IF EXISTS notification;
-- DROP TABLE IF EXISTS reply;
-- DROP TABLE IF EXISTS post_tag;
-- DROP TABLE IF EXISTS post;
-- DROP TABLE IF EXISTS user;
-- DROP TABLE IF EXISTS tag;
-- DROP TABLE IF EXISTS tag_type;

-- create tag_type table
CREATE TABLE tag_type (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tag_type_name VARCHAR(50) NOT NULL
)ENGINE = InnoDB;
ALTER TABLE tag_type AUTO_INCREMENT=20000;

-- create tag table
CREATE TABLE tag (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tag_name VARCHAR(50) NOT NULL,
    tag_type_id INT NOT NULL,
    FOREIGN KEY (tag_type_id)
        REFERENCES tag_type (id)
)ENGINE = InnoDB;

-- create user table
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(30) NOT NULL,
    lastname VARCHAR(30) NOT NULL,
    username VARCHAR(30) NULL, -- default should be firstname + ' ' + lastname
    email_address VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    gmt_created BIGINT NOT NULL, -- GMT for Greenwich Mean Time
    gmt_modified BIGINT NOT NULL,
    avatar_url VARCHAR(100) NULL, -- default url comes from https://picsum.photos/
    token VARCHAR(36) NULL -- for used for log in session
)ENGINE = InnoDB;
ALTER TABLE user AUTO_INCREMENT=1;

-- create post table
CREATE TABLE post (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    gmt_created BIGINT NOT NULL, -- GMT for Greenwich Mean Time
    gmt_modified BIGINT NOT NULL,
    user_id INT NOT NULL, -- user who make this post.
    view_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
    reply_count INT DEFAULT 0, -- count of replies to the post
    FOREIGN KEY (user_id) REFERENCES user (id)
)ENGINE = InnoDB;
ALTER TABLE post AUTO_INCREMENT=50000;

-- create post_tag table, for many-to-many relationship
CREATE TABLE post_tag (
    post_id INT NOT NULL,
    tag_id INT NOT NULL,
    FOREIGN KEY (post_id) REFERENCES post (id),
    FOREIGN KEY (tag_id) REFERENCES tag (id),
    PRIMARY KEY (post_id, tag_id)
)ENGINE = InnoDB;

-- 这里我将一级回复和二级回复拆分开来保证trigger的正常运行

-- create reply table
CREATE TABLE primary_reply ( -- 一级评论
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL, -- id of user who makes this reply
    post_id INT NOT NULL, -- id of post
    gmt_created BIGINT NOT NULL, -- GMT for Greenwich Mean Time
    gmt_modified BIGINT NOT NULL,
    content TEXT NOT NULL, -- offer fake delete function
    reply_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE CASCADE
)ENGINE = InnoDB;
ALTER TABLE primary_reply AUTO_INCREMENT=100000000;

CREATE TABLE sub_reply ( -- 二级评论
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL, -- id of user who makes this reply
    primary_reply_id INT NOT NULL, -- id of parent reply id when it's a sub-reply
    gmt_created BIGINT NOT NULL, -- GMT for Greenwich Mean Time
    gmt_modified BIGINT NOT NULL,
    content TEXT NOT NULL, -- offer fake delete function
    reply_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (primary_reply_id) REFERENCES primary_reply (id) ON DELETE CASCADE 
)ENGINE = InnoDB;
ALTER TABLE sub_reply AUTO_INCREMENT=150000000;

-- create like_post table
CREATE TABLE like_post (
    user_id INT NOT NULL,
    post_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, post_id)
)ENGINE = InnoDB;

-- create like_reply table 只针对一级评论
CREATE TABLE like_reply (
    user_id INT NOT NULL,
    primary_reply_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (primary_reply_id) REFERENCES primary_reply (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, primary_reply_id)
)ENGINE = InnoDB;

-- create notification table
CREATE TABLE notification (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status TINYINT(1) DEFAULT 0, -- 0 means UNREAD, 1 means READ
    user_id INT NOT NULL,
    post_id INT NULL,
    primary_reply_id INT NULL,
    gmt_created BIGINT NOT NULL, -- GMT for Greenwich Mean Time
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (post_id) REFERENCES post (id),
    FOREIGN KEY (primary_reply_id) REFERENCES primary_reply (id)
)ENGINE = InnoDB;
ALTER TABLE notification AUTO_INCREMENT=400000000;

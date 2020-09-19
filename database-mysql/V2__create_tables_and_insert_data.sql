/*
 * Author: Kun Xie
 */

USE qacommunity;

-- DROP tables in a reversing order
DROP TABLE IF EXISTS notification;
DROP TABLE IF EXISTS reply;
DROP TABLE IF EXISTS post_tag;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS tag_type;

-- create tag_type table

CREATE TABLE tag_type (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tag_type_name VARCHAR(50) NOT NULL
)ENGINE = InnoDB;
ALTER TABLE tag_type AUTO_INCREMENT=20000;

-- insert data into tag_type table
INSERT INTO tag_type (tag_type_name)
VALUES
('Programming Language'),
('framework'),
('database'),
('Class Code');

-- create tag table
CREATE TABLE tag (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tag_name VARCHAR(50) NOT NULL,
    tag_type_id INT NOT NULL,
    FOREIGN KEY (tag_type_id)
        REFERENCES tag_type (id)
)ENGINE = InnoDB;

-- insert data into tag table
INSERT INTO tag (tag_name, tag_type_id)
VALUE
("javascript", 20000), -- programming language
("php",20000),
("css",20000),
("html",20000),
("html5",20000),
("java",20000),
("node.js",20000),
("python",20000),
("c++",20000),
("c",20000),
("golang",20000),
("objective-c",20000),
("typescript",20000),
("shell",20000),
("swift",20000),
("c#",20000),
("sass",20000),
("ruby",20000),
("bash",20000),
("less",20000),
("asp.net",20000),
("lua",20000),
("scala",20000),
("coffeescript",20000),
("actionscript",20000),
("rust",20000),
("erlang",20000),
("perl",20000),
("laravel",20001), -- framework
("spring",20001),
("express",20001),
("django",20001),
("flask",20001),
("yii",20001),
("ruby-on-rails",20001),
("tornado",20001),
("koa",20001),
("struts",20001),
("react.js",20001),
("vue.js",20001),
("angular",20001),
("mysql",20002), -- database
("redis",20002),
("mongodb",20002),
("sql",20002),
("oracle",20002),
("nosql memcached",20002),
("sqlserver",20002),
("postgresql",20002),
("sqlite",20002),
("CM231",20003), -- class code
("CM245",20003),
("CM307",20003),
("CM361",20003),
("CM330",20003),
("CM332",20003),
("CM333",20003),
("CM336",20003),
("CM390",20003);

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

-- insert data into user table
INSERT INTO user
(firstname, lastname, username, email_address, password, gmt_created, gmt_modified, avatar_url, token)
VALUES
('James', 'Bond', NULL, 'james.bond@gmail.com', '123456', 1600384525000, 1600384525237, 'https://picsum.photos/id/1/460/460', NULL),
('Harry', 'Potter', NULL, 'harry.potter@gmail.com', '123456', 1600334525000, 1600344525237, 'https://picsum.photos/id/2/460/460', NULL),
('Kun', 'Xie', NULL, 'alex.xiekun@gmail.com', '123456', 1600384525000, 1600384525237, 'https://picsum.photos/id/3/460/460', NULL),
('Kun', 'Xie2', NULL, 'kun.xie@washburn.edu', '123456', 1600184525000, 1600334525237, 'https://picsum.photos/id/4/460/460', NULL);

-- create post table
CREATE TABLE post (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    gmt_created BIGINT NOT NULL, -- GMT for Greenwich Mean Time
    gmt_modified BIGINT NOT NULL,
    user_id INT NOT NULL, -- user who make this post.
    view_count INT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES user (id)
)ENGINE = InnoDB;
ALTER TABLE post AUTO_INCREMENT=50000;

-- create post_tag table, for many-to-many relationship
CREATE TABLE post_tag (
    id INT AUTO_INCREMENT PRIMARY KEY,
    post_id INT NOT NULL,
    tag_id INT NOT NULL,
    FOREIGN KEY (post_id) REFERENCES post (id),
    FOREIGN KEY (tag_id) REFERENCES tag (id)
)ENGINE = InnoDB;
ALTER TABLE post_tag AUTO_INCREMENT=1;

-- create reply table
CREATE TABLE reply (
    id INT AUTO_INCREMENT PRIMARY KEY,
    reply_from_user_id INT NOT NULL, -- id of user who makes this reply
    reply_to_post_id INT NOT NULL, -- id of post
    reply_to_reply_id INT NULL, -- id of parent reply id when it's a sub-reply
    gmt_created BIGINT NOT NULL, -- GMT for Greenwich Mean Time
    gmt_modified BIGINT NOT NULL,
    content TEXT NOT NULL, -- offer fake delete function
    FOREIGN KEY (reply_from_user_id) REFERENCES user (id),
    FOREIGN KEY (reply_to_post_id) REFERENCES post (id),
    FOREIGN KEY (reply_to_reply_id) REFERENCES reply (id)
)ENGINE = InnoDB;
ALTER TABLE reply AUTO_INCREMENT=100000000;

-- create notification table
CREATE TABLE notification (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status TINYINT(1) DEFAULT 0, -- 0 means UNREAD, 1 means READ
    notify_from_user_id INT NOT NULL,
    notify_to_user_id INT NOT NULL,
    notify_from_post_id INT NOT NULL,
    notify_from_reply_id INT NULL,
    gmt_created BIGINT NOT NULL, -- GMT for Greenwich Mean Time
    gmt_modified BIGINT NOT NULL,
    FOREIGN KEY (notify_from_user_id) REFERENCES user (id),
    FOREIGN KEY (notify_to_user_id) REFERENCES user (id),
    FOREIGN KEY (notify_from_post_id) REFERENCES post (id),
    FOREIGN KEY (notify_from_reply_id) REFERENCES reply (id)
)ENGINE = InnoDB;
ALTER TABLE notification AUTO_INCREMENT=400000000;
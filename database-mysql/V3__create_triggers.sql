/*
 * Author: Kun Xie
 */

USE qacommunity;

-- create triggers
DROP TRIGGER IF EXISTS generate_nofication_for_post;
DROP TRIGGER IF EXISTS generate_nofication_for_reply;

DROP TRIGGER IF EXISTS increment_reply_count_for_post;
DROP TRIGGER IF EXISTS decrement_reply_count_for_post;
DROP TRIGGER IF EXISTS increment_reply_count_for_reply;
DROP TRIGGER IF EXISTS decrement_reply_count_for_reply;

DROP TRIGGER IF EXISTS increment_like_post_count_after_insert;
DROP TRIGGER IF EXISTS decrement_like_post_count_after_delete;
DROP TRIGGER IF EXISTS increment_like_reply_count_after_insert;
DROP TRIGGER IF EXISTS decrement_like_reply_count_after_delete;

DELIMITER //

-- for each reply insertion, generate a notification
CREATE TRIGGER generate_nofication_for_post
    AFTER INSERT ON primary_reply
    FOR EACH ROW
BEGIN
    INSERT INTO notification 
    (status, user_id, post_id, primary_reply_id, gmt_created)
    VALUES
    (0, NEW.user_id, NEW.post_id, NULL, UNIX_TIMESTAMP() * 1000);
END//

CREATE TRIGGER generate_nofication_for_reply
    AFTER INSERT ON sub_reply
    FOR EACH ROW
BEGIN
    INSERT INTO notification 
    (status, user_id, post_id, primary_reply_id, gmt_created)
    VALUES
    (0, NEW.user_id, NULL, NEW.primary_reply_id, UNIX_TIMESTAMP() * 1000);
END//

-- for each primary reply insertion, increment reply_count of post
CREATE TRIGGER increment_reply_count_for_post
    AFTER INSERT ON primary_reply
    FOR EACH ROW
BEGIN
    UPDATE post
    SET reply_count = reply_count + 1
    WHERE post.id = NEW.post_id;
END//

-- for each primary reply delete, decrement reply_count of post
CREATE TRIGGER decrement_reply_count_for_post
    AFTER DELETE ON primary_reply
    FOR EACH ROW
BEGIN
    UPDATE post
    SET reply_count = reply_count - 1
    WHERE post.id = OLD.post_id;
END//

-- for each sub reply insertion, increment reply_count of primary reply
CREATE TRIGGER increment_reply_count_for_reply
    AFTER INSERT ON sub_reply
    FOR EACH ROW
BEGIN
    UPDATE primary_reply
    SET reply_count = reply_count + 1
    WHERE primary_reply.id = NEW.primary_reply_id;
END//

-- for each sub reply delete, decrement reply_count of primary reply
CREATE TRIGGER decrement_reply_count_for_reply
    AFTER DELETE ON sub_reply
    FOR EACH ROW
BEGIN
    UPDATE primary_reply
    SET reply_count = reply_count - 1
    WHERE primary_reply.id = OLD.primary_reply_id;
END//

-- for each like insertion, increment like count
CREATE TRIGGER increment_like_post_count_after_insert
    AFTER INSERT ON like_post
    FOR EACH ROW
BEGIN
    UPDATE post
    SET like_count = like_count + 1
    WHERE post.id = NEW.post_id;
END//

-- for each like delete, decrement like count
CREATE TRIGGER decrement_like_post_count_after_delete
    AFTER DELETE ON like_post
    FOR EACH ROW
BEGIN
    UPDATE post
    SET like_count = like_count - 1
    WHERE post.id = OLD.post_id;
END//

-- for each like insertion, increment like count
CREATE TRIGGER increment_like_reply_count_after_insert
    AFTER INSERT ON like_reply
    FOR EACH ROW
BEGIN
    UPDATE primary_reply
    SET like_count = like_count + 1
    WHERE primary_reply.id = NEW.primary_reply_id;
END//

-- for each like delete, decrement like count
CREATE TRIGGER decrement_like_reply_count_after_delete
    AFTER DELETE ON like_reply
    FOR EACH ROW
BEGIN
    UPDATE primary_reply
    SET like_count = like_count - 1
    WHERE primary_reply.id = OLD.primary_reply_id;
END//

DELIMITER ;

-- creat a post_list_view specific for index.html
-- we should make it immutable
CREATE OR REPLACE VIEW post_list_view AS
    SELECT post.id AS post_id, 
        post.title AS title, 
        post.gmt_modified AS gmt_modified,
        post.view_count AS view_count,
        post.like_count AS like_count,
        post.reply_count AS reply_count,
        user.id AS user_id,
        user.avatar_url AS avatar_url  
    FROM post JOIN user ON post.user_id = user.id;

CREATE OR REPLACE VIEW tag_count_view AS
    SELECT tag_name, COUNT(post_id) AS tag_count
    FROM tag JOIN post_tag ON tag.id = post_tag.tag_id
    GROUP BY tag_name;


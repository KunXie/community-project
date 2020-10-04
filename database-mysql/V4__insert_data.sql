/*
 * Author: Kun Xie
 */

USE qacommunity;

-- insert data into tag_type table
INSERT INTO tag_type (tag_type_name)
VALUES
('Programming Language'), -- id: 20000
('framework'),
('database'),
('Class Code');

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

-- insert data into user table
INSERT INTO user
(firstname, lastname, username, email_address, password, gmt_created, gmt_modified, avatar_url, token)
VALUES
('James', 'Bond', "James Bond", 'james.bond@gmail.com', '123456', 1600384525000, 1600384525237, 'https://picsum.photos/id/1/460/460', NULL),
('Harry', 'Potter', "Harry Potter", 'harry.potter@gmail.com', '123456', 1600334525000, 1600344525237, 'https://picsum.photos/id/2/460/460', NULL),
('Kun', 'Xie', "Kun Xie1", 'alex.xiekun@gmail.com', '123456', 1600384525000, 1600384525237, 'https://picsum.photos/id/3/460/460', NULL),
('Kun', 'Xie2', "KunXie2", 'kun.xie@washburn.edu', '123456', 1600184525000, 1600334525237, 'https://picsum.photos/id/4/460/460', NULL);

-- insert data into post table
INSERT INTO post
(title, content, gmt_created, gmt_modified, user_id, view_count, like_count)
VALUES
('test post11', '# test<br>## head2', 1600384525000, 1600384525000, 1, 0, 0),
('test post21', '# test<br>## head2', 1600384525000, 1600384525000, 1, 0, 0),
('test post31', '# test<br>## head2', 1600384525000, 1600384525000, 1, 0, 0),
('test post41', '# test<br>## head2', 1600384525000, 1600384525000, 1, 0, 0),
('test post51', '# test<br>## head2', 1600384525000, 1600384525000, 1, 0, 0),
('test post61', '# test<br>## head2', 1600384525000, 1600384525000, 1, 0, 0),
('test post71', '# test<br>## head2', 1600384525000, 1600384525000, 1, 0, 0),
('test post81', '# test<br>## head2', 1600384525000, 1600384525000, 1, 0, 0),
('test post91', '# test<br>## head2', 1600384525000, 1600384525000, 1, 0, 0),
('test post101', '# test<br>## head2', 1600384525000, 1600384525000, 1, 0, 0),
('test post111', '# test<br>## head2', 1600384525000, 1600384525000, 1, 0, 0),
('test post121', '# test<br>## head2', 1600384525000, 1600384525000, 1, 0, 0),
('test post131', '# test<br>## head2', 1600384525000, 1600384525000, 1, 0, 0),
('test post141', '# test<br>## head2', 1600384525000, 1600384525000, 1, 0, 0),
('test post151', '# test<br>## head2', 1600384525000, 1600384525000, 1, 0, 0),
('test post162', '# test<br>## head2', 1600384525000, 1600384525000, 2, 0, 0),
('test post172', '# test<br>## head2', 1600384525000, 1600384525000, 2, 0, 0),
('test post182', '# test<br>## head2', 1600384525000, 1600384525000, 2, 0, 0),
('test post192', '# test<br>## head2', 1600384525000, 1600384525000, 2, 0, 0),
('test post202', '# test<br>## head2', 1600384525000, 1600384525000, 2, 0, 0),
('test post212', '# test<br>## head2', 1600384525000, 1600384525000, 2, 0, 0),
('test post222', '# test<br>## head2', 1600384525000, 1600384525000, 2, 0, 0),
('test post232', '# test<br>## head2', 1600384525000, 1600384525000, 2, 0, 0),
('test post242', '# test<br>## head2', 1600384525000, 1600384525000, 2, 0, 0),
('test post252', '# test<br>## head2', 1600384525000, 1600384525000, 2, 0, 0);
     

-- insert data into post_tag table
INSERT INTO post_tag (post_id, tag_id)
VALUES
(50000, 1),
(50000, 2),
(50000, 3),
(50000, 4),
(50000, 6),
(50000, 16),
(50000, 21),
(50000, 22),
(50000, 24),
(50000, 25),
(50000, 30),
(50001, 17),
(50001, 13),
(50001, 25),
(50001, 30),
(50001, 48),
(50001, 50),
(50002, 14),
(50002, 19),
(50002, 31),
(50003, 1),
(50004, 1),
(50005, 1),
(50006, 1),
(50007, 1),
(50008, 1),
(50009, 1),
(50010, 1),
(50011, 1),
(50012, 1),
(50013, 1),
(50014, 1),
(50015, 1),
(50016, 1),
(50017, 1),
(50018, 1),
(50019, 1),
(50020, 1),
(50021, 1),
(50022, 1),
(50023, 1),
(50024, 1);

-- insert first-level reply into reply
INSERT INTO primary_reply 
(user_id, post_id, gmt_created, gmt_modified, content)
VALUES
(1, 50000, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, "# test reply"), -- id: 100000000
(1, 50000, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000,  "# test reply2"),
(2, 50000, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, "# test reply"),
(3, 50000, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000,  "## test reply"),
(1, 50001, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000,  "# test reply"),
(1, 50001, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, "# test reply2"),
(2, 50001, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, "# test reply"),
(3, 50001, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, "## test reply"),
(1, 50002, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, "# test reply"),
(1, 50002, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, "# test reply2"),
(2, 50002, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, "# test reply"),
(3, 50002, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, "## test reply"),
(1, 50003, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, "# test reply"),
(1, 50003, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, "# test reply2"),
(2, 50003, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, "# test reply"),
(3, 50003, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, "## test reply"),
(1, 50004, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, "# test reply"),
(1, 50004, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, "# test reply2"),
(2, 50004, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, "# test reply"),
(3, 50004, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, "## test reply"); -- id: 100000019


-- insert like list to post
INSERT INTO like_post (user_id, post_id)
VALUES
(1, 50000),
(3, 50000),
(4, 50000),
(1, 50001),
(2, 50001),
(3, 50001),
(4, 50001),
(1, 50002),
(3, 50002),
(4, 50002),
(1, 50003),
(2, 50004),
(3, 50004),
(4, 50004),
(1, 50005),
(1, 50006),
(1, 50007),
(1, 50008),
(1, 50009),
(1, 50010),
(1, 50011),
(1, 50012),
(1, 50013),
(1, 50014),
(1, 50015),
(1, 50016),
(1, 50017),
(1, 50018),
(1, 50019),
(1, 50020),
(1, 50021),
(1, 50022),
(1, 50023),
(1, 50024);

-- insert like list to reply
INSERT INTO like_reply (user_id, primary_reply_id)
VALUES
(1, 100000000),
(2, 100000000),
(3, 100000000),
(4, 100000000),
(1, 100000001),
(3, 100000001),
(1, 100000002),
(1, 100000003),
(2, 100000004),
(3, 100000005),
(4, 100000006),
(1, 100000007),
(2, 100000008),
(3, 100000009),
(3, 100000010),
(4, 100000011),
(4, 100000012),
(3, 100000013),
(2, 100000014),
(1, 100000015),
(2, 100000016),
(2, 100000017),
(3, 100000018),
(1, 100000019),
(3, 100000019);


-- insert second-level reply into reply 这个table往后挪才能不与前面的reply插入起冲突
INSERT INTO sub_reply 
(user_id, primary_reply_id, gmt_created, gmt_modified, content)
VALUES
(3, 100000000, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, "test second reply"),
(4, 100000000, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000,  "test second reply"),
(2, 100000001, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, "test reply"),
(3, 100000002, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000,  "test reply"),
(3, 100000005, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000,  "test reply"),
(3, 100000005, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, "# test reply2"),
(4, 100000005, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, "# test reply");





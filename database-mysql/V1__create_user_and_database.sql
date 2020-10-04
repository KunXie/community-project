/*
 * Author: Kun Xie
 *
 */

-- create an account for this project
-- username: community-project
-- password: community-project
CREATE USER IF NOT EXISTS 'community-project'@'localhost' IDENTIFIED BY 'community-project';

-- create a database
DROP DATABASE IF EXISTS qacommunity;
CREATE DATABASE qacommunity CHARACTER SET UTF8 COLLATE utf8_bin;

-- grant privileges
GRANT ALL PRIVILEGES ON qacommunity.* TO 'community-project'@'localhost';
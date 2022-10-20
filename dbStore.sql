DROP DATABASE IF EXISTS store;
CREATE DATABASE IF NOT EXISTS store;
USE store;

DROP TABLE IF EXISTS product;
CREATE TABLE product (
id int NOT NULL,
name varchar(40),
price Double,
quantity integer,
PRIMARY KEY(id));

DROP TABLE IF EXISTS clothe;
CREATE TABLE product (
id int NOT NULL,
Csize int,
FOREIGN KEY (id) REFERENCES product(id));

DROP TABLE IF EXISTS shoe;
CREATE TABLE shoe (
id int NOT NULL,
Ssize int,
FOREIGN KEY (id) REFERENCES product(id));


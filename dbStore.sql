DROP DATABASE IF EXISTS store;
CREATE DATABASE IF NOT EXISTS store;
USE store;

DROP TABLE IF EXISTS product;
CREATE TABLE product (
id int NOT NULL,
name varchar(40),
category varchar(40),
sellingPrice Double,
purchasingPrice Double,
nbItems integer,
PRIMARY KEY(id));

DROP TABLE IF EXISTS clothe;
CREATE TABLE clothe (
id int NOT NULL,
Csize int,
FOREIGN KEY (id) REFERENCES product(id));

DROP TABLE IF EXISTS shoe;
CREATE TABLE shoe (
id int NOT NULL,
Ssize int,
FOREIGN KEY (id) REFERENCES product(id));


select * from product;
select * from clothe;
SELECT * FROM product NATURAL JOIN clothe;

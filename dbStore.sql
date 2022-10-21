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

DROP TABLE IF EXISTS accessory;
CREATE TABLE accessory (
id int NOT NULL,
FOREIGN KEY (id) REFERENCES product(id));

DROP TABLE IF EXISTS actions;
CREATE TABLE actions(
id int NOT NULL,
gain int);

INSERT INTO actions VALUES (1,30);
INSERT INTO actions VALUES (2,-50);

select sum(gain) from actions where gain>0;



select * from product;
select * from clothe;

SELECT * FROM product NATURAL JOIN clothe;
SELECT * FROM product NATURAL JOIN clothe;

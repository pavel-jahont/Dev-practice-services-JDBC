CREATE DATABASE IF NOT EXISTS jd2_jdbc;
CREATE TABLE film
(
    id       INT PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(60) NOT NULL,
    duration int         NOT NULL,
    price    int         NOT NULL
);
INSERT INTO film(name, duration, price)
VALUES ('test1', 60, 10);
INSERT INTO film(name, duration, price)
VALUES ('test2', 90, 15);
INSERT INTO film(name, duration, price)
VALUES ('test3', 120, 20);
INSERT INTO film(name, duration, price)
VALUES ('test4', 60, 5);
INSERT INTO film(name, duration, price)
VALUES ('test5', 90, 80);
SELECT MAX(price) as MAX_PRICE
FROM film;

DELETE
FROM film
WHERE price = (SELECT * FROM (SELECT MIN(price) FROM film) AS min);
UPDATE film
SET name='Hot film!'
WHERE price = (SELECT MAX(price) as MAX_PRICE FROM film);
DROP TABLE film;
DROP DATABASE jd2_jdbc;
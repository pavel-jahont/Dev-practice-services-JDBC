CREATE DATABASE jd2_jdbc;
CREATE TABLE IF NOT EXISTS car
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    name            VARCHAR(60) NOT NULL,
    car_model       VARCHAR(60) NOT NULL,
    engine_capacity INT         NOT NULL
);
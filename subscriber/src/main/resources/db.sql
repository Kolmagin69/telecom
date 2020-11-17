CREATE USER subscriber WITH PASSWORD 'pas123';
CREATE DATABASE subscriber OWNER subscriber;

CREATE TABLE purchases (
    id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    msisdn int NOT NULL,
    timestamp timestamp NOT NULL
);

CREATE TABLE subscriptions (
    id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    msisdn int NOT NULL,
    timestamp timestamp NOT NULL
);

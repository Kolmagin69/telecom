CREATE USER publisher WITH PASSWORD 'pas123';
CREATE DATABASE subscriber OWNER publisher;

CREATE TABLE messages (
    id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    msisdn int NOT NULL,
    action varchar(15) NOT NULL,
    timestamp timestamp NOT NULL DEFAULT NOW()
);
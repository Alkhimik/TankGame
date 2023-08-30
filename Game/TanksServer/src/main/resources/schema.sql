CREATE SCHEMA tank;

CREATE TABLE tank.stats(
id int PRIMARY KEY,
total_shots int not null,
hits int not null,
missed int not null
);
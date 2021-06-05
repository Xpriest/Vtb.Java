DROP TABLE cars IF EXISTS;
CREATE TABLE IF NOT EXISTS cars (id bigserial PRIMARY KEY, brandName VARCHAR(255), horsePower VARCHAR(255));
INSERT INTO cars (brandName, horsePower) VALUES ('Lada', 100);
INSERT INTO cars (brandName, horsePower) VALUES ('Porsche', 310);

DROP TABLE planes IF EXISTS;
CREATE TABLE IF NOT EXISTS planes (id bigserial PRIMARY KEY, producer VARCHAR(255), flightHeight FLOAT);
INSERT INTO planes (producer, flightHeight) VALUES ('Boeing', 10000);

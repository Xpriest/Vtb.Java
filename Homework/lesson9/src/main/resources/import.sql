DROP TABLE orders IF EXISTS;
CREATE TABLE IF NOT EXISTS orders (id bigserial PRIMARY KEY, title VARCHAR(255));
INSERT INTO orders (title) VALUES ('order1');
INSERT INTO orders (title) VALUES ('order2');
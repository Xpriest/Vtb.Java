create table items (id bigserial primary key, title varchar(255), price int);

insert into items (title, price) values
('Milk', 96),
('Bread', 25),
('Cheese', 400);
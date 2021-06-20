create table users (id bigserial primary key, name varchar(255));

insert into users (name) values
('Andrei'),
('Ivan'),
('Natasha');


create table items (id bigserial primary key, name varchar(255), price int);

insert into items (name, price) values
('Bread', 25),
('Milk', 81),
('Cheese', 400);


create table purchases (id bigserial primary key, userid bigserial, itemid bigserial, price int, purchasedate timestamp);

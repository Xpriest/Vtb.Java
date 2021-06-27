create table users (id bigserial, username varchar(30) not null unique, password varchar(80) not null,
                    email varchar(50) unique, primary key (id));

create table roles (id serial, name varchar(50) not null, primary key (id));

create table rules (id serial, name varchar(50) not null, primary key (id));

CREATE TABLE roles_rules (role_id bigint not null, rule_id int not null, primary key (role_id, rule_id),
                          foreign key (role_id) references roles (id),
                          foreign key (rule_id) references rules (id));

CREATE TABLE users_roles (user_id bigint not null, role_id int not null, primary key (user_id, role_id),
                          foreign key (user_id) references users (id),
                          foreign key (role_id) references roles (id));

CREATE TABLE users_rules (user_id bigint not null, rule_id int not null, primary key (user_id, rule_id),
                          foreign key (user_id) references users (id),
                          foreign key (rule_id) references rules (id));

insert into roles (name)
values ('ROLE_USER'), ('ROLE_ADMIN');

insert into rules (name)
values ('RULE1'), ('RULE2'), ('RULE3');

insert into users (username, password, email)
values ('user1', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'user1@gmail.com'),
       ('user2', '$2y$12$vD15xX5KSNlnUtFMBUfdR.qooQH2Jz/rKkhveDFm21ZGEY44hzpKy', 'user2@gmail.com'),
       ('user3', '$2y$12$Ip56nLnVhUTZitv3VZBsiuCCfk6q5COsaIooG5Bcw/C26lCkAf.0W', 'user3@gmail.com'),
       ('user4', '$2y$12$t05mUOWKNuQ2PM9iFxoM9eF8eAZNiQAe6pYezkcA6pC.4s2we2Wfm', 'user4@gmail.com');

insert into roles_rules (role_id, rule_id)
values (2, 1), (2, 2);

insert into users_roles (user_id, role_id)
values (1, 1), (2, 2), (4, 1);

insert into users_rules (user_id, rule_id)
values (3, 1), (4, 3);
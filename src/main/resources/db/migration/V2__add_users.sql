create table users
(
    id       bigserial,
    username varchar(30) not null unique,
    password varchar(60) not null,
    email    varchar(50) not null,
    primary key (id)
);

create table roles
(
    id   serial,
    name varchar not null,
    primary key (id)
);

create table users_roles
(
    user_id bigint not null,
    role_id int    not null,
    primary key (user_id, role_id),
    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id)
);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_MANAGER'),
       ('ROLE_ADMIN');

insert into users (username, password, email)
values ('user', '$2a$12$rr6hixcXbZPIPTYRPW5fkO8OZlcyESLYdWDyTgQ77jr.Lp2OSHjjK', 'user@mail.ru'),
       ('admin', '$2a$12$rr6hixcXbZPIPTYRPW5fkO8OZlcyESLYdWDyTgQ77jr.Lp2OSHjjK', 'admin@mail.ru'),
       ('manager', '$2a$12$rr6hixcXbZPIPTYRPW5fkO8OZlcyESLYdWDyTgQ77jr.Lp2OSHjjK', 'manager@mail.ru');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 1),
       (2, 3),
       (3, 1),
       (3, 2);
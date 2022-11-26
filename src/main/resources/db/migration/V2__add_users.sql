create table users
(
    id         bigserial primary key,
    username   varchar(36) not null,
    password   varchar(80) not null,
    email      varchar(50) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table roles
(
    id         bigserial primary key,
    name       varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

CREATE TABLE users_roles
(
    user_id bigint not null references users (id),
    role_id bigint not null references roles (id),
    primary key (user_id, role_id)
);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_MANAGER'),
       ('ROLE_ADMIN');

insert into users (username, password, email)
values ('user', '$2a$12$rr6hixcXbZPIPTYRPW5fkO8OZlcyESLYdWDyTgQ77jr.Lp2OSHjjK', 'user@mail.ru'),
       ('admin', '$2a$12$rr6hixcXbZPIPTYRPW5fkO8OZlcyESLYdWDyTgQ77jr.Lp2OSHjjK', 'admin@mail.ru'),
       ('manager', '$2a$12$rr6hixcXbZPIPTYRPW5fkO8OZlcyESLYdWDyTgQ77jr.Lp2OSHjjK', 'manager@mai.ru'),
       ('fant', '$2a$12$rr6hixcXbZPIPTYRPW5fkO8OZlcyESLYdWDyTgQ77jr.Lp2OSHjjK', 'user@ail.ru');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 1),
       (2, 3),
       (3, 1),
       (3, 2);
create table if not exists users (
    id serial primary key,
    username varchar(25) not null,
    password varchar(255) not null,
    email varchar(100) not null unique,
    roles char(6)[] not null
);
create table if not exists users (
    id serial primary key,
    username varchar(25) not null,
    password varchar(255) not null,
    email varchar(100) not null unique,
    roles char(6) not null,
    created_by varchar(50),
    created_dt timestamp,
    updated_by varchar(50),
    updated_dt timestamp,
    version integer not null default 0
);
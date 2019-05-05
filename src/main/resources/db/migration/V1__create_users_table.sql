create table if not exists users
(
    id         serial,
    first_name varchar(255) NULL,
    last_name  varchar(255) NULL,
    birth_date timestamp    null,
    login      varchar(255) not NULL,
    password   varchar(255) not NULL,
    address    varchar(255) NULL,
    about_me   text         NULL,

    primary key (id)
);
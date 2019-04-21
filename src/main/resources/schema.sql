drop table users;
create table users
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

-- todo add indices.

insert into users (first_name, last_name, birth_date, login, password, address, about_me)
values ('Joe', 'Mann', null, 'joemann', '123456', null, null);
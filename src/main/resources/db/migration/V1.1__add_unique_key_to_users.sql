alter table users
    add constraint username_is_unique unique (login);

create index login on users (login);
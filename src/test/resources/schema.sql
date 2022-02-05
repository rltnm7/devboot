drop table if exists users;

create table users (
  id serial primary key,
  username varchar not null,
  name varchar not null,
  birthday DATE not null
);

set client_encoding = 'UTF8';

create table users (
  id serial primary key,
  username varchar not null,
  name varchar not null,
  birthday DATE not null
);

insert into users(username, name, birthday) values 
  ('nana-mizuki', 'Nana Mizuki', to_date('1980/01/21', 'yyyy/mm/dd')),
  ('maaya-uchida', 'Maaya Uchida', to_date('1989/12/27', 'yyyy/mm/dd'))
;

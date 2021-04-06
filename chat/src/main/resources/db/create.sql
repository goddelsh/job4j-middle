create table roles (
   id serial primary key not null,
   name varchar(2000) unique
);

create table users (
   id serial primary key not null,
   login varchar(2000) unique not null,
   password varchar(2000) not null
);

create table rooms (
   id serial primary key not null,
   name varchar(2000) not null,
   create_date date not null default CURRENT_DATE,
   status integer not null default 1,
   creator_id integer not null references users(id)
);

create table rooms_roles_access (
   id serial primary key not null,
   room_id integer not null references rooms(id),
   role_id integer not null references  roles(id)
);


create table users_roles (
   id serial primary key not null,
   user_id integer not null references users(id),
   role_id integer not null references  roles(id)
);


create table messages (
   id serial primary key not null,
   message varchar(2000),
   create_date date not null default CURRENT_DATE,
   user_id integer not null references users(id),
   room_id integer not null references rooms(id)
);


create table person (
   id serial primary key not null,
   login varchar(2000),
   password varchar(2000)
);

insert into person (login, password) values ('parsentev', '123');
insert into person (login, password) values ('ban', '123');
insert into person (login, password) values ('ivan', '123');

create table employees (
   id serial primary key not null,
   name varchar(2000),
   ssn varchar(2000),
   hiringDate date
);

INSERT INTO public.employees(
	id, name, ssn, hiringdate)
	VALUES (1, 'Ivan Ivanov', '1235678', '25-12-2020');
INSERT INTO public.employees(
	id, name, ssn, hiringdate)
	VALUES (2, 'Petr Petrov', 'AS8585858AS', '02-02-2021');



create table employees_access_history (
   id serial primary key not null,
   employee_id integer,
   person_id integer,
   accessDate date not null default CURRENT_DATE
);
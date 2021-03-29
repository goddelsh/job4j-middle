INSERT INTO public.roles(
	id, name)
	VALUES (1, 'ADMIN');

INSERT INTO public.roles(
	id, name)
	VALUES (2, 'USER');

INSERT INTO public.users(
	id, login, password)
	VALUES (1, 'root', '123');

INSERT INTO public.users(
	id, login, password)
	VALUES (2, 'user', '123');

INSERT INTO public.users_roles(
	id, user_id, role_id)
	VALUES (1, 1, 1);
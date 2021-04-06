INSERT INTO public.roles(
	id, name)
	VALUES (1, 'ROLE_ADMIN');

INSERT INTO public.roles(
	id, name)
	VALUES (2, 'ROLE_USER');

INSERT INTO public.users(
	id, login, password)
	VALUES (1, 'root', '$2a$10$aWlZ726biY80v.HlZ5INiuOwhOQSsWvBDY2Cz5l4RHNkymy0UiQEC');

INSERT INTO public.users_roles(
	id, user_id, role_id)
	VALUES (1, 1, 1);
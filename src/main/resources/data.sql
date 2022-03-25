insert into roles (name)
values ('ROLE_ADMIN');

insert into users (email, name, password, role_id)
values ('admin@example.com', 'Admin Admin', '$2a$11$1pbNJ3WA56JGLSxeRkQ2DONJZoUMfHqkH0KQSwMChqA2Lt5yC76g6', 1);

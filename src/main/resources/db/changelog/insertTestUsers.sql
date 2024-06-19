insert into users (username, password) values ('admin_test', '$2a$10$shX1wMcdKeg7ihPgMY37s.lhONoh8NM6XgCMQ4JdgOllqvnowRhAK');

insert into users_roles (role_id, user_id) VALUES ((select id from users where username = 'admin_test'), (select id from role where name = 'ROLE_ADMIN'));
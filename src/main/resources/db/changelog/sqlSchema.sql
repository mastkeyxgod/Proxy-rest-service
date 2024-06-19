
CREATE TABLE audit
(
    id        BIGSERIAL NOT NULL,
    method    VARCHAR(255),
    endpoint  VARCHAR(255),
    status    VARCHAR(255),
    username  VARCHAR(255),
    ip        VARCHAR(255),
    timestamp TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_audit PRIMARY KEY (id)
);

CREATE TABLE privilege
(
    id   BIGSERIAL NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_privilege PRIMARY KEY (id)
);

CREATE TABLE role
(
    id   BIGSERIAL NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE roles_privileges
(
    privilege_id BIGINT NOT NULL,
    role_id      BIGINT NOT NULL,
    CONSTRAINT pk_roles_privileges PRIMARY KEY (privilege_id, role_id)
);

CREATE TABLE "users"
(
    id       BIGSERIAL NOT NULL,
    username VARCHAR(255),
    password VARCHAR(255),
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE users_roles
(
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_users_roles PRIMARY KEY (role_id, user_id)
);



INSERT INTO privilege (name)
VALUES
    ('CREATE_POSTS_PRIVILEGE'),
    ('UPDATE_POSTS_PRIVILEGE'),
    ('DELETE_POSTS_PRIVILEGE'),
    ('CREATE_ALBUMS_PRIVILEGE'),
    ('UPDATE_ALBUMS_PRIVILEGE'),
    ('DELETE_ALBUMS_PRIVILEGE'),
    ('CREATE_USERS_PRIVILEGE'),
    ('UPDATE_USERS_PRIVILEGE'),
    ('DELETE_USERS_PRIVILEGE'),
    ('READ_POSTS_PRIVILEGE'),
    ('READ_ALBUMS_PRIVILEGE'),
    ('READ_USERS_PRIVILEGE'),
    ('WEBSOCKETS_PRIVILEGE');

INSERT INTO role (name)
VALUES
    ('ROLE_WEBSOCKETS'),
    ('ROLE_USERS_VIEWER'),
    ('ROLE_ALBUMS_VIEWER'),
    ('ROLE_POSTS_VIEWER'),
    ('ROLE_USERS_EDITOR'),
    ('ROLE_ALBUMS_EDITOR'),
    ('ROLE_POSTS_EDITOR'),
    ('ROLE_ADMIN'),
    ('ROLE_VIEWER');

INSERT INTO roles_privileges (role_id, privilege_id)
SELECT r.id, p.id
FROM role r
         JOIN privilege p ON p.name = 'WEBSOCKETS_PRIVILEGE'
WHERE r.name = 'ROLE_WEBSOCKETS';

INSERT INTO roles_privileges (role_id, privilege_id)
SELECT r.id, p.id
FROM role r
         JOIN privilege p ON p.name IN ('READ_USERS_PRIVILEGE')
WHERE r.name = 'ROLE_USERS_VIEWER';

INSERT INTO roles_privileges (role_id, privilege_id)
SELECT r.id, p.id
FROM role r
         JOIN privilege p ON p.name IN ('READ_ALBUMS_PRIVILEGE')
WHERE r.name = 'ROLE_ALBUMS_VIEWER';

INSERT INTO roles_privileges (role_id, privilege_id)
SELECT r.id, p.id
FROM role r
         JOIN privilege p ON p.name IN ('READ_POSTS_PRIVILEGE')
WHERE r.name = 'ROLE_POSTS_VIEWER';

INSERT INTO roles_privileges (role_id, privilege_id)
SELECT r.id, p.id
FROM role r
         JOIN privilege p ON p.name IN ('CREATE_USERS_PRIVILEGE', 'UPDATE_USERS_PRIVILEGE', 'DELETE_USERS_PRIVILEGE')
WHERE r.name = 'ROLE_USERS_EDITOR';

INSERT INTO roles_privileges (role_id, privilege_id)
SELECT r.id, p.id
FROM role r
         JOIN privilege p ON p.name IN ('CREATE_ALBUMS_PRIVILEGE', 'UPDATE_ALBUMS_PRIVILEGE', 'DELETE_ALBUMS_PRIVILEGE')
WHERE r.name = 'ROLE_ALBUMS_EDITOR';

INSERT INTO roles_privileges (role_id, privilege_id)
SELECT r.id, p.id
FROM role r
         JOIN privilege p ON p.name IN ('CREATE_POSTS_PRIVILEGE', 'UPDATE_POSTS_PRIVILEGE', 'DELETE_POSTS_PRIVILEGE')
WHERE r.name = 'ROLE_POSTS_EDITOR';




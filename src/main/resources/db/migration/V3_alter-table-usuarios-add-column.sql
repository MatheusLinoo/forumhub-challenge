alter table usuarios
add COLUMN role ENUM('ROLE_USER', 'ROLE_ADMIN') not null default 'ROLE_USER';

update usuarios set role = 'ROLE_USER' where role is null;
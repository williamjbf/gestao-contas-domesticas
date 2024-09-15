create table "t_usuario" (
    "id" serial,
    "login" varchar(255),
    "password" varchar(255),
    constraint "usuario_pk" primary key ("id")
);

insert into "t_usuario" (login, password) values ('admin','$2a$10$7Czf0mhepbZCN1bZWpEdwuC0ASX9o8yW1.5hCyoRJosBt2C1lya92');
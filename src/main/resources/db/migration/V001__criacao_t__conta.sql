create table "t_conta"
(
    "id"        serial,
    "descricao" varchar(255) not null,
    "valor" numeric not null,
    "data" date not null,
    "status" varchar(255) default 'PENDENTE',
    "categoria" varchar(255) default 'OUTRO',
    "tipo_conta" varchar(255) not null,
    constraint "categoria_pk" primary key ("id")
);
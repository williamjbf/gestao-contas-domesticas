create table "t_cartao"
(
    "id"     serial,
    "descricao" varchar(255) not null,
    "limite" numeric not null ,
    constraint "cartao_pk" primary key ("id")
)
create table "t_compra"
(
    "id"          serial,
    "data_compra" date,
    "valor"       numeric,
    "descricao"   varchar(255),
    "categoria"   varchar(255),
    "id_cartao"   bigint,
    constraint "compra_pk" primary key ("id"),
    constraint "fk_cartao" foreign key (id_cartao) references t_cartao(id)
)
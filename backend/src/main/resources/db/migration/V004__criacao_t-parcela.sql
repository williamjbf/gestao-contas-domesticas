create table "t_parcela"
(
    "id"          serial,
    "data_cobranca" date not null,
    "valor"       numeric not null,
    "ordem"        integer not null,
    "id_compra"   bigint not null,
    constraint "parcela`_pk" primary key ("id"),
    constraint "fk_compra" foreign key (id_compra) references t_compra(id)
)
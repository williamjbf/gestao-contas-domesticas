alter table "t_cartao" add column "id_usuario" bigint not null ;
alter table "t_cartao" add constraint fk_usuario foreign key (id_usuario) references "t_usuario"(id);

alter table "t_compra" add column "id_usuario" bigint not null ;
alter table "t_compra" add constraint fk_usuario foreign key (id_usuario) references "t_usuario"(id);

alter table "t_conta" add column "id_usuario" bigint not null ;
alter table "t_conta" add constraint fk_usuario foreign key (id_usuario) references "t_usuario"(id);

alter table "t_parcela" add column "id_usuario" bigint not null;
alter table "t_parcela" add constraint fk_usuario foreign key (id_usuario) references "t_usuario"(id);
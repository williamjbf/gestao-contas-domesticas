ALTER TABLE t_compra
    ADD COLUMN paga BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE t_parcela
    ADD COLUMN paga BOOLEAN NOT NULL DEFAULT FALSE;
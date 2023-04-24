--liquibase formatted sql

--changeset rudnev:1
CREATE TABLE socks(
    id Serial,
    color Text,
    cotton_part INTEGER,
    quantity INTEGER
)
--liquibase formatted sql
--changeset vpetrova:2

alter table tours
alter column price type real;
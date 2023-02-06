--liquibase formatted sql
--changeset lisov:2

ALTER TABLE tours ADD COLUMN description VARCHAR(1024);
ALTER TABLE tours ADD COLUMN name VARCHAR(255);

--liquibase formatted sql
--changeset ermakovich:1

create schema if not exists qaprotours;
set schema 'qaprotours';


CREATE TABLE IF NOT EXISTS users(
  id           bigserial PRIMARY KEY,
  name         varchar(35)         NOT NULL,
  surname      varchar(35)         NOT NULL,
  email        varchar(320) UNIQUE NOT NULL,
  password     varchar(200)        NOT NULL,
  role         varchar(20)         NOT NULL,
  is_activated boolean             NOT NULL DEFAULT false
);

CREATE TABLE IF NOT EXISTS passports (
    user_id bigint PRIMARY KEY,
    serial_number varchar(20) UNIQUE NOT NULL,
    identity_number varchar(35) UNIQUE NOT NULL,
    constraint fk_user foreign key(user_id) references users(id)
);
CREATE TABLE IF NOT EXISTS countries (
    id bigserial PRIMARY KEY,
    name varchar(300) NOT NULL
);

CREATE TABLE IF NOT EXISTS cities (
    id bigserial PRIMARY KEY,
    name varchar(300) NOT NULL,
    latitude double precision NOT NULL,
    longitude double precision NOT NULL,
    country_id bigint,
    constraint fk_country foreign key(country_id) references countries(id)
);

CREATE TABLE IF NOT EXISTS addresses (
    id bigint PRIMARY KEY,
    country_id bigint NOT NULL,
    city_id bigint NOT NULL,
    street bigint NOT NULL,
    house_number int NOT NULL,
    constraint fk_country foreign key(country_id) references countries(id),
    constraint fk_hotel foreign key(city_id) references cities(id)
);

CREATE TABLE IF NOT EXISTS hotels (
    id bigserial PRIMARY KEY,
    name varchar(300) NOT NULL,
    coastline int,
    stars_amount int,
    address_id bigint NOT NULL,
    constraint fk_address foreign key(address_id) references addresses(id)
);

CREATE TABLE IF NOT EXISTS tours (
    id bigint PRIMARY KEY,
    name varchar(255),
    description varchar(1024),
    country_id bigint,
    city_id bigint,
    type varchar(30),
    catering_type varchar(30),
    hotel_id bigint,
    arrival_time timestamp without time zone,
    departure_time timestamp without time zone,
    place_amount int,
    date_amount int,
    price money,
    constraint fk_hotel foreign key(hotel_id) references hotels(id),
    constraint fk_country foreign key(country_id) references countries(id),
    constraint fk_city foreign key(city_id) references cities(id)
);


CREATE TABLE IF NOT EXISTS user_tours (
    user_id bigint,
    tour_id bigint,
    order_time timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status varchar(50) NOT NULL DEFAULT 'ORDERED',
    client_amount int NOT NULL,
    constraint pk_user_tour primary key(user_id, tour_id),
    constraint fk_user foreign key(user_id) references users(id),
    constraint fk_tour foreign key(tour_id) references tours(id)
);

--liquibase formatted sql
--changeset lisov:2

INSERT INTO users (id, name, surname, email, password, role)
VALUES (1, 'Mark', 'Ivanov', 'mark.ivanov@gmail.com', '$2a$10$wnXXYMX2IUTMtC7ajTIeM.dMLEZcpMjorqfdvAFhdOeYiJW3WXewG',
        'CLIENT'),
       (2, 'Ivan', 'Petrov', 'ivan.petrov@gmail.com', '$2a$10$wnXXYMX2IUTMtC7ajTIeM.dMLEZcpMjorqfdvAFhdOeYiJW3WXewG',
        'EMPLOYEE');

INSERT INTO passports (user_id, serial_number, identity_number)
VALUES (1, 'AB1234567', '1234567890'),
       (2, 'AB1234568', '1234567891');

INSERT INTO addresses (id, country, city, street, house_number)
VALUES (1, 'Belarus', 'Minsk', 'Mogilevskaya', 1),
       (2, 'Egypt', 'Cairo', 'Tahrir st', 12);

INSERT INTO hotels (id, name, coastline, stars_amount, address_id)
VALUES (1, 'Hotel Minsk', null, 5, 1),
       (2, 'Hotel Cairo', 2, 3, 2);

INSERT INTO tours (id, name, description, country, city, type, catering_type, hotel_id, arrival_time, departure_time,
                   place_amount, price, rating, day_duration, latitude, longitude)
VALUES (1, 'Inspiring Minsk', 'Tour through the most popular Minsk landmarks', 'Belarus', 'Minsk', 'CULTURE',
        'ALL_INCLUSIVE', 1, '2022-11-05 05:00:00', '2022-11-08 10:10:00', 10, 100, 4.5, 3, 53.9, 27.5667),
       (2, 'Cairo tour', 'Tour through the most popular Cairo landmarks', 'Egypt', 'Cairo', 'CULTURE', 'ALL_INCLUSIVE',
        2, '2022-11-05 15:25:00', '2022-11-08 23:00:00', 10, 130, 4.2, 3, 30.0444, 31.2357);

INSERT INTO tickets (id, user_id, tour_id, order_time, status, client_amount)
VALUES (1, 1, 1, '2021-11-04 05:00:00', 'ORDERED', 1),
       (2, 1, 2, '2021-11-02 05:00:00', 'PAID', 2);
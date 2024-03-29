--liquibase formatted sql
--changeset lisov:3

INSERT INTO addresses (id, country, city, street, house_number)
VALUES (1, 'Belarus', 'Minsk', 'Mogilevskaya', 1),
       (2, 'Egypt', 'Cairo', 'Tahrir st', 12),
       (3, 'Belarus', 'Grodno', 'Kalinina', 2),
       (4, 'France', 'Paris', 'Freedom avenue', 178);

INSERT INTO hotels (id, name, coastline, stars_amount, address_id)
VALUES (1, 'Hotel Minsk', null, 5, 1),
       (2, 'Hotel Cairo', 2, 3, 2),
       (3, 'Hotel Grodno', null, 4, 3),
       (4, 'Hotel Paris', null, 5, 4);

INSERT INTO tours (id, name, description, country, city, type, catering_type, hotel_id, arrival_time, departure_time,
                   place_amount, price, rating, day_duration, latitude, longitude, is_draft)
VALUES (1, 'Inspiring Minsk', 'Tour through the most popular Minsk landmarks', 'Belarus', 'Minsk', 'CULTURE',
        'ALL_INCLUSIVE', 1, '2023-11-08 05:00:00', '2023-11-11 10:10:00', 10, 100, 4.5, 3, 53.9, 27.5667, false),
       (2, 'Cairo tour', 'Tour through the most popular Cairo landmarks', 'Egypt', 'Cairo', 'CULTURE', 'ALL_INCLUSIVE',
        2, '2023-11-05 15:25:00', '2023-11-08 23:00:00', 25, 130, 4.2, 3, 30.0444, 31.2357, false),
       (3, 'Grodno tour', 'Tour through the most popular Grodno landmarks', 'Belarus', 'Grodno', 'CULTURE',
        'ALL_INCLUSIVE', 3, '2023-11-24 05:00:00', '2023-11-25 10:10:00', 50, 60, 4.9, 1, 53.6667, 23.8333, false),
       (4, 'Paris tour', 'Tour through the most popular Paris landmarks', 'France', 'Paris', 'CULTURE', 'ALL_INCLUSIVE',
        4, '2022-12-28 15:25:00', '2022-01-03 23:00:00', 35, 450, 3.7, 7, 48.8566, 2.3522, false);

INSERT INTO tickets (id, user_id, tour_id, order_time, status, clients_amount)
VALUES (1, '641ac0ef33728c2416b8c367', 1, '2021-11-04 05:00:00', 'ORDERED', 1),
       (2, '641ac0ef33728c2416b8c367', 2, '2021-11-02 05:00:00', 'CONFIRMED',
        2),
       (3, '641ac3efc26fb5145313680f', 3, '2021-11-03 05:00:00', 'ORDERED', 3),
       (4, '641ac456c26fb51453136810', 4, '2021-11-04 05:40:00', 'ORDERED', 3);
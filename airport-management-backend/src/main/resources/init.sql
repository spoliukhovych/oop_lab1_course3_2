CREATE TABLE IF NOT EXISTS crew_members(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    position VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS flights(
    id BIGSERIAL PRIMARY KEY,
    departure_from VARCHAR(255) NOT NULL,
    destination VARCHAR(255) NOT NULL,
    departure_time TIMESTAMP(6) NOT NULL,
    arrival_time TIMESTAMP(6) NOT NULL
);

CREATE TABLE IF NOT EXISTS crew_members_flights(
    id BIGSERIAL PRIMARY KEY,
    fk_crew_member_id BIGINT REFERENCES crew_members(id) ON DELETE CASCADE,
    fk_flight_id BIGINT REFERENCES flights(id) ON DELETE CASCADE
)
INSERT INTO crew_members (id, name, position, surname) VALUES (1, 'Roman', 'PILOT', 'Vasin');
INSERT INTO crew_members (id, name, position, surname) VALUES (2, 'Ivan', 'PILOT', 'Prigoda');
INSERT INTO crew_members (id, name, position, surname) VALUES (3, 'Stepan', 'OPERATOR', 'Ilchuk');
INSERT INTO crew_members (id, name, position, surname) VALUES (4, 'Viktor', 'NAVIGATOR', 'Muzyka');

INSERT INTO flights (id, departure_time, departure_from, arrival_time, destination)
VALUES (1, '2023-05-23 23:53:00' , 'Kyiv', '2024-05-24 16:09:00', 'Krakow');

INSERT INTO flights (id, departure_time, departure_from, arrival_time, destination)
VALUES (2, '2023-05-24 06:41:00' , 'Frankurt', '2024-05-24 20:59:00', 'New York');

INSERT INTO flights (id, departure_time, departure_from, arrival_time, destination)
VALUES (3, '2023-04-12 15:12:00' , 'Ankara', '2024-04-13 00:49:00', 'Berlin');

INSERT INTO crew_members_flights (fk_crew_member_id, fk_flight_id) VALUES (1, 1);
INSERT INTO crew_members_flights (fk_crew_member_id, fk_flight_id) VALUES (3, 1);
INSERT INTO crew_members_flights (fk_crew_member_id, fk_flight_id) VALUES (4, 1);

INSERT INTO crew_members_flights (fk_crew_member_id, fk_flight_id) VALUES (2, 2);
INSERT INTO crew_members_flights (fk_crew_member_id, fk_flight_id) VALUES (3, 2);

INSERT INTO crew_members_flights (fk_crew_member_id, fk_flight_id) VALUES (1, 3);
INSERT INTO crew_members_flights (fk_crew_member_id, fk_flight_id) VALUES (4, 3);
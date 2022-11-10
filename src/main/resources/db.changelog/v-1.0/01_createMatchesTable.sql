--liquibase formatted sql
--changeset Ulyanov:createMatchesTable
CREATE TABLE matches (
                         id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                         my_hero VARCHAR(30) NOT NULL,
                         opponent_hero VARCHAR(30) NOT NULL,
                         result VARCHAR(4) NOT NULL,
                         played_on DATE
                     );
--rollback drop table matches;
DELETE FROM matches;
ALTER TABLE matches ALTER column id RESTART;

DELETE FROM players;
INSERT INTO players(polygon_address, nickname, joined_on)
VALUES ('0xfc79a6fdea5da8c0734378914bb453fdbe4c995d', 'feedmeburgers', '2021-11-20');
INSERT INTO players(polygon_address, nickname, joined_on)
VALUES ('0x66e3f0f89d1882ae8cd323cb42ed1825197324be', 'Puza2010', '2021-08-07');

INSERT INTO matches(my_hero, opponent_hero, result, played_on, opponent_address)
VALUES ('ADA', 'SAMYA', 'WIN', '2022-10-26', null);
INSERT INTO matches(my_hero, opponent_hero, result, played_on, opponent_address)
VALUES ('MIRA', 'BOURAN', 'LOSE', '2022-10-20', '0xfc79a6fdea5da8c0734378914bb453fdbe4c995d');
INSERT INTO matches(my_hero, opponent_hero, result, played_on, opponent_address)
VALUES ('LOTUS', 'TITUS', 'DRAW', '2022-10-24', '0x66e3f0f89d1882ae8cd323cb42ed1825197324be');

DELETE FROM players WHERE polygon_address = '0x66e3f0f89d1882ae8cd323cb42ed1825197324be';
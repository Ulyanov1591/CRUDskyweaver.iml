DELETE FROM matches;
ALTER TABLE matches ALTER column id RESTART;

INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('ADA', 'SAMYA', 'WIN', '2022-10-26');
INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('MIRA', 'BOURAN', 'LOSE', '2022-10-20');
INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('LOTUS', 'TITUS', 'DRAW', '2022-10-24');
INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('AXEL', 'LOTUS', 'WIN', '2022-10-27');
INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('SAMYA', 'BOURAN', 'LOSE', '2022-10-23');
INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('SAMYA', 'BOURAN', 'WIN', '2022-10-29');
INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('SITTY', 'SAMYA', 'LOSE', '2022-10-26');
INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('ZOEY', 'SITTY', 'WIN', '2022-10-20');
INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('ZOEY', 'ADA', 'WIN', '2022-10-20');
INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('ZOEY', 'BANJO', 'WIN', '2022-10-19');
INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('SAMYA', 'ADA', 'LOSE', '2022-10-23');
INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('SAMYA', 'MIRA', 'LOSE', '2022-10-28');
INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('ADA', 'SAMYA', 'WIN', '2022-10-27');
INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('MIRA', 'BOURAN', 'LOSE', '2022-10-15');
INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('LOTUS', 'TITUS', 'DRAW', '2022-10-24');
INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('AXEL', 'HORIK', 'WIN', '2022-10-27');
INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('IRIS', 'BOURAN', 'LOSE', '2022-10-24');
INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('SAMYA', 'IRIS', 'WIN', '2022-10-27');
INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('FOX', 'AXEL', 'WIN', '2022-10-26');
INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('AXEL', 'SITTY', 'LOSE', '2022-10-20');

-- 21st match
INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('SITTY', 'IRIS', 'DRAW', '2022-10-24');
INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('BANJO', 'FOX', 'WIN', '2022-10-27');
INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('SAMYA', 'SAMYA', 'LOSE', '2022-10-23');
INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('SAMYA', 'SAMYA', 'WIN', '2022-10-29');
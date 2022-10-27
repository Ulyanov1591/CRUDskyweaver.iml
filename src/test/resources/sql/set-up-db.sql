DELETE FROM matches;
ALTER TABLE matches ALTER column id RESTART;

INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('ADA', 'SAMYA', 'WIN', '2022-10-20');
INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('MIRA', 'BOURAN', 'LOSE', '2022-10-22');
INSERT INTO matches(my_hero, opponent_hero, result, played_on)
VALUES ('LOTUS', 'TITUS', 'DRAW', '2022-10-24');
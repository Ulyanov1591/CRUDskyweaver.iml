DELETE FROM users;

INSERT INTO users(id, nickname, email, additional_info)
VALUES('6d55418a-80b4-495f-824c-36eb3639af13',
       'Maxim_Godov',
       'godovmaxim@gmail.com',
       '{"dateOfBirth":"1998-05-29", "previousNicknames":["Sup2ch", "GitGud"], "countryOfResidence":"Russia"}'::jsonb
       );
INSERT INTO S_USER (USER_NAME, USER_PASSWORD) VALUES ('Armin', '123');
INSERT INTO S_USER (USER_NAME, USER_PASSWORD) VALUES ('Abdiii', '999');
INSERT INTO S_EXERCISE(EXERCISE_NAME) VALUES ('Bankdrucken');
INSERT INTO S_TEMPLATE(USER_ID, TEMPLATE_NAME) VALUES (1, 'Push');
INSERT INTO S_EXERCISEPOSITION(EXERCISE_NR, TEMPLATE_NR) VALUES (1, 1);
INSERT INTO S_SET(EXERCISE_NR, SET_WEIGHT, SET_REPS) VALUES (1, 60, 8);
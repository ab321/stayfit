CREATE TABLE S_USER(
    USER_ID                     INT NOT NULL CONSTRAINT USER_PK PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    USER_NAME                   VARCHAR(50),
    USER_PASSWORD               VARCHAR(50)
);

CREATE TABLE S_EXERCISE(
    EXERCISE_NR                 INT NOT NULL CONSTRAINT EXERCISE_PK PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    EXERCISE_NAME               VARCHAR(50)
);

CREATE TABLE S_TEMPLATE(
                           TEMPLATE_NR                 INT NOT NULL CONSTRAINT TEMPLATE_PK PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                           USER_ID                     INT CONSTRAINT TEMPLATE_FK REFERENCES S_USER(USER_ID) ON DELETE CASCADE,
                           TEMPLATE_NAME               VARCHAR(50)
);

CREATE TABLE S_SET(
    SET_NR                      INT NOT NULL CONSTRAINT SET_PK PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    EXERCISE_NR                 INT CONSTRAINT SET_FK REFERENCES S_EXERCISE(EXERCISE_NR) ON DELETE CASCADE,
    SET_USER_ID                 INT CONSTRAINT SET_USER_FK REFERENCES S_USER(USER_ID) ON DELETE CASCADE,
    SET_TEMPLATE_NR             INT CONSTRAINT SET_TEMPLATE_FK REFERENCES S_TEMPLATE(TEMPLATE_NR) ON DELETE CASCADE,
    SET_WEIGHT                  DOUBLE,
    SET_DATE                    DATE,
    SET_REPS                    INT
);

CREATE TABLE S_EXERCISEPOSITION(
    EXERCISEPOSITION_NR         INT NOT NULL CONSTRAINT EXERCISEPOSITION_PK PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    EXERCISE_NR                 INT CONSTRAINT EXERCISEPOSITION_EX_FK REFERENCES S_EXERCISE(EXERCISE_NR) ON DELETE CASCADE,
    TEMPLATE_NR                 INT CONSTRAINT EXERCISEPOSITION_TEMP_FK REFERENCES S_TEMPLATE(TEMPLATE_NR) ON DELETE CASCADE
);

CREATE TABLE USERS(
    id SERIAL CONSTRAINT userPK PRIMARY KEY,
    mail varchar(255) NOT NULL,
    password text NOT NULL,
    role varchar(50) NOT NULL,
    CONSTRAINT role_type_check CHECK(mail='ADMIN' OR mail='USER'),
    CONSTRAINT user_mail UNIQUE(mail)
);

CREATE TABLE URLS (
    id SERIAL PRIMARY KEY,
    shortURL varchar(6) NOT NULL,
    fullURl varchar(255) NOT NULL,
    iterations SERIAL NOT NULL,
    userID SERIAL REFERENCES USERS (id) ON DELETE SET NULL
);

CREATE SEQUENCE my_seq START WITH 0;
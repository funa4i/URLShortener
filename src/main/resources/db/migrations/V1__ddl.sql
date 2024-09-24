CREATE TABLE USERS(
    id BIGSERIAL CONSTRAINT userPK PRIMARY KEY,
    mail varchar(255) NOT NULL,
    password text NOT NULL,
    role varchar(50) NOT NULL,
    CONSTRAINT role_type_check CHECK(mail='ADMIN' OR mail='USER'),
    CONSTRAINT user_mail UNIQUE(mail)
);

CREATE TABLE URLS (
    id BIGSERIAL PRIMARY KEY,
    shortURL CHAR(6) NOT NULL,
    fullURl varchar(255) NOT NULL,
    iterations SERIAL NOT NULL,
    userID BIGSERIAL REFERENCES USERS (id) ON DELETE SET NULL
);





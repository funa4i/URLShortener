CREATE TABLE USERS(
    id BIGSERIAL CONSTRAINT userPK PRIMARY KEY,
    mail varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    role varchar(50) NOT NULL,
    maxlinkavail serial  NOT NULL,
    createlinksleft serial NOT NULL,
    lastcreate TIMESTAMP NOT NULL,
    CONSTRAINT role_type_check CHECK(role='ADMIN' OR role='USER' OR role='GOD' OR role='BAN'),
    CONSTRAINT user_mail UNIQUE(mail)
);

CREATE TABLE URLS (
    id BIGSERIAL PRIMARY KEY,
    shortUrl varchar(7) NOT NULL,
    fullUrl varchar(255) NOT NULL,
    iterations SERIAL NOT NULL,
    validUntil TIMESTAMP NOT NULL,
    USERMAIL varchar(255) CONSTRAINT usermail_mail REFERENCES USERS (mail) ON DELETE SET NULL
);
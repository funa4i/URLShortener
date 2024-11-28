CREATE TABLE users(
    id BIGSERIAL CONSTRAINT userPK PRIMARY KEY,
    mail varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    role varchar(50) NOT NULL,
    maxlinkavail serial  NOT NULL,
    createlinksleft serial NOT NULL,
    lastcreate TIMESTAMP NOT NULL
);

ALTER TABLE users
ADD CONSTRAINT mailUQ UNIQUE(mail);

CREATE TABLE url (
    id BIGSERIAL PRIMARY KEY,
    shortUrl varchar(7) NOT NULL,
    fullUrl varchar(255) NOT NULL,
    iterations SERIAL NOT NULL,
    validUntil TIMESTAMP NOT NULL,
    userid BIGINT
);

ALTER TABLE url
ADD CONSTRAINT url_userFK FOREIGN KEY (userid) REFERENCES users (id) ON DELETE SET NULL;
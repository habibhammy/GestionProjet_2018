CREATE TABLE users
(
    id bigint NOT NULL,
    username VARCHAR(40) NOT NULL,
    passwords VARCHAR(255) NOT NULL,
    email VARCHAR(255) ,
    firstname VARCHAR(255) ,
    lastname VARCHAR(255) ,
    country VARCHAR(255) ,
    CONSTRAINT firstkey PRIMARY KEY (id)
);

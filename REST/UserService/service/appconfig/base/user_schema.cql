DROP KEYSPACE IF EXISTS forxy;
CREATE KEYSPACE forxy WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 1};

USE forxy;

CREATE TABLE user (
    tokenKey TEXT,
    login TEXT,
    password BLOB,
    first_name TEXT,
    last_name TEXT,
    gender TEXT,
    birth_date TIMESTAMP,
    create_date timestamp,
    favorites SET<TEXT>,
    PRIMARY KEY (tokenKey)
);
//WITH CLUSTERING ORDER BY (create_date DESC);

CREATE INDEX create_date_idx ON user(create_date);
CREATE INDEX login_idx ON user(login);
CREATE INDEX password_idx ON user(password);
CREATE INDEX first_name_idx ON user(first_name);
CREATE INDEX last_name_idx ON user(last_name);
CREATE INDEX gender_idx ON user(gender);
CREATE INDEX birth_date_idx ON user(birth_date);
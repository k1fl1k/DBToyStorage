DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS Cart;
DROP TABLE IF EXISTS Client;
DROP TABLE IF EXISTS Sections;
DROP TABLE IF EXISTS Toy;
DROP TABLE IF EXISTS Manufacture;
DROP TABLE IF EXISTS Category;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE Category (
                          id              UUID            PRIMARY KEY DEFAULT uuid_generate_v4(),
                          name            VARCHAR(255)    NOT NULL,
                          description     TEXT            NOT NULL
);

CREATE TABLE Manufacture (
                             id              UUID            PRIMARY KEY DEFAULT uuid_generate_v4(),
                             name            VARCHAR(255)    NOT NULL,
                             description     TEXT            NOT NULL
);

CREATE TABLE Toy (
                     id              UUID            PRIMARY KEY DEFAULT uuid_generate_v4(),
                     name            VARCHAR(255)    NOT NULL,
                     description     TEXT            NOT NULL,
                     price           NUMERIC(10, 2)  NOT NULL,
                     category_id     UUID,
                     manufacture_id  UUID,
                     FOREIGN KEY (category_id) REFERENCES Category(id),
                     FOREIGN KEY (manufacture_id) REFERENCES Manufacture(id)
);

CREATE TABLE users (
                        id              UUID            PRIMARY KEY DEFAULT uuid_generate_v4(),
                        login           VARCHAR(32)     NOT NULL UNIQUE,
                        password        VARCHAR(255)    NOT NULL,
                        role            VARCHAR(20)     CHECK (role IN ('admin', 'moder', 'client')) NOT NULL,
                        name            VARCHAR(64)     NOT NULL
);

CREATE TABLE Sections (
                          id              UUID            PRIMARY KEY DEFAULT uuid_generate_v4(),
                          name            VARCHAR(255)    NOT NULL,
                          category_id     UUID            REFERENCES Category(id)
);

CREATE TABLE Client (
                        id              UUID            PRIMARY KEY DEFAULT uuid_generate_v4(),
                        name            VARCHAR(64)     NOT NULL,
                        phone           VARCHAR(64)     NOT NULL UNIQUE,
                        address         TEXT            NOT NULL UNIQUE
);

CREATE TABLE Cart (
                      id              UUID            PRIMARY KEY DEFAULT uuid_generate_v4(),
                      client_id       UUID            NOT NULL,  -- Make client_id NOT NULL
                      toy_id          UUID            REFERENCES Toy(id),
                      FOREIGN KEY (client_id) REFERENCES Client(id) -- Add foreign key referencing Client
);


CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username varchar(50) NOT NULL,
    password_hash varchar(68) NOT NULL,
    active boolean NOT NULL
);

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    "role" varchar(50) NOT NULL
);

CREATE TABLE user_roles (
    user_id integer NOT NULL,
    role_id integer NOT NULL,
    CONSTRAINT user_id_pk PRIMARY KEY (user_id, role_id),
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT role_id_fk FOREIGN KEY (role_id) REFERENCES roles (id)
);
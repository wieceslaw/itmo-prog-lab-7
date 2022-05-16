CREATE TABLE IF NOT EXISTS users (
    login VARCHAR PRIMARY KEY,
    password bytea NULL
);

CREATE TABLE IF NOT EXISTS organizations (
    id BIGINT PRIMARY KEY,
    name VARCHAR NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    annual_turnover INTEGER NULL,
    employees_count INTEGER NOT NULL,
    type VARCHAR NULL,
    user_login VARCHAR REFERENCES users (login)
    CHECK (annual_turnover > 0 AND employees_count > 0)
);

CREATE TABLE IF NOT EXISTS cords (
    id SERIAL PRIMARY KEY,
    x_cord DOUBLE PRECISION NOT NULL,
    y_cord REAL NOT NULL,
    org_id INTEGER REFERENCES organizations (id) ON DELETE CASCADE
    CHECK (x_cord <= 76)
);

CREATE TABLE IF NOT EXISTS address(
    id SERIAL PRIMARY KEY,
    street VARCHAR NULL,
    zip_code VARCHAR NULL,
    org_id INTEGER REFERENCES organizations (id) ON DELETE CASCADE
);

CREATE SEQUENCE IF NOT EXISTS organization_id_sequence
    START WITH 1
    INCREMENT BY 1
    CYCLE;
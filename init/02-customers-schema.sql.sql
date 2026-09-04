-- DDL

\c customers_db

CREATE TABLE public."person" (
    id          SERIAL PRIMARY KEY,
    identification  VARCHAR(15)  UNIQUE NOT NULL,
    first_name  VARCHAR(50)  NOT NULL,
    last_name   VARCHAR(50)  NOT NULL,
    gender      VARCHAR(1)   NOT NULL,
    birthdate   DATE         NOT NULL,
    address     VARCHAR(100) NOT NULL,
    phone_number VARCHAR(12) NOT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP    NULL,
    created_by  VARCHAR(15)  NOT NULL,
    updated_by  VARCHAR(15)
);

CREATE TABLE public."customer" (
    id          SERIAL PRIMARY KEY,
    password    VARCHAR(255) NOT NULL,
    person_id   INT          NOT NULL,
    status      BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP    NULL,
    created_by  VARCHAR(15)  NOT NULL,
    updated_by  VARCHAR(15),
    FOREIGN KEY (person_id) REFERENCES public.person(id)
);

CREATE TABLE public.account_projection (
    account_id      INT          PRIMARY KEY,
    customer_id     INT          NOT NULL,
    account_number  VARCHAR(10)  NOT NULL,
    status          BOOLEAN      NOT NULL DEFAULT TRUE,
    last_event_id   UUID         NULL,
    last_event_at   TIMESTAMP    NULL,
    updated_at      TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_account_projection_customer_status
    ON public.account_projection (customer_id, status);

-- AUDIT

CREATE SCHEMA audit;

CREATE TABLE audit."person_customer" (
    audit_id    SERIAL PRIMARY KEY,
    id          INT,
    identification  VARCHAR(15),
    first_name  VARCHAR(50),
    last_name   VARCHAR(50),
    password    VARCHAR(255),
    gender      VARCHAR(1),
    birthdate   DATE,
    address     VARCHAR(100),
    phone_number VARCHAR(12),
    status      BOOLEAN,
    updated_at  TIMESTAMP NULL,
    updated_by  VARCHAR(15)
);

-- DML

INSERT INTO public."person"
    (identification, first_name, last_name, gender, birthdate, address, phone_number, created_at, created_by, updated_by)
VALUES
    ('1234567890', 'Jose',      'Lema',     'M', '1990-01-01', 'Otavalo sn y principal', '098254785', NOW(), 'admin', NULL),
    ('2345678901', 'Marianela', 'Montalvo', 'F', '1992-02-02', 'Amazonas y NNUU',        '097548965', NOW(), 'admin', NULL),
    ('3456789012', 'Juan',      'Osorio',   'M', '1994-03-03', '13 junio y Equinoccial',  '098874587', NOW(), 'admin', NULL);

INSERT INTO public."customer"
    (password, person_id, status, created_at, created_by, updated_by)
VALUES
    ('$2a$10$7FJcaRgWMh66J0IZbdzMqeIIr3itkiR.Qi3Qdw3mencNJZOIqoxYq', 1, TRUE, NOW(), 'admin', NULL),
    ('$2a$10$7FJcaRgWMh66J0IZbdzMqeIIr3itkiR.Qi3Qdw3mencNJZOIqoxYq', 2, TRUE, NOW(), 'admin', NULL),
    ('$2a$10$7FJcaRgWMh66J0IZbdzMqeIIr3itkiR.Qi3Qdw3mencNJZOIqoxYq', 3, TRUE, NOW(), 'admin', NULL);

INSERT INTO public.account_projection
    (account_id, customer_id, account_number, status)
VALUES
    (1, 1, '478758', TRUE),
    (2, 2, '225487', TRUE),
    (3, 3, '495878', TRUE),
    (4, 2, '496825', TRUE);
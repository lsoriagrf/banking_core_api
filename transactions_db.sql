CREATE DATABASE transactions_db;

CREATE TABLE public.account_type (
    id          SERIAL PRIMARY KEY,
    description VARCHAR(50) NOT NULL,
    created_at  TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP   NULL,
    created_by  VARCHAR(15) NOT NULL,
    updated_by  VARCHAR(15)
);

CREATE TABLE public.account (
    id              SERIAL PRIMARY KEY,
    account_number  VARCHAR(10)    UNIQUE NOT NULL,
    customer_id     INT            NOT NULL,
    account_type_id INT            NOT NULL,
    balance         NUMERIC(12,2)  NOT NULL DEFAULT 0,
    status          BOOLEAN        NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP      NULL,
    created_by      VARCHAR(15)    NOT NULL,
    updated_by      VARCHAR(15),
    FOREIGN KEY (account_type_id) REFERENCES public.account_type(id)
);

CREATE TABLE public.transaction_type (
    id          SERIAL PRIMARY KEY,
    description VARCHAR(50) NOT NULL,
    created_at  TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP   NULL,
    created_by  VARCHAR(15) NOT NULL,
    updated_by  VARCHAR(15)
);

CREATE TABLE public.movements (
    id               SERIAL PRIMARY KEY,
    transaction_code UUID          NOT NULL DEFAULT gen_random_uuid(),
    account_id       INT           NOT NULL,
    transaction_type_id INT        NOT NULL,
    amount           NUMERIC(12,2) NOT NULL,
    balance          NUMERIC(12,2) NOT NULL,
    note             VARCHAR(50),
    created_at       TIMESTAMP     NOT NULL DEFAULT NOW(),
    created_by       VARCHAR(15)   NOT NULL,
    FOREIGN KEY (account_id)           REFERENCES public.account(id),
    FOREIGN KEY (transaction_type_id)  REFERENCES public.transaction_type(id)
);

-- AUDIT

CREATE SCHEMA audit;

CREATE TABLE audit.account (
    audit_id        SERIAL PRIMARY KEY,
    id              INT,
    account_number  VARCHAR(20),
    customer_id     INT,
    account_type_id INT,
    balance         NUMERIC(12,2),
    status          BOOLEAN,
    updated_at      TIMESTAMP NULL,
    updated_by      VARCHAR(15)
);

-- DML

INSERT INTO public.account_type (description, created_by)
VALUES
    ('Corriente', 'admin'),
    ('Ahorros',   'admin');

INSERT INTO public.transaction_type (description, created_by)
VALUES
    ('Deposito', 'admin'),
    ('Retiro',   'admin');

INSERT INTO public.account (account_number, customer_id, account_type_id, balance, status, created_at, created_by)
VALUES
    ('1234567890', 1, 2, 2000, TRUE, NOW(), 'admin'),
    ('2345678901', 2, 1,  100, TRUE, NOW(), 'admin'),
    ('3456789012', 3, 2,    0, TRUE, NOW(), 'admin'),
    ('4567890123', 2, 2,  540, TRUE, NOW(), 'admin');
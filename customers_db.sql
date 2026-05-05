-- DDL

CREATE DATABASE customers_db;

CREATE TABLE public."customer" (
    id          SERIAL PRIMARY KEY,
    identification  VARCHAR(15)  UNIQUE NOT NULL,
    first_name  VARCHAR(50)  NOT NULL,
    last_name   VARCHAR(50)  NOT NULL,
    password    VARCHAR(255) NOT NULL,
    gender      VARCHAR(1)   NOT NULL,
    birthdate   DATE         NOT NULL,
    address     VARCHAR(100) NOT NULL,
    phone_number VARCHAR(12) NOT NULL,
    status      BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP    NULL,
    created_by  VARCHAR(15)  NOT NULL,
    updated_by  VARCHAR(15)
);

-- AUDIT

CREATE SCHEMA audit;

CREATE TABLE audit."customer" (
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

INSERT INTO public."customer"
    (identification, first_name, last_name, password, gender, birthdate, address, phone_number, status, created_at, created_by, updated_by)
VALUES
    ('1234567890', 'Jose',      'Lema',     '$2a$10$7FJcaRgWMh66J0IZbdzMqeIIr3itkiR.Qi3Qdw3mencNJZOIqoxYq', 'M', '1990-01-01', 'Otavalo sn y principal', '098254785', TRUE, NOW(), 'admin', NULL),
    ('2345678901', 'Marianela', 'Montalvo', '$2a$10$7FJcaRgWMh66J0IZbdzMqeIIr3itkiR.Qi3Qdw3mencNJZOIqoxYq', 'F', '1992-02-02', 'Amazonas y NNUU',        '097548965', TRUE, NOW(), 'admin', NULL),
    ('3456789012', 'Juan',      'Osorio',   '$2a$10$7FJcaRgWMh66J0IZbdzMqeIIr3itkiR.Qi3Qdw3mencNJZOIqoxYq', 'M', '1994-03-03', '13 junio y Equinoccial',  '098874587', TRUE, NOW(), 'admin', NULL);
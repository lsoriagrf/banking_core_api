DROP TABLE IF EXISTS movements;
DROP TABLE IF EXISTS account;

CREATE TABLE account (
    id              INTEGER       PRIMARY KEY,
    account_number  VARCHAR(10)   NOT NULL UNIQUE,
    customer_id     INTEGER       NOT NULL,
    account_type_id INTEGER       NOT NULL,
    balance         NUMERIC(14,2) NOT NULL,
    status          BOOLEAN       NOT NULL,
    created_at      TIMESTAMP,
    updated_at      TIMESTAMP,
    created_by      VARCHAR(50),
    updated_by      VARCHAR(50)
);

CREATE TABLE movements (
    id                  SERIAL        PRIMARY KEY,
    account_id          INTEGER       NOT NULL REFERENCES account(id),
    transaction_type_id INTEGER       NOT NULL,
    transaction_code    UUID          NOT NULL,
    amount              NUMERIC(14,2) NOT NULL,
    balance             NUMERIC(14,2) NOT NULL,
    note                VARCHAR(100),
    created_at          TIMESTAMP     NOT NULL,
    created_by          VARCHAR(50)   NOT NULL
);
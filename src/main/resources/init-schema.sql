DROP SCHEMA IF EXISTS "order" CASCADE;

CREATE SCHEMA "order";

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TYPE IF EXISTS product_category;
CREATE TYPE product_category AS ENUM ('LANCHE','ACOMPANHAMENTO','BEBIDA');

DROP TABLE IF EXISTS "order".products CASCADE;

CREATE TABLE "order".products
(
    id uuid NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    price numeric(10,2) NOT NULL,
    description character varying COLLATE pg_catalog."default" NOT NULL,
    category product_category,
    CONSTRAINT products_pkey PRIMARY KEY (id)
);



DROP TYPE IF EXISTS order_status;
CREATE TYPE order_status AS ENUM ('CRIADO', 'AGUARDANDO_PAGAMENTO', 'RECEBIDO', 'EM_PREPARACAO', 'PRONTO', 'FINALIZADO');

DROP TABLE IF EXISTS "order".orders CASCADE;

CREATE TABLE "order".orders
(
    id uuid NOT NULL,
    price numeric(10,2) NOT NULL,
    order_status order_status NOT NULL,
    CONSTRAINT orders_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "order".order_items CASCADE;

CREATE TABLE "order".order_items
(
    id bigint NOT NULL,
    order_id uuid NOT NULL,
    product_id uuid NOT NULL,
    quantity integer NOT NULL,
    sub_total numeric(10,2) NOT NULL,
    CONSTRAINT order_items_pkey PRIMARY KEY (id, order_id)
);

ALTER TABLE "order".order_items
    ADD CONSTRAINT "FK_ORDER_ID" FOREIGN KEY (order_id)
    REFERENCES "order".orders (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE
    NOT VALID;

ALTER TABLE "order".order_items
    ADD CONSTRAINT "FK_PRODUCT_ID" FOREIGN KEY (product_id)
    REFERENCES "order".products (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE
    NOT VALID;


DROP TABLE IF EXISTS "order".customers CASCADE;

CREATE TABLE "order".customers
(
    id uuid NOT NULL,
    cpf character varying COLLATE pg_catalog."default" NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    email character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT customers_pkey PRIMARY KEY (id)
);




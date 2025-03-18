CREATE TABLE products
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255)     NOT NULL,
    description TEXT,
    price       DOUBLE PRECISION NOT NULL,
    quantity    INT              NOT NULL,
    image       bytea
);

INSERT INTO public.products (id, name, description, price, quantity, image)
VALUES (
        DEFAULT,
        'orange',
        'я вкусный апельсин',
        30.4,
        30,
        null);

SELECT * FROM products;
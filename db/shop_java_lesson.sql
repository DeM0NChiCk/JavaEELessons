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

CREATE TABLE category (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL
);

CREATE TABLE product_category (
                                  product_id INT NOT NULL,
                                  category_id INT NOT NULL,
                                  PRIMARY KEY (product_id, category_id),
                                  FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
                                  FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE
);

select c.id, c.name FROM category c join product_category pc on c.id = pc.category_id where pc.product_id = ?;

-- Пользователи
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(100) NOT NULL,
                       email VARCHAR(100) UNIQUE,
                       role VARCHAR(20) DEFAULT 'USER'
);

-- Авторы
CREATE TABLE authors (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         bio TEXT
);

-- Жанры
CREATE TABLE genres (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(50) NOT NULL UNIQUE
);

-- Книги
CREATE TABLE books (
                       id SERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       author_id INT REFERENCES authors(id) ON DELETE CASCADE,
                       price DECIMAL(10, 2),
                       description TEXT
);

-- Связь Many-to-Many: Книги и Жанры
CREATE TABLE book_genres (
                        book_id INT REFERENCES books(id) ON DELETE CASCADE,
                        genre_id INT REFERENCES genres(id) ON DELETE CASCADE,
                        PRIMARY KEY (book_id, genre_id)
);

-- Заказы
CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        user_id INT REFERENCES users(id) ON DELETE CASCADE,
                        order_date TIMESTAMP DEFAULT NOW(),
                        status VARCHAR(20) DEFAULT 'PENDING'
);

-- Элементы заказа (One-to-Many к Order)
CREATE TABLE order_items (
                        id SERIAL PRIMARY KEY,
                        order_id INT REFERENCES orders(id) ON DELETE CASCADE,
                        book_id INT REFERENCES books(id) ON DELETE SET NULL,
                        quantity INT DEFAULT 1,
                        price DECIMAL(10, 2)
);
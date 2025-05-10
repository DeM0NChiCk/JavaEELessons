INSERT INTO authors (name, bio) VALUES
         ('Джоан Роулинг', 'Автор Гарри Поттера'),
         ('Джордж Оруэлл', 'Автор "1984"');

INSERT INTO genres (name) VALUES
         ('Фэнтези'), ('Антиутопия'), ('Роман');

INSERT INTO books (title, author_id, price) VALUES
         ('Гарри Поттер и философский камень', 1, 500),
         ('1984', 2, 450);

INSERT INTO book_genres (book_id, genre_id) VALUES
         (1, 1), (1, 3), (2, 2);
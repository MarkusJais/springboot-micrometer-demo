DROP TABLE IF EXISTS books;

CREATE TABLE books
(
    book_id INTEGER NOT NULL AUTO_INCREMENT,
    title   VARCHAR(100) NOT NULL,
    author  VARCHAR(100) NOT NULL
);
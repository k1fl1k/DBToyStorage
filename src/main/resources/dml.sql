-- Вставка даних
-- Додавання категорій
INSERT INTO category (Name, Description)
VALUES ('Лялька', 'Різні ляльки для дітей'),
       ('Конструктор', 'Набори конструкторів для творчої гри'),
       ('Транспорт', 'Іграшкові транспортні засоби, включаючи автомобілі та вантажівки'),
       ('М`які іграшки', 'Плюшеві та м`які іграшки для дітей'),
       ('Настільні ігри', 'Ігри для гри на столі в компанії');

-- Додавання виробників
INSERT INTO manufacture (Name, Description)
VALUES ('Mattel', 'Лідируючий виробник іграшок'),
       ('LEGO', 'Відомий своїми конструкторськими наборами'),
       ('Hasbro', 'Великий виробник іграшок та ігор'),
       ('Bandai', 'Японський виробник іграшок');

INSERT INTO toy (Name, description, price, category_id, manufacture_id)
VALUES ('Лялька Барбі з аксесуарами', 'Лялька Барбі з різноманітними аксесуарами', 25.99,
        (SELECT id FROM category WHERE name = 'Лялька'),
        (SELECT id FROM manufacture WHERE name = 'Mattel')),
       ('Лялька Барбі Модель', 'Лялька Барбі у модному одязі', 22.99,
        (SELECT id FROM category WHERE name = 'Лялька'),
        (SELECT id FROM manufacture WHERE name = 'Mattel')),
       ('Лялька Барбі Доктор', 'Лялька Барбі у образі лікаря', 27.99,
        (SELECT id FROM category WHERE name = 'Лялька'),
        (SELECT id FROM manufacture WHERE name = 'Mattel')),

       ('Конструктор LEGO Star Wars', 'Конструктор для фанатів Зоряних Війн', 69.99,
        (SELECT id FROM category WHERE name = 'Конструктор'),
        (SELECT id FROM manufacture WHERE name = 'LEGO')),
       ('Конструктор LEGO Ninjago', 'Захоплюючий конструктор з серії Ninjago', 39.99,
        (SELECT id FROM category WHERE name = 'Конструктор'),
        (SELECT id FROM manufacture WHERE name = 'LEGO')),
       ('Конструктор LEGO Friends', 'Конструктор для дітей з серії Friends', 49.99,
        (SELECT id FROM category WHERE name = 'Конструктор'),
        (SELECT id FROM manufacture WHERE name = 'LEGO')),

       ('Трактор Bruder', 'Реалістична модель трактора', 35.99,
        (SELECT id FROM category WHERE name = 'Транспорт'),
        (SELECT id FROM manufacture WHERE name = 'Mattel')),
       ('Автомобіль на радіокеруванні', 'Автомобіль з дистанційним управлінням', 45.99,
        (SELECT id FROM category WHERE name = 'Транспорт'),
        (SELECT id FROM manufacture WHERE name = 'Mattel')),
       ('Машинка Hot Wheels', 'Маленька машинка Hot Wheels', 9.99,
        (SELECT id FROM category WHERE name = 'Транспорт'),
        (SELECT id FROM manufacture WHERE name = 'Mattel')),

       ('Плюшевий зайчик', 'М`яка іграшка зайчик', 18.99,
        (SELECT id FROM category WHERE name = 'М`які іграшки'),
        (SELECT id FROM manufacture WHERE name = 'Hasbro')),
       ('Плюшевий лев', 'М`яка іграшка лев', 20.99,
        (SELECT id FROM category WHERE name = 'М`які іграшки'),
        (SELECT id FROM manufacture WHERE name = 'Hasbro')),
       ('Плюшевий слон', 'М`яка іграшка слон', 22.99,
        (SELECT id FROM category WHERE name = 'М`які іграшки'),
        (SELECT id FROM manufacture WHERE name = 'Hasbro')),

       ('Настільна гра Scrabble', 'Класична настільна гра для створення слів', 19.99,
        (SELECT id FROM category WHERE name = 'Настільні ігри'),
        (SELECT id FROM manufacture WHERE name = 'Hasbro')),
       ('Настільна гра Катан', 'Популярна гра для стратегічного мислення', 29.99,
        (SELECT id FROM category WHERE name = 'Настільні ігри'),
        (SELECT id FROM manufacture WHERE name = 'Hasbro')),
       ('Настільна гра Уно', 'Весела карткова гра для всієї родини', 14.99,
        (SELECT id FROM category WHERE name = 'Настільні ігри'),
        (SELECT id FROM manufacture WHERE name = 'Hasbro')),

       ('Конструктор Bandai Dragon Ball', 'Пластиковий модельний набір для складання персонажів Dragon Ball', 34.99,
        (SELECT id FROM category WHERE name = 'Конструктор'),
        (SELECT id FROM manufacture WHERE name = 'Bandai')),
       ('Конструктор Bandai One Piece', 'Пластиковий модельний набір для складання персонажів One Piece', 39.99,
        (SELECT id FROM category WHERE name = 'Конструктор'),
        (SELECT id FROM manufacture WHERE name = 'Bandai')),
       ('Конструктор Bandai Naruto', 'Пластиковий модельний набір для складання персонажів Naruto', 29.99,
        (SELECT id FROM category WHERE name = 'Конструктор'),
        (SELECT id FROM manufacture WHERE name = 'Bandai'));


-- Додавання користувачів
INSERT INTO users (Name, Login, Password, Role)
VALUES ('Адміністратор', 'admin', 'admin123', 'admin'),
       ('Марія Сидорова', 'manager', 'manager123', 'moder'),
       ('Іван Петров', 'client1', 'client123', 'client');

-- Додавання клієнтів
INSERT INTO client (Name, Address, Phone)
SELECT Name, 'вул. Головна, 1', '+1234567890' FROM users WHERE role = 'client'
UNION ALL
SELECT Name, 'просп. Центральний, 5', '+0987654321' FROM users WHERE role = 'moder';

-- Додавання секцій складу
INSERT INTO sections (Name, category_id)
VALUES ('М`які іграшки', (SELECT id FROM category WHERE name = 'Лялька')),
       ('Конструктори', (SELECT id FROM category WHERE name = 'Конструктор')),
       ('Іграшкові машинки', (SELECT id FROM category WHERE name = 'Транспорт')),
       ('Плюшеві іграшки', (SELECT id FROM category WHERE name = 'М`які іграшки')),
       ('Настільні ігри', (SELECT id FROM category WHERE name = 'Настільні ігри'));

INSERT INTO users (id, login, password, role, name)
VALUES ('018f39f9-1826-7cb9-9775-feee72794e6a', 'mike_wilson1', 'password5', 'client',  'mike.wilson'),
       ('018f39f9-05de-704c-bdc4-9fb5e1432e19', 'emily_brown2', 'password5', 'moder', 'emily.brown123');

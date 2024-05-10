-- Вставка даних
-- Додавання категорій
INSERT INTO category (Name, Description)
VALUES ('Лялька', 'Різні ляльки для дітей'),
       ('Конструктор', 'Набори конструкторів для творчої гри'),
       ('Транспорт', 'Іграшкові транспортні засоби, включаючи автомобілі та вантажівки');

-- Додавання виробників
INSERT INTO manufacture (Name, Description)
VALUES ('Mattel', 'Лідируючий виробник іграшок'),
       ('LEGO', 'Відомий своїми конструкторськими наборами');

INSERT INTO toy (Name, description, price, category_id, manufacture_id)
VALUES ('Лялька Барбі', 'Прекрасна лялька Барбі для дітей', 19.99,
        (SELECT id FROM category WHERE name = 'Лялька'),
        (SELECT id FROM manufacture WHERE name = 'Mattel')),
       ('Конструктор LEGO City Police Station', 'Побудуйте свою власну поліцейську станцію', 59.99,
        (SELECT id FROM category WHERE name = 'Конструктор'),
        (SELECT id FROM manufacture WHERE name = 'LEGO')),
       ('Набір треків Hot Wheels', 'Цікавий трек для гонок', 29.99,
        (SELECT id FROM category WHERE name = 'Транспорт'),
        (SELECT id FROM manufacture WHERE name = 'Mattel'));

-- Додавання користувачів
INSERT INTO users (Name, Login, Password, Role)
VALUES ('Адміністратор', 'admin', 'admin123', 'адміністратор'),
       ('Марія Сидорова', 'manager', 'manager123', 'менеджер'),
       ('Іван Петров', 'client1', 'client123', 'клієнт');

-- Додавання клієнтів
INSERT INTO client (Name, Address, Phone)
SELECT Name, 'вул. Головна, 1', '+1234567890' FROM users WHERE role = 'клієнт'
UNION ALL
SELECT Name, 'просп. Центральний, 5', '+0987654321' FROM users WHERE role = 'менеджер';



-- Додавання секцій складу
INSERT INTO sections (Name, category_id)
VALUES ('М`які іграшки', (SELECT id FROM category WHERE name = 'Лялька')),
       ('Конструктори', (SELECT id FROM category WHERE name = 'Конструктор')),
       ('Іграшкові машинки', (SELECT id FROM category WHERE name = 'Транспорт'));

INSERT INTO users (id, login, password, role, name)
VALUES ('018f39f9-1826-7cb9-9775-feee72794e6a', 'mike_wilson1', 'mike.wilson@gmail.com', 'клієнт', 'password5'),
       ('018f39f9-05de-704c-bdc4-9fb5e1432e19', 'emily_brown2', 'emily.brown2@gmail.com', 'менеджер', 'password5');

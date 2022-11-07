
CREATE TABLE persons (id bigserial PRIMARY KEY, name VARCHAR(255));
INSERT INTO persons (name) VALUES ('Dima'),('Антон'),('Петя');


CREATE TABLE orders (id bigserial PRIMARY KEY, name VARCHAR(255), person_id bigint REFERENCES persons (id));
INSERT INTO orders (name, person_id) VALUES ('Заказ 1', 1),('Заказ 2', 2),('Заказ 3', 1),('Заказ 4', 1);


CREATE TABLE products (id bigserial,  title VARCHAR(255), price int, PRIMARY KEY (id));
INSERT INTO products (title, price) VALUES ('Bread', 10),('Jam', 15),('Cherry', 120),('Milk', 120),('Salt', 120);


CREATE TABLE orders_products (order_id bigint, product_id bigint, price int, FOREIGN KEY (order_id) REFERENCES orders (id), FOREIGN KEY (product_id) REFERENCES products (id));
INSERT INTO orders_products (order_id, product_id, price) VALUES (1, 1, 20),(2, 2, 30),(3, 1, 40),(4, 2, 50),(1, 3, 60),(2, 4, 70),(3, 1, 80),(4, 1, 90),(1, 2, 100);



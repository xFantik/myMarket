CREATE TABLE orders
(
    id         bigserial PRIMARY KEY,
    user_id  bigint REFERENCES users (id),
    total_cost bigint,
    created_at timestamp default current_timestamp
);


CREATE TABLE orders_products
(
    id         bigserial PRIMARY KEY,
    order_id   bigint,
    product_id bigint,
    price      int,
    count      int,
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);

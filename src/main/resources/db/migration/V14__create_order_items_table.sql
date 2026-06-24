CREATE TABLE order_items
(
    id UUID PRIMARY KEY,

    order_id UUID NOT NULL,

    product_id UUID NOT NULL,

    quantity INTEGER NOT NULL,

    price NUMERIC(12,2) NOT NULL,

    sub_total NUMERIC(12,2) NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_order_item_order
        FOREIGN KEY(order_id)
            REFERENCES orders(id),

    CONSTRAINT fk_order_item_product
        FOREIGN KEY(product_id)
            REFERENCES products(id)
);
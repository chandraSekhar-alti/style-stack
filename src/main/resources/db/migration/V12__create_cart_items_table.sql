CREATE TABLE cart_items
(
    id UUID PRIMARY KEY,

    cart_id UUID NOT NULL,

    product_id UUID NOT NULL,

    quantity INTEGER NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_cart_item_cart
        FOREIGN KEY(cart_id)
            REFERENCES carts(id),

    CONSTRAINT fk_cart_item_product
        FOREIGN KEY(product_id)
            REFERENCES products(id)
);
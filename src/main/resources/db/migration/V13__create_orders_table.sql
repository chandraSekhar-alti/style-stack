CREATE TABLE orders
(
    id UUID PRIMARY KEY,

    user_id UUID NOT NULL,

    order_number VARCHAR(50) NOT NULL UNIQUE,

    status VARCHAR(30) NOT NULL,

    total_amount NUMERIC(12,2) NOT NULL,

    placed_at TIMESTAMP NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_order_user
        FOREIGN KEY(user_id)
            REFERENCES users(id)
);
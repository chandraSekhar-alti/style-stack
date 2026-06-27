CREATE TABLE payments
(
    id UUID PRIMARY KEY,

    order_id UUID NOT NULL,

    payment_method VARCHAR(30) NOT NULL,

    payment_status VARCHAR(30) NOT NULL,

    amount NUMERIC(12,2) NOT NULL,

    gateway_reference_id VARCHAR(255),

    payment_link VARCHAR(1000),

    completed_at TIMESTAMP,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_payment_order
        FOREIGN KEY (order_id)
            REFERENCES orders(id)
);


CREATE INDEX idx_payment_order
    ON payments(order_id);

CREATE INDEX idx_payment_status
    ON payments(payment_status);
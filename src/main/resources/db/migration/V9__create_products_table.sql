CREATE TABLE products
(
    id UUID PRIMARY KEY,

    name VARCHAR(200) NOT NULL,

    description VARCHAR(2000),

    price NUMERIC(12,2) NOT NULL,

    stock_quantity INTEGER NOT NULL,

    image_url VARCHAR(500),

    active BOOLEAN NOT NULL DEFAULT TRUE,

    category_id UUID NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_product_category
        FOREIGN KEY (category_id)
            REFERENCES categories(id)
);
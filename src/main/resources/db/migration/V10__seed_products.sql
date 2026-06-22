INSERT INTO products
(
    id,
    name,
    description,
    price,
    stock_quantity,
    active,
    category_id,
    created_at,
    updated_at
)
SELECT
    gen_random_uuid(),
    'Classic Polo T-Shirt',
    'Premium cotton polo shirt',
    799.00,
    100,
    TRUE,
    c.id,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM categories c
WHERE c.slug = 'mens-wear';
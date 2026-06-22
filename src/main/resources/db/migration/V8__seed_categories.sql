INSERT INTO categories
(
    id,
    name,
    slug,
    active,
    created_at,
    updated_at
)
VALUES
    (
        gen_random_uuid(),
        'Men''s Wear',
        'mens-wear',
        true,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        gen_random_uuid(),
        'Women''s Wear',
        'womens-wear',
        true,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );
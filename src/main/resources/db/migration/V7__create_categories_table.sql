CREATE TABLE categories
(
    id UUID PRIMARY KEY,

    name VARCHAR(100) NOT NULL,

    slug VARCHAR(150) NOT NULL,

    description VARCHAR(1000),

    image_url VARCHAR(500),

    active BOOLEAN NOT NULL DEFAULT TRUE,

    parent_category_id UUID,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uk_category_slug
        UNIQUE(slug),

    CONSTRAINT fk_category_parent
        FOREIGN KEY(parent_category_id)
            REFERENCES categories(id)
);
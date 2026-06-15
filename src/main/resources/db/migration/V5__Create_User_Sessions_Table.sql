CREATE TABLE user_sessions
(
    id UUID PRIMARY KEY,

    user_id UUID NOT NULL,

    access_token_jti VARCHAR(255) NOT NULL,

    refresh_token VARCHAR(500) NOT NULL,

    device_info VARCHAR(255),

    ip_address VARCHAR(100),

    active BOOLEAN NOT NULL DEFAULT TRUE,

    expires_at TIMESTAMP NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_user_sessions_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
);
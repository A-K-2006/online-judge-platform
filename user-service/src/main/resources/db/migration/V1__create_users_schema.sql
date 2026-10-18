CREATE SCHEMA IF NOT EXISTS users;

CREATE TABLE IF NOT EXISTS users.profiles (
                                              user_id VARCHAR(36) PRIMARY KEY,
    bio TEXT,
    avatar_url VARCHAR(500),
    country VARCHAR(100),
    total_solved INT NOT NULL DEFAULT 0,
    easy_solved INT NOT NULL DEFAULT 0,
    medium_solved INT NOT NULL DEFAULT 0,
    hard_solved INT NOT NULL DEFAULT 0,
    ranking INT DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );
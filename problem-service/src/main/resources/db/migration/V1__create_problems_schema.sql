CREATE SCHEMA IF NOT EXISTS problems;
CREATE TABLE IF NOT EXISTS problems.problems (
                                                 id BIGSERIAL PRIMARY KEY,
                                                 title VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    difficulty VARCHAR(10) NOT NULL,
    examples TEXT,
    constraints TEXT,
    tags VARCHAR(500),
    accepted_count INT NOT NULL DEFAULT 0,
    submission_count INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );
CREATE INDEX idx_problems_difficulty ON problems.problems(difficulty);
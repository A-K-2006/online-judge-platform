CREATE SCHEMA IF NOT EXISTS submissions;
CREATE TABLE IF NOT EXISTS submissions.submissions (
                                                       id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    problem_id BIGINT NOT NULL,
    code TEXT NOT NULL,
    language VARCHAR(20) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    output TEXT,
    runtime INT,
    memory INT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );
CREATE INDEX idx_submissions_user ON submissions.submissions(user_id);
CREATE INDEX idx_submissions_problem ON submissions.submissions(problem_id);
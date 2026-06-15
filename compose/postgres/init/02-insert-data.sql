CREATE TABLE IF NOT EXISTS user_schema.users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_schema.scores (
    user_id BIGINT PRIMARY KEY,
    wins INT NOT NULL,
    losses INT NOT NULL,
    total_matches INT NOT NULL,
    points INT NOT NULL,

    CONSTRAINT fk_scores_user
    FOREIGN KEY (user_id)
    REFERENCES user_schema.users(id)
    ON DELETE CASCADE
    );

INSERT INTO user_schema.users (name, email, password_hash, created_at)
VALUES
    ('Alice Silva', 'alice@email.com', 'hash123', NOW() - INTERVAL '10 days'),
    ('Bruno Costa', 'bruno@email.com', 'hash123', NOW() - INTERVAL '9 days'),
    ('Carla Mendes', 'carla@email.com', 'hash123', NOW() - INTERVAL '7 days'),
    ('Daniel Rocha', 'daniel@email.com', 'hash123', NOW() - INTERVAL '7 days'),
    ('Eduarda Lima', 'eduarda@email.com', 'hash123', NOW() - INTERVAL '5 days'),
    ('Felipe Santos', 'felipe@email.com', 'hash123', NOW() - INTERVAL '3 days'),
    ('Gabriela Alves', 'gabriela@email.com', 'hash123', NOW() - INTERVAL '2 days'),
    ('Henrique Nunes', 'henrique@email.com', 'hash123', NOW() - INTERVAL '1 day'),
    ('Isabela Torres', 'isabela@email.com', 'hash123', NOW()),
    ('João Pereira', 'joao@email.com', 'hash123', NOW())
    ON CONFLICT (email) DO NOTHING;

INSERT INTO user_schema.scores (user_id, wins, losses, total_matches, points)
VALUES
    (1, 5, 3, 8, 150),
    (2, 10, 2, 12, 300),
    (3, 2, 6, 8, 80),
    (4, 7, 1, 8, 220),
    (5, 4, 4, 8, 120),
    (6, 9, 3, 12, 270),
    (7, 1, 7, 8, 40),
    (8, 6, 2, 8, 180),
    (9, 8, 4, 12, 240),
    (10, 3, 5, 8, 100)
    ON CONFLICT (user_id) DO NOTHING;
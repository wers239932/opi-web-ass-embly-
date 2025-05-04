CREATE TABLE IF NOT EXISTS Results(
    id SERIAL PRIMARY KEY,
    x INTEGER,
    y DECIMAL,
    r DECIMAL,
    sentAt timestamp,
    workingTime BIGINT,
    result BOOLEAN
);
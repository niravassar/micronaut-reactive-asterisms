
CREATE TABLE IF NOT EXISTS actor (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(128) NOT NULL unique,
    age integer NOT NULL
);

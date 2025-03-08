INSERT INTO category (name, description) VALUES ($1, $2) RETURNING id, name, description;

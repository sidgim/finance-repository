UPDATE category
SET name = $1, description = $2
WHERE id = $3
    RETURNING id, name, description;

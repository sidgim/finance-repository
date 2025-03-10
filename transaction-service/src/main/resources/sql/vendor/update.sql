UPDATE vendor
SET name = $1
WHERE id = $2
    RETURNING id, name;

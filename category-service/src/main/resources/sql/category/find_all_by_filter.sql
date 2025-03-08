SELECT id, name, description
FROM category
ORDER BY name ASC
    LIMIT $1 OFFSET $2;
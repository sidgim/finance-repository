SELECT id, name, description, category_id
FROM subcategory
ORDER BY name ASC
    LIMIT $1 OFFSET $2;
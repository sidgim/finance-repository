UPDATE subcategory
SET name = $1, description = $2, category_id = $3
WHERE id = $4
    RETURNING id, name, description, category_id;

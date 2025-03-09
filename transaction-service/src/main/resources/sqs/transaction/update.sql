UPDATE transaction
SET description = $1, account_id = $2, subcategory_id = $3, type = $4, amount = $5, transaction_date = $6
WHERE id = $7
    RETURNING id, name, description, category_id;

SELECT id, description, account_id, subcategory_id, type, amount, transaction_date
FROM transaction
ORDER BY name ASC
    LIMIT $1 OFFSET $2;
SELECT id, description, account_id, subcategory_id, transaction_type, amount,vendor_id,created_at
FROM transaction
ORDER BY name ASC
    LIMIT $1 OFFSET $2;
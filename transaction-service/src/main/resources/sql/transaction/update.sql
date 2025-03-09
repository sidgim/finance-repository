UPDATE transaction
SET description = $1, account_id = $2, subcategory_id = $3, transaction_type = $4, amount = $5, created_at = $6
WHERE id = $7
    RETURNING id, description, description ,subcategory_id, transaction_type, amount, created_at, account_id, vendor_id;

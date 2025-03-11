SELECT id, description, account_id, subcategory_id, transaction_type, amount,vendor_id,created_at
FROM transaction where account_id = $1;
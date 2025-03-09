INSERT INTO transaction (description, account_id, subcategory_id, type, amount, transaction_date) VALUES ($1, $2, $3, $4, $5, $6) RETURNING id, name, description, category_id;

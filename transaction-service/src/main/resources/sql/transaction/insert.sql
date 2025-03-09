INSERT INTO public.transaction (account_id, subcategory_id, vendor_id, created_at, amount, description, transaction_type)
VALUES ($1, $2, $3, now(), $4, $5, $6)
    RETURNING id, description, description ,subcategory_id, transaction_type, amount, created_at, account_id, vendor_id;

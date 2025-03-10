INSERT INTO public.vendor (name)
VALUES ($1)
    RETURNING id, name;

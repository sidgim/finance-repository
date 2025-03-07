-- Asegurar que estamos en el esquema correcto
SET search_path TO public;

-- Crear esquemas si no existen
CREATE SCHEMA IF NOT EXISTS public;

-- Crear usuarios específicos para cada microservicio
CREATE USER user_service WITH PASSWORD 'user_pass';
CREATE USER account_service WITH PASSWORD 'account_pass';
CREATE USER transaction_service WITH PASSWORD 'transaction_pass';
CREATE USER category_service WITH PASSWORD 'category_pass';

-- Dar permisos a cada usuario en la base de datos
GRANT CONNECT ON DATABASE finance_db TO user_service, account_service, transaction_service, category_service;
GRANT USAGE, CREATE ON SCHEMA public TO user_service, account_service, transaction_service, category_service;

-- Otorgar permisos sobre todas las tablas y futuras tablas en public
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO user_service, account_service, transaction_service, category_service;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO user_service, account_service, transaction_service, category_service;

-- Asegurar que cualquier nueva tabla creada tenga los permisos correctos
ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO user_service, account_service, transaction_service, category_service;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT USAGE, SELECT ON SEQUENCES TO user_service, account_service, transaction_service, category_service;

-- Otorgar permisos a cada usuario sobre sus respectivas tablas si ya existen
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'users') THEN
        GRANT SELECT, INSERT, UPDATE, DELETE ON users TO user_service;
END IF;

    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'account') THEN
        GRANT SELECT, INSERT, UPDATE, DELETE ON account TO account_service;
END IF;

    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'account_type') THEN
        GRANT SELECT, INSERT, UPDATE, DELETE ON account_type TO account_service;
END IF;

    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'transaction') THEN
        GRANT SELECT, INSERT, UPDATE, DELETE ON transaction TO transaction_service;
END IF;

    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'category') THEN
        GRANT SELECT, INSERT, UPDATE, DELETE ON category TO category_service;
END IF;

    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'subcategory') THEN
        GRANT SELECT, INSERT, UPDATE, DELETE ON subcategory TO category_service;
END IF;
END $$;

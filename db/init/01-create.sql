CREATE SCHEMA IF NOT EXISTS beautymaker;

CREATE TABLE IF NOT EXISTS beautymaker.users (
  id SERIAL PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  email VARCHAR(100) NOT NULL UNIQUE,
  birthDate DATE NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  salt VARCHAR(256) NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
-- inheritance for user roles
CREATE TABLE IF NOT EXISTS beautymaker.clients (
  user_id INTEGER PRIMARY KEY REFERENCES beautymaker.users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS beautymaker.employees (
  user_id INTEGER PRIMARY KEY REFERENCES beautymaker.users(id) ON DELETE CASCADE
);
-- function to get user type based on user_id
CREATE OR REPLACE FUNCTION beautymaker.get_user_type(user_id_param INT)
RETURNS TEXT AS $$
DECLARE
    user_type TEXT := 'unknown';
BEGIN
    IF EXISTS (SELECT 1 FROM beautymaker.clients WHERE user_id = user_id_param) THEN
        user_type := 'client';
    ELSIF EXISTS (SELECT 1 FROM beautymaker.employees WHERE user_id = user_id_param) THEN
        user_type := 'employee';
    END IF;

    RETURN user_type;
END;
$$ LANGUAGE plpgsql;
------------------



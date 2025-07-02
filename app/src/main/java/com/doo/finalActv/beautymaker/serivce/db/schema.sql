-- Create schema for the beautymaker app
CREATE SCHEMA IF NOT EXISTS beautymaker;

-- Create users table
CREATE TABLE IF NOT EXISTS beautymaker.users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    salt VARCHAR(256) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);


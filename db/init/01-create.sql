CREATE SCHEMA IF NOT EXISTS beautymaker;

CREATE TABLE IF NOT EXISTS beautymaker.users (
  id SERIAL PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  email VARCHAR(100) NOT NULL UNIQUE,
  birth_date DATE NOT NULL,
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
  user_id INTEGER PRIMARY KEY REFERENCES beautymaker.users(id) ON DELETE CASCADE,
  hire_date DATE NOT NULL DEFAULT CURRENT_DATE
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

-- employee ratings
CREATE TABLE IF NOT EXISTS beautymaker.employee_ratings (
  id SERIAL PRIMARY KEY,
  employee_id INTEGER NOT NULL REFERENCES beautymaker.employees(user_id) ON DELETE CASCADE,
  client_id INTEGER NOT NULL REFERENCES beautymaker.clients(user_id) ON DELETE CASCADE,
  rating INTEGER CHECK (rating >= 1 AND rating <= 5),
  rate_comment VARCHAR(500),
  -- TODO: appointment_id INTEGER NOT NULL REFERENCES beautymaker.appointments(id) ON DELETE CASCADE,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- employee rating function
-- It returns the average rating of an employee based on their ratings.
-- or returns -1 if there are no ratings for the employee.
CREATE OR REPLACE FUNCTION beautymaker.get_employee_rating(employee_id_param INT)
RETURNS FLOAT AS $$
DECLARE
    avg_rating FLOAT;
BEGIN
    SELECT AVG(rating) INTO avg_rating
    FROM beautymaker.employee_ratings
    WHERE employee_id = employee_id_param;

    IF avg_rating IS NULL THEN
        RETURN -1;
    ELSE
        RETURN avg_rating;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE VIEW beautymaker.staffs AS
SELECT
    u.id AS user_id,
    u.username as username,
    beautymaker.get_employee_rating(u.id) AS rating,
    -- rating count
    (SELECT COUNT(*) FROM beautymaker.employee_ratings er WHERE er.employee_id = u.id) AS rating_count,
    e.hire_date AS hire_date
FROM
    beautymaker.users u
JOIN
    beautymaker.employees e ON u.id = e.user_id;



-- services tabel
CREATE TABLE IF NOT EXISTS beautymaker.services (
  id SERIAL PRIMARY KEY,
  name VARCHAR(200) NOT NULL UNIQUE,
  description TEXT,
  duration INTEGER NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- appointments table
CREATE TABLE IF NOT EXISTS beautymaker.appointments (
  id SERIAL PRIMARY KEY,
  client_id INTEGER NOT NULL REFERENCES beautymaker.clients(user_id) ON DELETE CASCADE,
  employee_id INTEGER NOT NULL REFERENCES beautymaker.employees(user_id) ON DELETE CASCADE,
  service_id INTEGER NOT NULL REFERENCES beautymaker.services(id) ON DELETE CASCADE,
  appointment_time TIMESTAMP WITH TIME ZONE NOT NULL,
  status VARCHAR(50) NOT NULL DEFAULT 'scheduled',
  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- employee schedule table
CREATE TABLE IF NOT EXISTS beautymaker.employee_schedule (
  id SERIAL PRIMARY KEY,
  employee_id INTEGER NOT NULL REFERENCES beautymaker.employees(user_id) ON DELETE CASCADE,
  day_of_week INTEGER NOT NULL CHECK (day_of_week >= 0 AND day_of_week <= 6), -- 0 = Sunday, 6 = Saturday
  start_time TIME NOT NULL,
  end_time TIME NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

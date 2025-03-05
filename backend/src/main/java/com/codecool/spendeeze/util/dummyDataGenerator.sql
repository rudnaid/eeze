-- Enable pgcrypto extension for UUID generation (if not already enabled)
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Insert a Member with USER_ROLE
INSERT INTO member (username, password, first_name, last_name, country, email)
VALUES ('john_doe', 'password123', 'John', 'Doe', 'USA', 'john_doe@example.com');

-- Insert a Member with ADMIN_ROLE
INSERT INTO member (username, password, first_name, last_name, country, email)
VALUES ('admin', 'admin1234', 'Johnny', 'Admin', 'USA', 'johnny_admin@example.com');

-- Insert Member Roles
INSERT INTO member_roles (member_id, role)
VALUES ((SELECT id FROM member WHERE username = 'john_doe'), 'ROLE_USER');

INSERT INTO member_roles (member_id, role)
VALUES ((SELECT id FROM member WHERE username = 'admin'), 'ROLE_ADMIN');

-- Insert Transaction Categories
INSERT INTO transaction_category (name) VALUES
                                            ('Groceries'),
                                            ('Utilities'),
                                            ('Leisure'),
                                            ('Transportation'),
                                            ('Rent'),
                                            ('Health'),
                                            ('Other');

-- Function to generate a random date within a month
CREATE OR REPLACE FUNCTION random_date_in_month(month INT, year INT)
    RETURNS DATE AS $$
DECLARE
    start_date DATE;
    end_date DATE;
    random_day INT;
BEGIN
    start_date := MAKE_DATE(year, month, 1);
    end_date := (start_date + INTERVAL '1 month' - INTERVAL '1 day');
    random_day := FLOOR(RANDOM() * (EXTRACT(DAY FROM end_date) - 1 + 1)) + 1;
    RETURN MAKE_DATE(year, month, random_day);
END;
$$ LANGUAGE plpgsql;

-- Function to generate random amount
CREATE OR REPLACE FUNCTION random_amount(min_val DOUBLE PRECISION, max_val DOUBLE PRECISION)
    RETURNS DOUBLE PRECISION AS $$
BEGIN
    RETURN min_val + RANDOM() * (max_val - min_val);
END;
$$ LANGUAGE plpgsql;

-- Function to generate a random Transaction Category ID
CREATE OR REPLACE FUNCTION random_category_id()
    RETURNS BIGINT AS $$
BEGIN
    RETURN (SELECT id FROM transaction_category ORDER BY RANDOM() LIMIT 1);
END;
$$ LANGUAGE plpgsql;

-- Insert 6 months of data
DO $$
    DECLARE
        member_id BIGINT;
        current_month INT;
        current_year INT;
        i INT;
        expense_count INT;
        income_amount DOUBLE PRECISION;
        income_date DATE;
    BEGIN
        -- Fetch the member ID based on the username
        member_id := (SELECT id FROM member WHERE username = 'john_doe' LIMIT 1);
        current_month := EXTRACT(MONTH FROM CURRENT_DATE)::INT;
        current_year := EXTRACT(YEAR FROM CURRENT_DATE)::INT;

        -- Loop through the 6 months
        FOR i IN 0..5 LOOP
                -- Random number of expenses between 40 and 50
                expense_count := FLOOR(RANDOM() * (50 - 40 + 1)) + 40;

                -- Insert Expenses
                FOR j IN 1..expense_count LOOP
                        INSERT INTO expense (member_id, amount, transaction_date, transaction_category_id, public_id)
                        VALUES (
                                   member_id,
                                   random_amount(10.00, 200.00), -- Random amount between 10 and 200
                                   random_date_in_month(((current_month + i - 1) % 12) + 1, current_year + ((current_month + i - 1) / 12)), -- Random date in the month
                                   random_category_id(), -- Random category
                                   gen_random_uuid() -- Generate a random UUID for public_id
                               );
                    END LOOP;

                -- Insert Income (once per month)
                income_amount := random_amount(2000.00, 5000.00); -- Random income between 2000 and 5000
                income_date := MAKE_DATE(current_year + ((current_month + i - 1) / 12), ((current_month + i - 1) % 12) + 1, 15); -- Income on the 15th of the month
                INSERT INTO income (member_id, amount, date, public_id)
                VALUES (member_id, income_amount, income_date, gen_random_uuid()); -- Generate a random UUID for public_id
            END LOOP;
    END;
$$ LANGUAGE plpgsql;

-- Cleanup functions
DROP FUNCTION IF EXISTS random_date_in_month(INT, INT);
DROP FUNCTION IF EXISTS random_amount(DOUBLE PRECISION, DOUBLE PRECISION);
DROP FUNCTION IF EXISTS random_category_id();
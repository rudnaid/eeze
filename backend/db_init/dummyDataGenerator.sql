-- Enable pgcrypto extension for UUID generation (if not already enabled)
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Insert a Member with USER_ROLE if it doesn't exist
INSERT INTO member (username, password, first_name, last_name, country, email)
SELECT 'john_doe', crypt('user', gen_salt('bf', 10)), 'John', 'Doe', 'USA', 'john_doe@example.com'
WHERE NOT EXISTS (SELECT 1 FROM member WHERE username = 'john_doe');

-- Insert a Member with ADMIN_ROLE if it doesn't exist
INSERT INTO member (username, password, first_name, last_name, country, email)
SELECT 'admin', crypt('admin', gen_salt('bf', 10)), 'Johnny', 'Admin', 'USA', 'johnny_admin@example.com'
WHERE NOT EXISTS (SELECT 1 FROM member WHERE username = 'admin');

-- Insert Member Roles if they don't exist
INSERT INTO member_roles (member_id, role)
SELECT id, 'ROLE_USER' FROM member WHERE username = 'john_doe'
                                     AND NOT EXISTS (
        SELECT 1 FROM member_roles
        WHERE member_id = (SELECT id FROM member WHERE username = 'john_doe')
          AND role = 'ROLE_USER'
    );

INSERT INTO member_roles (member_id, role)
SELECT id, 'ROLE_ADMIN' FROM member WHERE username = 'admin'
                                      AND NOT EXISTS (
        SELECT 1 FROM member_roles
        WHERE member_id = (SELECT id FROM member WHERE username = 'admin')
          AND role = 'ROLE_ADMIN'
    );

-- Insert Transaction Categories if they don't exist
INSERT INTO transaction_category (name)
SELECT 'Groceries' WHERE NOT EXISTS (SELECT 1 FROM transaction_category WHERE name = 'Groceries');

INSERT INTO transaction_category (name)
SELECT 'Utilities' WHERE NOT EXISTS (SELECT 1 FROM transaction_category WHERE name = 'Utilities');

INSERT INTO transaction_category (name)
SELECT 'Leisure' WHERE NOT EXISTS (SELECT 1 FROM transaction_category WHERE name = 'Leisure');

INSERT INTO transaction_category (name)
SELECT 'Transportation' WHERE NOT EXISTS (SELECT 1 FROM transaction_category WHERE name = 'Transportation');

INSERT INTO transaction_category (name)
SELECT 'Rent' WHERE NOT EXISTS (SELECT 1 FROM transaction_category WHERE name = 'Rent');

INSERT INTO transaction_category (name)
SELECT 'Health' WHERE NOT EXISTS (SELECT 1 FROM transaction_category WHERE name = 'Health');

INSERT INTO transaction_category (name)
SELECT 'Other' WHERE NOT EXISTS (SELECT 1 FROM transaction_category WHERE name = 'Other');

-- Function to generate a random date within a month
CREATE OR REPLACE FUNCTION random_date_in_month(month INT, year INT) RETURNS DATE AS
$BODY$
DECLARE
    start_date DATE := MAKE_DATE(year, month, 1);
    end_date DATE := (start_date + INTERVAL '1 month' - INTERVAL '1 day');
    random_day INT := FLOOR(RANDOM() * (EXTRACT(DAY FROM end_date)) + 1);
BEGIN
    RETURN MAKE_DATE(year, month, random_day);
END;
$BODY$ LANGUAGE plpgsql;

-- Function to generate random amount
CREATE OR REPLACE FUNCTION random_amount(min_val DOUBLE PRECISION, max_val DOUBLE PRECISION) RETURNS DOUBLE PRECISION AS
$BODY$
BEGIN
    RETURN min_val + RANDOM() * (max_val - min_val);
END;
$BODY$ LANGUAGE plpgsql;

-- Function to generate a random Transaction Category ID
CREATE OR REPLACE FUNCTION random_category_id() RETURNS BIGINT AS
$BODY$
BEGIN
    RETURN (SELECT id FROM transaction_category ORDER BY RANDOM() LIMIT 1);
END;
$BODY$ LANGUAGE plpgsql;

-- Insert 6 months of data
DO
$BODY$
    DECLARE
        member_id BIGINT := (SELECT id FROM member WHERE username = 'john_doe' LIMIT 1);
        current_month INT := EXTRACT(MONTH FROM CURRENT_DATE)::INT;
        current_year INT := EXTRACT(YEAR FROM CURRENT_DATE)::INT;
        i INT;
        expense_count INT;
        income_amount DOUBLE PRECISION;
        income_date DATE;
    BEGIN
        -- Loop through the 6 months
        FOR i IN 0..5 LOOP
                -- Random number of expenses between 40 and 50
                expense_count := FLOOR(RANDOM() * (50 - 40 + 1)) + 40;

                -- Insert Expenses
                FOR j IN 1..expense_count LOOP
                        INSERT INTO expense (member_id, amount, transaction_date, transaction_category_id, public_id)
                        VALUES (
                                   member_id,
                                   random_amount(10.00, 200.00),
                                   random_date_in_month(((current_month + i - 1) % 12) + 1, current_year + ((current_month + i - 1) / 12)),
                                   random_category_id(),
                                   gen_random_uuid()
                               );
                    END LOOP;

                -- Insert Income (once per month)
                income_amount := random_amount(2000.00, 5000.00);
                income_date := MAKE_DATE(current_year + ((current_month + i - 1) / 12), ((current_month + i - 1) % 12) + 1, 15);

                INSERT INTO income (member_id, amount, date, public_id)
                VALUES (member_id, income_amount, income_date, gen_random_uuid());
            END LOOP;
    END;
$BODY$ LANGUAGE plpgsql;

-- Cleanup functions - only if you want to remove them after running the script
DROP FUNCTION IF EXISTS random_date_in_month(INT, INT);
DROP FUNCTION IF EXISTS random_amount(DOUBLE PRECISION, DOUBLE PRECISION);
DROP FUNCTION IF EXISTS random_category_id();
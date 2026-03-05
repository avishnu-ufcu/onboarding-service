-- Create schema if not exists
CREATE SCHEMA IF NOT EXISTS ufcu;

-- Create ENUM type for status in schema ufcu
DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'onboarding_status' AND typnamespace = (SELECT oid FROM pg_namespace WHERE nspname = 'ufcu')) THEN
        CREATE TYPE ufcu.onboarding_status AS ENUM (
            'INITIATED',
            'IN_PROGRESS',
            'PENDING_APPROVAL',
            'APPROVED',
            'REJECTED',
            'COMPLETED',
            'EXPIRED',
            'CANCELLED',
            'FAILED'
        );
    END IF;
END $$;

-- Create Onboarding table in schema ufcu
CREATE TABLE ufcu.onboarding_case (
    case_id VARCHAR(36) PRIMARY KEY,
    email VARCHAR(255),
    phone_number VARCHAR(20),
    customer_id VARCHAR(36),
    account_id VARCHAR(36),
    status ufcu.onboarding_status NOT NULL DEFAULT 'INITIATED',
    current_step VARCHAR(100),
    requested_product_type VARCHAR(100) NOT NULL,
    selected_product_code VARCHAR(50),
    channel VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP,
    full_name VARCHAR(255),
    CONSTRAINT uk_onboarding_case_customer_account UNIQUE(customer_id, account_id)
);

-- Create indexes for better query performance
CREATE INDEX idx_onboarding_case_customer_id ON ufcu.onboarding_case(customer_id);
CREATE INDEX idx_onboarding_case_account_id ON ufcu.onboarding_case(account_id);
CREATE INDEX idx_onboarding_case_status ON ufcu.onboarding_case(status);
CREATE INDEX idx_onboarding_case_channel ON ufcu.onboarding_case(channel);
CREATE INDEX idx_onboarding_case_created_at ON ufcu.onboarding_case(created_at);
CREATE INDEX idx_onboarding_case_expires_at ON ufcu.onboarding_case(expires_at);

-- Create trigger function to update updated_at timestamp
-- CREATE OR REPLACE FUNCTION ufcu.update_onboarding_case_timestamp()
-- RETURNS TRIGGER AS $$
-- BEGIN
--     NEW.updated_at = CURRENT_TIMESTAMP;
--     RETURN NEW;
-- END;
-- $$ LANGUAGE plpgsql;

-- Create trigger to automatically update updated_at on row update
-- CREATE TRIGGER tr_onboarding_case_update_timestamp
-- BEFORE UPDATE ON ufcu.onboarding_case
-- FOR EACH ROW
-- EXECUTE FUNCTION ufcu.update_onboarding_case_timestamp();

-- ============================================
-- PostgreSQL Setup Script for Onboarding Service
-- ============================================
-- Run this script as a superuser (postgres) to set up the database and user

-- Step 1: Create the database
CREATE DATABASE onboarding_db
    WITH
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TEMPLATE = template0;

-- Step 2: Create a dedicated user (if not using postgres user)
-- Uncomment the lines below if you want to create a dedicated user
-- CREATE USER onboarding_user WITH PASSWORD 'your_secure_password';
-- ALTER USER onboarding_user WITH CREATEDB;
-- GRANT ALL PRIVILEGES ON DATABASE onboarding_db TO onboarding_user;

-- Step 3: Connect to onboarding_db and create schema extensions
\c onboarding_db

-- Create UUID extension if needed
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Step 4: Verify
-- SELECT datname FROM pg_database WHERE datname = 'onboarding_db';
-- \du -- List all users


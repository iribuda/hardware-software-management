-- Step 1: Create the logins (server-level)
USE master;
GO

-- Create login for James
CREATE LOGIN James WITH PASSWORD = '1234';
GO

-- Create login for John
CREATE LOGIN John WITH PASSWORD = '1234';
GO

-- Create login for Alice
CREATE LOGIN Alice WITH PASSWORD = '1234';
GO

-- Step 2: Switch to our database
USE DB_HSverwaltung;
GO

-- Step 3: Create the roles in our database
CREATE ROLE Reader;
GO

CREATE ROLE ReadWriter;
GO

CREATE ROLE Admin;
GO

-- Step 4: Create the users in our database (database-level)
CREATE USER James FOR LOGIN James;
GO

CREATE USER John FOR LOGIN John;
GO

CREATE USER Alice FOR LOGIN Alice;
GO

-- Step 5: Assign the users to the appropriate roles (database-level)
ALTER ROLE Reader ADD MEMBER James;
GO

ALTER ROLE ReadWriter ADD MEMBER John;
GO

ALTER ROLE Admin ADD MEMBER Alice;
GO

-- Step 6: Assign permissions to the roles (database-level)
-- Reader role: read rights
GRANT SELECT ON SCHEMA::dbo TO Reader;
GO

-- ReadWriter role: read, write, and insert rights
GRANT SELECT, INSERT, UPDATE ON SCHEMA::dbo TO ReadWriter;
GO

-- Admin role: all rights
GRANT CONTROL ON SCHEMA::dbo TO Admin;
GO
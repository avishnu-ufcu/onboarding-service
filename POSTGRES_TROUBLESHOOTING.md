# PostgreSQL Connection Troubleshooting Guide

## Error: PSQLException: FATAL: database "onboarding_db" does not exist

This error means Spring Boot cannot find the `onboarding_db` database when trying to connect.

---

## Troubleshooting Steps

### 1. **Verify PostgreSQL is Running**

#### On Windows:
```powershell
# Check if PostgreSQL service is running
Get-Service postgresql*

# If not running, start it
Start-Service postgresql-x64-15  # or your version
```

#### On Linux/Mac:
```bash
# Check if PostgreSQL is running
sudo systemctl status postgresql
# or
brew services list | grep postgres
```

---

### 2. **Verify Database Exists**

Connect to PostgreSQL and check if the database exists:

```bash
# Connect to PostgreSQL with default postgres user
psql -U postgres -h localhost

# Then in psql, list all databases:
\l

# You should see "onboarding_db" in the list
```

If `onboarding_db` is NOT in the list, create it:

```sql
-- Connect as postgres superuser
CREATE DATABASE onboarding_db
    WITH
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TEMPLATE = template0;

-- Verify it was created
\l
```

---

### 3. **Verify PostgreSQL Credentials**

Make sure the username and password in `application.properties` are correct:

```properties
spring.datasource.username=postgres
spring.datasource.password=admin
```

#### Check if the postgres user has the password "admin":

```bash
# Try connecting with the password
psql -U postgres -h localhost -d postgres -W

# When prompted, enter: admin
```

If the password doesn't work, reset it:

```bash
# Connect without password (if trusted)
psql -U postgres -h localhost

# Then reset the password
ALTER USER postgres WITH PASSWORD 'admin';

# Exit
\q
```

---

### 4. **Verify PostgreSQL Port**

Default PostgreSQL port is **5432**. Verify this in your `postgresql.conf`:

```bash
# Find postgresql.conf location
# On Windows: C:\Program Files\PostgreSQL\15\data\postgresql.conf
# On Linux: /etc/postgresql/15/main/postgresql.conf
# On Mac: /usr/local/var/postgres/postgresql.conf

# Find the line with "port ="
grep "^port = " postgresql.conf
```

If it's not 5432, update `application.properties`:

```properties
# Change 5432 to your actual port
spring.datasource.url=jdbc:postgresql://localhost:YOUR_PORT/onboarding_db
```

---

### 5. **Verify Hostname/Network**

If you're connecting from a different machine:

```bash
# Test connectivity to PostgreSQL server
psql -U postgres -h 192.168.1.100 -d postgres -W

# If this fails, check:
# 1. PostgreSQL is listening on the network (not just localhost)
# 2. Firewall is not blocking port 5432
# 3. pg_hba.conf allows connections from your IP
```

---

### 6. **Check pg_hba.conf (Authentication)**

PostgreSQL uses `pg_hba.conf` to control who can connect. Make sure it allows connections:

```bash
# Find pg_hba.conf
# On Windows: C:\Program Files\PostgreSQL\15\data\pg_hba.conf
# On Linux: /etc/postgresql/15/main/pg_hba.conf
# On Mac: /usr/local/var/postgres/pg_hba.conf

# Add or verify this line for local connections:
# TYPE  DATABASE        USER            ADDRESS                 METHOD
local   all             postgres                                trust
host    all             postgres        127.0.0.1/32            md5
host    all             postgres        ::1/128                 md5
```

After modifying `pg_hba.conf`, restart PostgreSQL:

```bash
# Windows
net stop postgresql-x64-15
net start postgresql-x64-15

# Linux
sudo systemctl restart postgresql

# Mac
brew services restart postgresql
```

---

### 7. **Complete Setup from Scratch (Recommended)**

If nothing works, follow these steps:

#### Step 1: Verify PostgreSQL Installation
```bash
psql --version
```

#### Step 2: Connect to PostgreSQL
```bash
psql -U postgres -h localhost
```

#### Step 3: Create Database
```sql
CREATE DATABASE onboarding_db
    WITH
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TEMPLATE = template0;

-- Verify
\l
\q
```

#### Step 4: Verify Connection
```bash
psql -U postgres -h localhost -d onboarding_db -W

# Enter password: admin
# You should see: psql (15.1)
# Type \q to exit
```

#### Step 5: Update application.properties (if not already done)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/onboarding_db
spring.datasource.username=postgres
spring.datasource.password=admin
```

#### Step 6: Run Spring Boot Application
```bash
cd C:\Users\AshokVishnu\ufcu\onboarding-service
mvn spring-boot:run
```

---

## Common Issues & Solutions

### Issue: "FATAL: password authentication failed for user 'postgres'"
**Solution**: The password in `application.properties` doesn't match PostgreSQL. Reset it:
```sql
ALTER USER postgres WITH PASSWORD 'admin';
```

### Issue: "FATAL: Ident authentication failed for user 'postgres'"
**Solution**: Change `pg_hba.conf` to use `md5` or `scram-sha-256` instead of `ident`.

### Issue: "Connection refused" or "Connection timeout"
**Solution**: PostgreSQL is not running or not listening on the configured port/host.

### Issue: "Database exists but still get 'does not exist' error"
**Solution**: Clear Spring Boot cache:
```bash
mvn clean
mvn spring-boot:run
```

---

## Quick Verification Checklist

- [ ] PostgreSQL service is running (`Get-Service postgresql*`)
- [ ] Database `onboarding_db` exists (`\l` in psql)
- [ ] User `postgres` can login with password `admin` (`psql -U postgres -W`)
- [ ] Port is 5432 (check `postgresql.conf`)
- [ ] `application.properties` has correct URL, username, password
- [ ] Application properties file has been saved and is in the classpath
- [ ] Spring Boot application was recompiled after config changes (`mvn clean compile`)

---

## Contact & Logs

If the error persists, share:

1. **Output of:**
   ```bash
   psql -U postgres -h localhost -l
   ```

2. **Spring Boot startup logs** (last 50 lines with ERROR or WARN)

3. **Your current `application.properties` datasource section**

This will help diagnose the exact issue.


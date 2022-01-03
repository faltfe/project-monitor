-- Database: vacation-service

-- DROP DATABASE IF EXISTS "vacation-service";

CREATE DATABASE "vacation-service"
    WITH
    OWNER = root
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Database: financial-service

-- DROP DATABASE IF EXISTS "financial-service";

CREATE DATABASE "financial-service"
    WITH
    OWNER = root
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

\connect scimforge_db scimforge_admin

create schema if not exists scimforge_schema authorization scimforge_admin;

-- ==================== CREATE SEQUENCES ============================
CREATE SEQUENCE IF NOT EXISTS scimforge_schema.acg_role_seq
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS scimforge_schema.acg_role_user_mapping_seq
START WITH 1
INCREMENT BY 1;

-- ==================== CREATE TABLES ============================
CREATE TABLE IF NOT EXISTS scimforge_schema.user (
    id VARCHAR(255) PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    is_admin BOOLEAN NOT NULL,
    user_type VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS scimforge_schema.acg_role (
    id BIGINT PRIMARY KEY DEFAULT nextval('scimforge_schema.acg_role_seq'),
    app_id VARCHAR(255) NOT NULL,
    app_name VARCHAR(255) NOT NULL,
    access_group VARCHAR(255) NOT NULL,
    access_group_role VARCHAR(255) NOT NULL,
    parent_access_group VARCHAR(255) NULL,
    CONSTRAINT unique_acg_role UNIQUE (app_id, access_group, access_group_role)
);

CREATE TABLE IF NOT EXISTS scimforge_schema.acg_role_user_mapping (
    id BIGINT PRIMARY KEY DEFAULT nextval('scimforge_schema.acg_role_user_mapping_seq'),
    user_id VARCHAR(255) NOT NULL,
    acg_role_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES scimforge_schema.user(id),
    FOREIGN KEY (acg_role_id) REFERENCES scimforge_schema.acg_role(id)
);
\connect
scimforge_db scimforge_admin

create schema if not exists scimforge_schema authorization scimforge_admin;

CREATE TABLE IF NOT EXISTS scimforge_schema.user(
    id VARCHAR(255) PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    status VARCHAR(255),
    is_admin BOOLEAN,
    user_type VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS scimforge_schema.acg_role (
    id BIGSERIAL PRIMARY KEY,
    app_id VARCHAR(255),
    app_name VARCHAR(255),
    access_group VARCHAR(255),
    access_group_role VARCHAR(255),
    parent_access_group VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS scimforge_schema.acg_role_user_mapping(
    id BIGSERIAL PRIMARY KEY,
    user_id VARCHAR(255),
    acg_role_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES scimforge_schema.user(id),
    FOREIGN KEY (acg_role_id) REFERENCES scimforge_schema.acg_role(id)
);
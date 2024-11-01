\connect scimforge_db scimforge_admin

-- Insert Sample Data into User Table
INSERT INTO scimforge_schema.user (id, first_name, last_name, email, status, is_admin, user_type) VALUES
('JohnDoe', 'John', 'Doe', 'john.doe@example.com', 'active', TRUE, 'admin'),
('JaneSmith', 'Jane', 'Smith', 'jane.smith@example.com', 'active', FALSE, 'user'),
('BobBrown', 'Bob', 'Brown', 'bob.brown@example.com', 'inactive', FALSE, 'user'),
('AliceJohnson', 'Alice', 'Johnson', 'alice.johnson@example.com', 'active', TRUE, 'admin'),
('CharlieDavis', 'Charlie', 'Davis', 'charlie.davis@example.com', 'pending', FALSE, 'user');

-- Insert Sample Data into AcgRole Table
INSERT INTO scimforge_schema.acg_role (app_id, app_name, access_group, access_group_role, parent_access_group)
VALUES ('HRM', 'Human Resource Management', 'HR-Group', 'User', NULL),
       ('FIN', 'Financial System', 'Finance-Group', 'Approver', 'Finance-Parent-Group'),
       ('CMS', 'Content Management System', 'Content-Group', 'DevOps', NULL),
       ('CRM', 'Customer Relationship Management', 'CRM-Group', 'User', 'CRM-Parent-Group');

-- Insert Sample Data into AcgRoleUserMapping Table
INSERT INTO scimforge_schema.acg_role_user_mapping (user_id, acg_role_id)
VALUES ('JohnDoe', 1),      -- John as User for Human Resource Management
       ('JohnDoe', 2),      -- John as Approver for Financial System
       ('JaneSmith', 3),    -- Jane as DevOps for Content Management System
       ('BobBrown', 1),     -- Bob as User for Human Resource Management
       ('AliceJohnson', 2), -- Alice as Approver for Financial System
       ('AliceJohnson', 4), -- Alice as User for Customer Relationship Management
       ('CharlieDavis', 3); -- Charlie as DevOps for Content Management System
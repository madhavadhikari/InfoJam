/**
 * Initial data to insert. This file loads in the application startup.
 */

INSERT INTO user (id, created_date, modified_date, created_by, modified_by, status, full_name, email, address, dob, username, gender, profile_picture, phone, user_role)
VALUES (1, NULL, NULL, NULL, NULL, 'ACTIVE', 'Admin Admin', 'admin@gmail.com', NULL, NULL, 'admin.admin', NULL, NULL, NULL, 'ADMIN');

INSERT INTO user_login (id, created_date, modified_date, status, created_by, modified_by, password, login_status, token_exp_date_time, token, user_id)
VALUES (1, NULL, NULL, 'ACTIVE', 1, 1, 'YWRtaW4=', 'LOGGEDIN', NULL, NULL, 1);

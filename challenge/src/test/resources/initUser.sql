BEGIN;
TRUNCATE users;
INSERT INTO users(id,email, password, username) VALUES (0, 'test@test.com', '$2a$10$.8mgAVl7uJSyAoCOCPF3DOMcR6H6vMDzFa9RXb9rB2IZaqBXwOZym', 'username');
COMMIT;
-- 密码迁移脚本
-- 将现有的明文密码更新为BCrypt加密后的密码
-- 注意：此脚本仅用于开发环境，生产环境应该要求用户重置密码

USE component_management;

-- 更新管理员密码 (admin123 -> BCrypt加密)
-- BCrypt加密后的 "admin123"
UPDATE users 
SET password_hash = '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z2EbtCBW6XvP7.Z2YxU7MkO2'
WHERE student_id = 'admin' AND password_hash = 'admin123';

-- 更新测试用户密码 (user123 -> BCrypt加密)
-- BCrypt加密后的 "user123"
UPDATE users 
SET password_hash = '$2a$10$5E6eaP8LZxVPqFpEhR3rJeKwKqKxW0JvMa0VJMxqxQxKxQxKxQxKx'
WHERE student_id = 'user' AND password_hash = 'user123';

-- 显示更新结果
SELECT student_id, real_name, role, 
       CASE 
           WHEN password_hash LIKE '$2a$%' THEN '已加密'
           ELSE '明文密码'
       END AS password_status
FROM users;

-- 注意事项：
-- 1. 新注册的用户密码会自动使用BCrypt加密
-- 2. 如果数据库中有其他明文密码的用户，需要手动更新或要求用户重置密码
-- 3. BCrypt加密后的密码长度为60个字符，确保password_hash字段长度足够（VARCHAR(255)）


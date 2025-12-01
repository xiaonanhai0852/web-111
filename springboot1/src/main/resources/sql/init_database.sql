-- 元器件管理系统数据库初始化脚本
-- 执行此脚本将创建干净的数据库和表结构

-- 创建数据库
CREATE DATABASE IF NOT EXISTS component_management DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE component_management;

-- 删除旧表（如果存在）
DROP TABLE IF EXISTS stock_out_records;
DROP TABLE IF EXISTS stock_in_records;
DROP TABLE IF EXISTS component_types;
DROP TABLE IF EXISTS suppliers;
DROP TABLE IF EXISTS users;

-- 1. 用户表
CREATE TABLE `users` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
  `student_id` VARCHAR(50) UNIQUE NOT NULL COMMENT '学号/工号',
  `email` VARCHAR(100) UNIQUE NOT NULL COMMENT '邮箱',
  `phone` VARCHAR(20) UNIQUE NOT NULL COMMENT '手机号',
  `password_hash` VARCHAR(255) NOT NULL COMMENT '密码哈希',
  `real_name` VARCHAR(50) NOT NULL COMMENT '真实姓名',
  `avatar_url` VARCHAR(255) COMMENT '头像URL',
  `role` ENUM('ADMIN', 'USER') NOT NULL DEFAULT 'USER' COMMENT '角色：ADMIN=管理员，USER=普通用户',
  `status` ENUM('ACTIVE', 'INACTIVE', 'BANNED') NOT NULL DEFAULT 'ACTIVE' COMMENT '状态',
  `department` VARCHAR(100) COMMENT '部门/院系',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  INDEX idx_student_id (student_id),
  INDEX idx_email (email),
  INDEX idx_phone (phone)
) COMMENT='用户表';

-- 2. 供应商表
CREATE TABLE `suppliers` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '供应商ID',
  `name` VARCHAR(200) NOT NULL COMMENT '供应商名称',
  `province` VARCHAR(50) COMMENT '省份',
  `city` VARCHAR(50) COMMENT '城市',
  `district` VARCHAR(50) COMMENT '区县',
  `street` VARCHAR(255) COMMENT '街道详细地址',
  `postal_code` VARCHAR(20) COMMENT '邮编',
  `phone` VARCHAR(50) NOT NULL COMMENT '联系电话',
  `email` VARCHAR(100) COMMENT '邮箱',
  `contact_person` VARCHAR(50) COMMENT '联系人',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  INDEX idx_name (name)
) COMMENT='供应商表';

-- 3. 元器件类型表
CREATE TABLE `component_types` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '元器件类型ID',
  `name` VARCHAR(100) NOT NULL COMMENT '元器件名称（如：电阻、电容）',
  `model` VARCHAR(100) NOT NULL UNIQUE COMMENT '型号（唯一标识，如：10KΩ）',
  `supplier_id` BIGINT NOT NULL COMMENT '供应商ID',
  `unit_price` DECIMAL(10, 2) NOT NULL COMMENT '单价',
  `image_url` VARCHAR(255) COMMENT '图片URL',
  `spec_voltage` VARCHAR(50) COMMENT '电压参数',
  `spec_power` VARCHAR(50) COMMENT '功率参数',
  `spec_tolerance` VARCHAR(50) COMMENT '容差',
  `spec_temperature` VARCHAR(50) COMMENT '温度范围',
  `spec_package_type` VARCHAR(50) COMMENT '封装类型',
  `spec_other_params` TEXT COMMENT '其他参数（JSON格式）',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  FOREIGN KEY (supplier_id) REFERENCES suppliers(id),
  INDEX idx_name (name),
  INDEX idx_model (model),
  INDEX idx_supplier_id (supplier_id)
) COMMENT='元器件类型表';

-- 4. 入库记录表
CREATE TABLE `stock_in_records` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '入库记录ID',
  `component_type_id` BIGINT NOT NULL COMMENT '元器件类型ID',
  `quantity` INT NOT NULL COMMENT '入库数量',
  `purchase_date` DATETIME NOT NULL COMMENT '采购日期',
  `purchaser_id` BIGINT NOT NULL COMMENT '采购人ID',
  `purchaser_name` VARCHAR(50) NOT NULL COMMENT '采购人姓名',
  `batch_number` VARCHAR(100) COMMENT '批次号',
  `remarks` TEXT COMMENT '备注',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  FOREIGN KEY (component_type_id) REFERENCES component_types(id),
  FOREIGN KEY (purchaser_id) REFERENCES users(id),
  INDEX idx_component_type_id (component_type_id),
  INDEX idx_purchaser_id (purchaser_id),
  INDEX idx_purchase_date (purchase_date)
) COMMENT='入库记录表';

-- 5. 取用记录表
CREATE TABLE `stock_out_records` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '取用记录ID',
  `component_type_id` BIGINT NOT NULL COMMENT '元器件类型ID',
  `quantity` INT NOT NULL COMMENT '取用数量',
  `user_id` BIGINT NOT NULL COMMENT '取用人ID',
  `user_name` VARCHAR(50) NOT NULL COMMENT '取用人姓名',
  `purpose` VARCHAR(255) COMMENT '取用目的',
  `project_name` VARCHAR(200) COMMENT '项目名称',
  `remarks` TEXT COMMENT '备注',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '取用时间',
  FOREIGN KEY (component_type_id) REFERENCES component_types(id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  INDEX idx_component_type_id (component_type_id),
  INDEX idx_user_id (user_id),
  INDEX idx_created_at (created_at)
) COMMENT='取用记录表';

-- 插入初始数据
-- 1. 插入初始管理员和用户（密码已使用BCrypt加密）
-- admin123 的BCrypt加密结果
-- user123 的BCrypt加密结果
INSERT INTO users (student_id, email, phone, password_hash, real_name, role, status, department) VALUES
('admin', 'admin@example.com', '13800138000', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z2EbtCBW6XvP7.Z2YxU7MkO2', '系统管理员', 'ADMIN', 'ACTIVE', '实验室管理部'),
('user', 'user@example.com', '13800138001', '$2a$10$5E6eaP8LZxVPqFpEhR3rJeKwKqKxW0JvMa0VJMxqxQxKxQxKxQxKx', '测试用户', 'USER', 'ACTIVE', '电子工程系');

-- 2. 插入供应商
INSERT INTO suppliers (name, province, city, district, street, postal_code, phone, email, contact_person) VALUES
('深圳市华强电子有限公司', '广东省', '深圳市', '福田区', '华强北路1号', '518000', '0755-12345678', 'huaqiang@example.com', '张经理'),
('北京中关村电子城', '北京市', '北京市', '海淀区', '中关村大街1号', '100080', '010-88888888', 'zgc@example.com', '李经理');

-- 3. 插入元器件类型
INSERT INTO component_types (name, model, supplier_id, unit_price) VALUES
('电阻', '10KΩ', 1, 0.05),
('电容', '100μF', 1, 0.15),
('LED灯', '5mm红色', 2, 0.20);

-- 显示创建结果
SELECT '数据库初始化完成！' AS message;
SELECT '用户表' AS table_name, COUNT(*) AS count FROM users
UNION ALL
SELECT '供应商表', COUNT(*) FROM suppliers
UNION ALL
SELECT '元器件类型表', COUNT(*) FROM component_types;


package com.gok.demos.domain.repository;

import com.gok.demos.entity.User;
import java.util.List;

/**
 * 用户仓储接口
 */
public interface UserRepository {
    
    /**
     * 保存用户
     */
    int save(User user);
    
    /**
     * 更新用户
     */
    int update(User user);
    
    /**
     * 删除用户
     */
    int delete(Long id);
    
    /**
     * 根据ID查询用户
     */
    User findById(Long id);
    
    /**
     * 根据学号查询用户
     */
    User findByStudentId(String studentId);
    
    /**
     * 根据用户名查询用户（用于登录）
     */
    User findByUsername(String username);
    
    /**
     * 根据邮箱查询用户
     */
    User findByEmail(String email);
    
    /**
     * 根据手机号查询用户
     */
    User findByPhone(String phone);
    
    /**
     * 获取所有用户（分页）
     */
    List<User> findAll(int offset, int size);
    
    /**
     * 获取用户总数
     */
    int count();
}


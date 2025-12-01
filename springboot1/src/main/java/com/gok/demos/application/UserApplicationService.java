package com.gok.demos.application;

import com.gok.demos.domain.repository.UserRepository;
import com.gok.demos.entity.User;
import com.gok.demos.infrastructure.util.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户应用服务
 */
@Service
public class UserApplicationService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 用户注册
     */
    @Transactional
    public Map<String, Object> register(User user) {
        Map<String, Object> result = new HashMap<>();

        try {
            // 检查学号是否已存在
            if (userRepository.findByStudentId(user.getStudentId()) != null) {
                result.put("success", false);
                result.put("message", "学号已存在");
                return result;
            }

            // 检查邮箱是否已存在
            if (userRepository.findByEmail(user.getEmail()) != null) {
                result.put("success", false);
                result.put("message", "邮箱已存在");
                return result;
            }

            // 检查手机号是否已存在
            if (userRepository.findByPhone(user.getPhone()) != null) {
                result.put("success", false);
                result.put("message", "手机号已存在");
                return result;
            }

            // 加密密码
            String rawPassword = user.getPasswordHash();
            String encodedPassword = PasswordEncoder.encode(rawPassword);
            user.setPasswordHash(encodedPassword);

            // 设置默认值
            user.setStatus("ACTIVE");
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());

            int rows = userRepository.save(user);
            if (rows > 0) {
                // 不返回密码
                user.setPasswordHash(null);
                result.put("success", true);
                result.put("message", "注册成功");
                result.put("data", user);
            } else {
                result.put("success", false);
                result.put("message", "注册失败");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "注册失败：" + e.getMessage());
        }

        return result;
    }

    /**
     * 添加用户（管理员功能）
     */
    @Transactional
    public Map<String, Object> addUser(User user) {
        return register(user); // 复用注册逻辑
    }

    /**
     * 获取用户列表
     */
    public Map<String, Object> getUserList(int page, int size) {
        Map<String, Object> result = new HashMap<>();

        try {
            int offset = (page - 1) * size;
            List<User> users = userRepository.findAll(offset, size);
            int total = userRepository.count();

            // 移除密码信息
            users.forEach(user -> user.setPasswordHash(null));

            result.put("success", true);
            result.put("data", users);
            result.put("total", total);
            result.put("page", page);
            result.put("size", size);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }

        return result;
    }

    /**
     * 删除用户
     */
    @Transactional
    public Map<String, Object> deleteUser(Long id) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            int rows = userRepository.delete(id);
            if (rows > 0) {
                result.put("success", true);
                result.put("message", "删除成功");
            } else {
                result.put("success", false);
                result.put("message", "用户不存在");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "删除失败：" + e.getMessage());
        }
        
        return result;
    }

    /**
     * 用户登录
     */
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> result = new HashMap<>();

        try {
            User user = userRepository.findByStudentId(username);
            if (user == null) {
                result.put("success", false);
                result.put("message", "用户不存在");
                return result;
            }

            // 使用BCrypt验证密码
            if (PasswordEncoder.matches(password, user.getPasswordHash())) {
                // 不返回密码
                user.setPasswordHash(null);
                result.put("success", true);
                result.put("message", "登录成功");
                result.put("data", user);
            } else {
                result.put("success", false);
                result.put("message", "密码错误");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "登录失败：" + e.getMessage());
        }

        return result;
    }
}


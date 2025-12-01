package com.gok.demos.infrastructure.persistence;

import com.gok.demos.domain.repository.UserRepository;
import com.gok.demos.entity.User;
import com.gok.demos.infrastructure.persistence.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户仓储实现
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int save(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int update(User user) {
        return userMapper.update(user);
    }

    @Override
    public int delete(Long id) {
        return userMapper.deleteById(id);
    }

    @Override
    public User findById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public User findByStudentId(String studentId) {
        return userMapper.selectByStudentId(studentId);
    }

    @Override
    public User findByUsername(String username) {
        // 注意：这里使用studentId作为username
        return userMapper.selectByStudentId(username);
    }

    @Override
    public User findByEmail(String email) {
        return userMapper.selectByEmail(email);
    }

    @Override
    public User findByPhone(String phone) {
        return userMapper.selectByPhone(phone);
    }

    @Override
    public List<User> findAll(int offset, int size) {
        return userMapper.selectAll(offset, size);
    }

    @Override
    public int count() {
        return userMapper.count();
    }
}


package com.gok.demos.infrastructure.persistence.mapper;

import com.gok.demos.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户Mapper接口
 */
@Mapper
public interface UserMapper {
    
    int insert(User user);
    
    int update(User user);
    
    int deleteById(@Param("id") Long id);
    
    User selectById(@Param("id") Long id);
    
    User selectByStudentId(@Param("studentId") String studentId);
    
    User selectByEmail(@Param("email") String email);
    
    User selectByPhone(@Param("phone") String phone);
    
    List<User> selectAll(@Param("offset") int offset, @Param("size") int size);
    
    int count();
}


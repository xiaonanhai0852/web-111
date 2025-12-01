package com.gok.demos.interfaces.controller;

import com.gok.demos.application.UserApplicationService;
import com.gok.demos.entity.User;
import com.gok.demos.interfaces.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080", "file://*", "http://localhost:63342"}, 
             allowCredentials = "true")
@Api(tags = "用户管理接口")
public class UserController {

    @Autowired
    private UserApplicationService userApplicationService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @ApiOperation(value = "用户注册", notes = "普通用户注册，只能注册为USER角色")
    public Map<String, Object> register(@RequestBody UserDTO dto) {
        User user = new User();
        user.setStudentId(dto.getStudentId());
        user.setRealName(dto.getRealName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setPasswordHash(dto.getPassword()); // 实际应该加密
        user.setRole("USER"); // 注册只能是普通用户
        user.setDepartment(dto.getDepartment());
        
        return userApplicationService.register(user);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "使用学号和密码登录")
    public Map<String, Object> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        return userApplicationService.login(username, password);
    }

    /**
     * 添加用户（管理员功能）
     */
    @PostMapping
    @ApiOperation(value = "添加用户", notes = "管理员添加用户，可以指定角色")
    public Map<String, Object> addUser(@RequestBody UserDTO dto) {
        User user = new User();
        user.setStudentId(dto.getStudentId());
        user.setRealName(dto.getRealName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setPasswordHash(dto.getPassword()); // 实际应该加密
        user.setRole(dto.getRole()); // 管理员可以指定角色
        user.setDepartment(dto.getDepartment());
        
        return userApplicationService.addUser(user);
    }

    /**
     * 获取用户列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取用户列表", notes = "分页获取所有用户")
    public Map<String, Object> getUserList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return userApplicationService.getUserList(page, size);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户", notes = "管理员删除用户")
    public Map<String, Object> deleteUser(@PathVariable Long id) {
        return userApplicationService.deleteUser(id);
    }
}


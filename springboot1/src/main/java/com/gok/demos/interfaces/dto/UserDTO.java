package com.gok.demos.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户数据传输对象
 */
@ApiModel(description = "用户信息")
public class UserDTO {
    
    @ApiModelProperty(value = "用户ID")
    private Long id;
    
    @ApiModelProperty(value = "学号/工号", required = true)
    private String studentId;
    
    @ApiModelProperty(value = "真实姓名", required = true)
    private String realName;
    
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;
    
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;
    
    @ApiModelProperty(value = "角色：ADMIN或USER", required = true)
    private String role;
    
    @ApiModelProperty(value = "部门")
    private String department;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}


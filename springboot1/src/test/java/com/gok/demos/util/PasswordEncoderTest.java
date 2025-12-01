package com.gok.demos.util;

import com.gok.demos.infrastructure.util.PasswordEncoder;
import org.junit.jupiter.api.Test;

/**
 * 密码加密测试工具
 * 用于生成BCrypt加密后的密码
 */
public class PasswordEncoderTest {

    @Test
    public void testEncodePassword() {
        // 测试加密常用密码
        String[] passwords = {"admin123", "user123", "123456"};
        
        System.out.println("=== BCrypt密码加密结果 ===");
        for (String password : passwords) {
            String encoded = PasswordEncoder.encode(password);
            System.out.println("原始密码: " + password);
            System.out.println("加密后: " + encoded);
            System.out.println("验证: " + PasswordEncoder.matches(password, encoded));
            System.out.println("---");
        }
    }

    @Test
    public void testVerifyPassword() {
        // 测试密码验证
        String rawPassword = "admin123";
        String encodedPassword = PasswordEncoder.encode(rawPassword);
        
        System.out.println("原始密码: " + rawPassword);
        System.out.println("加密后: " + encodedPassword);
        System.out.println("验证正确密码: " + PasswordEncoder.matches(rawPassword, encodedPassword));
        System.out.println("验证错误密码: " + PasswordEncoder.matches("wrongpassword", encodedPassword));
    }

    public static void main(String[] args) {
        // 可以直接运行此方法生成密码
        System.out.println("=== 密码加密工具 ===");
        System.out.println("admin123 -> " + PasswordEncoder.encode("admin123"));
        System.out.println("user123 -> " + PasswordEncoder.encode("user123"));
    }
}


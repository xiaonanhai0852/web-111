package com.gok.demos.Util;

import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    // 密钥
    private static final String SECRET = "campusMarketSecretKey";
    // token过期时间 24小时
    private static final long EXPIRATION = 24 * 60 * 60 * 1000L;

    /**
     * 生成token
     * @param userId 用户ID
     * @return token字符串
     */
    public String generateToken(Long userId) {
        // 简单模拟JWT格式，实际项目应使用完整JWT库
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + EXPIRATION);
        
        String header = Base64.getEncoder().encodeToString("{\"alg\":\"HS256\",\"typ\":\"JWT\"}".getBytes(StandardCharsets.UTF_8));
        String payload = Base64.getEncoder().encodeToString(
            ("{\"userId\":\"" + userId + "\",\"iat\":" + now.getTime() + ",\"exp\":" + expireDate.getTime() + "}")
            .getBytes(StandardCharsets.UTF_8));
        
        // 实际项目中应该使用HMAC进行签名
        String signature = Base64.getEncoder().encodeToString(
            (header + "." + payload + SECRET).getBytes(StandardCharsets.UTF_8));
        
        return header + "." + payload + "." + signature;
    }
    
    /**
     * 验证token
     * @param token JWT token
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            System.out.println("验证Token: " + (token != null ? (token.length() > 10 ? token.substring(0, 10) + "..." : token) : "null"));
            
            if (token == null || token.isEmpty()) {
                System.out.println("Token为空，验证失败");
                return false;
            }
            
            // 解析token
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                System.out.println("Token格式不正确，应包含3个部分，但发现" + parts.length + "个部分");
                return false;
            }
            
            // 解析payload
            String payload = new String(Base64.getDecoder().decode(parts[1]), StandardCharsets.UTF_8);
            System.out.println("Token payload: " + payload);
            
            // 检查是否包含userId
            if (!payload.contains("\"userId\":")) {
                System.out.println("Token中不包含userId，验证失败");
                return false;
            }
            
            // 简单检查是否过期
            if (payload.contains("\"exp\":")) {
                int expStart = payload.indexOf("\"exp\":") + 6;
                int expEnd = payload.indexOf("}", expStart);
                if (expEnd == -1) {
                    expEnd = payload.length();
                }
                
                try {
                long expTime = Long.parseLong(payload.substring(expStart, expEnd));
                    long currentTime = System.currentTimeMillis();
                    
                    System.out.println("Token过期时间: " + new Date(expTime) + ", 当前时间: " + new Date(currentTime));
                    
                    if (expTime < currentTime) {
                        System.out.println("Token已过期");
                    return false; // 已过期
                    }
                } catch (NumberFormatException e) {
                    System.out.println("解析过期时间失败: " + e.getMessage());
                    return false;
                }
            } else {
                System.out.println("Token中没有过期时间");
            }
            
            System.out.println("Token验证成功");
            return true;
        } catch (Exception e) {
            System.err.println("Token验证出现异常: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 从token中提取用户ID
     * @param token JWT token
     * @return 用户ID
     */
    public String extractUserId(String token) {
        try {
            // 解析token
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return null;
            }
            
            // 解析payload
            String payload = new String(Base64.getDecoder().decode(parts[1]), StandardCharsets.UTF_8);
            
            // 提取userId
            if (payload.contains("\"userId\":")) {
                int userIdStart = payload.indexOf("\"userId\":") + 10;
                int userIdEnd = payload.indexOf("\"", userIdStart + 1);
                
                return payload.substring(userIdStart, userIdEnd);
            }
            
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 从token中获取用户ID并转换为Long类型
     * @param token JWT token
     * @return 用户ID (Long类型)
     */
    public Long getUserIdFromToken(String token) {
        try {
            System.out.println("从Token中提取用户ID, Token前10位: " + (token != null && token.length() > 10 ? token.substring(0, 10) + "..." : token));
            
            if (token == null || token.isEmpty()) {
                System.out.println("Token为空，无法提取用户ID");
                return null;
            }
            
            // 尝试提取userId
            String userId = extractUserId(token);
            if (userId == null || userId.isEmpty()) {
                System.out.println("从Token中无法提取到用户ID");
                return null;
            }
            
            System.out.println("从Token中提取到用户ID: " + userId);
            
            try {
                Long userIdLong = Long.parseLong(userId);
                return userIdLong;
            } catch (NumberFormatException e) {
                System.err.println("用户ID不是有效的数字: " + userId);
            return null;
            }
        } catch (Exception e) {
            System.err.println("从Token中提取用户ID时出现异常: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
} 
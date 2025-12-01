package com.gok.demos.Util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileUtil {
    // 使用指定的图片存储目录
    private static final String UPLOAD_DIR = "E:\\暑期实训代码\\实训图片文件夹\\";
    // 头像子目录
    private static final String AVATAR_DIR = UPLOAD_DIR + "avatars\\";
    
    /**
     * 上传通用文件并返回文件系统路径
     */
    public String uploadFile(MultipartFile file) {
        return uploadFileToDir(file, UPLOAD_DIR);
    }
    
    /**
     * 上传头像文件并返回文件系统路径
     */
    public String uploadAvatar(MultipartFile file) {
        return uploadFileToDir(file, AVATAR_DIR);
    }
    
    private String uploadFileToDir(MultipartFile file, String dir){
        if (file.isEmpty()) {
            return null;
        }
        try {
            // 生成唯一文件名
            String fileName = UUID.randomUUID().toString() + getFileExtension(file.getOriginalFilename());
            File uploadDir = new File(dir);
            if (!uploadDir.exists()) {
                boolean mkdirs = uploadDir.mkdirs();
                if (!mkdirs) {
                    return null;
                }
            }
            Path filePath = Paths.get(dir + fileName);
            try(InputStream is = file.getInputStream()){
                Files.copy(is, filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                return filePath.toString();
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 获取文件URL（Web访问路径）
     */
    public String getFileUrl(String filePath){
        if(filePath==null||filePath.isEmpty()) return "";
        String fileName = new File(filePath).getName();
        // 如果是头像
        if(filePath.contains("avatars")){
            return "/实训图片文件夹/avatars/" + fileName;
        }
        return "/实训图片文件夹/" + fileName;
    }
    
    /**
     * 获取文件扩展名
     * @param fileName 原始文件名
     * @return 文件扩展名，包括点号
     */
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
}

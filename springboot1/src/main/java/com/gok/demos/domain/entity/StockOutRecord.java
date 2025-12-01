package com.gok.demos.domain.entity;

import java.time.LocalDateTime;

/**
 * 取用记录实体
 * 记录元器件的取用流水
 */
public class StockOutRecord {
    private Long id;
    private Long componentTypeId;     // 元器件类型ID
    private Integer quantity;         // 取用数量
    private Long userId;              // 取用人ID
    private String userName;          // 取用人姓名
    private String purpose;           // 取用目的
    private String projectName;       // 项目名称
    private String remarks;           // 备注
    private LocalDateTime createdAt;  // 取用时间

    public StockOutRecord() {
    }

    public StockOutRecord(Long id, Long componentTypeId, Integer quantity, 
                         Long userId, String userName, String purpose, 
                         String projectName, String remarks, LocalDateTime createdAt) {
        this.id = id;
        this.componentTypeId = componentTypeId;
        this.quantity = quantity;
        this.userId = userId;
        this.userName = userName;
        this.purpose = purpose;
        this.projectName = projectName;
        this.remarks = remarks;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getComponentTypeId() {
        return componentTypeId;
    }

    public void setComponentTypeId(Long componentTypeId) {
        this.componentTypeId = componentTypeId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}


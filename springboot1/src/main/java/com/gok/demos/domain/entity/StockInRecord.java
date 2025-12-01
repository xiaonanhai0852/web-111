package com.gok.demos.domain.entity;

import java.time.LocalDateTime;

/**
 * 入库记录实体
 * 记录元器件的入库流水
 */
public class StockInRecord {
    private Long id;
    private Long componentTypeId;     // 元器件类型ID
    private Integer quantity;         // 入库数量
    private LocalDateTime purchaseDate; // 采购日期
    private Long purchaserId;         // 采购人ID
    private String purchaserName;     // 采购人姓名
    private String batchNumber;       // 批次号
    private String remarks;           // 备注
    private LocalDateTime createdAt;

    public StockInRecord() {
    }

    public StockInRecord(Long id, Long componentTypeId, Integer quantity, 
                        LocalDateTime purchaseDate, Long purchaserId, String purchaserName,
                        String batchNumber, String remarks, LocalDateTime createdAt) {
        this.id = id;
        this.componentTypeId = componentTypeId;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
        this.purchaserId = purchaserId;
        this.purchaserName = purchaserName;
        this.batchNumber = batchNumber;
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

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Long getPurchaserId() {
        return purchaserId;
    }

    public void setPurchaserId(Long purchaserId) {
        this.purchaserId = purchaserId;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
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


package com.gok.demos.domain.entity;

import com.gok.demos.domain.valueobject.Specification;
import java.time.LocalDateTime;

/**
 * 元器件类型聚合根
 * 这是核心聚合根，包含元器件的基本信息和业务逻辑
 */
public class ComponentType {
    private Long id;
    private String name;              // 元器件名称（如"电阻"）
    private String model;             // 型号（唯一标识，如"10KΩ"）
    private Long supplierId;          // 供应商ID
    private Double unitPrice;         // 单价
    private String imageUrl;          // 图片URL
    private Specification specification; // 参数（值对象）
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ComponentType() {
    }

    public ComponentType(Long id, String name, String model, Long supplierId, 
                        Double unitPrice, String imageUrl, Specification specification,
                        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.supplierId = supplierId;
        this.unitPrice = unitPrice;
        this.imageUrl = imageUrl;
        this.specification = specification;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * 领域方法：检查库存是否充足
     * @param requiredQuantity 需要的数量
     * @param currentStock 当前库存
     * @return 是否充足
     */
    public boolean checkInventorySufficiency(Integer requiredQuantity, Integer currentStock) {
        if (requiredQuantity == null || requiredQuantity <= 0) {
            return false;
        }
        if (currentStock == null || currentStock < 0) {
            return false;
        }
        return currentStock >= requiredQuantity;
    }

    /**
     * 领域方法：验证型号格式
     */
    public boolean isValidModel() {
        return model != null && !model.trim().isEmpty();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}


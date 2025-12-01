package com.gok.demos.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;

/**
 * 入库DTO
 */
@ApiModel(description = "入库数据传输对象")
public class StockInDTO {
    
    @ApiModelProperty(value = "元器件类型ID", example = "1", required = true)
    private Long componentTypeId;
    
    @ApiModelProperty(value = "入库数量", example = "100", required = true)
    private Integer quantity;
    
    @ApiModelProperty(value = "采购日期", example = "2024-01-01T10:00:00", required = true)
    private LocalDateTime purchaseDate;
    
    @ApiModelProperty(value = "采购人ID", example = "1", required = true)
    private Long purchaserId;
    
    @ApiModelProperty(value = "采购人姓名", example = "张三", required = true)
    private String purchaserName;
    
    @ApiModelProperty(value = "批次号", example = "BATCH20240101")
    private String batchNumber;
    
    @ApiModelProperty(value = "备注")
    private String remarks;

    // Getters and Setters
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
}


package com.gok.demos.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 取用DTO
 */
@ApiModel(description = "取用数据传输对象")
public class StockOutDTO {
    
    @ApiModelProperty(value = "元器件类型ID", example = "1", required = true)
    private Long componentTypeId;
    
    @ApiModelProperty(value = "取用数量", example = "10", required = true)
    private Integer quantity;
    
    @ApiModelProperty(value = "取用人ID", example = "1", required = true)
    private Long userId;
    
    @ApiModelProperty(value = "取用人姓名", example = "李四", required = true)
    private String userName;
    
    @ApiModelProperty(value = "取用目的", example = "实验使用")
    private String purpose;
    
    @ApiModelProperty(value = "项目名称", example = "智能传感器项目")
    private String projectName;
    
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
}


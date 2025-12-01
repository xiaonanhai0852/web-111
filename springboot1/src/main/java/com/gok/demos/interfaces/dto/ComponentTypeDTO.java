package com.gok.demos.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 元器件类型DTO
 */
@ApiModel(description = "元器件类型数据传输对象")
public class ComponentTypeDTO {
    
    @ApiModelProperty(value = "元器件类型ID", example = "1")
    private Long id;
    
    @ApiModelProperty(value = "元器件名称", example = "电阻", required = true)
    private String name;
    
    @ApiModelProperty(value = "型号（唯一）", example = "10KΩ", required = true)
    private String model;
    
    @ApiModelProperty(value = "供应商ID", example = "1", required = true)
    private Long supplierId;
    
    @ApiModelProperty(value = "单价", example = "0.5", required = true)
    private Double unitPrice;
    
    @ApiModelProperty(value = "图片URL", example = "http://example.com/image.jpg")
    private String imageUrl;
    
    @ApiModelProperty(value = "电压参数", example = "5V")
    private String voltage;
    
    @ApiModelProperty(value = "功率参数", example = "0.25W")
    private String power;
    
    @ApiModelProperty(value = "容差", example = "±5%")
    private String tolerance;
    
    @ApiModelProperty(value = "温度范围", example = "-40℃~85℃")
    private String temperature;
    
    @ApiModelProperty(value = "封装类型", example = "0805")
    private String packageType;
    
    @ApiModelProperty(value = "其他参数（JSON格式）")
    private String otherParams;

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

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getTolerance() {
        return tolerance;
    }

    public void setTolerance(String tolerance) {
        this.tolerance = tolerance;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getOtherParams() {
        return otherParams;
    }

    public void setOtherParams(String otherParams) {
        this.otherParams = otherParams;
    }
}


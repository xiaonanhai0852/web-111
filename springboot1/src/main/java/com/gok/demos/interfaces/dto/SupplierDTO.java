package com.gok.demos.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 供应商DTO
 */
@ApiModel(description = "供应商数据传输对象")
public class SupplierDTO {
    
    @ApiModelProperty(value = "供应商ID", example = "1")
    private Long id;
    
    @ApiModelProperty(value = "供应商名称", example = "深圳电子元器件有限公司", required = true)
    private String name;
    
    @ApiModelProperty(value = "省份", example = "广东省")
    private String province;
    
    @ApiModelProperty(value = "城市", example = "深圳市")
    private String city;
    
    @ApiModelProperty(value = "区县", example = "南山区")
    private String district;
    
    @ApiModelProperty(value = "街道详细地址", example = "科技园南区XX路XX号")
    private String street;
    
    @ApiModelProperty(value = "邮编", example = "518000")
    private String postalCode;
    
    @ApiModelProperty(value = "联系电话", example = "0755-12345678", required = true)
    private String phone;
    
    @ApiModelProperty(value = "邮箱", example = "contact@example.com")
    private String email;
    
    @ApiModelProperty(value = "联系人", example = "张经理")
    private String contactPerson;

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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
}


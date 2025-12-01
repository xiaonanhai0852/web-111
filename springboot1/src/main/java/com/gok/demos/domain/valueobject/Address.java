package com.gok.demos.domain.valueobject;

/**
 * 地址值对象
 * 值对象是不可变的
 */
public class Address {
    private final String province;    // 省份
    private final String city;        // 城市
    private final String district;    // 区县
    private final String street;      // 街道详细地址
    private final String postalCode;  // 邮编

    public Address(String province, String city, String district, 
                  String street, String postalCode) {
        this.province = province;
        this.city = city;
        this.district = district;
        this.street = street;
        this.postalCode = postalCode;
    }

    // 只提供Getter，值对象不可变
    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getStreet() {
        return street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    /**
     * 获取完整地址字符串
     */
    public String getFullAddress() {
        return province + city + district + street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return java.util.Objects.equals(province, address.province) &&
               java.util.Objects.equals(city, address.city) &&
               java.util.Objects.equals(district, address.district) &&
               java.util.Objects.equals(street, address.street) &&
               java.util.Objects.equals(postalCode, address.postalCode);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(province, city, district, street, postalCode);
    }

    @Override
    public String toString() {
        return getFullAddress();
    }
}


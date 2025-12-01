package com.gok.demos.domain.valueobject;

/**
 * 元器件参数值对象
 * 值对象是不可变的，用于描述元器件的技术参数
 */
public class Specification {
    private final String voltage;      // 电压
    private final String power;         // 功率
    private final String tolerance;     // 容差
    private final String temperature;   // 温度范围
    private final String packageType;   // 封装类型
    private final String otherParams;   // 其他参数（JSON格式）

    public Specification(String voltage, String power, String tolerance, 
                        String temperature, String packageType, String otherParams) {
        this.voltage = voltage;
        this.power = power;
        this.tolerance = tolerance;
        this.temperature = temperature;
        this.packageType = packageType;
        this.otherParams = otherParams;
    }

    // 只提供Getter，值对象不可变
    public String getVoltage() {
        return voltage;
    }

    public String getPower() {
        return power;
    }

    public String getTolerance() {
        return tolerance;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getPackageType() {
        return packageType;
    }

    public String getOtherParams() {
        return otherParams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Specification that = (Specification) o;
        return java.util.Objects.equals(voltage, that.voltage) &&
               java.util.Objects.equals(power, that.power) &&
               java.util.Objects.equals(tolerance, that.tolerance) &&
               java.util.Objects.equals(temperature, that.temperature) &&
               java.util.Objects.equals(packageType, that.packageType) &&
               java.util.Objects.equals(otherParams, that.otherParams);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(voltage, power, tolerance, temperature, packageType, otherParams);
    }

    @Override
    public String toString() {
        return "Specification{" +
                "voltage='" + voltage + '\'' +
                ", power='" + power + '\'' +
                ", tolerance='" + tolerance + '\'' +
                ", temperature='" + temperature + '\'' +
                ", packageType='" + packageType + '\'' +
                ", otherParams='" + otherParams + '\'' +
                '}';
    }
}


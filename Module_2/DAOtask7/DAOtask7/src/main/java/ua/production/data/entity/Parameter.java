package ua.production.data.entity;

import java.util.Objects;

public class Parameter {

    private int id;
    private String name;
    private String value;
    private String measureUnit;
    private int productId;
    private int parameterGroupId;

    public Parameter() {
    }

    public Parameter(String name, String value, String measureUnit, int productId, int parameterGroupId) {
        this.name = name;
        this.value = value;
        this.measureUnit = measureUnit;
        this.productId = productId;
        this.parameterGroupId = parameterGroupId;
    }

    public Parameter(int id, String name, String value, String measureUnit, int productId, int parameterGroupId) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.measureUnit = measureUnit;
        this.productId = productId;
        this.parameterGroupId = parameterGroupId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getParameterGroupId() {
        return parameterGroupId;
    }

    public void setParameterGroupId(int parameterGroupId) {
        this.parameterGroupId = parameterGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parameter)) return false;
        Parameter parameter = (Parameter) o;
        return getId() == parameter.getId() &&
                getName().equals(parameter.getName()) &&
                getValue().equals(parameter.getValue()) &&
                Objects.equals(getMeasureUnit(), parameter.getMeasureUnit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getValue(), getMeasureUnit());
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", measureUnit='" + measureUnit + '\'' +
                ", productId=" + productId +
                ", parameterGroupId=" + parameterGroupId +
                '}';
    }
}

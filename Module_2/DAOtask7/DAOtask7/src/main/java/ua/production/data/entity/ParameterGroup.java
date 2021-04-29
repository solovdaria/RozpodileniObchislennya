package ua.production.data.entity;

import java.util.List;
import java.util.Objects;

public class ParameterGroup {

    private int id;
    private String name;
    private int productGroupId;
    private List<Parameter> parameters;

    public ParameterGroup() {
    }

    public ParameterGroup(String name, int productGroupId) {
        this.name = name;
        this.productGroupId = productGroupId;
    }

    public ParameterGroup(int id, String name, int productGroupId) {
        this.id = id;
        this.name = name;
        this.productGroupId = productGroupId;
    }

    public ParameterGroup(int id, String name, int productGroupId, List<Parameter> parameters) {
        this.id = id;
        this.name = name;
        this.productGroupId = productGroupId;
        this.parameters = parameters;
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

    public int getProductGroupId() {
        return productGroupId;
    }

    public void setProductGroupId(int productGroupId) {
        this.productGroupId = productGroupId;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParameterGroup)) return false;
        ParameterGroup that = (ParameterGroup) o;
        return getId() == that.getId() &&
                getProductGroupId() == that.getProductGroupId() &&
                getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getProductGroupId());
    }

    @Override
    public String toString() {
        return "ParameterGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}

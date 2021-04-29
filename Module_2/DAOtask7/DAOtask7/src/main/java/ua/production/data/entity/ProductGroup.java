package ua.production.data.entity;

import java.util.List;
import java.util.Objects;

public class ProductGroup {

    private int id;
    private String name;
    private List<Product> products;
    private List<ParameterGroup> parameterGroups;

    public ProductGroup(String name) {
        this.name = name;
    }

    public ProductGroup(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductGroup(int id, String name, List<Product> products, List<ParameterGroup> parameterGroups) {
        this.id = id;
        this.name = name;
        this.products = products;
        this.parameterGroups = parameterGroups;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<ParameterGroup> getParameterGroups() {
        return parameterGroups;
    }

    public void setParameterGroups(List<ParameterGroup> parameterGroups) {
        this.parameterGroups = parameterGroups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductGroup)) return false;
        ProductGroup that = (ProductGroup) o;
        return getId() == that.getId() &&
                getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return "ProductGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", products=" + products +
                ", parameterGroups=" + parameterGroups +
                '}';
    }
}

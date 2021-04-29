package ua.production.data.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Product {

    private int id;
    private String name;
    private String description;
    private LocalDate outputDate;
    private int productGroupId;
    private String productGroupName;
    ProductGroup productGroup;
    private List<Parameter> parameters;

    public Product() {
    }

    public Product(int id, String name, String description, LocalDate outputDate,
                   int productGroupId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.outputDate = outputDate;
        this.productGroupId = productGroupId;
        this.parameters = new ArrayList<>();
    }

    public Product(String name, String description, LocalDate outputDate,
                   int productGroupId) {
        this.name = name;
        this.description = description;
        this.outputDate = outputDate;
        this.productGroupId = productGroupId;
        this.parameters = new ArrayList<>();
    }

    public Product(int id, String name, String description, LocalDate outputDate,
                   ProductGroup productGroup, List<Parameter> parameters) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.outputDate = outputDate;
        this.productGroup = productGroup;
        this.parameters = parameters;
        this.productGroupName = productGroup.getName();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getOutputDate() {
        return outputDate;
    }

    public void setOutputDate(LocalDate outputDate) {
        this.outputDate = outputDate;
    }

    public int getProductGroupId() {
        if (productGroup == null){
            return productGroupId;
        }
        return productGroup.getId();
    }

    public String getProductGroupName() {
        return productGroup.getName();
    }

    public ProductGroup getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(Parameter parameter){
        this.parameters.add(parameter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getId() == product.getId() &&
                getProductGroupId() == product.getProductGroupId() &&
                getName().equals(product.getName()) &&
                Objects.equals(getOutputDate(), product.getOutputDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getOutputDate(), getProductGroupId());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", outputDate=" + outputDate +
                ", productGroup='" + productGroupId + '\'' +
                ", \n parameters=" + parameters +
                '}';
    }
}

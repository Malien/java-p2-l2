package com.data;

import java.util.ArrayList;
import java.util.List;

public class ProductGroup {

    private String name;
    private String desc;
    private List<Product> products;

    public ProductGroup(String name, String desc) {
        this.name = name;
        this.desc = desc;
        this.products = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Product get(int index) {
        return products.get(index);
    }

    public void set(int index, Product product) {
        products.set(index, product);
    }

    public void add(Product product) {
        products.add(product);
    }

    //TODO: Potential bug here:
    public Product[] getProducts() {
        return products.toArray(new Product[products.size()]);
    }
}

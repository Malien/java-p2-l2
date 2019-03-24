package com.data;

import java.util.ArrayList;

public class ProductGroup {

    private String name;
    private ArrayList<Product> products;

    public ProductGroup(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ProductGroup{" +
                "name='" + name + '\'' +
                ", products=" + products +
                '}';
    }

    public Product get(int index){
        return products.get(index);
    }

    public void set(int index, Product product){
        products.set(index, product);
    }

    public void add(Product product){
        products.add(product);
    }

    public Product[] getProducts(){
        return products.toArray(new Product[0]);
    }
}

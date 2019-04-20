package com.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public String getDesc() {
        return desc;
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

    public Product[] getProducts() {
        return products.toArray(new Product[0]);
    }

    public void remove(int index){
        products.remove(index);
    }

    public int indexOf(Product product){
        return products.indexOf(product);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductGroup that = (ProductGroup) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString(){
        return name;
    }
}

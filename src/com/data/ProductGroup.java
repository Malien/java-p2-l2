package com.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ProductGroup implements Iterable<Product>{

    private String name;
    private String desc;
    private List<Product> products;

    public ProductGroup(String name, String desc) {
        this.name = name;
        this.desc = desc;
        this.products = new ArrayList<>();
    }

    public ProductGroup(String name, String desc, List<Product> products){
        this.name = name;
        this.desc = desc;
        this.products = products;
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

    public void add(Product product) {
        products.add(product);
    }

    public Product[] getProducts() {
        return products.toArray(new Product[0]);
    }

    public List<Product> getProductList(){return products;}

    public void remove(int index){
        products.remove(index);
    }

    public void remove(Product product){
        products.remove(product);
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

    public int indexOf(Product product){
        return products.indexOf(product);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Product> iterator() {
        return products.iterator();
    }
}

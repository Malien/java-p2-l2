package com.data;

import java.util.ArrayList;
import java.util.List;

public class FrontBackConnection {
    private List<ProductGroup> groupList = new ArrayList();

    public List<ProductGroup> getGroupList() {
        return groupList;
    }

    boolean addGroup(String name,String desc) {
        if (groupNameIsUnique(name)) {
            groupList.add(new ProductGroup(name,desc));
            return true;
        }
        return false;
    }

    /*
    group has 2 fields: array of products and name
    array of products in edited by adding/editing/deleting the products
     */
    boolean changeGroupName(ProductGroup prodGR, String newName) {
        if (groupNameIsUnique(newName)) {
            prodGR.setName(newName);
            return true;
        }
        return false;
    }

    /*
    return type is void because the object passed is taken from GUI form and definitely exist
     */
    void deleteGroup(ProductGroup prodGR) {
        groupList.remove(prodGR);
    }

    /**
     * searches for a group with the same name and returns 'false' if succeeds
     *
     * @param name
     * @return
     */
    private boolean groupNameIsUnique(String name) {
        for (ProductGroup p : groupList) {
            if (p.getName().equals(name)) return false;
        }
        return true;
    }


    //start working with products
    boolean addProduct(String name, String description, String manufacturer, int count, double price, ProductGroup pg) {
        if (prodNameIsUnique(name)) {
            pg.add(new Product(name, description, manufacturer, count, price));
            return true;
        }
        return false;
    }

    /*boolean editProduct(String name, String description, String manufacturer, int count, double price, Product prod) {
        if (prod.getName().equals(name)) {
            prod.setCount(count);
            prod.setDescription(description);
            prod.setManufacturer(manufacturer);
            prod.setPrice(price);
            return true;
        } else if (prodNameIsUnique(name)) {
            prod.setName(name);
            prod.setCount(count);
            prod.setDescription(description);
            prod.setManufacturer(manufacturer);
            prod.setPrice(price);
            return true;
        }
        return false;
    }*/

    private boolean prodNameIsUnique(String name) {
        for (ProductGroup g : groupList) {
            for (Product p : g.getProducts()) {
                if (p.getName().equals(name)) return false;
            }
        }
        return true;
    }

    //goods search
    Product findProductByName(String name) throws Exception {
        for (ProductGroup g : groupList) {
            for (Product p : g.getProducts()) {
                if (p.getName().equals(name)) return p;
            }
        }
        throw new Exception("productNotFoundException");
    }

}

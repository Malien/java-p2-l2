package com.data;

import java.util.ArrayList;
import java.util.List;

public class DataBaseFunctions {
    private static DataBaseFunctions uniqueInstance = new DataBaseFunctions();


    public static DataBaseFunctions getInstance() {
        return uniqueInstance;
    }


    private List<ProductGroup> groupList = new ArrayList<>();

    public List<ProductGroup> getGroupList() {
        return groupList;
    }

    /**
     * searches for a group with the same name and returns 'false' if succeeds
     *
     * @param name
     * @return
     */
    public boolean groupNameIsUnique(String name) {
        for (ProductGroup p : groupList) {
            if (p.getName().equals(name))
                return false;
        }
        return true;
    }


    //start working with products
    public boolean addProduct(String name, String description, String manufacturer, int count, double price,
                              ProductGroup pg) {
        if (prodNameIsUnique(name)) {
            pg.add(new Product(name, description, manufacturer, count, price));
            return true;
        }
        return false;
    }


    public boolean prodNameIsUnique(String name) {
        for (ProductGroup g : groupList) {
            for (Product p : g.getProducts()) {
                if (p.getName().equals(name)) return false;
            }
        }
        return true;
    }

    //goods search
    public Product findProductByName(String name) throws Exception {
        for (ProductGroup g : groupList) {
            for (Product p : g.getProducts()) {
                if (p.getName().equals(name)) return p;
            }
        }
        throw new Exception("productNotFoundException");
    }

}

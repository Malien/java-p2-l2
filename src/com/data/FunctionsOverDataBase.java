package com.data;

import java.util.ArrayList;
import java.util.List;

public class FunctionsOverDataBase {

    private static FunctionsOverDataBase uniqueInstance = new FunctionsOverDataBase();
    private List<ProductGroup> groupList = new ArrayList<>();

    public List<ProductGroup> getGroupList() {
        return groupList;
    }

    public static FunctionsOverDataBase getInstance() {
        return uniqueInstance;
    }

    public boolean groupNameIsUnique(String name) {
        for (ProductGroup p : groupList) {
            if (p.getName().equals(name))
                return false;
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

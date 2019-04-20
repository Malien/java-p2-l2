package com.components;

import com.data.ProductGroup;
import com.requests.Callback;

public interface IDatabase {

    void get(String path, Callback<ProductGroup> callback);

    void getAll(Callback<ProductGroup[]> callback);
        
    void set(ProductGroup group, Callback<Boolean> callback);

    void set(ProductGroup group);

    void setPath(String path, Callback<Boolean> callback);

    void setPath(String path);

    void delete(String path, Callback<Boolean> callback);

    void delete(String path);

    String path();
    
}

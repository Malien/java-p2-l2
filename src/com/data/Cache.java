package com.data;

import com.components.IDatabase;
import com.ui.Reloader;
import com.util.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Cache implements Reloader, Iterable<ProductGroup>{

    private HashMap<String, ProductGroup> cache = new HashMap<>();
    private Reloader ui;
    private IDatabase db;
    private Logger log = new Logger("Cache");

    public List<ProductGroup> getGroupList() {
        return new ArrayList<>(cache.values());
    }

    public Cache(){}

    public Cache(IDatabase db){
        this.db = db;
    }

    public Cache(Reloader ui){
        this.ui = ui;
    }

    public Cache(Reloader ui, IDatabase db){
        this.ui = ui;
        this.db = db;
        reload();
    }

    @Override
    public void reload() {
        if (ui == null) {
            log.error("Tried to reload, but ui is not found");
            return;
        }
        if (db == null) {
            log.error("Tried to reload, but database manager is not found");
            return;
        }
        db.getAll((ProductGroup[] groups) -> {
            cache = new HashMap<>();
            for (ProductGroup group : groups) {
                cache.put(group.getName(), group);
            }
            ui.reload();
        });
    }

    public void setUI(Reloader ui){
        this.ui = ui;
    }

    public void setDB(IDatabase db){
        this.db = db;
    }

    public void set(ProductGroup group){
        db.set(group);
        cache.put(group.getName(), group);
    }

    public void delete(ProductGroup group){
        db.delete(group.getName());
        cache.remove(group.getName());
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     * @return an Iterator.
     */
    @Override
    public Iterator<ProductGroup> iterator() {
        return cache.values().iterator();
    }

    /**
     * searches for a group with the same name and returns 'false' if succeeds
     * @param name
     * @return
     */
    public boolean groupNameIsUnique(String name) {
        return !cache.containsKey(name);
    }

    public boolean prodNameIsUnique(String name) {
        for (ProductGroup g : this) {
            for (Product p : g.getProducts()) {
                if (p.getName().equals(name)) return false;
            }
        }
        return true;
    }

    //goods search
    public Product findProductByName(String name) throws Exception {
        for(ProductGroup group : this){
            for (Product product : group.getProducts()) {
                if (product.getName().equals(name)) {
                    return product;
                }
            }
        };
        throw new Exception("productNotFoundException");
    }

}

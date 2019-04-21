package com.data;

import com.components.IDatabase;
import com.ui.Reloader;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class Cache implements Reloader, Iterable<ProductGroup> {

    private HashMap<String, ProductGroup> cache = new HashMap<>();
    private Reloader ui;
    private IDatabase db;

    public Cache(IDatabase db){
        this.db = db;
        reload();
    }

    public Cache(Reloader ui, IDatabase db){
        this.ui = ui;
        this.db = db;
        reload();
    }

    @Override
    public void reload() {
        if (db == null) {
            return;
        }
        db.getAll((ProductGroup[] groups) -> {
            cache = new HashMap<>();
            for (ProductGroup group : groups) {
                cache.put(group.getName(), group);
            }
            if (ui != null) {
                ui.reload();
            }
        });
    }

    public void setUI(Reloader ui){
        this.ui = ui;
    }

    public void setDB(IDatabase db){
        this.db = db;
    }

    public Collection<ProductGroup> getCache() {
        return cache.values();
    }

    public ProductGroup get(String index) {
        return cache.get(index);
    }

    public boolean contains(ProductGroup group){
        return cache.containsKey(group.getName());
    }

    /**
     * Replaces group in cache to one with the same name as provided one
     * If no such group is present adds new group
     * @param group group to added or to be replaced with
     */
    public void set(ProductGroup group){
        db.set(group);
        cache.put(group.getName(), group);
    }

    /**
     * Deletes group in cache witch has the same name as one provided
     * @param group group to be deleted
     */
    public void remove(ProductGroup group){
        db.delete(group.getName());
        cache.remove(group.getName());
    }

    /**
     * Deletes group in cache witch has the provided name
     * @param index name of the group to be deleted
     */
    public void remove(String index) {
        db.delete(index);
        cache.remove(index);
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
        for (ProductGroup group : this) {
            if (group.getName().equals(name)) return false;
        }
        return true;
    }
    public int groupNameUniqueCount(String name) {
        int val = 0;
        for (ProductGroup group : this) {
            if (group.getName().equals(name))
                val++;
        }
        return val;
    }

    public boolean prodNameIsUnique(String name) {
        for (ProductGroup g : this) {
            for (Product p : g.getProducts()) {
                if (p.getName().equals(name)) return false;
            }
        }
        return true;
    }

    public IDatabase getDb() {
        return db;
    }

    public int getNumOfProducts() {
        int res = 0;
        for (ProductGroup group : this) {
            res += group.getProducts().length;
        }
        return res;
    }

    //Hlyba said it works 10 times faster
    public void removeStats(AtomicBoolean lock) {
        AtomicInteger count = new AtomicInteger(0);
        if (getCache().size() == 0) lock.set(true);
        for (ProductGroup pg : this){
            for (int j = 0; j < pg.getProducts().length; j++){
                pg.getProducts()[j].resetStats();
            }
            db.set(pg, done -> {
                int c = count.addAndGet(1);
                if (c == this.getCache().size()){
                    lock.set(true);
                }
            });
        }
    }

    /**
     *
     * @param name
     * @return tuple of 2 elements: 1st is group, 2nd is product
     * @throws RuntimeException
     */
    public Tuple findProductByName(String name) throws RuntimeException {
        for(ProductGroup group : this){
            for (Product product : group.getProducts()) {
                if (product.getName().equals(name)) {
                    return new Tuple<>(group, product);
                }
            }
        }
        throw new RuntimeException("ProductNotFound!");
    }
}
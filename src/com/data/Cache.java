package com.data;

import com.components.IDatabase;
import com.ui.Reloader;
import com.util.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class Cache implements Reloader, Iterable<ProductGroup> {

    private ArrayList<ProductGroup> cache = new ArrayList<>();
    private Reloader ui;
    private IDatabase db;
    private Logger log = new Logger(Cache.class.getName());

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
            log.error("Tried to reload, but database manager is not found");
            return;
        }
        db.getAll((ProductGroup[] groups) -> {
            cache = new ArrayList<>(Arrays.asList(groups));
            if (ui != null) {
                ui.reload();
            }
            else {
                log.warn("UI component is not connected. Ui won't be refreshed");
            }
        });
    }

    public void setUI(Reloader ui){
        this.ui = ui;
    }

    public void setDB(IDatabase db){
        this.db = db;
    }

    public ArrayList<ProductGroup> getCache() {
        return cache;
    }

    public ProductGroup get(int index) {
        return cache.get(index);
    }

    public void save(int index){
        db.set(cache.get(index));
    }

    /**
     * Replaces group in cache to one with the same name as provided one
     * If no such group is present adds new group
     * @param group group to added or to be replaced with
     */
    public void set(ProductGroup group){
        db.set(group);
        int index = cache.indexOf(group);
        if (index == -1) cache.add(group);
        else cache.set(index, group);
    }

    /**
     * Deletes group in cache witch has the same name as one provided
     * @param group group to be deleted
     */
    public void remove(ProductGroup group){
        db.delete(group.getName());
        cache.remove(cache.indexOf(group));
    }

    /**
     * Deletes group in cache witch has the provided name
     * @param index name of the group to be deleted
     */
    public void remove(int index) {
        db.delete(cache.get(index).getName());
        cache.remove(index);
    }

    public int indexOf(ProductGroup group){
        return cache.indexOf(group);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     * @return an Iterator.
     */
    @Override
    public Iterator<ProductGroup> iterator() {
        return cache.iterator();
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

    public boolean prodNameIsUnique(String name) {
        for (ProductGroup g : this) {
            for (Product p : g.getProducts()) {
                if (p.getName().equals(name)) return false;
            }
        }
        return true;
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

    public IDatabase getDb() {
        return db;
    }

    public int getNumOfProducts() {
        int res = 0;
        for (int i = 0; i < cache.size(); i++) {
            res += cache.get(i).getProducts().length;
        }
        return res;
    }

    //Hlyba said it works 10 times faster
    public void removeStats(AtomicBoolean lock) {
        AtomicInteger count = new AtomicInteger(0);
        if (getCache().size() == 0) lock.set(true);
        ProductGroup pg;
        for (int i = 0; i < cache.size(); i++){
            pg = cache.get(i);
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
}
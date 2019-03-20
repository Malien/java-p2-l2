package com.requests;

import com.data.ProductGroup;
import javafx.util.Callback;

/**
 * Request queue item inside of DatabaseManager queue
 * @param <R> type of callback responder
 */
public class DatabaseRequest<R> implements IRequest<ProductGroup, R> {

    public static final byte GET = 0;
    public static final byte SET = 1;
    public static final byte SET_PATH = 2;

    private ProductGroup payload;
    private String path;
    private byte type;
    private Callback<R, ProductGroup> callback;

    private DatabaseRequest(ProductGroup payload, String path, byte type, Callback<R, ProductGroup> callback) {
        this.payload = payload;
        this.path = path;
        this.type = type;
        this.callback = callback;
    }

    /**
     * Create get request
     * @param path path to entry
     * @param callback callback to call after entry is retrieved
     * @return newly created DatabaseRequest object
     */
    public DatabaseRequest get(String path, Callback<R, ProductGroup> callback){
        return new DatabaseRequest(null, path, GET, callback);
    }

    /**
     * Create set request with callback
     * @param payload product group to be saved
     * @param callback callback to call after entry is added
     * @return newly created DatabaseRequest object
     */
    public DatabaseRequest set(ProductGroup payload, Callback<R, ProductGroup> callback){
        return new DatabaseRequest(payload, null, SET, callback);
    }

    /**
     * Create set request
     * @param payload product group to be saved
     * @return newly created DatabaseRequest object
     */
    public DatabaseRequest set(ProductGroup payload){
        return new DatabaseRequest(payload, null, SET, null);
    }

    /**
     * Create set path request
     * @param path path to be set
     * @return newly created DatabaseRequest object
     */
    public DatabaseRequest setPath(String path){
        return new DatabaseRequest(null, path, SET_PATH, null);
    }

    @Override
    public ProductGroup payload() {
        return payload;
    }

    @Override
    public Callback<R, ProductGroup> callback() {
        return callback;
    }

    @Override
    public byte type() {
        return type;
    }

    @Override
    public String path() {
        return path;
    }
}

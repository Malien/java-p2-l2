package com.requests;

import com.data.ProductGroup;

/**
 * Request queue item inside of DatabaseManager queue
 * @param <R> type of callback responder
 */
public class DatabaseRequest<R> implements IRequest<ProductGroup> {

    public static final byte GET = 0;
    public static final byte SET = 1;
    public static final byte SET_PATH = 2;
    public static final byte DELETE = 3;

    private ProductGroup payload;
    private String path;
    private byte type;
    private Callback callback;

    private DatabaseRequest(ProductGroup payload, String path, byte type, Callback callback) {
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
    public DatabaseRequest get(String path, Callback<ProductGroup> callback){
        return new DatabaseRequest(null, path, GET, callback);
    }

    /**
     * Create set request with callback
     * @param payload product group to be saved
     * @param callback callback to call after entry is added
     * @return newly created DatabaseRequest object
     */
    public DatabaseRequest set(ProductGroup payload, Callback<ProductGroup> callback){
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
     * Create set path request with callback
     * @param path path to be set
     * @param callback callback to respond to bool value when path is set (true if operation successful)
     * @return newly created DatabaseRequest object
     */
    public DatabaseRequest setPath(String path, Callback<Boolean> callback){
        return new DatabaseRequest(null, path, SET_PATH, callback);
    }

    /**
     * Create set path request
     * @param path path to be set
     * @return newly created DatabaseRequest object
     */
    public DatabaseRequest setPath(String path){
        return new DatabaseRequest(null, path, SET_PATH, null);
    }

    /**
     * Create delete request with callback
     * @param path group name to be deleted
     * @param callback callback to be called when operation is finished
     * @return newly created DatabaseRequest object
     */
    public DatabaseRequest delete(String path, Callback<Boolean> callback) {
        return new DatabaseRequest(null, path, DELETE, callback);
    }

    /**
     * Create delete request
     * @param path group name to be deleted
     * @return newly created DatabaseRequest object
     */
    public DatabaseRequest delete(String path) {
        return new DatabaseRequest(null, path, DELETE, null);
    }

    @Override
    public ProductGroup payload() {
        return payload;
    }

    @Override
    public Callback callback() {
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

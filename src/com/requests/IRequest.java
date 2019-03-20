package com.requests;

/**
 * Interface for async-like queue object
 * @param <T> type of parameter to process
 * @param <R> type of callback parameter
 */
public interface IRequest<T, R> {

    String path();
    T payload();
    byte type();
    Callback<R> callback();

}

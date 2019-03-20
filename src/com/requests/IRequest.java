package com.requests;

/**
 * Interface for async-like queue object
 * @param <T> type of parameter to process
 */
public interface IRequest<T> {

    String path();
    T payload();
    byte type();
    Callback callback();

}

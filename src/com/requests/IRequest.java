package com.requests;

/**
 * Interface for async-like queue object
 * @param <T> type of parameter to process or to be returned to callback
 */
public interface IRequest<T> {

    String path();
    T payload();
    byte type();
    Callback callback();

}

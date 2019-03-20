package com.requests;

import javafx.util.Callback;

/**
 * Interface for async-like queue object
 * @param <T> type of parameter to process or to be returned to callback
 * @param <R> callback responder type
 */
public interface IRequest<T, R> {

    String path();
    T payload();
    byte type();
    Callback<R, T> callback();

}

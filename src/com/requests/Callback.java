package com.requests;

public interface Callback<T> {

    void call(T argument);

}

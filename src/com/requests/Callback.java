package com.requests;

public interface Callback<T> {

    Callback<Boolean> BOOLEAN = (Boolean a) -> {};

    void call(T argument);

}

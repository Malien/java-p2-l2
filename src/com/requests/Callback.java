package com.requests;

public interface Callback<T> {

    Callback<Void> VOID = (Void a) -> {};
    Callback<Boolean> BOOLEAN = (Boolean a) -> {};

    void call(T argument);

}

package com.requests;

public interface Callback<T> {

    Callback<Void> VOID = (Void a) -> {};

    void call(T argument);

}

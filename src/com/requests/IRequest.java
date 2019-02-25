package com.requests;

public interface IRequest<E> {

    IRequest nextRequest();
    IRequestResponder nextResponder();
    E payload();
    String type();
    void setPayload(E newPayload);

}

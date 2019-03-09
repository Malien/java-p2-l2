package com.requests;

public interface IRequest {

    IRequest nextRequest();
    IRequestResponder nextResponder();
    String path();
    Object payload();
    String type();
    void setPayload(Object newPayload);
    void processNext(Object payload);

}

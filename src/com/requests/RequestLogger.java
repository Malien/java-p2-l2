package com.requests;

import com.util.Logger;

public class RequestLogger implements IRequestResponder {

    private static Logger log = new Logger("RequestLogger");

    @Override
    public void respond(IRequest request) {
        log.info(" request:" + request +
                ", type:" + request.type() +
                ", path:" + request.path() +
                ", payload:" + request.payload() +
                ", next:" + request.nextRequest() +
                ", nextRes: " + request.nextResponder()
        );
    }
}

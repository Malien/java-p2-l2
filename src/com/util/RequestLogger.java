package com.util;

import com.requests.IRequest;
import com.requests.IRequestResponder;

public class RequestLogger extends Logger implements IRequestResponder {

    public RequestLogger(String name) {
        super(name);
    }

    public String toMessage(IRequest request){
        return " request:" + request +
                ", type:" + request.type() +
                ", path:" + request.path() +
                ", payload:" + request.payload() +
                ", next:" + request.nextRequest() +
                ", nextRes: " + request.nextResponder();
    }

    public void info(IRequest request) {
        info(toMessage(request));
    }

    public void warn(IRequest request) {
        warn(toMessage(request));
    }

    public void error(IRequest request) {
        error(toMessage(request));
    }

    public void fatal(IRequest request) {
        fatal(toMessage(request));
    }

    @Override
    public void respond(IRequest request) {
        info(request);
    }
}

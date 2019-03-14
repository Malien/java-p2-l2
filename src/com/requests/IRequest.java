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

    IRequest EMPTY = new IRequest() {
        private Object payload;
        @Override
        public IRequest nextRequest() {
            return null;
        }

        @Override
        public IRequestResponder nextResponder() {
            return null;
        }

        @Override
        public String path() {
            return null;
        }

        @Override
        public Object payload() {
            return payload;
        }

        @Override
        public String type() {
            return null;
        }

        @Override
        public void setPayload(Object newPayload) {
            this.payload = newPayload;
        }

        @Override
        public void processNext(Object payload) {

        }
    };

}

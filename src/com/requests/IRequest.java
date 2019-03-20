package com.requests;

/**
 * Interface for async-like queue object
 * @param <T> type of parameter to process
 */
public interface IRequest<T> {

    String path();
    T payload();
    byte type();
    Callback callback();

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

package com.requests;

public interface IRequest {

    /* Request-based concurrency model developer guide

    UI event    Request1        Database      Request2         UI
    handler  ----------------->  manager ------------------> updater
                           (IRequestHandler)            (IRequestHandler)

                Request1                     Request2
               type: get                    type: update
               path: path to entry          path: element id/name
            payload: null                payload: entry from db
               next: Request2               next: null
           next res: UI updater         next res: null

                     Looks like HTTP, doesn't it?
     */

    IRequest nextRequest();
    IRequestResponder nextResponder();
    String path();
    Object payload();
    String type();
    void setPayload(Object newPayload);
    void processNext(Object payload);

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

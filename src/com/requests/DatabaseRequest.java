package com.requests;

public class DatabaseRequest implements IRequest {

    private Object payload;
    private String path;
    private String type;
    private IRequestResponder nextResponder;
    private IRequest next;

//    /**
//     * Constructor for requesting database entries
//     * @param payload payload to be processed by the database.
//     * @param type type of the request.
//     *             "get" for retrieving database entry
//     *             "set" for replacing database entry
//     *             "add" for appending entry to database
//     * @param path path at which request should be operated upon
//     * @param next request to be formed. If data needs to be redirected or consecutive call
//     *             should be made after this one
//     * @param nextResponder next responder to which next request should be forwarded to
//     */
//    public DatabaseRequest(@Nullable Object payload,
//                           String type,
//                           String path,
//                           @Nullable IRequest next,
//                           @Nullable IRequestResponder nextResponder) {
//        this.next = next;
//        this.payload = payload;
//        this.type = type;
//        this.path = path;
//        this.nextResponder = nextResponder;
//    }

    /**
     * Constructor for database "get" request
     * @param path path at which request should be operated upon
     * @param next request to be formed after completion. If data needs to be redirected or consecutive call
     *             should be made after this one
     * @param nextResponder next responder to which next request should be forwarded to.
     */
    private DatabaseRequest(String path, IRequest next, IRequestResponder nextResponder) {
        this.next = next;
        this.type = "get";
        this.path = path;
        this.nextResponder = nextResponder;
    }

    /**
     * Constructor for database "set" and "add" request
     * @param payload payload to be processed by the database.
     * @param type type of the request.
     *             "set" for replacing database entry
     *             "add" for appending entry to database
     * @param path path at which request should be operated upon
     */
    private DatabaseRequest(Object payload, String type, String path) {
        this.payload = payload;
        this.type = type;
        this.path = path;
    }

    /**
     * Create database "get" request
     * @param path path at which request should be operated upon
     * @param next request to be formed after completion. If data needs to be redirected or consecutive call
     *             should be made after this one
     * @param nextResponder next responder to which next request should be forwarded to.
     */
    public static DatabaseRequest get(String path, IRequest next, IRequestResponder nextResponder){
        return new DatabaseRequest(path, next, nextResponder);
    }

    /**
     * Create database "set" request for a product
     * @param payload product payload to be processed by the database.
     * @param path path at which request should be operated upon
     */
    public static DatabaseRequest set(Object payload, String path){
        return new DatabaseRequest(payload, "set", path);
    }

    /**
     * Create database "add" request for a product
     * @param payload product payload to be processed by the database.
     * @param path path at which request should be operated upon
     */
    public static DatabaseRequest add(Object payload, String path){
        return new DatabaseRequest(payload, "add", path);
    }

    @Override
    public void processNext(Object payload){
        IRequest nextReq = this.nextRequest();
        nextReq.setPayload(payload);
        nextResponder().respond(nextReq);
    }

    @Override
    public IRequest nextRequest() {
        return next;
    }

    @Override
    public Object payload() {
        return payload;
    }

    @Override
    public void setPayload(Object newPayload) {
        payload = newPayload;
    }

    @Override
    public String type() {
        return type;
    }

    @Override
    public IRequestResponder nextResponder() {
        return nextResponder;
    }

    @Override
    public String path() {
        return path;
    }
}

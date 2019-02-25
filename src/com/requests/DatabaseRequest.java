package com.requests;

import com.sun.istack.internal.Nullable;

public class DatabaseRequest<E> implements IRequest<E> {

    private E payload;
    private String type;
    private String path;
    private IRequestResponder nextResponder;
    private IRequest next;

    /**
     * Constructor for requesting database entries
     * @param payload payload to be processed by the database.
     * @param type type of the request.
     *             "get" for retrieving database entry
     *             "set" for replacing database entry
     *             "add" for appending entry to database
     * @param path path at which request should be operated upon
     * @param next request to be formed. If data needs to be redirected or consecutive call
     *             should be made after this one
     * @param nextResponder next responder to which next request should be forwarded to
     */
    public DatabaseRequest(@Nullable E payload,
                           String type,
                           String path,
                           @Nullable IRequest next,
                           @Nullable IRequestResponder nextResponder) {
        this.next = next;
        this.payload = payload;
        this.type = type;
        this.path = path;
        this.nextResponder = nextResponder;
    }
    /**
     * Constructor for database "get" request
     * @param path path at which request should be operated upon
     * @param next request to be formed after completion. If data needs to be redirected or consecutive call
     *             should be made after this one
     * @param nextResponder next responder to which next request should be forwarded to.
     */
    public DatabaseRequest(String path, IRequest next, IRequestResponder nextResponder) {
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
    public DatabaseRequest(E payload, String type, String path) {
        this.payload = payload;
        this.type = type;
        this.path = path;
    }

    @Override
    public IRequest nextRequest() {
        return next;
    }

    @Override
    public E payload() {
        return payload;
    }

    @Override
    public void setPayload(E newPayload) {
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
}

package com.components;

import com.requests.DatabaseRequest;
import com.requests.IRequest;
import com.requests.IRequestResponder;

import java.util.concurrent.LinkedBlockingQueue;

public class DatabaseManager implements Runnable, IRequestResponder {

    private static final DatabaseManager instance = new DatabaseManager();
    private Thread dataThread = new Thread(this);

    //TODO: change Integer to object that will represent queue item
    //      and think about that arbitrary 512 queue depth
    private LinkedBlockingQueue<DatabaseRequest> queue = new LinkedBlockingQueue<>(512);

    private DatabaseManager() {
        dataThread.start();
    }

    public static DatabaseManager getInstance(){
        return instance;
    }

    @Override
    public void run() {
        //TODO: check for available requests and handle them
    }

    @Override
    public void respond(IRequest request) {
        //TODO: Implement request response
    }
}

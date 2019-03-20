package com.components;

import com.requests.DatabaseRequest;
import com.util.Logger;

import java.util.concurrent.LinkedBlockingQueue;

public class DatabaseManager implements Runnable {

    private static final DatabaseManager instance = new DatabaseManager();
    private Thread dataThread = new Thread(this);
    private Logger log = new Logger("DatabaseManager");

    //TODO: think about that arbitrary 512 queue depth
    private LinkedBlockingQueue<DatabaseRequest> queue = new LinkedBlockingQueue<>(512);

    private DatabaseManager() {
        dataThread.start();
    }

    public static DatabaseManager getInstance(){
        return instance;
    }

    @Override
    public void run() {

    }
}

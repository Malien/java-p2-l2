package com.components;

import com.data.Product;
import com.data.ProductGroup;
import com.requests.DatabaseRequest;
import com.requests.IRequest;
import com.requests.IRequestResponder;
import com.util.Logger;

import java.util.concurrent.LinkedBlockingQueue;

public class DatabaseManager implements Runnable, IRequestResponder {

    private static final DatabaseManager instance = new DatabaseManager();
    private Thread dataThread = new Thread(this);
    private Logger log = new Logger("DatabaseManager");

    //TODO: think about that arbitrary 512 queue depth
    private LinkedBlockingQueue<IRequest> queue = new LinkedBlockingQueue<>(512);

    private DatabaseManager() {
        dataThread.start();
    }

    public static DatabaseManager getInstance(){
        return instance;
    }

    @Override
    public void run() {
        try {
            while (true){
                IRequest request = queue.take();
                switch (request.type()){
                    //TODO: Write an actual implementation
                    case "get":
                        Thread.sleep(1000);
                        log.info(request.path());
                        request.processNext(new Product(request.path(), "", "", 2, 1));
                        break;
                    case "set":
                        Thread.sleep(2000);
                        log.info(request.path());
                        break;
                    case "add":
                        Thread.sleep(2500);
                        log.info(request.path());
                        break;
                    default:
                        log.error("Request "+request
                                +" contains type that can't be processed. How could this happen?");
                }
            }
        } catch (InterruptedException e){
            log.error(e.toString());
        }
    }

    @Override
    public void respond(IRequest request) {
        if (!(request instanceof DatabaseRequest)) {
            log.warn("Received request "+ request +" is not of type DatabaseRequest. Could not grant compatibility");
            if (!(request.type().equals("get") || request.type().equals("set") || request.type().equals("add"))){
                log.error("Received request"+ request + "do not satisfy type requirements");
                return;
            }
            if (!(request.payload() instanceof Product || request.payload() instanceof ProductGroup)){
                log.error("Received request"+ request + "do not satisfy payload type requirements");
                return;
            }
        }
        try {
            queue.put(request);
        } catch (InterruptedException e){
            log.error(e.toString());
        }
    }
}

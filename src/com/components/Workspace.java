package com.components;

import com.data.Product;
import com.requests.DatabaseRequest;
import javafx.application.Application;
import javafx.stage.Stage;

public class Workspace extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        DatabaseManager manager = DatabaseManager.getInstance();
        //TODO: set up application
        DatabaseRequest req1 = DatabaseRequest.add(null, "/somewhere");
        DatabaseRequest req2 = DatabaseRequest.get("/whatever", req1, manager);
        DatabaseRequest req3 = DatabaseRequest.set(
                new Product("","","", 3, 2),
                "/whatever/eee"
        );
        manager.respond(req2);
        manager.respond(req3);
    }
}

package com.components;

import com.data.Product;
import com.data.ProductGroup;
import com.requests.DatabaseRequest;
import javafx.application.Application;
import javafx.stage.Stage;

public class Workspace extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        DatabaseManager manager = DatabaseManager.getInstance();
        manager.respond(DatabaseRequest.changeDir("/Users/user/Documents/Development/Study/Java P2/L2/test_data"));
        //TODO: set up application
        ProductGroup group = new ProductGroup("group1");
        group.add(new Product("item1", "item1 description", "item1 manufacturer", 1, 1.5));
        group.add(new Product("item2", "item2 description", "item2 manufacturer", 2, 3));
        group.add(new Product("item3", "item3 description", "item3 manufacturer", 3, 4.5));
        manager.respond(DatabaseRequest.set(group, "/group1"));
    }
}

package com.components;

import com.data.Product;
import com.data.ProductGroup;
import com.requests.Callback;
import com.requests.DatabaseRequest;
import com.util.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class DatabaseManager implements Runnable {

    /* ****File structure****
    workspacePath:
        group1.prg -- product group 1
        group2.prg -- product group 2

    .prg file structure:
    |Product|Product|... EOF

    Product
    parameters: ||  amount ||  price  ||   name    |  0x0   || manufacturer |  0x0   || description |  0x0   ||
    block len:  || 4 bytes || 4 bytes || variable  | 1 byte ||   variable   | 1 byte ||   variable  | 1 byte ||
     */

    private static final DatabaseManager instance = new DatabaseManager();
    private Thread dataThread = new Thread(this, "DatabaseManager");
    private Logger log = new Logger("DatabaseManager");
    private String workspacePath = "/";

    //TODO: think about that arbitrary 512 queue depth
    private LinkedBlockingQueue<DatabaseRequest> queue = new LinkedBlockingQueue<>(512);

    private DatabaseManager() {
        dataThread.start();
    }

    public static DatabaseManager getInstance() {
        return instance;
    }

    private File[] workspaceFiles() {
        FileFilter filter = (File pathname) -> pathname.getName().endsWith(".prg") && pathname.isFile();
        File workspaceFolder = new File(workspacePath);
        return workspaceFolder.listFiles(filter);
    }

    private boolean changeDir(String path) {
        File wrksp = new File(path);
        if (!wrksp.isDirectory()) return false;
        workspacePath = path;
        return true;
    }

    private ProductGroup[] getProductGroups() throws IOException {
        ArrayList<ProductGroup> groups = new ArrayList<>();
        for (File file : workspaceFiles()) {
            groups.add(getProductGroup(file));
        }
        return groups.toArray(new ProductGroup[0]);
    }

    private ProductGroup getProductGroup(File file) throws IOException {
        FileInputStream fileStream = new FileInputStream(file);
        DataInputStream data = new DataInputStream(fileStream);
        String filename = file.getName();
        //TODO: fix for changed ProductGroup constructor needed
        ProductGroup group = new ProductGroup(filename.substring(0, filename.length() - 4));
        while (true) {
            try {
                int amount = data.readInt();
                float price = data.readFloat();
                String name = data.readUTF();
                String manufacturer = data.readUTF();
                String description = data.readUTF();
                Product prd = new Product(name, manufacturer, description, amount, price);
                group.add(prd);
            } catch (EOFException e) {
                break;
            }
        }

        return group;
    }

    private ProductGroup getProductGroup(String path) throws IOException {
        try {
            File groupFile = new File(workspacePath + "/" + path + ".prg");
            return getProductGroup(groupFile);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    private boolean setProductGroup(String path, ProductGroup group) throws IOException {
        File found = null;
        for (File file : workspaceFiles()) {
            if (file.getName().equals(path + ".prg")) {
                found = file;
            }
        }

        //TODO: Write something more efficient than just deleting and recreating whole file (path isn't used properly btw)
        if (group == null) {
            found.delete();
            return true;
        }
        if (found == null) {
            found = new File(workspacePath + "/" + path + ".prg");
            found.createNewFile();
        }

        DataOutputStream stream = new DataOutputStream(new FileOutputStream(found));
        for (Product product : group.getProducts()) {
            stream.writeInt(product.getCount());
            stream.writeFloat((float) product.getPrice());
            stream.writeUTF(product.getName());
            stream.writeUTF(product.getManufacturer());
            stream.writeUTF(product.getDescription());
        }
        stream.close();
        return true;
    }

    public void get(String path, Callback<ProductGroup> callback) throws InterruptedException {
        queue.put(DatabaseRequest.get(path, callback));
    }

    public void getAll(Callback<ProductGroup[]> callback) throws InterruptedException {
        queue.put(DatabaseRequest.getAll(callback));
    }

    public void set(ProductGroup group, Callback<Boolean> callback) throws InterruptedException {
        queue.put(DatabaseRequest.set(group, callback));
    }

    public void set(ProductGroup group) throws InterruptedException {
        queue.put(DatabaseRequest.set(group));
    }

    public void setPath(String path, Callback<Boolean> callback) throws InterruptedException {
        queue.put(DatabaseRequest.setPath(path, callback));
    }

    public void setPath(String path) throws InterruptedException {
        queue.put(DatabaseRequest.setPath(path));
    }

    public void delete(String path, Callback<Boolean> callback) throws InterruptedException {
        queue.put(DatabaseRequest.delete(path, callback));
    }

    public void delete(String path) throws InterruptedException {
        queue.put(DatabaseRequest.delete(path));
    }

    @Override
    public void run() {
        while (dataThread.isAlive()) {
            try {
                DatabaseRequest request = queue.take();
                switch (request.type()) {
                    case DatabaseRequest.GET:
                        request.callback().call(getProductGroup(request.path()));
                        break;
                    case DatabaseRequest.GET_ALL:
                        request.callback().call(getProductGroups());
                        break;
                    case DatabaseRequest.SET:
                        request.callback().call(setProductGroup(request.payload().getName(), request.payload()));
                        break;
                    case DatabaseRequest.SET_PATH:
                        request.callback().call(changeDir(request.path()));
                        break;
                    case DatabaseRequest.DELETE:
                        request.callback().call(setProductGroup(request.path(), null));
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

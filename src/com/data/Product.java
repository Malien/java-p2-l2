package com.data;

public class Product {

    private String name;
    private String description;
    private String manufacturer;
    private int count;
    private double price;

    public Product(String name, String description, String manufacturer, int count, double price) {
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.count = count;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void productRefactor(String name, String description, String manufacturer, double price) {
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void incrementCount(){
        count++;
    }

    public void incrementCount(int inc){
        count+=inc;
    }
    public void decrementCount(){
        count--;
    }

    public boolean ableToSubtract(int subs){
        return count >= subs;
    }

    public void decrementCount(int dec){
        count -= dec;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name;
    }
}

package com.data;

public class Product {

    private String name;
    private String description;
    private String manufacturer;
    private int count;
    private double price;
    private int produced;
    private int sold;
    private int writtenOff;

    public Product(String name, String manufacturer, String description, int count, double price) {
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

    public String getDescription() {
        return description;
    }

    public String getManufacturer() {
        return manufacturer;
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

    public void incrementCount() {
        count++;
    }

    public void incrementCount(int inc) {
        count += inc;
    }

    public void decrementCount() {
        count--;
    }

    public boolean ableToSubtract(int subs) {
        return count >= subs;
    }

    public void decrementCount(int dec) {
        count -= dec;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name;
    }

    public void addProduced(int produced) {
        this.produced += produced;
    }

    public void addSold(int sold) {
        this.sold += sold;
    }

    public void addWrittenOff(int writtenOff) {
        this.writtenOff += writtenOff;
    }

    public int getProduced() {
        return produced;
    }

    public int getSold() {
        return sold;
    }

    public int getWrittenOff() {
        return writtenOff;
    }
}

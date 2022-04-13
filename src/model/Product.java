package model;

import javafx.collections.ObservableList;

/**
 *
 * @author Subarna Karki
 */
public class Product {
    private int id;
    private String name;
    private int stock;
    private double price;
    private int max;
    private int min;
    private ObservableList<Part> associatedParts;

    public Product(int id, String name, int stock, double price, int max, int min, ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    public boolean deleteAssociatedPart(Part part) {
        return associatedParts.remove(part);
    }
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}

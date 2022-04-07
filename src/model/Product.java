package model;

import javafx.collections.FXCollections;
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
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    public Product(int id, String name, int stock, double price, int max, int min, ObservableList<Part> associatedParts) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.max = max;
        this.min = min;
        this.associatedParts = associatedParts;
    }

    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
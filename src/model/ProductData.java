package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductData {
    private static ObservableList<Product> products = FXCollections.observableArrayList();
    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();



    public static ObservableList<Product> getProducts() {
        return products;
    }

    public static void setProducts(ObservableList<Product> products) {
        ProductData.products = products;
    }

    public static void addProduct(Product product) {
        products.add(product);
    }
    public static ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    public static void setAssociatedParts(ObservableList<Part> associatedParts) {
        ProductData.associatedParts = associatedParts;
    }
    public static void addAssociatedPart(Part associatedPart) {
        associatedParts.add((Part) associatedPart);
    }
}

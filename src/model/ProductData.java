package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductData {
    private static ObservableList<Product> products = FXCollections.observableArrayList();

    public static ObservableList<Product> getProducts() {
        return products;
    }

    public static void setProducts(ObservableList<Product> products) {
        ProductData.products = products;
    }

    public static void addProduct(Product product) {
        products.add(product);
    }

    public static boolean modify(int id, Product modifiedProduct) {
        int index = 0;
        for(Product product : ProductData.getProducts()) {
            if(product.getId() == id) {
                ProductData.getProducts().set(index, modifiedProduct);
                return true;
            }
            index++;
        }
        return false;
    }

    public static ObservableList<Part> getAssociatedPartsForProduct(int id) {
        for(Product product : ProductData.getProducts()) {
            if(product.getId() == id) {
                return product.getAssociatedParts();
            }
        }
//        ObservableList<Part> associatedParts = FXCollections.observableArrayList();
        return null;
    }
}

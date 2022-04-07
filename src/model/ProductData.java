package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductData {
    private static ObservableList<Product> products = FXCollections.observableArrayList();
    private static ObservableList<Product> associatedProducts = FXCollections.observableArrayList();

    public static ObservableList<Product> getProducts() {
        return products;
    }

    public static void setProducts(ObservableList<Product> products) {
        ProductData.products = products;
    }

    public static void addProduct(Product product) {
        products.add(product);
    }
    public static ObservableList<Product> getAssociatedProducts() {
        return associatedProducts;
    }
//
    public static void setAssociatedProducts(ObservableList<Product> associatedProducts) {
        ProductData.associatedProducts = associatedProducts;
    }
    public static void addAssociatedProduct(Product associatedProduct) {
        associatedProducts.add(associatedProduct);
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
}

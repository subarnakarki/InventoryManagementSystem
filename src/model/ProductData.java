package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ProductData {
    private static ObservableList<Product> products = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

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
        return null;
    }

    public static  ObservableList<Product> getFilteredProducts() {
        return filteredProducts;
    }

    public static ObservableList<Product> filterProductsWithText(String searchText) {
        if (!(getFilteredProducts().isEmpty())) {
            // clear list
            getFilteredProducts().clear();
        }
        for (Product product : getProducts()) {
            if(product.getName().contains(searchText)) {
                getFilteredProducts().add(product);
            }
        }
        if (getFilteredProducts().isEmpty()) {
            return getProducts();
        } else {
            return getFilteredProducts();
        }
    }
    public static ObservableList<Product> filterProductsWithId(int id) {
        if (!(getFilteredProducts().isEmpty())) {
            getFilteredProducts().clear();
        }
        for (Product product : getProducts()) {
            if(product.getId() == id) {
                getFilteredProducts().add(product);
            }
        }
        if (getFilteredProducts().isEmpty()) {
            return getProducts();
        } else {
            return getFilteredProducts();
        }
    }
    public static void search(String searchText, TableView tableView) {
        System.out.println(searchText);
        try {
            int id = Integer.parseInt(searchText);
            tableView.setItems(filterProductsWithId(id));
            tableView.setItems(getFilteredProducts());
        } catch (NumberFormatException error) {
            tableView.setItems(filterProductsWithText(searchText));
            tableView.setItems(getFilteredProducts());
        }
    }
}

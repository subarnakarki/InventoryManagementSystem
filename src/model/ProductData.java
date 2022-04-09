package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ProductData {
    private static ObservableList<Product> products = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();

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

    public static  ObservableList<Part> getFilteredParts() {
        return filteredParts;
    }

    public static ObservableList<Part> filterParts(String searchText) {
        if (!(PartData.getFilteredParts().isEmpty())) {
            // clear list
            PartData.getFilteredParts().clear();
        }
        for (Part part : PartData.getAllParts()) {
            if(part.getName().contains(searchText)) {
                PartData.getFilteredParts().add(part);
            }
        }
        if (PartData.getFilteredParts().isEmpty()) {
            return PartData.getAllParts();
        } else {
            return PartData.getFilteredParts();
        }
    }

    public static void search(String searchText, TableView tableView) {
        try {
            int id = Integer.parseInt(searchText);
            tableView.setItems(filterParts(searchText));
            tableView.setItems(getFilteredParts());
        } catch (NumberFormatException error) {
            tableView.setItems(filterParts(searchText));
            tableView.setItems(getFilteredParts());
        }
    }
}

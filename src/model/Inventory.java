package model;

import controller.ModifyPartController;
import controller.ModifyProductController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/** This class contains all inventory data*/
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partId = 0;
    private static int productId = 0;
    public static int generateProductId() {
        productId = productId + 1;
        return productId;
    }

    public void navigateToScreen(ActionEvent actionEvent, String path) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource(path));
        stage.setScene(new Scene((Parent) scene));
        stage.setTitle(path.substring(path.lastIndexOf("/" ) + 1, ( path.lastIndexOf("." ))));
        stage.show();
    }

    public static int generatePartId() {
        partId = partId + 1;
        return partId;
    }

    public void sendDataAndLoadPage(ActionEvent actionEvent, TableView tableView, String path, String dataType) throws IOException {
        if (dataType == "parts") {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(path));
            loader.load();

            ModifyPartController modifyPartController = loader.getController();
            modifyPartController.sendPart((Part) tableView.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene((scene)));
            stage.setTitle("Modify Part Form");
            stage.show();
        } else if (dataType == "products") {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(path));
            loader.load();

            ModifyProductController modifyProductController = loader.getController();
            modifyProductController.sendProduct((Product) tableView.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene((scene)));
            stage.setTitle("Modify Product Form");
            stage.show();
        }
    }
    public boolean createAlert(Alert.AlertType alertType, String title, String context) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(context);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() &&  result.get() == ButtonType.OK) {
            return true;
        }
        return false;
    }


    public static class PartData {
//        private static ObservableList<Part> allParts = FXCollections.observableArrayList();
        private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();

        public static void addPart(Part part) {
            allParts.add(part);
        }
        public static boolean deletePart(int id) {
            for(Part part : allParts) {
                if(part.getId() == id) {
                    return allParts.remove(part);
                }
            }
            return false;
        }

        public static  ObservableList<Part> getAllParts() {
            return allParts;
        }

        public static  ObservableList<Part> getFilteredParts() {
            return filteredParts;
        }

        public static ObservableList<Part> filterPartsWithId(int id) {
            if (!(getFilteredParts().isEmpty())) {
                getFilteredParts().clear();
            }
            for (Part part : allParts) {
                if(part.getId() == id) {
                    getFilteredParts().add(part);
                }
            }
            if (getFilteredParts().isEmpty()) {
                return allParts;
            } else {
                return getFilteredParts();
            }
        }

        public static ObservableList<Part> filterPartsWithText(String searchText) {
            if (!(getFilteredParts().isEmpty())) {
                // clear list
                getFilteredParts().clear();
            }
            for (Part part : allParts) {
                if(part.getName().contains(searchText)) {
                    getFilteredParts().add(part);
                }
            }
            if (getFilteredParts().isEmpty()) {
                return allParts;
            } else {
                return getFilteredParts();
            }
        }

        public static void search(String searchText, TableView tableView) {
            try {
                int id = Integer.parseInt(searchText);
                tableView.setItems(filterPartsWithId(id));
                tableView.setItems(getFilteredParts());
            } catch (NumberFormatException error) {
                tableView.setItems(filterPartsWithText(searchText));
                tableView.setItems(getFilteredParts());
            }
        }

        public static boolean modify(int id, Part modifiedPart) {
            int index = 0;
            for(Part part : allParts) {
                if(part.getId() == id) {
                    allParts.set(index, modifiedPart);
                    return true;
                }
                index++;
            }
            return false;
        }
    }

    public static class ProductData {
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
                    return product.getAllAssociatedParts();
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
}

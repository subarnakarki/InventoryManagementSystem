package model;

import controller.ModifyPartController;
import controller.ModifyProductController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/** This class contains all inventory data*/
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
    private static int partId = 0;
    private static int productId = 0;

    public static int generateProductId() {
        productId = productId + 1;
        return productId;
    }
    /** This method generates navigates to a new screen based on the path that is passed in.
     * @param path the path to the screen
     * @param actionEvent the action even object which triggers the navigation
     * @exception  IOException the io exception
     */
    public void navigateToScreen(ActionEvent actionEvent, String path) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource(path));
        stage.setScene(new Scene((Parent) scene));
        stage.setTitle(splitCamelCase(path.substring(path.lastIndexOf("/" ) + 1, ( path.lastIndexOf("." )))));
        stage.show();
    }

    /** This method generates a part id
     * @return returns a new unique ID for a part
     */
    public static int generatePartId() {
        partId = partId + 1;
        return partId;
    }
    /** This method replaces camel cased text with a space. Used to help set titles for all forms
     @param s The string to split
     @return returns a string that has camelcase split with a space
     */
    public static String splitCamelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }
    /** This method is used to send data from one controller to another and then load the new page
     * @param actionEvent the action even object which triggers the navigation
     * @param tableView the table view to get the selected part or product
     * @param path the path to the new page
     * @param dataType the type of data to load, either "parts" or "products"
     * @exception  IOException the io exception
     */
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
    /** This method is used to send data from one controller to another and then load the new page
     * @param alertType the alertType that should be displayed
     * @param title the title of the alert
     * @param context the text to be displayed in the alert
     * @return return true if the alert has a result and the user pressed OK, or else returns false
     */
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

    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    /** This method adds a part to all parts
     * @param part the part to add
     * */
    public static void addPart(Part part) {
        allParts.add(part);
    }
    /** This method deletes a part
     * @param id id of the part to delete
     * @return return true of part is deleted or else return false
     * */
    public static boolean deletePart(int id) {
        for(Part part : allParts) {
            if(part.getId() == id) {
                return allParts.remove(part);
            }
        }
        return false;
    }
    /** This method gets all parts
     * @return returns an observable list of all parts
     * */
    public static  ObservableList<Part> getAllParts() {
        return allParts;
    }
    /** This method gets all filtered parts
     * @return returns an observable list of filtered parts
     * */
    public static  ObservableList<Part> getFilteredParts() {
        return filteredParts;
    }
    /** This method filters parts by id
     * @param id id of part to search
     * @return returns an observable list of filtered parts
     * */
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
    /** This method filters parts by text
     * @param searchText text of the part to search
     * @return returns an observable list of filtered parts
     * */
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
    /** This method searches for a part
     * @param searchText text of the part to search
     * @param tableView the table view to search parts on
     * @param label the label that needs to be modified
     * */
    public static void search(String searchText, TableView tableView, Label label) {
        try {
            int id = Integer.parseInt(searchText);
            tableView.setItems(filterPartsWithId(id));
            tableView.setItems(getFilteredParts());
        } catch (NumberFormatException error) {
            tableView.setItems(filterPartsWithText(searchText));
            tableView.setItems(getFilteredParts());
        }
        if (getFilteredParts().isEmpty()) {
            label.setText("no parts found");
        } else {
            label.setText("");
        }
    }
    /** This method searches for a product
     * @param id the id of the part to modify
     * @param modifiedPart the part to modify
     * @return boolean, true if successful and false if not successful
     * */
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

    /** This method gets all products
     * @return returns all products
     * */
    public static ObservableList<Product> getProducts() {
        return allProducts;
    }
    /** This method adds a product to all parts
     * @param product the product to add
     * */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }
    /** This method searches for a product
     * @param id the id of the product to modify
     * @param modifiedProduct the product to modify
     * @return boolean that is true if the part was modified
     * */
    public static boolean modify(int id, Product modifiedProduct) {
        int index = 0;
        for(Product product : getProducts()) {
            if(product.getId() == id) {
                getProducts().set(index, modifiedProduct);
                return true;
            }
            index++;
        }
        return false;
    }
    /** This method gets all associated parts for a product
     * @param id the id of the product
     * @return returns all associated part
     * */
    public static ObservableList<Part> getAssociatedPartsForProduct(int id) {
        for(Product product : getProducts()) {
            if(product.getId() == id) {
                return product.getAllAssociatedParts();
            }
        }
        return null;
    }
    /** This method gets filtered products
     * @return returns  filtered products
     * */
    public static  ObservableList<Product> getFilteredProducts() {
        return filteredProducts;
    }
    /** This method filters products by text
     * @param searchText text of the products to search
     * @return returns an observable list of filtered products
     * */
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
    /** This method filters products by id
     * @param id id of products to search
     * @return returns an observable list of filtered products
     * */
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
    /** This method searches for a product
     * @param searchText text of the part to search
     * @param tableView the table view to search parts on
     * @param label the label that needs to be modified
     * */
    public static void searchProducts(String searchText, TableView tableView, Label label ) {
        try {
            int id = Integer.parseInt(searchText);
            tableView.setItems(filterProductsWithId(id));
            tableView.setItems(getFilteredProducts());
        } catch (NumberFormatException error) {
            tableView.setItems(filterProductsWithText(searchText));
            tableView.setItems(getFilteredProducts());
        }
        if (getFilteredProducts().isEmpty()) {
            label.setText("no products found");
        } else {
            label.setText("");
        }
    }

}

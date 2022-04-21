package controller;

import model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This class is this controller for the main form page*/
public class MainFormController implements Initializable {
    @FXML
    public TableView productsTableView;
    @FXML
    public Label searchProductsLabel;

    public Label getSearchProductsLabel() {
        return searchProductsLabel;
    }

    public void setSearchProductsLabel(Label searchProductsLabel) {
        this.searchProductsLabel = searchProductsLabel;
    }

    public Label getSearchPartsLabel() {
        return searchPartsLabel;
    }

    public void setSearchPartsLabel(Label searchPartsLabel) {
        this.searchPartsLabel = searchPartsLabel;
    }

    @FXML
    public Label searchPartsLabel;
    @FXML
    private TextField searchPartsTextField;
    @FXML
    private TextField searchProductsTextField;
    @FXML
    private TableView partsTableView;
    @FXML
    private TableColumn partIdColumn;
    @FXML
    private TableColumn partNameColumn;
    @FXML
    private TableColumn partInventorColumn;
    @FXML
    private TableColumn partPriceColumn;
    @FXML
    private TableColumn productIdColumn;
    @FXML
    private TableColumn productNameColumn;
    @FXML
    private TableColumn productInventoryColumn;
    @FXML
    private TableColumn productPriceColumn;
    Inventory inventory = new Inventory();

    /** This method triggers when the add part button is clicked
     * @param actionEvent the action event
     * */
    public void onActionAddPart(ActionEvent actionEvent) throws IOException {
        inventory.navigateToScreen(actionEvent, "/view/AddPartForm.fxml");
    }
    /** This method triggers when the modify part button is clicked
     * @param actionEvent the action event
     * */
    public void onActionModifyPart(ActionEvent actionEvent) throws IOException {
        if(partsTableView.getSelectionModel().getSelectedIndex() > -1) {
            inventory.sendDataAndLoadPage(actionEvent, partsTableView, "/view/ModifyPartForm.fxml", "parts");
        } else {
            inventory.createAlert( Alert.AlertType.WARNING,"Part Not Selected", "Select part to modify");
        }
    }
    /** This method triggers when the delete part button is clicked
     * @param actionEvent the action event
     * */
    public void onActionDeletePart(ActionEvent actionEvent) throws IOException {
        if(partsTableView.getSelectionModel().getSelectedIndex() > -1) {
            Part part = (Part) partsTableView.getSelectionModel().getSelectedItem();
            boolean deletePart = inventory.createAlert( Alert.AlertType.CONFIRMATION,"Delete Part Confirmation", "Are you sure you want to delete the " + part.getName() + "?");
            if(deletePart == true) {
                Inventory.deletePart(part.getId());
            }
        } else {
            inventory.createAlert( Alert.AlertType.WARNING,"Part Not Selected", "Select part to delete");
        }
    }
    /** This method triggers when the add product button is clicked
     * @param actionEvent the action event
     * */
    public void onActionAddProduct(ActionEvent actionEvent) throws IOException {
        inventory.navigateToScreen(actionEvent, "/view/AddProductForm.fxml");
    }
    /** This method triggers when the modify product button is clicked
     * @param actionEvent the action event
     * */
    public void onActionModifyProduct(ActionEvent actionEvent) throws IOException {
        if(productsTableView.getSelectionModel().getSelectedIndex() > -1) {
            inventory.sendDataAndLoadPage(actionEvent, productsTableView, "/view/ModifyProductForm.fxml", "products");
        } else {
            inventory.createAlert( Alert.AlertType.WARNING,"Product Not Selected", "Select product to modify");
        }
    }
    /** This method triggers when the exit button is clicked
     * @param actionEvent the action event
     * */
    public void onActionExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    /** This method is called when the page loads
     * @param url the url
     * @param resourceBundle the resource bundle
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTableView.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventorColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTableView.setItems(Inventory.getProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /** This method triggers when the delete product button is clicked
     * @param actionEvent the action event
     * */
    public void onActionDeleteProduct(ActionEvent actionEvent) {
        try {
            Product selectedProduct = (Product) productsTableView.getSelectionModel().getSelectedItem();
            boolean deleteProduct = inventory.createAlert( Alert.AlertType.CONFIRMATION,"Delete Part Confirmation", "Are you sure you want to delete the " + selectedProduct.getName() + "?");
            boolean productHasAssociatedParts = true;
            productHasAssociatedParts = selectedProduct.getAllAssociatedParts() != null && !selectedProduct.getAllAssociatedParts().isEmpty();
            if(deleteProduct == true) {
                if(!productHasAssociatedParts) {
                    for(Product product : Inventory.getProducts()) {
                        if (product.getId() == selectedProduct.getId()) {
                            Inventory.getProducts().remove(product);
                            break;
                        }
                    }
                } else {
                    inventory.createAlert(Alert.AlertType.ERROR, "Product Delete Failure", "Cannot delete a product that has associated parts");
                }
            }
        } catch (NullPointerException error) {
            inventory.createAlert(Alert.AlertType.WARNING, "Product Delete Failure", "Select a product to delete");
        }

    }
    /** This method triggers when the product search text field has new text
     * @param ketEvent the action event
     * */
    public void onActionSearchProduct(KeyEvent ketEvent) {
        String searchText = searchProductsTextField.getText();
        Inventory.searchProducts(searchText, productsTableView, searchProductsLabel);
    }
    /** This method triggers when the part search text field has new text
     * @param ketEvent the action event
     * */
    public void onActionSearchPart(KeyEvent ketEvent) {
        String searchText = searchPartsTextField.getText();
        Inventory.search(searchText, partsTableView, searchPartsLabel);
    }
}

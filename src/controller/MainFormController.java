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

public class MainFormController implements Initializable {
    @FXML
    public TableView productsTableView;
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


    public void onActionAddPart(ActionEvent actionEvent) throws IOException {
        inventory.navigateToScreen(actionEvent, "/view/AddPartForm.fxml");
    }

    public void onActionModifyPart(ActionEvent actionEvent) throws IOException {
        if(partsTableView.getSelectionModel().getSelectedIndex() > -1) {
            inventory.sendDataAndLoadPage(actionEvent, partsTableView, "/view/ModifyPartForm.fxml", "parts");
        } else {
            inventory.createAlert( Alert.AlertType.WARNING,"Part Not Selected", "Select part to modify");
        }
    }

    public void onActionDeletePart(ActionEvent actionEvent) throws IOException {
        if(partsTableView.getSelectionModel().getSelectedIndex() > -1) {
            Part part = (Part) partsTableView.getSelectionModel().getSelectedItem();
            boolean deletePart = inventory.createAlert( Alert.AlertType.CONFIRMATION,"Delete Part Confirmation", "Are you sure you want to delete the " + part.getName() + "?");

            if(deletePart == true) {
                Inventory.PartData.deletePart(part.getId());
                System.out.println(part instanceof InHousePart ? "Inhouse part deleted" : "Outsourced Part deleted");
            }
        } else {
            inventory.createAlert( Alert.AlertType.WARNING,"Part Not Selected", "Select part to delete");
        }
    }

    public void onActionAddProduct(ActionEvent actionEvent) throws IOException {
        inventory.navigateToScreen(actionEvent, "/view/AddProductForm.fxml");
    }

    public void onActionModifyProduct(ActionEvent actionEvent) throws IOException {
        if(productsTableView.getSelectionModel().getSelectedIndex() > -1) {
            inventory.sendDataAndLoadPage(actionEvent, productsTableView, "/view/ModifyProductForm.fxml", "products");
        } else {
            inventory.createAlert( Alert.AlertType.WARNING,"Product Not Selected", "Select product to modify");
        }
    }

    public void onActionExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTableView.setItems(Inventory.PartData.getAllParts());

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventorColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTableView.setItems(Inventory.ProductData.getProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }


    public void onActionDeleteProduct(ActionEvent actionEvent) {
        try {
            Product selectedProduct = (Product) productsTableView.getSelectionModel().getSelectedItem();
            boolean deleteProduct = inventory.createAlert( Alert.AlertType.CONFIRMATION,"Delete Part Confirmation", "Are you sure you want to delete the " + selectedProduct.getName() + "?");
            boolean productHasAssociatedParts = true;
            productHasAssociatedParts = selectedProduct.getAllAssociatedParts() != null && !selectedProduct.getAllAssociatedParts().isEmpty();
            if(deleteProduct == true) {
                if(!productHasAssociatedParts) {
                    for(Product product : Inventory.ProductData.getProducts()) {
                        if (product.getId() == selectedProduct.getId()) {
                            Inventory.ProductData.getProducts().remove(product);
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
    public void onActionSearchProduct(KeyEvent actionEvent) {
        String searchText = searchProductsTextField.getText();
        Inventory.ProductData.search(searchText, productsTableView);
    }

    public void onActionSearchPart(KeyEvent actionEvent) {
        String searchText = searchPartsTextField.getText();
        Inventory.ProductData.search(searchText, partsTableView);
    }
}

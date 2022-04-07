package controller;

import javafx.fxml.FXMLLoader;
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
    Helper helper = new Helper();


    public void onActionAddPart(ActionEvent actionEvent) throws IOException {
        helper.navigateToScreen(actionEvent, "/view/AddPartForm.fxml");
    }

    public void onActionModifyPart(ActionEvent actionEvent) throws IOException {
        if(partsTableView.getSelectionModel().getSelectedIndex() > -1) {
            helper.sendDataAndLoadPage(actionEvent, partsTableView, "/view/ModifyPartForm.fxml", "parts");
        } else {
            helper.createAlert( Alert.AlertType.WARNING,"Part Not Selected", "Select part to modify");
        }
    }

    public void onActionDeletePart(ActionEvent actionEvent) throws IOException {
        if(partsTableView.getSelectionModel().getSelectedIndex() > -1) {
            Part part = (Part) partsTableView.getSelectionModel().getSelectedItem();
            boolean deletePart = helper.createAlert( Alert.AlertType.CONFIRMATION,"Delete Part Confirmation", "Are you sure you want to delete the " + part.getName() + "?");

            if(deletePart == true) {
                PartData.deletePart(part.getId());
                System.out.println(part instanceof InHousePart ? "Inhouse part deleted" : "Outsourced Part deleted");
            }
        } else {
            helper.createAlert( Alert.AlertType.WARNING,"Part Not Selected", "Select part to delete");
        }
    }

    public void onActionAddProduct(ActionEvent actionEvent) throws IOException {
        helper.navigateToScreen(actionEvent, "/view/AddProductForm.fxml");
    }

    public void onActionModifyProduct(ActionEvent actionEvent) throws IOException {
        if(productsTableView.getSelectionModel().getSelectedIndex() > -1) {
            helper.sendDataAndLoadPage(actionEvent, productsTableView, "/view/ModifyProductForm.fxml", "products");
        } else {
            helper.createAlert( Alert.AlertType.WARNING,"Product Not Selected", "Select product to modify");
        }
    }

    public void onActionDeleteProduct(ActionEvent actionEvent) {
    }

    public void onActionExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTableView.setItems(PartData.getAllParts());

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventorColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTableView.setItems(ProductData.getProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    public void onActionSearchProduct(ActionEvent actionEvent) {

    }

    public void onActionSearchPart(KeyEvent actionEvent) {
        String searchText = searchPartsTextField.getText();
        PartData.search(searchText, partsTableView);
    }
}

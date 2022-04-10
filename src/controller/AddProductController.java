package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {
    public TextField searchPartsTextField;
    @FXML
    private TextField idTxt;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField invTxt;
    @FXML
    private TextField priceCostTxt;
    @FXML
    private TextField maxTxt;
    @FXML
    private TextField minTxt;
    @FXML
    private TableView allPartsTableView;
    @FXML
    private TableColumn allPartsIdColumn;
    @FXML
    private TableColumn allPartsNameColumn;
    @FXML
    private TableColumn allPartsInventorColumn;
    @FXML
    private TableColumn allPartsPriceColumn;
    @FXML
    private TableView addedPartsTableView;
    @FXML
    private TableColumn addedPartsIdColumn;
    @FXML
    private TableColumn addedPartsNameColumn;
    @FXML
    private TableColumn addedPartsInventorColumn;
    @FXML
    private TableColumn addedPartsPriceColumn;
    private static ObservableList<Part> tempAssociatedParts = FXCollections.observableArrayList();
    Inventory inventory = new Inventory();
    public void onActionAdd(ActionEvent actionEvent) {
        if(allPartsTableView.getSelectionModel().getSelectedIndex() > -1) {
            Part selectedPart = (Part) allPartsTableView.getSelectionModel().getSelectedItem();
            int selectedPartID = selectedPart.getId();
            boolean partAlreadyAdded = false;

            if (tempAssociatedParts.isEmpty()) {
                tempAssociatedParts.add(selectedPart);
            }
            for (Part part : tempAssociatedParts) {
                if (selectedPartID == part.getId()) {
                    partAlreadyAdded = true;
                }
            }
            if (!partAlreadyAdded) {
                tempAssociatedParts.add(selectedPart);
            }
        } else {
            inventory.createAlert( Alert.AlertType.WARNING,"Select a part", "Select a part to add");
        }
    }

    public void onActionRemoveAssociatedProduct(ActionEvent actionEvent) {
    }

    public void onActionSaveProduct(ActionEvent actionEvent) throws IOException {
        try {
            String name = nameTxt.getText();
            double price = Double.parseDouble(priceCostTxt.getText());
            int stock = Integer.parseInt(invTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            ObservableList<Part> associatedParts = FXCollections.observableArrayList();
            for (Part part : tempAssociatedParts) {
                associatedParts.add(part);
            }
            Inventory.ProductData.addProduct(new Product((int)(Inventory.generateProductId()), name, stock, price, max,min,associatedParts));
            inventory.navigateToScreen(actionEvent, "/view/MainForm.fxml");
        } catch(NumberFormatException error) {
            inventory.createAlert(Alert.AlertType.ERROR, "Invalid Input For Product", "Error: Please check all input for the product");
        }
    }

    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        inventory.navigateToScreen(actionEvent, "/view/MainForm.fxml");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allPartsTableView.setItems(Inventory.PartData.getAllParts());
        allPartsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInventorColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        tempAssociatedParts.clear();
        addedPartsTableView.setItems(tempAssociatedParts);
        addedPartsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addedPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addedPartsInventorColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addedPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void onActionRemoveAssociatedPart(ActionEvent actionEvent) {
        if (addedPartsTableView.getSelectionModel().getSelectedIndex() > -1) {
            Part part = (Part) addedPartsTableView.getSelectionModel().getSelectedItem();
            if (tempAssociatedParts.contains(part)) {
                tempAssociatedParts.remove(part);
            }
        } else {
            inventory.createAlert(Alert.AlertType.WARNING, "No Part Selected", "There are no parts selected to remove");
        }
    }

    public void onActionSearchPart(KeyEvent keyEvent) {
        String searchText = searchPartsTextField.getText();
        Inventory.PartData.search(searchText, allPartsTableView);
    }
}

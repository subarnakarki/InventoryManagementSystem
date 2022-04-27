package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This class is this controller for the add product page*/
public class AddProductController implements Initializable {
    @FXML
    private Label searchPartsLabel;
    @FXML
    private TextField searchPartsTextField;
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
    /** This method triggers when the add button is clicked
     * @param actionEvent the action event
     * */
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

    /** This method triggers when the  save button is clicked
     * @param actionEvent the action event
     * */
    public void onActionSaveProduct(ActionEvent actionEvent) throws IOException {
        try {
            String name = nameTxt.getText();
            double price = Double.parseDouble(priceCostTxt.getText());
            int stock = Integer.parseInt(invTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            ObservableList<Part> associatedParts = FXCollections.observableArrayList();
            if (min > max) {
                throw new Exception("Min cannot be greater than max");
            }
            if (stock > max) {
                throw new Exception("Inventory cannot be greater than max");
            }
            if (min > stock) {
                throw new Exception("Inventory cannot be less than min");
            }
            if (min < 0 || max < 0 || stock < 0 || price < 0) {
                throw new Exception("Inv, Price, Min, and Max should all be 0 or greater");
            }
            for (Part part : tempAssociatedParts) {
                associatedParts.add(part);
            }
            Inventory.addProduct(new Product((int)(Inventory.generateProductId()), name, stock, price, max,min,associatedParts));
            inventory.navigateToScreen(actionEvent, "/view/MainForm.fxml");
        } catch(NumberFormatException error) {
            inventory.createAlert(Alert.AlertType.ERROR, "Invalid Input For Product", "Error: Please check all input for the product");
        } catch (Exception e) {
            inventory.createAlert(Alert.AlertType.ERROR, "Invalid Form Data", e.getMessage());
        }
    }
    /** This method triggers when the cancele button is clicked
     * @param actionEvent the action event
     * */
    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        inventory.navigateToScreen(actionEvent, "/view/MainForm.fxml");
    }

    /** This method is called when the page loads
     * @param url the url
     * @param resourceBundle the resource bundle
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allPartsTableView.setItems(Inventory.getAllParts());
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
    /** This method triggers when the remove button is clicked
     * @param actionEvent the action event
     * */
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
    /** This method triggers when the text field has new text
     * @param keyEvent the key event
     * */
    public void onActionSearchPart(KeyEvent keyEvent) {
        String searchText = searchPartsTextField.getText();
        Inventory.search(searchText, allPartsTableView, searchPartsLabel);
    }
}

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
/** This class is this controller for the modify product page*/
public class ModifyProductController implements Initializable {
    @FXML
    public Label searchPartsLabel;
    @FXML
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

    private ObservableList<Part> tempAssociatedParts = FXCollections.observableArrayList();
    Inventory inventory = new Inventory();
    /** This method triggers when the add button is clicked
     * @param actionEvent the action event
     * */
    public void onActionAdd(ActionEvent actionEvent) {
        if(allPartsTableView.getSelectionModel().getSelectedIndex() > -1) {
            Part selectedPart = (Part) allPartsTableView.getSelectionModel().getSelectedItem();
            int selectedPartID = selectedPart.getId();
            boolean partAlreadyAdded = false;
            int currentPartID = Integer.parseInt(idTxt.getText());
            ObservableList<Part> partsOnProduct = Inventory.getAssociatedPartsForProduct(currentPartID);
            if (partsOnProduct != null) {
                for (Part part : partsOnProduct) {
                    if(!tempAssociatedParts.contains(part)) {
                        tempAssociatedParts.add(part);
                    }
                }
            }
            if (tempAssociatedParts.size() == 0 && partsOnProduct == null ) {
                tempAssociatedParts= FXCollections.observableArrayList();
                tempAssociatedParts.add(selectedPart);
            } else {
                for (Part part : tempAssociatedParts) {
                    if (selectedPartID == part.getId()) {
                        partAlreadyAdded = true;
                    }
                }
                if (!partAlreadyAdded) {
                    tempAssociatedParts.add(selectedPart);
                } else {
                    inventory.createAlert( Alert.AlertType.WARNING,"Part Already Added", "Part already added, please select another part");
                }
            }
            addedPartsTableView.setItems(tempAssociatedParts);
        } else {
            inventory.createAlert( Alert.AlertType.WARNING,"Select a part", "Select a part to add");
        }
    }
    /** This method removed an associated part from the produc
     * @param actionEvent the action event
     * */
    public void onActionRemoveAssociatedPart(ActionEvent actionEvent) {

        if (addedPartsTableView.getSelectionModel().getSelectedIndex() > -1) {
            Part partToRemove = (Part) addedPartsTableView.getSelectionModel().getSelectedItem();
                for(Part part : tempAssociatedParts) {
                if(partToRemove.getId() == part.getId()) {
                    tempAssociatedParts.remove(partToRemove);
                    break;
                }
            }
            addedPartsTableView.setItems(tempAssociatedParts);
        } else {
            inventory.createAlert(Alert.AlertType.WARNING, "No Part Selected", "There are no parts selected to remove");
        }
    }
    /** This method triggers when the  save button is clicked
     * @param actionEvent the action event
     * @exception  IOException the io exception
     * */
    public void onActionSaveProduct(ActionEvent actionEvent) throws IOException {
        String name = nameTxt.getText();
        double price = Double.parseDouble(priceCostTxt.getText());
        int stock = Integer.parseInt(invTxt.getText());
        int min = Integer.parseInt(minTxt.getText());
        int max = Integer.parseInt(maxTxt.getText());
        int id = Integer.parseInt(idTxt.getText());
        Inventory.modify(id, new Product(id, name, stock, price, max,min,tempAssociatedParts));
        inventory.navigateToScreen(actionEvent, "/view/MainForm.fxml");
    }
    /** This method triggers when the cancel button is clicked
     * @param actionEvent the action event
     * @exception  IOException the io exception
     * */
    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        inventory.navigateToScreen(actionEvent, "/view/MainForm.fxml");
    }
    /** This method sends the product data from this controller to the main controller
     * @param product the action event
     * */
    public void sendProduct(Product product) {
        idTxt.setText(String.valueOf(product.getId()));
        nameTxt.setText(product.getName());
        invTxt.setText(String.valueOf(product.getStock()));
        priceCostTxt.setText(String.valueOf(product.getPrice()));
        maxTxt.setText(String.valueOf(product.getMax()));
        minTxt.setText(String.valueOf(product.getMin()));

        if(product.getAllAssociatedParts() != null)  {
            for(Part part : product.getAllAssociatedParts()) {
                tempAssociatedParts.add(part);
            }
        }
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

        addedPartsTableView.setItems(tempAssociatedParts);
        addedPartsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addedPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addedPartsInventorColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addedPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** This method is called when you search a part
     * @param keyEvent the key event
     * */
    public void onActionSearchPart(KeyEvent keyEvent) {
        String searchText = searchPartsTextField.getText();
        Inventory.search(searchText, allPartsTableView, searchPartsLabel);
    }
}

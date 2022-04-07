package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
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

public class ModifyProductController implements Initializable {
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

    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    public TextField getIdTxt() {
        return idTxt;
    }

    public void setIdTxt(TextField idTxt) {
        this.idTxt = idTxt;
    }

    public TextField getNameTxt() {
        return nameTxt;
    }

    public void setNameTxt(TextField nameTxt) {
        this.nameTxt = nameTxt;
    }

    public TextField getInvTxt() {
        return invTxt;
    }

    public void setInvTxt(TextField invTxt) {
        this.invTxt = invTxt;
    }

    public TextField getPriceCostTxt() {
        return priceCostTxt;
    }

    public void setPriceCostTxt(TextField priceCostTxt) {
        this.priceCostTxt = priceCostTxt;
    }

    public TextField getMaxTxt() {
        return maxTxt;
    }

    public void setMaxTxt(TextField maxTxt) {
        this.maxTxt = maxTxt;
    }

    public TextField getMinTxt() {
        return minTxt;
    }

    public void setMinTxt(TextField minTxt) {
        this.minTxt = minTxt;
    }

    public TableView getAllPartsTableView() {
        return allPartsTableView;
    }

    public void setAllPartsTableView(TableView allPartsTableView) {
        this.allPartsTableView = allPartsTableView;
    }

    public TableColumn getAllPartsIdColumn() {
        return allPartsIdColumn;
    }

    public void setAllPartsIdColumn(TableColumn allPartsIdColumn) {
        this.allPartsIdColumn = allPartsIdColumn;
    }

    public TableColumn getAllPartsNameColumn() {
        return allPartsNameColumn;
    }

    public void setAllPartsNameColumn(TableColumn allPartsNameColumn) {
        this.allPartsNameColumn = allPartsNameColumn;
    }

    public TableColumn getAllPartsInventorColumn() {
        return allPartsInventorColumn;
    }

    public void setAllPartsInventorColumn(TableColumn allPartsInventorColumn) {
        this.allPartsInventorColumn = allPartsInventorColumn;
    }

    public TableColumn getAllPartsPriceColumn() {
        return allPartsPriceColumn;
    }

    public void setAllPartsPriceColumn(TableColumn allPartsPriceColumn) {
        this.allPartsPriceColumn = allPartsPriceColumn;
    }

    public TableView getAddedPartsTableView() {
        return addedPartsTableView;
    }

    public void setAddedPartsTableView(TableView addedPartsTableView) {
        this.addedPartsTableView = addedPartsTableView;
    }

    public TableColumn getAddedPartsIdColumn() {
        return addedPartsIdColumn;
    }

    public void setAddedPartsIdColumn(TableColumn addedPartsIdColumn) {
        this.addedPartsIdColumn = addedPartsIdColumn;
    }

    public TableColumn getAddedPartsNameColumn() {
        return addedPartsNameColumn;
    }

    public void setAddedPartsNameColumn(TableColumn addedPartsNameColumn) {
        this.addedPartsNameColumn = addedPartsNameColumn;
    }

    public TableColumn getAddedPartsInventorColumn() {
        return addedPartsInventorColumn;
    }

    public void setAddedPartsInventorColumn(TableColumn addedPartsInventorColumn) {
        this.addedPartsInventorColumn = addedPartsInventorColumn;
    }

    public TableColumn getAddedPartsPriceColumn() {
        return addedPartsPriceColumn;
    }

    public void setAddedPartsPriceColumn(TableColumn addedPartsPriceColumn) {
        this.addedPartsPriceColumn = addedPartsPriceColumn;
    }

    Helper helper = new Helper();
    public void onActionAdd(ActionEvent actionEvent) {
        if(allPartsTableView.getSelectionModel().getSelectedIndex() > -1) {
            Part selectedPart = (Part) allPartsTableView.getSelectionModel().getSelectedItem();
            int selectedPartID = selectedPart.getId();
            boolean partAlreadyAdded = false;
            if (associatedParts.isEmpty()) {
                associatedParts.add(selectedPart);
            }
            for (Part part : associatedParts) {
                if (selectedPartID == part.getId()) {
                    partAlreadyAdded = true;
                    System.out.println("part already added");
                }
            }
            if (!partAlreadyAdded) {
                associatedParts.add(selectedPart);
            }
        } else {
            helper.createAlert( Alert.AlertType.WARNING,"Select a part", "Select a part to add");
        }
    }

    public void onActionRemoveAssociatedPart(ActionEvent actionEvent) {
    }

    public void onActionSaveProduct(ActionEvent actionEvent) throws IOException {
        String name = nameTxt.getText();
        double price = Double.parseDouble(priceCostTxt.getText());
        int stock = Integer.parseInt(invTxt.getText());
        int min = Integer.parseInt(minTxt.getText());
        int max = Integer.parseInt(maxTxt.getText());
        int id = Integer.parseInt(idTxt.getText());

        ProductData.modify(id, new Product((int)(Helper.generateProductId()), name, stock, price, max,min,associatedParts));
        helper.navigateToScreen(actionEvent, "/view/MainForm.fxml");
    }

    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        helper.navigateToScreen(actionEvent, "/view/MainForm.fxml");
    }
    public void sendProduct(Product product) {
        idTxt.setText(String.valueOf(product.getId()));
        nameTxt.setText(product.getName());
        invTxt.setText(String.valueOf(product.getStock()));
        priceCostTxt.setText(String.valueOf(product.getPrice()));
        maxTxt.setText(String.valueOf(product.getMax()));
        minTxt.setText(String.valueOf(product.getMin()));
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allPartsTableView.setItems(PartData.getAllParts());
        allPartsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInventorColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        addedPartsTableView.setItems(associatedParts);
        addedPartsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addedPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addedPartsInventorColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addedPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}

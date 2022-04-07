package controller;

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

public class AddProductForm implements Initializable {
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

    Helper helper = new Helper();
    public void onActionAdd(ActionEvent actionEvent) {
        if(allPartsTableView.getSelectionModel().getSelectedIndex() > -1) {
            Part selectedPart = (Part) allPartsTableView.getSelectionModel().getSelectedItem();
            int selectedPartID = selectedPart.getId();
            boolean partAlreadyAdded = false;
            if (ProductData.getAssociatedParts().isEmpty()) {
                ProductData.addAssociatedPart(selectedPart);
            }
            for (Part part : ProductData.getAssociatedParts()) {
                if (selectedPartID == part.getId()) {
                    partAlreadyAdded = true;
                    System.out.println("part already added");
                }
            }
            if (!partAlreadyAdded) {
                ProductData.addAssociatedPart(selectedPart);
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
        ProductData.addProduct(new Product((int)(Helper.generateProductId()), name, stock, price, max,min,ProductData.getAssociatedParts()));
        helper.navigateToScreen(actionEvent, "/view/MainForm.fxml");
    }

    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        helper.navigateToScreen(actionEvent, "/view/MainForm.fxml");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allPartsTableView.setItems(PartData.getAllParts());
        allPartsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInventorColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        addedPartsTableView.setItems(ProductData.getAssociatedParts());
        addedPartsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addedPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addedPartsInventorColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addedPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}

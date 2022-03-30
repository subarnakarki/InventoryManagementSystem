package controller;

import helper.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DataProvider;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {

    Helper helper = new Helper();
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


    public void onActionAddPart(ActionEvent actionEvent) throws IOException {
        helper.navigateToScreen(actionEvent, "/view/AddPartForm.fxml");
    }

    public void onActionModifyPart(ActionEvent actionEvent) throws IOException {
        helper.navigateToScreen(actionEvent, "/view/ModifyPartForm.fxml");
    }

    public void onActionDeletePart(ActionEvent actionEvent) {
    }

    public void onActionAddProduct(ActionEvent actionEvent) throws IOException {
        helper.navigateToScreen(actionEvent, "/view/AddProductForm.fxml");
    }

    public void onActionModifyProduct(ActionEvent actionEvent) throws IOException {
        helper.navigateToScreen(actionEvent, "/view/ModifyProductForm.fxml");
    }

    public void onActionDeleteProduct(ActionEvent actionEvent) {
    }

    public void onActionExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTableView.setItems(DataProvider.getAllParts());

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventorColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}

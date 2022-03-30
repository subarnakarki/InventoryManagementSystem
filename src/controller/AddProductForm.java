package controller;

import helper.Helper;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddProductForm {
    public TextField idTxt;
    public TextField nameTxt;
    public TextField invTxt;
    public TextField priceCostTxt;
    public TextField maxTxt;
    public TextField minTxt;
    public TableView allPartsTableView;
    public TableColumn allPartsIdColumn;
    public TableColumn allPartsNameColumn;
    public TableColumn allPartsInventorColumn;
    public TableColumn allPartsPriceColumn;
    public TableView addedPartsTableView;
    public TableColumn addedPartsIdColumn;
    public TableColumn addedPartsNameColumn;
    public TableColumn addedPartsInventorColumn;
    public TableColumn addedPartsPriceColumn;

    Helper helper = new Helper();
    public void onActionAdd(ActionEvent actionEvent) {
    }

    public void onActionRemoveAssociatedPart(ActionEvent actionEvent) {
    }

    public void onActionSaveProduct(ActionEvent actionEvent) throws IOException {
        helper.navigateToScreen(actionEvent, "/view/MainForm.fxml");
    }

    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        helper.navigateToScreen(actionEvent, "/view/MainForm.fxml");
    }
}

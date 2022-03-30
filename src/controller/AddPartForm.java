package controller;

import helper.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DataProvider;
import model.InHousePart;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartForm implements Initializable {
    @FXML
    public ToggleGroup addPartToggleGroup;

    @FXML
    public RadioButton inHouseRBtn;

    @FXML
    public RadioButton outsourcedRBtn;

    @FXML
    public TextField idTxt;

    @FXML
    public TextField nameTxt;

    @FXML
    public TextField stockTxt;

    @FXML
    public TextField priceCostTxt;

    @FXML
    public TextField maxTxt;

    @FXML
    public TextField minTxt;

    @FXML
    public TextField machineIdTxt;

    Helper helper = new Helper();
    public void onActionSavePart(ActionEvent actionEvent) throws IOException {
        String name = nameTxt.getText();
        double price = Double.parseDouble(priceCostTxt.getText());
        int stock = Integer.parseInt(stockTxt.getText());
        int min = Integer.parseInt(minTxt.getText());
        int max = Integer.parseInt(maxTxt.getText());
        int machineId = Integer.parseInt(machineIdTxt.getText());
        DataProvider.addPart(new InHousePart(DataProvider.generateId(DataProvider.getAllParts()), name, price, stock, min, max, machineId));
        helper.navigateToScreen(actionEvent, "/view/MainForm.fxml");
    }

    public void onActionDisplayMainForm(ActionEvent actionEvent) throws IOException {
        helper.navigateToScreen(actionEvent, "/view/MainForm.fxml");
    }

    public void onActionShowInhouse(ActionEvent actionEvent) {
    }

    public void onActionShowOutsourced(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idTxt.setEditable(false);
    }
}

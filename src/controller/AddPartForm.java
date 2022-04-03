package controller;

import helper.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DataProvider;
import model.InHousePart;
import model.OutsourcedPart;
import model.Part;

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
    public Label machineIdOrCompanyLabel;

    @FXML
    public TextField machineIdOrCompanyTxt;

    Helper helper = new Helper();

    public void onActionSavePart(ActionEvent actionEvent) throws IOException {
        boolean partIsInhouse = inHouseRBtn.isSelected() ? true : false;
        String name = nameTxt.getText();
        double price = Double.parseDouble(priceCostTxt.getText());
        int stock = Integer.parseInt(stockTxt.getText());
        int min = Integer.parseInt(minTxt.getText());
        int max = Integer.parseInt(maxTxt.getText());
        Part newPart;
        if (partIsInhouse) {
            newPart = new InHousePart(DataProvider.generateId(DataProvider.getAllParts()), name , price, stock, min, max,Integer.parseInt(machineIdOrCompanyTxt.getText()));
        } else {
            newPart = new OutsourcedPart(DataProvider.generateId(DataProvider.getAllParts()), name , price, stock, min, max,machineIdOrCompanyTxt.getText());
        }
        DataProvider.addPart(newPart);
        helper.navigateToScreen(actionEvent, "/view/MainForm.fxml");
    }

    public void onActionDisplayMainForm(ActionEvent actionEvent) throws IOException {
        helper.navigateToScreen(actionEvent, "/view/MainForm.fxml");
    }

    public void onActionShowOutsourced(ActionEvent actionEvent) {
        machineIdOrCompanyLabel.setText("Company Name");
    }

    public void onActionShowInhouse(ActionEvent actionEvent) {
        machineIdOrCompanyLabel.setText("Machine ID");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idTxt.setEditable(false);
    }
}

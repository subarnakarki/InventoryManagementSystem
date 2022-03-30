package controller;

import helper.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartForm implements Initializable {
    public ToggleGroup modifyPartToggleGroup;
    public RadioButton inHouseRBtn;
    public RadioButton outsourcedRBtn;
    public TextField idTxt;
    public TextField nameTxt;
    public TextField invTxt;
    public TextField priceCostTxt;
    public TextField maxTxt;
    public TextField minTxt;
    public TextField machineIdTxt;
    Helper helper = new Helper();
    public void onActionShowOutsourced(ActionEvent actionEvent) {

    }

    public void onActionShowInhouse(ActionEvent actionEvent) {
    }

    public void onActionSavePart(ActionEvent actionEvent) {
    }

    public void onActionDisplayMainForm(ActionEvent actionEvent) throws IOException {
        helper.navigateToScreen(actionEvent, "/view/MainForm.fxml");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idTxt.setEditable(false);
    }
}

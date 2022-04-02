package controller;

import helper.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import model.InHousePart;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartFormController implements Initializable {
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
    public void sendPart(Part part) {
        idTxt.setText(String.valueOf(part.getId()));
        nameTxt.setText(part.getName());
        invTxt.setText(String.valueOf(part.getStock()));
        priceCostTxt.setText(String.valueOf(part.getPrice()));
        maxTxt.setText(String.valueOf(part.getMax()));
        minTxt.setText(String.valueOf(part.getMin()));
//
//        if(part.isVaccinated()) {
//            vacLbl.setText("yes");
//        } else {
//            vacLbl.setText("no");
//        }
//
        if(part instanceof InHousePart) {
            machineIdTxt.setText(String.valueOf(((InHousePart) part).getMachineId()));
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idTxt.setEditable(false);
    }
}

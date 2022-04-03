package controller;

import helper.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import model.DataProvider;
import model.InHousePart;
import model.OutsourcedPart;
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
    public Label machineIdOrCompanyLabel;
    public TextField machineIdOrCompanyTxt;
    Helper helper = new Helper();

    public void onActionShowOutsourced(ActionEvent actionEvent) {
        machineIdOrCompanyLabel.setText("Company Name");
    }

    public void onActionShowInhouse(ActionEvent actionEvent) {
        machineIdOrCompanyLabel.setText("Machine ID");
    }

    public void onActionSavePart(ActionEvent actionEvent) throws IOException {
        boolean partIsInhouse = inHouseRBtn.isSelected() ? true : false;
        int id = Integer.parseInt(idTxt.getText());
        String name = nameTxt.getText();
        double price = Double.parseDouble(priceCostTxt.getText());
        int stock = Integer.parseInt(invTxt.getText());
        int min = Integer.parseInt(minTxt.getText());
        int max = Integer.parseInt(maxTxt.getText());
        Part modifiedPart;
        String companyName = partIsInhouse ? null : machineIdOrCompanyTxt.getText();
        if (partIsInhouse) {
            modifiedPart = new InHousePart(id, name , price, stock, min, max,Integer.parseInt(machineIdOrCompanyTxt.getText()));
        } else {
            modifiedPart = new OutsourcedPart(id, name , price, stock, min, max,machineIdOrCompanyTxt.getText());
        }
        DataProvider.modify(id, modifiedPart);
        helper.navigateToScreen(actionEvent, "/view/MainForm.fxml");
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

        if(part instanceof InHousePart) {
            modifyPartToggleGroup.selectToggle(inHouseRBtn);
            machineIdOrCompanyTxt.setText(String.valueOf(((InHousePart) part).getMachineId()));
            machineIdOrCompanyLabel.setText("Machine ID");

        } else {
            modifyPartToggleGroup.selectToggle(outsourcedRBtn);
            machineIdOrCompanyTxt.setText(String.valueOf(((OutsourcedPart) part).getCompanyName()));
            machineIdOrCompanyLabel.setText("Company Name");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idTxt.setEditable(false);
    }
}

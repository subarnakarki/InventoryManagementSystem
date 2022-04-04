package controller;

import model.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.PartData;
import model.InHousePart;
import model.OutsourcedPart;
import model.Part;

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
        try {
            boolean partIsInhouse = inHouseRBtn.isSelected() ? true : false;
            String name = nameTxt.getText();
            double price = Double.parseDouble(priceCostTxt.getText());
            int stock = Integer.parseInt(stockTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            if (min > max) {
                throw new Exception("Min cannot be greater than max");
            }
            if (min > stock) {
                throw new Exception("Inventory cannot be less than min");
            }
            if (min < 0 || max < 0 || stock < 0 || price < 0) {
                throw new Exception("Inv, Price, Min, and Max should all be 0 or greater");
            }

            Part newPart;
            if (partIsInhouse) {
                newPart = new InHousePart(PartData.generateId(PartData.getAllParts()), name , price, stock, min, max,Integer.parseInt(machineIdOrCompanyTxt.getText()));
            } else {
                newPart = new OutsourcedPart(PartData.generateId(PartData.getAllParts()), name , price, stock, min, max,machineIdOrCompanyTxt.getText());
            }
            PartData.addPart(newPart);
            helper.navigateToScreen(actionEvent, "/view/MainForm.fxml");
        } catch (NumberFormatException e) {
                helper.createAlert(Alert.AlertType.ERROR, "Invalid Form Data", "Invalid form data, please check all input");
        } catch (Exception e) {
            helper.createAlert(Alert.AlertType.ERROR, "Invalid Form Data", e.getMessage());
        }
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

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

public class ModifyPartController implements Initializable {
    @FXML
    private ToggleGroup modifyPartToggleGroup;
    @FXML
    private RadioButton inHouseRBtn;
    @FXML
    private RadioButton outsourcedRBtn;
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
    private Label machineIdOrCompanyLabel;
    @FXML
    private TextField machineIdOrCompanyTxt;

    public ToggleGroup getModifyPartToggleGroup() {
        return modifyPartToggleGroup;
    }

    public void setModifyPartToggleGroup(ToggleGroup modifyPartToggleGroup) {
        this.modifyPartToggleGroup = modifyPartToggleGroup;
    }

    public RadioButton getInHouseRBtn() {
        return inHouseRBtn;
    }

    public void setInHouseRBtn(RadioButton inHouseRBtn) {
        this.inHouseRBtn = inHouseRBtn;
    }

    public RadioButton getOutsourcedRBtn() {
        return outsourcedRBtn;
    }

    public void setOutsourcedRBtn(RadioButton outsourcedRBtn) {
        this.outsourcedRBtn = outsourcedRBtn;
    }

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

    public Label getMachineIdOrCompanyLabel() {
        return machineIdOrCompanyLabel;
    }

    public void setMachineIdOrCompanyLabel(Label machineIdOrCompanyLabel) {
        this.machineIdOrCompanyLabel = machineIdOrCompanyLabel;
    }

    public TextField getMachineIdOrCompanyTxt() {
        return machineIdOrCompanyTxt;
    }

    public void setMachineIdOrCompanyTxt(TextField machineIdOrCompanyTxt) {
        this.machineIdOrCompanyTxt = machineIdOrCompanyTxt;
    }

    Helper helper = new Helper();

    public void onActionShowOutsourced(ActionEvent actionEvent) {
        machineIdOrCompanyLabel.setText("Company Name");
    }

    public void onActionShowInhouse(ActionEvent actionEvent) {
        machineIdOrCompanyLabel.setText("Machine ID");
    }

    public void onActionSavePart(ActionEvent actionEvent) throws IOException {
        try {
            boolean partIsInhouse = inHouseRBtn.isSelected() ? true : false;
            int id = Integer.parseInt(idTxt.getText());
            String name = nameTxt.getText();
            double price = Double.parseDouble(priceCostTxt.getText());
            int stock = Integer.parseInt(invTxt.getText());
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

            Part modifiedPart;
            String companyName = partIsInhouse ? null : machineIdOrCompanyTxt.getText();
            if (partIsInhouse) {
                modifiedPart = new InHousePart(id, name , price, stock, min, max,Integer.parseInt(machineIdOrCompanyTxt.getText()));
            } else {
                modifiedPart = new OutsourcedPart(id, name , price, stock, min, max,machineIdOrCompanyTxt.getText());
            }
            PartData.modify(id, modifiedPart);
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

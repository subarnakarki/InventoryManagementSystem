package controller;

import model.Inventory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.InHouse;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This class is this controller for the modify part page*/
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
    /**
     * @return the modify toggle group
     */
    public ToggleGroup getModifyPartToggleGroup() {
        return modifyPartToggleGroup;
    }
    /**
     * @param modifyPartToggleGroup the modify toggle group
     */
    public void setModifyPartToggleGroup(ToggleGroup modifyPartToggleGroup) {
        this.modifyPartToggleGroup = modifyPartToggleGroup;
    }
    /**
     * @return the inhouse radio button
     */
    public RadioButton getInHouseRBtn() {
        return inHouseRBtn;
    }
    /**
     * @param inHouseRBtn the inhouse radio button
     */
    public void setInHouseRBtn(RadioButton inHouseRBtn) {
        this.inHouseRBtn = inHouseRBtn;
    }
    /**
     * @return the outsourced radio button
     */
    public RadioButton getOutsourcedRBtn() {
        return outsourcedRBtn;
    }
    /**
     * @param outsourcedRBtn the outsourced radio button
     */
    public void setOutsourcedRBtn(RadioButton outsourcedRBtn) {
        this.outsourcedRBtn = outsourcedRBtn;
    }
    /**
     * @return the id text
     */
    public TextField getIdTxt() {
        return idTxt;
    }
    /**
     * @param idTxt the id text field
     */
    public void setIdTxt(TextField idTxt) {
        this.idTxt = idTxt;
    }
    /**
     * @return the name text
     */
    public TextField getNameTxt() {
        return nameTxt;
    }
    /**
     * @param nameTxt the name text field
     */
    public void setNameTxt(TextField nameTxt) {
        this.nameTxt = nameTxt;
    }
    /**
     * @return the inventory text
     */
    public TextField getInvTxt() {
        return invTxt;
    }
    /**
     * @param invTxt the inventory text field
     */
    public void setInvTxt(TextField invTxt) {
        this.invTxt = invTxt;
    }
    /**
     * @return the price text
     */
    public TextField getPriceCostTxt() {
        return priceCostTxt;
    }
    /**
     * @param priceCostTxt the price text field
     */
    public void setPriceCostTxt(TextField priceCostTxt) {
        this.priceCostTxt = priceCostTxt;
    }
    /**
     * @return the max text
     */
    public TextField getMaxTxt() {
        return maxTxt;
    }
    /**
     * @param maxTxt the max text field
     */
    public void setMaxTxt(TextField maxTxt) {
        this.maxTxt = maxTxt;
    }
    /**
     * @return the min text
     */
    public TextField getMinTxt() {
        return minTxt;
    }
    /**
     * @param minTxt the min text field
     */
    public void setMinTxt(TextField minTxt) {
        this.minTxt = minTxt;
    }
    /**
     * @return the machine id or company label
     */
    public Label getMachineIdOrCompanyLabel() {
        return machineIdOrCompanyLabel;
    }
    /**
     * @param machineIdOrCompanyLabel the machine id or company label
     */
    public void setMachineIdOrCompanyLabel(Label machineIdOrCompanyLabel) {
        this.machineIdOrCompanyLabel = machineIdOrCompanyLabel;
    }
    /**
     * @return the machine id or company text
     */
    public TextField getMachineIdOrCompanyTxt() {
        return machineIdOrCompanyTxt;
    }
    /**
     * @param machineIdOrCompanyTxt the machine id or company text field
     */
    public void setMachineIdOrCompanyTxt(TextField machineIdOrCompanyTxt) {
        this.machineIdOrCompanyTxt = machineIdOrCompanyTxt;
    }

    Inventory inventory = new Inventory();
    /**
     * @param actionEvent the action event
     */
    public void onActionShowOutsourced(ActionEvent actionEvent) {
        machineIdOrCompanyLabel.setText("Company Name");
    }
    /**
     * @param actionEvent the action event
     */
    public void onActionShowInhouse(ActionEvent actionEvent) {
        machineIdOrCompanyLabel.setText("Machine ID");
    }
    /** saves the part
     * @param actionEvent the action event
     * @exception  IOException the io exception
     */
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
            if (stock > max) {
                throw new Exception("Inventory cannot be greater than max");
            }
            if (min > stock) {
                throw new Exception("Inventory cannot be less than min");
            }
            if (min < 0 || max < 0 || stock < 0 || price < 0) {
                throw new Exception("Inv, Price, Min, and Max should all be 0 or greater");
            }

            Part modifiedPart;
            if (partIsInhouse) {
                modifiedPart = new InHouse(id, name , price, stock, min, max,Integer.parseInt(machineIdOrCompanyTxt.getText()));
            } else {
                modifiedPart = new Outsourced(id, name , price, stock, min, max,machineIdOrCompanyTxt.getText());
            }
            Inventory.modify(id, modifiedPart);
            inventory.navigateToScreen(actionEvent, "/view/MainForm.fxml");
        } catch (NumberFormatException e) {
            inventory.createAlert(Alert.AlertType.ERROR, "Invalid Form Data", "Invalid form data, please check all input");
        } catch (Exception e) {
            inventory.createAlert(Alert.AlertType.ERROR, "Invalid Form Data", e.getMessage());
        }
    }
    /** displays the main form
     * @param actionEvent the action event
     * @throws IOException handles io exception
     */
    public void onActionDisplayMainForm(ActionEvent actionEvent) throws IOException {
        inventory.navigateToScreen(actionEvent, "/view/MainForm.fxml");
    }
    /** sends part data from this controller to the main controller
     * @param part the part to load from the main screen
     */
    public void sendPart(Part part) {
        idTxt.setText(String.valueOf(part.getId()));
        nameTxt.setText(part.getName());
        invTxt.setText(String.valueOf(part.getStock()));
        priceCostTxt.setText(String.valueOf(part.getPrice()));
        maxTxt.setText(String.valueOf(part.getMax()));
        minTxt.setText(String.valueOf(part.getMin()));

        if(part instanceof InHouse) {
            modifyPartToggleGroup.selectToggle(inHouseRBtn);
            machineIdOrCompanyTxt.setText(String.valueOf(((InHouse) part).getMachineId()));
            machineIdOrCompanyLabel.setText("Machine ID");

        } else {
            modifyPartToggleGroup.selectToggle(outsourcedRBtn);
            machineIdOrCompanyTxt.setText(String.valueOf(((Outsourced) part).getCompanyName()));
            machineIdOrCompanyLabel.setText("Company Name");
        }
    }

    /** This method is called when the page loads
     * @param url the url
     * @param resourceBundle the resource bundle
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idTxt.setEditable(false);
    }
}

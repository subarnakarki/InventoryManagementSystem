package model;

import controller.ModifyPartController;
import controller.ModifyProductController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class Helper {
    public static int generateProductId() {
        return ProductData.getProducts().size() + 1;
    }

    public void navigateToScreen(ActionEvent actionEvent, String path) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource(path));
        stage.setScene(new Scene((Parent) scene));
        stage.setTitle(path.substring(path.lastIndexOf("/" ) + 1, ( path.lastIndexOf("." ))));
        stage.show();
    }

    public static int generatePartId() {
        return PartData.getAllParts().size() + 1;
    }

    public void sendDataAndLoadPage(ActionEvent actionEvent, TableView tableView, String path, String dataType) throws IOException {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource(path));
//        loader.load();
        if (dataType == "parts") {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(path));
            loader.load();

            ModifyPartController modifyPartController = loader.getController();
            modifyPartController.sendPart((Part) tableView.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene((scene)));
            stage.show();
        } else if (dataType == "products") {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(path));
            loader.load();

//            System.out.println("PRODUCTS");
            ModifyProductController modifyProductController = loader.getController();
            modifyProductController.sendProduct((Product) tableView.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene((scene)));
            stage.show();
        }
//
//        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
//        Parent scene = loader.getRoot();
//        stage.setScene(new Scene((scene)));
//        stage.show();
    }
    public boolean createAlert(Alert.AlertType alertType, String title, String context) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(context);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() &&  result.get() == ButtonType.OK) {
            return true;
        }
        return false;
    }
}

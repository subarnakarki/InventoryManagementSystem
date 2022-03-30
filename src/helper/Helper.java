package helper;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Helper {
    public void navigateToScreen(ActionEvent actionEvent, String path) throws IOException {
        // Casting event source and where the event came from
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource(path));
        stage.setScene(new Scene((Parent) scene));
        stage.setTitle(path.substring(path.lastIndexOf("/" ) + 1, ( path.lastIndexOf("." ))));
        stage.show();
    }
}

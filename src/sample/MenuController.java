package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;



public class MenuController {
    public void pressButtonNew(ActionEvent event){
        try {
            Stage stage;
            Parent root;
            stage = (Stage) ((Button) (event.getSource())).getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("NewFile.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }   catch(Exception e) {
            e.printStackTrace();
    }
}




public void pressButtonEdit(ActionEvent event){
        try {
            Stage stage;
            Parent root;
            stage=(Stage) ((Button)(event.getSource())).getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("EditFile.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

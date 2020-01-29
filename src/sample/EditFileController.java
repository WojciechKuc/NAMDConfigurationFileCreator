package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditFileController {

    static String lastDirectory= Paths.get(".").toAbsolutePath().normalize().toString();
    @FXML
    private TextArea textArea;
    @FXML
    private TextField searchBox;
    @FXML
    private Label errorLabel;
    int index = 0;

    @FXML
    private void initialize() {


        searchBox.setPromptText("Enter searched value");
    }



    public void pressButtonBackInEdit(ActionEvent event) {
        try {
            Stage stage;
            Parent root;
            stage = (Stage) ((Button) (event.getSource())).getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Stage primaryStage;

    public static class MyUtilities {

        public static FileChooser createFileChooser() {
            FileChooser chooser = new FileChooser();

            chooser.setInitialDirectory(new File(lastDirectory));
            return chooser ;
        }
    }

    public void pressButtonOpenInEdit() {

        if(!textArea.getText().isEmpty()){

        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Open new file " +
                "gonna erase actually work ",ButtonType.OK,ButtonType.CANCEL);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("custom.css").toExternalForm());
        alert.setTitle("INFORMATION");

        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                FileChooser fileChooser = MyUtilities.createFileChooser();

                //Set extension filter
                FileChooser.ExtensionFilter extFilter =
                        new FileChooser.ExtensionFilter("Namd files (*.NAMD)", "*.NAMD");
                fileChooser.getExtensionFilters().add(extFilter);

                //Show save file dialog
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    String fileAsString = file.toString();
                    lastDirectory = Paths.get(fileAsString).getParent().toAbsolutePath().normalize().toString();
                    System.out.println(lastDirectory);
                    textArea.setText(readFile(file));
                }
            } else if (rs == ButtonType.CANCEL) {

            }
        });} else{
            FileChooser fileChooser = MyUtilities.createFileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("Namd files (*.NAMD)", "*.NAMD");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show save file dialog
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                String fileAsString = file.toString();
                lastDirectory = Paths.get(fileAsString).getParent().toAbsolutePath().normalize().toString();
                System.out.println(lastDirectory);
                textArea.setText(readFile(file));
            }
        }



    }

    private String readFile(File file) {
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(file));

            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text + "\n");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EditFileController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EditFileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stringBuffer.toString();
    }

    public void pressButtonSaveInEdit() {

        FileChooser fileChooser = MyUtilities.createFileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Namd files (*.NAMD)", "*.NAMD");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            String fileAsString = file.toString();
            lastDirectory = Paths.get(fileAsString).getParent().toAbsolutePath().normalize().toString();
            System.out.println(lastDirectory);
            SaveFile(textArea.getText(), file);
        }

    }

    private void SaveFile(String content, File file) {
        try {
            FileWriter fileWriter;

            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(NewFileController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Search() {
        if (searchBox.getText() != null && !searchBox.getText().isEmpty()) {
            index = textArea.getText().toLowerCase().indexOf(searchBox.getText(), index);

        if (index == -1) {
            errorLabel.setText("Search value Not in the text");
        } else {
            errorLabel.setText("");
            textArea.selectRange(index, index + searchBox.getLength());
        }
    }else {
            errorLabel.setText("Missing search value");
          }
    }
    public void Next() {
        if (searchBox.getText() != null && !searchBox.getText().isEmpty()) {
            index = textArea.getText().toLowerCase().indexOf(searchBox.getText(), index+1);

            if (index == -1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(
                        getClass().getResource("custom.css").toExternalForm());
                alert.setTitle("Error");
                alert.setHeaderText("all search words were found");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
            } else {
                errorLabel.setText("");
                textArea.selectRange(index, index + searchBox.getLength());
            }
        }else {
            errorLabel.setText("Missing search value");
        }
    }
}

















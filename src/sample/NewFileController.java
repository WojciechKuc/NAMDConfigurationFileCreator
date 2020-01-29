package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NewFileController {

    ObservableList<String> valuesList = FXCollections.observableArrayList(
            "none",
            "1-2",
            "1-3",
            "1-4",
            "scaled1-4");

    static String lastDirectory= Paths.get(".").toAbsolutePath().normalize().toString();

    @FXML
    private TextField numsteps;
    @FXML
    private TextField coordinates;
    @FXML
    private TextField structure;
    @FXML
    private TextField parameters;
    @FXML
    private TextField outputname;
    @FXML
    private TextField temperatureTextField;
    @FXML
    private TextField velocitiesTextField;
    @FXML
    private TextField binvelocitiesTextField;
    @FXML
    private ComboBox ComboBox;
    @FXML
    private RadioButton temperature;
    @FXML
    private RadioButton velocities;
    @FXML
    private RadioButton binvelocities;
    @FXML
    private TextArea additionalParameters;
    @FXML
    private Label showNumsteps;
    @FXML
    private Tooltip showNumstepsTooltip;
    @FXML
    private Label showCoordinates;
    @FXML
    private Tooltip showCoordinatesTooltip;
    @FXML
    private Label showStructure;
    @FXML
    private Tooltip showStructureTooltip;
    @FXML
    private Label showParameters;
    @FXML
    private Tooltip showParametersTooltip;
    @FXML
    private Label showExclude;
    @FXML
    private Label showOutputname;
    @FXML
    private Tooltip showOutputnameTooltip;
    @FXML
    private Label showTemperature;
    @FXML
    private Tooltip showTemperatureTooltip;
    @FXML
    private Label showVelocities;
    @FXML
    private Tooltip showVelocitiesTooltip;
    @FXML
    private Label showBinvelocities;
    @FXML
    private Tooltip showBinvelocitiesTooltip;



    @FXML
    private void initialize() {
        ComboBox.setItems(valuesList);
        ComboBox.getSelectionModel().selectFirst();

        //Formatowanie pola numsteps

        numsteps.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches("([1-9][0-9]*)?"))
                        ? change : null));


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


    public void pressButtonSaveInEdit() {


        if(numsteps.getText().isEmpty() || coordinates.getText().isEmpty()
                || structure.getText().isEmpty()
                || parameters.getText().isEmpty()
                || outputname.getText().isEmpty()
                || (temperature.isSelected() && temperatureTextField.getText().isEmpty())
                || (velocities.isSelected() && velocitiesTextField.getText().isEmpty())
                || (binvelocities.isSelected() && binvelocitiesTextField.getText().isEmpty()))
        {
            String error = "-fx-border-color: #DB5461; -fx-background-color: #00171F; -fx-text-fill: #e1ce7a;\n";
            String removeerror = "-fx-background-color: #00171F; -fx-border-color: #e1ce7a; -fx-text-fill: #e1ce7a;\n";
            if(numsteps.getText().isEmpty()){numsteps.setStyle(error);}
            else{numsteps.setStyle(removeerror);}
            if(coordinates.getText().isEmpty()){coordinates.setStyle(error);}
            else{coordinates.setStyle(removeerror);}
            if(structure.getText().isEmpty()){structure.setStyle(error);}
            else{structure.setStyle(removeerror);}
            if(parameters.getText().isEmpty()){parameters.setStyle(error);}
            else{parameters.setStyle(removeerror);}
            if(outputname.getText().isEmpty()){outputname.setStyle(error);}
            else{outputname.setStyle(removeerror);}
            if(temperature.isSelected() &&
                    temperatureTextField.getText().isEmpty()){temperatureTextField.setStyle(error);}
            else{temperatureTextField.setStyle(removeerror);}
            if(velocities.isSelected() &&
                    velocitiesTextField.getText().isEmpty()){velocitiesTextField.setStyle(error);}
            else{velocitiesTextField.setStyle(removeerror);}
            if(binvelocities.isSelected() &&
                    binvelocitiesTextField.getText().isEmpty()){binvelocitiesTextField.setStyle(error);}
            else{binvelocitiesTextField.setStyle(removeerror);}
            Alert alert = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("custom.css").toExternalForm());
            alert.setTitle("Error");
            alert.setHeaderText("Fill all required fields");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
        else {

            String removeerror = "-fx-background-color: #00171F; -fx-border-color: #e1ce7a; -fx-text-fill: #e1ce7a;\n";

            numsteps.setStyle(removeerror);
            coordinates.setStyle(removeerror);
            structure.setStyle(removeerror);
            parameters.setStyle(removeerror);
            outputname.setStyle(removeerror);
            temperatureTextField.setStyle(removeerror);
            velocitiesTextField.setStyle(removeerror);
            binvelocitiesTextField.setStyle(removeerror);


            FileChooser fileChooser = MyUtilities.createFileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("Namd files (*.NAMD)", "*.NAMD");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show save file dialog
            File file = fileChooser.showSaveDialog(primaryStage);

            //warunek dla trzech ostatnich parametrów
            String oneOfThree = "";
            if (temperature.isSelected()) {
                oneOfThree = "temperature         " + temperatureTextField.getText();
            } else if (velocities.isSelected()) {
                String vtmp = velocitiesTextField.getText();
                String newvelocities = vtmp.replace('\\', '/');
                oneOfThree = "velocities          " + newvelocities;
            } else if (binvelocities.isSelected()) {
                String bvtmp = binvelocitiesTextField.getText();
                String newbinvelocities = bvtmp.replace('\\', '/');
                oneOfThree = "binvelocities       " + newbinvelocities;
            }

            //zmiana \ na / w scieżkach do plików wejściowych
            String ctmp = coordinates.getText();
            String newcoordinates = ctmp.replace('\\', '/');

            String stmp = structure.getText();
            String newstructure = stmp.replace('\\', '/');

            String ptmp = parameters.getText();
            String newparameters = ptmp.replace('\\', '/');

            if (file != null) {
                String fileAsString = file.toString();
                lastDirectory = Paths.get(fileAsString).getParent().toAbsolutePath().normalize().toString();
                System.out.println(lastDirectory);
                SaveFile("#NAMD configuration file mad in 'NAMD Configuration Fie Creator'\n\n#Required\n"
                        + "\nnumsteps            " + numsteps.getText()
                        + "\ncoordinates         " + newcoordinates
                        + "\nstructure           " + newstructure
                        + "\nparameters          " + newparameters
                        + "\nexclude             " + ComboBox.getValue().toString()
                        + "\noutputname          " + outputname.getText()
                        + "\n" + oneOfThree + "\n"
                        + "\n\n#Additional Parameters \n" + additionalParameters.getText(), file);
            }
        }

    }

    public static class MyUtilities {

        public static FileChooser createFileChooser() {
            FileChooser chooser = new FileChooser();

            chooser.setInitialDirectory(new File(lastDirectory));
            return chooser ;
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

    public void pressHyperlinkPDBfile() {
        FileChooser fileChooser = MyUtilities.createFileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Coordinates files (*.pdb)", "*.pdb");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            String fileAsString = file.toString();
            lastDirectory = Paths.get(fileAsString).getParent().toAbsolutePath().normalize().toString();
            coordinates.setText(fileAsString);
        }

    }

    public void pressHyperlinkPSFfile() {
        FileChooser fileChooser = MyUtilities.createFileChooser();
        //Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Structure files (*.psf)", "*.psf");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            String fileAsString = file.toString();
            lastDirectory = Paths.get(fileAsString).getParent().toAbsolutePath().normalize().toString();
            structure.setText(fileAsString);
        }

    }

    public void pressHyperlinkCharmmfile() {
        FileChooser fileChooser = MyUtilities.createFileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Charmm files (*.params)",
                        "*.params");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            String fileAsString = file.toString();
            lastDirectory = Paths.get(fileAsString).getParent().toAbsolutePath().normalize()
                    .toString();
            parameters.setText(fileAsString);
        }

    }

    public void pressHyperlinkPSFfilevelocities() {
        FileChooser fileChooser = MyUtilities.createFileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Namd files (*.psf)", "*.psf");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            String fileAsString = file.toString();
            lastDirectory = Paths.get(fileAsString).getParent().toAbsolutePath().normalize().toString();
            velocitiesTextField.setText(fileAsString);
        }


    }

    public void pressHyperlinkPSFfilebinvelocities() {
        FileChooser fileChooser = MyUtilities.createFileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Binvelocities files (*.vel)", "*.vel");
        fileChooser.getExtensionFilters().add(extFilter);


        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            String fileAsString = file.toString();
            lastDirectory = Paths.get(fileAsString).getParent().toAbsolutePath().normalize().toString();
            binvelocitiesTextField.setText(fileAsString);
        }

    }



    public void onSelectionChangedAdditionalParametersTab(){

        showNumsteps.setText(numsteps.getText());
        showNumstepsTooltip.setText(numsteps.getText());

        showCoordinates.setText(coordinates.getText());
        showCoordinatesTooltip.setText(coordinates.getText());

        showStructure.setText(structure.getText());
        showStructureTooltip.setText(structure.getText());

        showParameters.setText(parameters.getText());
        showParametersTooltip.setText(parameters.getText());

        showExclude.setText(ComboBox.getValue().toString());

        showOutputname.setText(outputname.getText());
        showOutputnameTooltip.setText(outputname.getText());

        showTemperature.setText(temperatureTextField.getText());
        showTemperatureTooltip.setText(temperatureTextField.getText());

        showVelocities.setText(velocitiesTextField.getText());
        showVelocitiesTooltip.setText(velocitiesTextField.getText());

        showBinvelocities.setText(binvelocitiesTextField.getText());
        showBinvelocitiesTooltip.setText(binvelocitiesTextField.getText());
        }

    }



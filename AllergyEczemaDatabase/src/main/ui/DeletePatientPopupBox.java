package ui;

/**
 * This class contains the text information and layout for the popup that is prompted when you are deleting a
 * patient from the database
 * */

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DeletePatientPopupBox {

    static boolean initializeDelete;
    private static Label label;

    public static boolean display(String title, String message) {
        Stage saveConfirmBoxWindow = setStagePresets(title, message);

        RadioButton yesRadioButton = new RadioButton("Yes");
        RadioButton noRadioButton = new RadioButton("No");
        ToggleGroup radioGroup = new ToggleGroup();
        yesRadioButton.setToggleGroup(radioGroup);
        noRadioButton.setToggleGroup(radioGroup);

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            if (radioGroup.getSelectedToggle().equals(yesRadioButton)) {
                initializeDelete = true;
            } else {
                initializeDelete = false;
            }
            saveConfirmBoxWindow.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yesRadioButton, noRadioButton, submitButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 150);
        scene.getStylesheets().add(DeletePatientPopupBox.class.getResource("CustomTheme.css").toExternalForm());
        saveConfirmBoxWindow.setScene(scene);
        saveConfirmBoxWindow.showAndWait();

        return initializeDelete;
    }

    // Sets spacing and layout of stage
    private static Stage setStagePresets(String title, String message) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(200);

        label = new Label();
        label.setText(message);

        return stage;
    }
}

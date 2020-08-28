package ui;

/**
 * This class contains the text information and layout for the popup that is prompted you exit or quit out of the
 * application and are asked if you would like to save before.
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

public class SaveConfirmBox {
    private static int initializeSave;
    private static Label label;
    private static RadioButton yesRadioButton;
    private static RadioButton noRadioButton;
    private static ToggleGroup radioGroup;

    public static int display(String title, String message) {
        Stage saveConfirmBoxWindow = setStagePresets(title, message);
        initializeRadioButtons();

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            if (radioGroup.getSelectedToggle().equals(yesRadioButton)) {
                initializeSave = 1;
            } else if (radioGroup.getSelectedToggle().equals(noRadioButton)) {
                initializeSave = 2;
            } else {
                initializeSave = 0;
            }
            saveConfirmBoxWindow.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yesRadioButton, noRadioButton, submitButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 150, 150);
        scene.getStylesheets().add(SaveConfirmBox.class.getResource("CustomTheme.css").toExternalForm());
        saveConfirmBoxWindow.setScene(scene);
        saveConfirmBoxWindow.showAndWait();

        return initializeSave;
    }

    // Creates stage layout
    private static Stage setStagePresets(String title, String message) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(200);

        label = new Label();
        label.setText(message);

        return stage;
    }

    // Initializes radio buttons
    private static void initializeRadioButtons() {
        yesRadioButton = new RadioButton("Yes");
        noRadioButton = new RadioButton("No");
        radioGroup = new ToggleGroup();
        yesRadioButton.setToggleGroup(radioGroup);
        noRadioButton.setToggleGroup(radioGroup);
    }
}

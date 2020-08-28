package ui;

/**
 * This class contains the text information and layout for the popup that is prompted when click the more info buttons
 * for the odds ratio and relative risk analyses.
 * */

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MoreInfoPopupBox {
    public static void display(String title, String message) {
        Stage alertWindow = new Stage();

        alertWindow.initModality(Modality.APPLICATION_MODAL);
        alertWindow.setTitle(title);
        alertWindow.setMinWidth(300);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button();
        closeButton.setText("Close");
        closeButton.setOnAction(e -> alertWindow.close());

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 600, 300);
        scene.getStylesheets().add(MoreInfoPopupBox.class.getResource("CustomTheme.css").toExternalForm());
        alertWindow.setScene(scene);
        alertWindow.showAndWait();
    }
}

package ui;

/**
 * This class contains the text information and scene layout for the homepage.
 * */

import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HomePageScene {
    private static String homeTitle = "The Statistical Correspondence Between Eczema and Food Allergies";
    private static String homeIntro = "This application provides a suite of statistical tests that are aimed a t"
            + "investigating the association of two medical conditions\nthat are rapidly increasing in prevalence - "
            + "eczema (i.e. atopic dermatitis) and food allergies - on a database of patients.\n\nThe features of this "
            + "application are delineated into four pages:\n\n     (i) About, which can be found in the options menu, "
            + "provides some information about the tests making up the application\n          as well as the "
            + "application itself;\n    (ii) Database Information, provides summary statistics for the medical "
            + "conditions of interest (also presents how different\n          subsets of the patient population, "
            + "such as sex, may influence those summary statistics;\n   (iii) Run Analysis, the statistical tests "
            + "that address the aim of attempting to gain insight into a potential statistical correspondence"
            + "\n          between eczema and food allergies; and\n   (iv) Update Database, which provides the"
            + " means to add or remove patients from the database, as well as look up a particular patient.";

    // Creates homepage scene
    protected static Scene createScene(MenuBar menu, VBox vbox) {
        BorderPane pane = new BorderPane();
        pane.setTop(menu);
        pane.setCenter(vbox);

        Scene scene = new Scene(pane, 1000, 600);
        return scene;
    }

    // Returns text for the introduction text on the home screen
    public static Text homeIntroductionText() {
        Text text = new Text();
        text.setText(homeIntro);
        return text;
    }

    // Returns text for the introduction text on the home screen
    public static Text homeTitleText() {
        Text text = new Text();
        text.setText(homeTitle);
        text.setId("homeTitleText");
        return text;
    }
}

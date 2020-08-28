package ui;

/**
 * This class contains the text information and layout for the about page in the GUI.
 * */

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class AboutPageScene {
    private static String aboutApplicationIntro = "The main purpose of this database is to investigate the "
            + "statistical relationship between the presence of the medical condition known as eczema\nand the "
            + "later development of food allergies. Namely, it will attempt to substantiate the claim that there "
            + "is a correspondence through a number\nof statistical analysis based on concepts of conditional "
            + "probability. While the program cannot deduce whether a causal relationship\nexists between eczema "
            + "and food allergy development, it can provide insight that can aid further investigation into the "
            + "matter.";
    private static String aboutApplicationDataset = "Typically, real data obtained in studies are provided in the "
            + "form of summary statistics, which excludes any information pertaining to a particular patient.\nAs a "
            + "result, in the current state of the application, the database is filled with a theoretical sample set"
            + "that is based on the the reported statistical figures.\n(This status may be suspect to change, "
            + "depending on if I can get access to the raw dataset from the authors of any of the studies."
            + " However, it may be\nunlikely due to the presence of personal information or other sensitive"
            + "information).";
    private static String aboutApplicationAssumptionsPrecursor = "The following table contains a list of simplifying "
            + "assumptions for the development of this application.";
    private static String assumptionOne = "The different manifestations of food allergies will be aggregated into "
            + "a Boolean value (true indicating the presence of food\nallergies and false indicating the absence of "
            + "food allergies).";
    private static String assumptionTwo = "The presence of eczema is also categorized under a single, binary category. "
            + "This means that the severity of eczema will\nnot be accounted for, and the condition will be represented"
            + " as either being present or absent.";
    private static String assumptionThree = "For the purpose of this application, the two medical conditions are to be"
            + " viewed under the working assumption that\nthe development of eczema precedes the development of food "
            + "allergies. However, in reality, this 'order' of\ndevelopment isn't necessarily true";
    private static String assumptionFour = "If a patient is diagnosed with a food allergy, then the patient "
            + "records will permanently indicate that they have a food allergy,\nand will not revert back even "
            + "if they grow out of their food allergy later in life.";
    private static String assumptionFive = "To reiterate, these analyses to not indicate causality! Rather, they"
            + " are measures of statistical association. As such, in\ntheir present forms, the findings may not"
            + " translate to experimental data.";
    private static String assumptionSix = "It's important to recognize the bias for which the sample data is based on."
            + " The summary statistics from which the sample\ndata are based on favours patients that are more "
            + "likely to be cognizant of their predisposition to such medical conditions.\nTherefore, the statistics"
            + " are likely to overestimate the actual population by some unknown margin.";

    private static String subsettingText = "The importance of implementing the suite of statistical analyses over "
            + "subsets of the database is based on the fact that the different measures\nmay vary considerably "
            + "depending on many factors (e.g. age, location, genetic predisposition, etc...). This presents "
            + "a challenge and emphasizes\nthe importance in refraining from generalizing any findings outside of the "
            + "their context.";
    private static Text aboutApplicationIntroText;
    private static Text aboutApplicationDatasetText;
    private static Text aboutAssumptions;
    private static ListView<String> assumptionsListView;
    private static Text informationOnSubsetsText;

    // Creates the aboutScene
    protected static Scene createScene(MenuBar menu) {
        aboutApplicationIntroText = new Text(aboutApplicationIntro);
        aboutApplicationDatasetText = new Text(aboutApplicationDataset);
        aboutAssumptions = new Text(aboutApplicationAssumptionsPrecursor);
        assumptionsListView = new ListView<>();
        assumptionsListView.getItems().addAll(assumptionOne, assumptionTwo, assumptionThree, assumptionFour,
                assumptionFive, assumptionSix);
        informationOnSubsetsText = new Text(subsettingText);
        gridPaneLayout();

        GridPane grid = gridPanePresets();
        grid.getChildren().addAll(aboutApplicationIntroText, aboutApplicationDatasetText, aboutAssumptions,
                assumptionsListView, informationOnSubsetsText);
        grid.setAlignment(Pos.CENTER);

        ScrollPane aboutScrollPane = new ScrollPane(grid);
        aboutScrollPane.setHvalue(0.5);

        BorderPane bp = new BorderPane();
        bp.setTop(menu);
        bp.setCenter(aboutScrollPane);

        Scene scene = new Scene(bp, 1000, 600);

        return scene;
    }

    // GridPane layout for the elements in the aboutScene page
    private static void gridPaneLayout() {
        GridPane.setConstraints(aboutApplicationIntroText, 0, 0, 2, 4);
        GridPane.setConstraints(aboutApplicationDatasetText, 0, 5, 2, 3);
        GridPane.setConstraints(aboutAssumptions, 0, 9, 2, 1);
        GridPane.setConstraints(assumptionsListView, 1, 10, 2, 5);
        GridPane.setConstraints(informationOnSubsetsText, 0, 16, 2, 3);
    }

    // Presents for some of the spacings and settings of the grid pane.
    private static GridPane gridPanePresets() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        return grid;
    }
}

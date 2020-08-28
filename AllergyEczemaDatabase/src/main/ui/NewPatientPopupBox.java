package ui;

/**
 * This class contains the text information and layout for the popup that is prompted when want to add a new patient to
 * the database
 * */

import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Patient;

public class NewPatientPopupBox {
    static Patient newPatientFile;
    private static Stage newPatientWindow;
    private static GridPane addPatientGrid;

    private static Label addPatientLabel;
    private static Label newIDLabel;
    private static TextField newIDInput;
    private static Label ageLabel;
    private static Slider ageSlider;
    private static Label sexLabel;
    private static RadioButton femaleRadioButton;
    private static RadioButton maleRadioButton;
    private static RadioButton unspecifiedRadioButton;
    private static ToggleGroup radioGroup;
    private static Label eczemaLabel;
    private static CheckBox eczemaCheckBox;
    private static Label allergyLabel;
    private static CheckBox allergyCheckBox;
    private static Label countryOfResidenceLabel;
    private static Label ethnicityLabel;
    private static Button addPatientButton;
    private static Button cancelButton;
    private static ComboBox<String> countryComboBox;
    private static ChoiceBox<String> ethnicityChoiceBox;

    private static Separator sep1;
    private static Separator sep2;
    private static Separator sep3;
    private static Separator sep4;
    private static Separator sep5;
    private static Separator sep6;
    private static Separator sep7;
    private static Separator sep8;

    public static Patient display(String title, String message) {
        configurePopupStage(title, message);
        addPatientGrid = gridPanePresets();
        createPopupBoxElements();
        initializeSeparatorElements();

        // Grid Layout
        addPatientGrid.getChildren().addAll(addPatientLabel, sep1,
                newIDLabel, newIDInput, sep2,
                ageLabel, ageSlider, sep3,
                sexLabel, femaleRadioButton, maleRadioButton, unspecifiedRadioButton, sep4,
                eczemaLabel, eczemaCheckBox, sep5,
                allergyLabel, allergyCheckBox, sep6,
                countryOfResidenceLabel, countryComboBox, sep7,
                ethnicityLabel, ethnicityChoiceBox, sep8,
                addPatientButton, cancelButton);
        Scene addPatientScene = new Scene(addPatientGrid, 600, 500);
        addPatientScene.getStylesheets().add(NewPatientPopupBox.class.getResource("CustomTheme.css")
                .toExternalForm());
        newPatientWindow.setScene(addPatientScene);
        newPatientWindow.showAndWait();

        return newPatientFile;
    }

    // Sets layout and spacing of the stage
    private static void configurePopupStage(String title, String message) {
        newPatientWindow = new Stage();
        newPatientWindow.initModality(Modality.APPLICATION_MODAL);
        newPatientWindow.setTitle(title);
        newPatientWindow.setMinWidth(250);
        Label label = new Label();
        label.setText(message);
    }

    // Sets layout and spacing of the grid pane
    private static GridPane gridPanePresets() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        return grid;
    }

    // Creates line separators
    private static Separator createSeparator() {
        Separator sep = new Separator();
        sep.setValignment(VPos.CENTER);
        GridPane.setColumnSpan(sep, 7);
        return sep;
    }

    // Initializes the creates line separators
    private static void initializeSeparatorElements() {
        sep1 = createSeparator();
        GridPane.setConstraints(sep1, 0, 1);
        sep2 = createSeparator();
        GridPane.setConstraints(sep2, 0, 3);
        sep3 = createSeparator();
        GridPane.setConstraints(sep3, 0, 5);
        sep4 = createSeparator();
        GridPane.setConstraints(sep4, 0, 9);
        sep5 = createSeparator();
        GridPane.setConstraints(sep5, 0, 11);
        sep6 = createSeparator();
        GridPane.setConstraints(sep6, 0, 13);
        sep7 = createSeparator();
        GridPane.setConstraints(sep7, 0, 15);
        sep8 = createSeparator();
        GridPane.setConstraints(sep8, 0, 17);
    }

    // Master method to initialize all objects
    private static void createPopupBoxElements() {
        initializePopupElements();
        initializeRadioGroup();
        initializeChoiceAndComboBoxElements();
        setPopupElementLayout();
    }

    // Instantiates objects for new patient
    private static void initializePopupElements() {
        addPatientLabel = new Label("Add a New Patient File");

        newIDLabel = new Label("Enter ID number");
        newIDInput = new TextField();
        newIDInput.setPromptText("ID number");

        ageLabel = new Label("Age");
        ageSlider = ageSliderPresets();

        sexLabel = new Label("Sex");
        femaleRadioButton = new RadioButton("Female");
        maleRadioButton = new RadioButton("Male");
        unspecifiedRadioButton = new RadioButton("Prefer not to say");
        initializeRadioGroup();

        eczemaLabel = new Label("Eczema");
        eczemaCheckBox = new CheckBox("Yes");

        allergyLabel = new Label("Food Allergy");
        allergyCheckBox = new CheckBox("Yes");

        initializeChoiceAndComboBoxElements();

        addPatientButton = new Button("Add Patient");
        addPatientButton.setOnAction(e -> addPatientMethodHandler());

        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> cancelButtonOperation());
    }

    // Sets group of radio buttons for the selection of sex
    private static void initializeRadioGroup() {
        radioGroup = new ToggleGroup();
        femaleRadioButton.setToggleGroup(radioGroup);
        maleRadioButton.setToggleGroup(radioGroup);
        unspecifiedRadioButton.setToggleGroup(radioGroup);
    }

    // Creates the country and ethnicity selection objects
    private static void initializeChoiceAndComboBoxElements() {
        countryOfResidenceLabel = new Label("Country of Residence");
        countryComboBox = new ComboBox<>();
        countryComboBox.getItems().addAll("United States of America", "Canada", "Australia",
                "United Kingdom", "Japan", "China");
        countryComboBox.setEditable(true);

        ethnicityLabel = new Label("Ethnicity");;
        ethnicityChoiceBox = new ChoiceBox<>();
        ethnicityChoiceBox.getItems().addAll("Caucasian", "Asian", "Hispanic", "Black or African American",
                "Mexican", "Indian", "Other");
    }

    // Sets the spacing of objects in the grid pane
    private static void setPopupElementLayout() {
        GridPane.setConstraints(addPatientLabel, 0, 0);

        GridPane.setConstraints(newIDLabel, 0, 2);
        GridPane.setConstraints(newIDInput, 1, 2);
        GridPane.setConstraints(ageLabel, 0, 4);
        GridPane.setConstraints(ageSlider, 1, 4);

        GridPane.setConstraints(sexLabel, 0, 6);
        GridPane.setConstraints(femaleRadioButton, 1, 6);
        GridPane.setConstraints(maleRadioButton, 1, 7);
        GridPane.setConstraints(unspecifiedRadioButton, 1, 8);

        GridPane.setConstraints(eczemaLabel, 0, 10);
        GridPane.setConstraints(eczemaCheckBox, 1, 10);

        GridPane.setConstraints(allergyLabel, 0, 12);
        GridPane.setConstraints(allergyCheckBox, 1, 12);

        GridPane.setConstraints(countryOfResidenceLabel, 0, 14);
        GridPane.setConstraints(countryComboBox, 1, 14);

        GridPane.setConstraints(ethnicityLabel, 0, 16);
        GridPane.setConstraints(ethnicityChoiceBox, 1, 16);

        GridPane.setConstraints(addPatientButton, 0, 18);
        GridPane.setConstraints(cancelButton, 1, 18);
    }

    // Creates the age slider
    private static Slider ageSliderPresets() {
        Slider slider = new Slider(1, 10, 5);
        slider.setMinorTickCount(0);
        slider.setMajorTickUnit(1.0);
        slider.setSnapToTicks(true);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        return slider;
    }

    // Upon pressing the add patient button, creates new patient
    private static void addPatientMethodHandler() {
        System.out.println("Patient information set as follows:\n");
        handleOperationsPrint(newIDInput, ageSlider, radioGroup, eczemaCheckBox, allergyCheckBox,
                countryComboBox, ethnicityChoiceBox);
        newPatientFile = handleOperationsNewPatient(newIDInput, ageSlider, radioGroup, eczemaCheckBox,
                allergyCheckBox, countryComboBox, ethnicityChoiceBox);
        newPatientWindow.close();
    }

    // Cancels creation of patient
    private static void cancelButtonOperation() {
        newPatientFile = null;
        System.out.println("Cancelling patient entry...\n");
        newPatientWindow.close();
    }

    // Creates new patient object with the entered information
    private static Patient handleOperationsNewPatient(TextField id, Slider age, ToggleGroup sex, CheckBox eczema,
                                                      CheckBox allergy, ComboBox<String> country,
                                                      ChoiceBox<String> ethnicity) {
        try {
            newPatientFile = new Patient(
                    Integer.parseInt(id.getText()),
                    (int) Math.round(age.getValue()),
                    getRadioToggleText(sex),
                    eczema.isSelected(),
                    allergy.isSelected(),
                    countryOfResidenceInput(country.getValue()),
                    ethnicityInput(ethnicity.getValue()));
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID entered.\n");
        }
        return newPatientFile;
    }

    private static String ethnicityInput(String value) {
        if (value == null) {
            System.out.println("Note: The 'Ethnicity' field was left empty.");
            System.out.println("The field was given the value 'Unreported'\n");
            return "Unreported";
        } else {
            return value;
        }
    }

    private static String countryOfResidenceInput(String value) {
        if (value == null) {
            System.out.println("Note: The 'Country of Residence' field was left empty.");
            System.out.println("The field was given the value 'Unreported'\n");
            return "Unreported";
        } else {
            return value;
        }
    }

    // Prints new patient information to console
    private static void handleOperationsPrint(TextField id, Slider age, ToggleGroup sex,
                                              CheckBox eczema, CheckBox allergy,
                                              ComboBox<String> country, ChoiceBox<String> ethnicity) {
        String message = "New Patient File: \n";
        // ID
        message += "ID: " + id.getText() + "\n";
        // Age
        message += "Age: " + (int) Math.round(age.getValue()) + "\n";
        // Sex
        message += "Sex: " + getRadioToggleText(sex) + "\n";
        // Eczema
        if (eczema.isSelected()) {
            message += "Eczema: true\n";
        } else {
            message += "Eczema: false\n";
        }
        // Food allergy
        if (allergy.isSelected()) {
            message += "Food Allergy: true\n";
        } else {
            message += "Food Allergy: false\n";
        }
        // Country of residence
        String countryCombo = country.getValue();
        message += "Country of Residence: " + countryCombo + "\n";
        // Ethnicity
        String ethnicityChoice = ethnicity.getValue();
        message += "Ethnicity: " + ethnicityChoice + "\n";
        System.out.println(message);
    }

    // Gets the radio button answer
    private static String getRadioToggleText(ToggleGroup group) {
        try {
            RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
            String toggleGroupValue = selectedRadioButton.getText();
            if (toggleGroupValue.toUpperCase().equals("PREFER NOT TO SAY")) {
                return "UNSPECIFIED";
            } else {
                return toggleGroupValue.toUpperCase();
            }
        } catch (NullPointerException e) {
            System.out.println("Note: The 'Sex' field was left empty.");
            System.out.println("The 'Sex' field was given the value 'UNSPECIFIED'\n");
            return "UNSPECIFIED";
        }
    }
}

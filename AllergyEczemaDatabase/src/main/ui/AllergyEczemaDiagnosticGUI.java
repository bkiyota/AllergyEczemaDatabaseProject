package ui;

/**
 * This class represents the graphical user interface for my personal project. It was created using the
 * JavaFX infrastructure, and is primarily modeled off of example content found in the Oracle documents, as
 * well as 'thenewboston' YouTube tutorials.
 * */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Database;
import model.Patient;
import model.exception.ExistingPatientException;
import model.exception.NoPatientFoundException;
import model.exception.UndefinedNumberException;
import persistence.Reader;
import persistence.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.text.DecimalFormat;

import static model.enumerations.ConditionalProbabilityCases.ALLERGY_GIVEN_ECZEMA;
import static model.enumerations.DatabaseSubsets.*;
import static model.enumerations.MeasureOfAssociation.ODDS_RATIO;
import static model.enumerations.MeasureOfAssociation.RELATIVE_RISK;
import static model.enumerations.MedicalCondition.*;

public class AllergyEczemaDiagnosticGUI extends Application {
    // Main window
    private Stage window;

    // Scenes
    private Scene homeScene;
    private Scene aboutScene;
    private Scene infoScene;
    private Scene analysisScene;
    private Scene updateScene;

    // Database Information objects
    private Label databaseInformationLabel;
    private Label databaseSizeLabel;
    private Label probEczemaLabel;
    private Label probFoodAllergyLabel;
    private Button refreshDatabaseSize;
    private Button refreshProbEczema;
    private Button refreshProbAllergy;
    private Text fullDBSizeText;
    private Text femaleDBSizeText;
    private Text maleDBSizeText;
    private Text probEczemaFullDB;
    private Text probEczemaFemaleDB;
    private Text probEczemaMaleDB;
    private Text probAllergyFullB;
    private Text probAllergyFemaleDB;
    private Text probAllergyMaleDB;

    // Run analysis objects and static elements
    private static final String runAnalysisLabelContent = "Run analyses on the conditional probability of developing "
            + "a food allergy given a positive diagnosis with eczema. In addition,\nmeasures of association between "
            + "the presence of eczema and developing a food allergy are to be provided in the form\nof an odds "
            + "ratio and relative risk assessment.";
    private static final String oddsRatioPopupText = "The odds ratio is a measure on the association between two "
            + "properties. In this case,\nwe are investigating how the presence (or absence) of eczema affects the "
            + "development of\nfood allergies. In this sense, we can identify whether eczema is a potential risk "
            + "factor in\ndeveloping food allergies.\n\nInterpretation:\n    OR > 1: there is a higher odds for "
            + "developing food allergies given the presence of eczema.\n    OR < 1: there is a lower odds for"
            + " developing food allergies given the presence of eczema.\n    OR = 1: the presence of eczema does not "
            + "affect the odds of developing a food allergy";
    private static final String relativeRiskPopupText = "The relative risk is a measure on the association between two "
            + "properties: the 'exposure'\nand the 'outcome'. In this case, it is the ratio of the probability of "
            + "developing a food\nallergy given the presence of eczema, to the probability of developing a food "
            + "allergy given\nno prior history of eczema. In this sense, we can identify whether eczema is a potential "
            + "risk\nfactor in developing food allergies.\n\nInterpretation:\n    RR > 1: the risk of developing a "
            + "food allergy is increased given the prior history of eczema.\n    RR < 1: the risk of developing a "
            + "food allergy is decreased given no prior history of eczema.\n    RR = 1: the presence of eczema does "
            + "not affect the probability of developing a food allergy";
    private Label condProbLabel;
    private Text condProbText;
    private Text condProbTextFemaleDB;
    private Text condProbTextMaleDB;
    private Button condProbCalculateButton;
    private Label orLabel;
    private Button orMoreInfoPopupButton;
    private Text orTextFullDatabase;
    private Text orTextFemaleDB;
    private Text orTextMaleDB;
    private Button orButton;
    private Label rrLabel;
    private Text rrTextFullDB;
    private Text rrTextFemaleDB;
    private Text rrTextMaleDB;
    private Button rrButton;
    private Button rrMoreInfoPopupButton;
    private Separator analysisSep1;
    private Separator analysisSep2;
    private Separator analysisSep3;
    private Separator analysisSep4;
    private Separator analysisSepV1;
    private Separator analysisSepV2;

    // Update Database objects and static elements
    public static final String updateDBLabel = "Add or Remove patient files. Given their ID number a patient can be "
            + "located in the database. In this view of the database, each patient file contains\nan ID, then a"
            + " number of attributes that are of interest for further statistical calculations.";
    private TableColumn<Patient, Integer> idColumn;
    private TableColumn<Patient, Integer> ageColumn;
    private TableColumn<Patient, String> sexColumn;
    private TableColumn<Patient, Boolean> eczemaColumn;
    private TableColumn<Patient, Boolean> allergyColumn;
    private TableColumn<Patient, String> countryColumn;
    private TableColumn<Patient, String> ethnicityColumn;
    private Label updateDatabaseLabel;
    private Label searchIDLabel;
    private TextField searchIDInput;
    private Button searchPatientButton;
    private Button addPatientButton;
    private Label selectedPatientLabel;
    private Button deleteSelectedPatientButton;
    private Button refreshTableViewButton;

    // Database object, patient list object, tableview object, txt files for data persistence, and number formatting
    protected Database fullDatabase;
    private List<Patient> patientList;
    TableView<Patient> viewDatabaseTable;
    private static final String FULL_DATABASE_FILE = "./data/full-database.txt";
    private static final String ORIGINAL_FULL_DATABASE_FILE = "./data/original-full-database.txt";
    private static DecimalFormat df = new DecimalFormat("0.0000");

    // Graphs: objects
    private CategoryAxis dbInfoGraphXAxis;
    private NumberAxis dbInfoGraphYAxis;
    private StackedBarChart<String, Number> dbInfoStackedBarChart;
    private XYChart.Series xyChartEczAndFA;
    private XYChart.Series xyChartEcz;
    private XYChart.Series xyChartFA;
    private XYChart.Series xyChartNoEczAndNoFA;
    private XYChart.Data dataEczFA;
    private XYChart.Data femaleDataEczFA;
    private XYChart.Data maleDataEczFA;
    private XYChart.Data dataEczNoFA;
    private XYChart.Data femaleDataEczNoFA;
    private XYChart.Data maleDataEczNoFA;
    private XYChart.Data dataNoEczFA;
    private XYChart.Data femaleDataNoEczFA;
    private XYChart.Data maleDataNoEczFA;
    private XYChart.Data dataNoEczNoFA;
    private XYChart.Data femaleDataNoEczNoFA;
    private XYChart.Data maleDataNoEczNoFA;

    private CategoryAxis orGraphXAxis;
    private NumberAxis orGraphYAxis;
    private ScatterChart<String, Number> orPlot;
    private CategoryAxis rrGraphXAxis;
    private NumberAxis rrGraphYAxis;
    private ScatterChart<String, Number> rrPlot;

    public static void main(String[] args) {
        Font.loadFont(AllergyEczemaDiagnosticGUI.class.getResourceAsStream("Abel.ttf"), 16);
        // this method goes into Application and sets up program as a JavaFX app
        launch(args);
    }

    // Initialize the database and list (for the purpose of the table)
    public void init() {
        fullDatabase = new Database();
        patientList = new ArrayList<>();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        // Home Page
        VBox homeVBox = homepageVBox();
        MenuBar homepageMenuBar = createMenuBar("Home");
        homeScene = HomePageScene.createScene(homepageMenuBar, homeVBox);

        // About Tab
        MenuBar aboutMenuBar = createMenuBar("About");
        aboutScene = AboutPageScene.createScene(aboutMenuBar);

        // Database Information Tab
        infoScene = databaseInformationScene();

        // Run Analysis Tab
        analysisScene = runAnalysisScene();

        // Update Database Tab
        updateScene = updateDatabaseScene();

        // Putting it all together
        window.setTitle("Eczema and Food Allergy Correspondence App");

        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        window.setScene(homeScene);
        customSceneThemes();
        window.show();
    }

    // Applying the custom CSS stylesheet to the scenes
    private void customSceneThemes() {
        homeScene.getStylesheets().add(getClass().getResource("CustomTheme.css").toExternalForm());
        aboutScene.getStylesheets().add(getClass().getResource("CustomTheme.css").toExternalForm());
        infoScene.getStylesheets().add(getClass().getResource("CustomTheme.css").toExternalForm());
        analysisScene.getStylesheets().add(getClass().getResource("CustomTheme.css").toExternalForm());
        updateScene.getStylesheets().add(getClass().getResource("CustomTheme.css").toExternalForm());
    }

    // Creates the MenuBar object for each of the scenes
    private MenuBar createMenuBar(String pageName) {
        Menu homeMenu = new Menu(pageName);
        MenuItem homeMenuItem = new MenuItem("Home");
        homeMenuItem.setOnAction(e -> window.setScene(homeScene));
        homeMenu.getItems().add(homeMenuItem);

        Menu optionsMenu = new Menu("_Options");

        MenuItem aboutMenuItem = new MenuItem("About...");
        aboutMenuItem.setOnAction(e -> window.setScene(aboutScene));
        MenuItem databaseMenuItem = new MenuItem("About Databases...");
        databaseMenuItem.setDisable(true);
        MenuItem saveMenuItem = new MenuItem("Save");
        saveMenuItem.setOnAction(e -> saveProgram());
        MenuItem loadMenuItem = new MenuItem("Load Original Database...");
        loadMenuItem.setOnAction(e -> loadOriginalState());
        MenuItem quitMenuItem = new MenuItem("Quit...");
        quitMenuItem.setOnAction(e -> closeProgram());

        optionsMenu.getItems().addAll(aboutMenuItem, databaseMenuItem, new SeparatorMenuItem(), saveMenuItem,
                loadMenuItem, quitMenuItem);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(homeMenu, optionsMenu);

        return menuBar;
    }

    // VBOX for home page buttons for navigating to the different scenes
    private VBox homepageVBox() {
        Button databaseInfoButton = new Button("Database Information");
        GridPane.setConstraints(databaseInfoButton, 1, 1);
        databaseInfoButton.setOnAction(e -> window.setScene(infoScene));
        databaseInfoButton.setMinWidth(250);

        Button analysisButton = new Button("Run Analysis");
        GridPane.setConstraints(analysisButton, 1, 2);
        analysisButton.setOnAction(e -> window.setScene(analysisScene));
        analysisButton.setMinWidth(250);

        Button updateDatabaseButton = new Button("Update Database");
        GridPane.setConstraints(updateDatabaseButton, 1, 3);
        updateDatabaseButton.setOnAction(e -> window.setScene(updateScene));
        updateDatabaseButton.setMinWidth(250);

        Button closeProgramButton = new Button("Quit Application");
        GridPane.setConstraints(closeProgramButton, 1, 4);
        closeProgramButton.setOnAction(e -> closeProgram());
        closeProgramButton.setMinWidth(250);

        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(HomePageScene.homeTitleText(), new Separator(),
                HomePageScene.homeIntroductionText(), new Separator(), databaseInfoButton, analysisButton,
                updateDatabaseButton, closeProgramButton);
        return box;
    }

    // Spacing and general settings for the GridPanes in each scene
    private GridPane gridPanePresets() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);
        return grid;
    }

    // Creates horizontal line separator objects with the following presets
    private Separator createSeparator() {
        Separator s = new Separator();
        s.setValignment(VPos.CENTER);
        s.setHalignment(HPos.CENTER);
        GridPane.setColumnSpan(s, 11);
        return s;
    }

    // Creates Vertical line separator objects with the following presets
    private Separator createSeparatorVertical() {
        Separator s = new Separator();
        s.setOrientation(Orientation.VERTICAL);
        s.setValignment(VPos.CENTER);
        return s;
    }

    // Text for displaying the size of the databases
    private String databaseSizeText(String subset) {
        switch (subset) {
            case "FEMALE":
                return "Female Database Size: " + fullDatabase.getDatabaseSize(FEMALE);
            case "MALE":
                return "Male Database Size: " + fullDatabase.getDatabaseSize(MALE);
            default:
                return "Database Size: " + fullDatabase.getDatabaseSize();
        }
    }

    // Calculation of the database size statistics when the button is pressed
    private void calculateDatabaseSize() {
        fullDBSizeText.setText(databaseSizeText("UNSPECIFIED"));
        femaleDBSizeText.setText(databaseSizeText("FEMALE"));
        maleDBSizeText.setText(databaseSizeText("MALE"));
    }

    // Text for displaying the proportion of patients afflicted with eczema in the database
    private String eczemaProbabilityText(String subset) {
        switch (subset) {
            case "FEMALE":
                try {
                    return "Female Database: P(Eczema) = "
                            + df.format(fullDatabase.probabilityMedicalCondition(ECZEMA, FEMALE));
                } catch (UndefinedNumberException e) {
                    return "Female Database: P(Eczema) = ";
                }
            case "MALE":
                try {
                    return "Male Database: P(Eczema) = "
                            + df.format(fullDatabase.probabilityMedicalCondition(ECZEMA, MALE));
                } catch (UndefinedNumberException e) {
                    return "Male Database: P(Eczema) = ";
                }
            default:
                try {
                    return "P(Eczema) = "
                            + df.format(fullDatabase.probabilityMedicalCondition(ECZEMA, FULL));
                } catch (UndefinedNumberException e) {
                    return "P(Eczema) = ";
                }
        }
    }

    // Calculation of the probability of eczema when the button is pressed
    private void calculateEczemaProbability() {
        probEczemaFullDB.setText(eczemaProbabilityText("UNSPECIFIED"));
        probEczemaFemaleDB.setText(eczemaProbabilityText("FEMALE"));
        probEczemaMaleDB.setText(eczemaProbabilityText("MALE"));
    }

    // Text for displaying the proportion of patients afflicted with a food allergy in the database
    private String foodAllergyProbabilityText(String subset) {
        switch (subset) {
            case "FEMALE":
                try {
                    return "Female Database: P(Food allergy) = "
                            + df.format(fullDatabase.probabilityMedicalCondition(ALLERGY, FEMALE));
                } catch (UndefinedNumberException e) {
                    return "Female Database: P(Food allergy) = ";
                }
            case "MALE":
                try {
                    return "Male Database: P(Food allergy) = "
                            + df.format(fullDatabase.probabilityMedicalCondition(ALLERGY, MALE));
                } catch (UndefinedNumberException e) {
                    return "Male Database: P(Food allergy) = ";
                }
            default:
                try {
                    return "P(Food allergy) = "
                            + df.format(fullDatabase.probabilityMedicalCondition(ALLERGY, FULL));
                } catch (UndefinedNumberException e) {
                    return "P(Food allergy) = ";
                }
        }
    }

    // Calculation of the probability of food all when the button is pressed
    private void calculateFoodAllergyProbability() {
        probAllergyFullB.setText(foodAllergyProbabilityText("UNSPECIFIED"));
        probAllergyFemaleDB.setText(foodAllergyProbabilityText("FEMALE"));
        probAllergyMaleDB.setText(foodAllergyProbabilityText("MALE"));
    }

    // Creates and returns the scene for the Database Information page
    private Scene databaseInformationScene() {
        initializeDBInfoText();
        initializeDBInfoButtons();
        MenuBar databaseInformationMenuBar = createMenuBar("Database Information");

        Separator infoSep1 = createSeparator();
        GridPane.setConstraints(infoSep1, 0, 2);

        Separator infoSep2 = createSeparator();
        GridPane.setConstraints(infoSep2, 0, 9);

        Separator infoSep3 = createSeparator();
        GridPane.setConstraints(infoSep3, 0, 16);

        Separator infoSep4 = createSeparator();
        GridPane.setConstraints(infoSep4, 0, 24);

        GridPane databaseInformationGrid = gridPanePresets();
        databaseInformationGrid.getChildren().addAll(databaseInformationLabel, databaseSizeLabel, fullDBSizeText,
                femaleDBSizeText, maleDBSizeText, probEczemaLabel, probEczemaFullDB, probEczemaFemaleDB,
                probEczemaMaleDB, probFoodAllergyLabel, probAllergyFullB, probAllergyFemaleDB, probAllergyMaleDB,
                refreshDatabaseSize, refreshProbEczema, refreshProbAllergy, infoSep1, infoSep2, infoSep3, infoSep4,
                dbInfoGraphSexSubset());

        ScrollPane dbInfoScrollPane = new ScrollPane(databaseInformationGrid);

        BorderPane databaseInformationBorderPane = new BorderPane();
        databaseInformationBorderPane.setTop(databaseInformationMenuBar);
        databaseInformationBorderPane.setCenter(dbInfoScrollPane);

        Scene scene = new Scene(databaseInformationBorderPane, 1000, 600);
        return scene;
    }

    // Initializes text objects and their location in the database information grid pane
    private void initializeDBInfoText() {
        fullDBSizeText = new Text();
        femaleDBSizeText = new Text();
        maleDBSizeText = new Text();
        GridPane.setConstraints(fullDBSizeText, 1, 5);
        GridPane.setConstraints(femaleDBSizeText, 1, 6);
        GridPane.setConstraints(maleDBSizeText, 1, 7);
        calculateDatabaseSize();

        probEczemaFullDB = new Text();
        probEczemaFemaleDB = new Text();
        probEczemaMaleDB = new Text();
        GridPane.setConstraints(probEczemaFullDB, 1, 12);
        GridPane.setConstraints(probEczemaFemaleDB, 1, 13);
        GridPane.setConstraints(probEczemaMaleDB, 1, 14);
        calculateEczemaProbability();

        probAllergyFullB = new Text();
        probAllergyFemaleDB = new Text();
        probAllergyMaleDB = new Text();
        GridPane.setConstraints(probAllergyFullB, 1, 20);
        GridPane.setConstraints(probAllergyFemaleDB, 1, 21);
        GridPane.setConstraints(probAllergyMaleDB, 1, 22);
        calculateFoodAllergyProbability();
    }

    // Initializes button objects and their location in the database information grid pane
    private void initializeDBInfoButtons() {
        databaseInformationLabel = new Label("Calculate database size and individual probabilities for"
                + " the prevalence of eczema or food allergies in the dataset.");
        databaseSizeLabel = new Label("Database Size:");
        probEczemaLabel = new Label("Proportion of patients\nafflicted with eczema:");
        probFoodAllergyLabel = new Label("Proportion of patients\nafflicted with a food allergy:");
        GridPane.setConstraints(databaseInformationLabel, 0, 0, 7, 1);
        GridPane.setConstraints(databaseSizeLabel, 0, 3, 1, 2);
        GridPane.setConstraints(probEczemaLabel, 0, 10, 1, 2);
        GridPane.setConstraints(probFoodAllergyLabel, 0, 17, 1, 2);

        refreshDatabaseSize = new Button("Calculate");
        refreshDatabaseSize.setOnAction(e -> calculateDatabaseSize());
        GridPane.setConstraints(refreshDatabaseSize, 1, 8, 5, 1);
        refreshDatabaseSize.setMinWidth(250);

        refreshProbEczema = new Button("Calculate");
        refreshProbEczema.setOnAction(e -> calculateEczemaProbability());
        GridPane.setConstraints(refreshProbEczema, 1, 15, 5, 1);
        refreshProbEczema.setMinWidth(250);

        refreshProbAllergy = new Button("Calculate");
        refreshProbAllergy.setOnAction(e -> calculateFoodAllergyProbability());
        GridPane.setConstraints(refreshProbAllergy, 1, 23, 5, 1);
        refreshProbAllergy.setMinWidth(250);
    }

    // Returns the graph for the database information data
    private StackedBarChart<String, Number> dbInfoGraphSexSubset() {
        getObservablePatientList();
        initializeDBInfoGraphSexSubset();
        dbInfoStackedBarChartDataEczema();
        dbInfoStackedBarChartDataNoEczema();

        xyChartEczAndFA.getData().addAll(dataEczFA, femaleDataEczFA, maleDataEczFA);
        xyChartEcz.getData().addAll(dataEczNoFA, femaleDataEczNoFA, maleDataEczNoFA);
        xyChartFA.getData().addAll(dataNoEczFA, femaleDataNoEczFA, maleDataNoEczFA);
        xyChartNoEczAndNoFA.getData().addAll(dataNoEczNoFA, femaleDataNoEczNoFA, maleDataNoEczNoFA);
        dbInfoStackedBarChart.getData().addAll(xyChartEczAndFA, xyChartEcz, xyChartFA, xyChartNoEczAndNoFA);
        GridPane.setConstraints(dbInfoStackedBarChart, 1, 25, 7, 3);
        return dbInfoStackedBarChart;
    }

    // Initializes the objects for the database information graph.
    private void initializeDBInfoGraphSexSubset() {
        dbInfoGraphXAxis = new CategoryAxis();
        dbInfoGraphYAxis = new NumberAxis();
        dbInfoStackedBarChart = new StackedBarChart<>(dbInfoGraphXAxis, dbInfoGraphYAxis);

        dbInfoStackedBarChart.setTitle("Distribution of Patient Information");
        dbInfoStackedBarChart.setLegendSide(Side.RIGHT);
        dbInfoGraphXAxis.setLabel("Sex Subset");
        dbInfoGraphXAxis.setCategories(FXCollections.<String>observableArrayList(
                Arrays.asList("Full\ndatabase", "Female\nsubset", "Male\nsubset")));
        dbInfoGraphYAxis.setLabel("Number of Patients in Database");

        xyChartEczAndFA = new XYChart.Series();
        xyChartEczAndFA.setName("Eczema & Food Allergy");

        xyChartEcz = new XYChart.Series();
        xyChartEcz.setName("Eczema & No Food Allergy");

        xyChartFA = new XYChart.Series();
        xyChartFA.setName("No Eczema & Food Allergy");

        xyChartNoEczAndNoFA = new XYChart.Series();
        xyChartNoEczAndNoFA.setName("No Eczema & No Food Allergy");
    }

    // Initializes the data objects for the database information graph.
    private void dbInfoStackedBarChartDataEczema() {
        getObservablePatientList();

        dataEczFA = new XYChart.Data("Full\ndatabase",
                fullDatabase.numPatientsCondition(ECZEMA_AND_ALLERGY, FULL));
        femaleDataEczFA = new XYChart.Data("Female\nsubset",
                fullDatabase.numPatientsCondition(ECZEMA_AND_ALLERGY, FEMALE));
        maleDataEczFA = new XYChart.Data("Male\nsubset",
                fullDatabase.numPatientsCondition(ECZEMA_AND_ALLERGY, MALE));

        dataEczNoFA = new XYChart.Data("Full\ndatabase",
                fullDatabase.numPatientsCondition(ECZEMA_AND_NO_ALLERGY, FULL));
        femaleDataEczNoFA = new XYChart.Data("Female\nsubset",
                fullDatabase.numPatientsCondition(ECZEMA_AND_NO_ALLERGY, FEMALE));
        maleDataEczNoFA = new XYChart.Data("Male\nsubset",
                fullDatabase.numPatientsCondition(ECZEMA_AND_NO_ALLERGY, MALE));
    }

    private void dbInfoStackedBarChartDataNoEczema() {
        getObservablePatientList();

        dataNoEczFA = new XYChart.Data("Full\ndatabase",
                fullDatabase.numPatientsCondition(NO_ECZEMA_AND_ALLERGY, FULL));
        femaleDataNoEczFA = new XYChart.Data("Female\nsubset",
                fullDatabase.numPatientsCondition(NO_ECZEMA_AND_ALLERGY, FEMALE));
        maleDataNoEczFA = new XYChart.Data("Male\nsubset",
                fullDatabase.numPatientsCondition(NO_ECZEMA_AND_ALLERGY, MALE));

        dataNoEczNoFA = new XYChart.Data("Full\ndatabase",
                fullDatabase.numPatientsCondition(NO_ECZEMA_AND_NO_ALLERGY, FULL));
        femaleDataNoEczNoFA = new XYChart.Data("Female\nsubset",
                fullDatabase.numPatientsCondition(NO_ECZEMA_AND_NO_ALLERGY, FEMALE));
        maleDataNoEczNoFA = new XYChart.Data("Male\nsubset",
                fullDatabase.numPatientsCondition(NO_ECZEMA_AND_NO_ALLERGY, MALE));
    }

    // Creates the Run Analysis scene
    private Scene runAnalysisScene() {
        MenuBar analysisMenuBar = createMenuBar("Run Analysis");
        initializeAnalysisConditionalProbability();
        initializeAnalysisOR();
        initializeAnalysisRR();
        initializeAnalysisSeparators();

        Label analysisLabel = new Label(runAnalysisLabelContent);
        GridPane.setConstraints(analysisLabel, 0, 0, 11, 2);

        GridPane analysisGrid = gridPanePresets();
        analysisGrid.getChildren().addAll(analysisLabel, condProbLabel, condProbText, condProbTextFemaleDB,
                condProbTextMaleDB, condProbCalculateButton, orLabel, orTextFullDatabase, orTextFemaleDB, orTextMaleDB,
                orMoreInfoPopupButton, orButton, rrLabel, rrTextFullDB, rrTextFemaleDB, rrTextMaleDB, rrButton,
                rrMoreInfoPopupButton, analysisSep1, analysisSep2, analysisSep3, analysisSep4,
                analysisSepV1, analysisSepV2, orGraphSexSubset(), rrGraphSexSubset());

        ScrollPane analysisScrollPane = new ScrollPane(analysisGrid);
        analysisScrollPane.setHvalue(0.5);
        analysisScrollPane.setVvalue(0.5);

        BorderPane analysisBorderPane = new BorderPane();
        analysisBorderPane.setTop(analysisMenuBar);
        analysisBorderPane.setCenter(analysisScrollPane);

        Scene scene = new Scene(analysisBorderPane, 1000, 600);
        return scene;
    }

    // Initializes the separators in the Run Analysis page
    private void initializeAnalysisSeparators() {
        analysisSep1 = createSeparator();
        GridPane.setConstraints(analysisSep1, 0, 2);

        analysisSep2 = createSeparator();
        GridPane.setConstraints(analysisSep2, 0, 9);

        analysisSep3 = createSeparator();
        GridPane.setConstraints(analysisSep3, 0, 21);

        analysisSep4 = createSeparator();
        GridPane.setConstraints(analysisSep4, 0, 33);

        analysisSepV1 = createSeparatorVertical();
        GridPane.setConstraints(analysisSepV1, 5, 11);
        GridPane.setRowSpan(analysisSepV1, 10);

        analysisSepV2 = createSeparatorVertical();
        GridPane.setConstraints(analysisSepV2, 5, 22);
        GridPane.setRowSpan(analysisSepV2, 10);
    }

    // Initializes the conditional probability objects in the Run Analysis page
    private void initializeAnalysisConditionalProbability() {
        condProbLabel = new Label("Conditional probability of developing a food\nallergy "
                + "given the presence of eczema: ");
        GridPane.setConstraints(condProbLabel, 0, 3, 10, 2);
        condProbLabel.setAlignment(Pos.CENTER);

        condProbText = new Text();
        condProbTextFemaleDB = new Text();
        condProbTextMaleDB = new Text();

        GridPane.setConstraints(condProbText, 1, 5, 11, 1);
        GridPane.setConstraints(condProbTextFemaleDB,1, 6, 11, 1);
        GridPane.setConstraints(condProbTextMaleDB, 1, 7, 11, 1);
        calculateConditionalProbabilityAnalysis();

        condProbCalculateButton = new Button("Calculate");
        condProbCalculateButton.setMinWidth(250);
        condProbCalculateButton.setOnAction(e -> calculateConditionalProbabilityAnalysis());
        GridPane.setConstraints(condProbCalculateButton,1, 8, 11, 1);
    }

    // Calculates the conditional probability text objects in the Run Analysis page
    private String conditionalProbabilityAnalysisText(String subset) {
        switch (subset) {
            case "FEMALE":
                try {
                    return "Female Database: P(Food allergy | Eczema) = "
                            + df.format(fullDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, FEMALE));
                } catch (UndefinedNumberException e) {
                    return "Female Database: P(Food allergy | Eczema) = ";
                }
            case "MALE":
                try {
                    return "Male Database: P(Food allergy | Eczema) = "
                            + df.format(fullDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, MALE));
                } catch (UndefinedNumberException e) {
                    return "Male Database: P(Food allergy | Eczema) = ";
                }
            default:
                try {
                    return "P(Food allergy | Eczema) = "
                            + df.format(fullDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, FULL));
                } catch (UndefinedNumberException e) {
                    return "P(Food allergy | Eczema) = ";
                }
        }
    }

    // Initializes the calculation of the conditional probability text objects via button click
    private void calculateConditionalProbabilityAnalysis() {
        condProbText.setText(conditionalProbabilityAnalysisText("UNSPECIFIED"));
        condProbTextFemaleDB.setText(conditionalProbabilityAnalysisText("FEMALE"));
        condProbTextMaleDB.setText(conditionalProbabilityAnalysisText("MALE"));
    }

    // Initializes objects for the odds ratio analysis in the Run Analysis page
    private void initializeAnalysisOR() {
        orLabel = new Label("Odds Ratio (OR) on the association between a positive\neczema diagnosis"
                + " and the outcome of developing a\nfood allergy: ");
        GridPane.setConstraints(orLabel, 0, 10, 5, 2);

        orTextFullDatabase = new Text();
        orTextFemaleDB = new Text();
        orTextMaleDB = new Text();

        GridPane.setConstraints(orTextFullDatabase, 1, 14);
        GridPane.setConstraints(orTextFemaleDB, 1, 15);
        GridPane.setConstraints(orTextMaleDB, 1, 16);
        calculateOddsRatioAnalysis();

        orButton = new Button("Calculate");
        orButton.setMinWidth(250);
        orButton.setOnAction(e -> calculateOddsRatioAnalysis());
        GridPane.setConstraints(orButton, 1, 17);

        orMoreInfoPopupButton = new Button("More info");
        orMoreInfoPopupButton.setOnAction(e -> {
            ui.MoreInfoPopupBox.display("Information: Odds Ratio", oddsRatioPopupText);
        });
        GridPane.setConstraints(orMoreInfoPopupButton, 0, 13);
    }

    // Calculates the odds ratio analysis in the Run Analysis page
    private String oddsRatioAnalysisText(String subset) {
        switch (subset) {
            case "FEMALE":
                try {
                    return "Female Database: Odds ratio = "
                            + df.format(fullDatabase.measureOfAssociation(ODDS_RATIO, FEMALE));
                } catch (UndefinedNumberException e) {
                    return "Female Database: Odds ratio = ";
                }
            case "MALE":
                try {
                    return "Male Database: Odds ratio = "
                            + df.format(fullDatabase.measureOfAssociation(ODDS_RATIO, MALE));
                } catch (UndefinedNumberException e) {
                    return "Male Database: Odds ratio = ";
                }
            default:
                try {
                    return "Odds ratio = "
                            + df.format(fullDatabase.measureOfAssociation(ODDS_RATIO, FULL));
                } catch (UndefinedNumberException e) {
                    return "Odds ratio = ";
                }
        }
    }

    // Calculates the odds ratio analysis via button click
    private void calculateOddsRatioAnalysis() {
        orTextFullDatabase.setText(oddsRatioAnalysisText("UNSPECIFIED"));
        orTextFemaleDB.setText(oddsRatioAnalysisText("FEMALE"));
        orTextMaleDB.setText(oddsRatioAnalysisText("MALE"));
    }

    // Initializes objects for the relative risk analysis in the Run Analysis page
    private void initializeAnalysisRR() {
        rrLabel = new Label("Relative risk (RR) ratio on the association that a positive\neczema"
                + "diagnosis has on the outcome of developing a\nfood allergy: ");
        GridPane.setConstraints(rrLabel, 0, 22, 5, 2);

        rrTextFullDB = new Text();
        rrTextFemaleDB = new Text();
        rrTextMaleDB = new Text();

        GridPane.setConstraints(rrTextFullDB, 1, 25);
        GridPane.setConstraints(rrTextFemaleDB, 1, 26);
        GridPane.setConstraints(rrTextMaleDB, 1, 27);
        calculateRelativeRiskAnalysis();

        rrButton = new Button("Calculate");
        rrButton.setMinWidth(250);
        rrButton.setOnAction(e -> calculateRelativeRiskAnalysis());
        GridPane.setConstraints(rrButton, 1, 28);

        rrMoreInfoPopupButton = new Button("More info");
        rrMoreInfoPopupButton.setOnAction(e -> {
            ui.MoreInfoPopupBox.display("Information: Relative Risk", relativeRiskPopupText);
        });
        GridPane.setConstraints(rrMoreInfoPopupButton, 0, 24);
    }

    // Calculates the relative risk analysis in the Run Analysis page
    private String relativeRiskAnalysisText(String subset) {
        switch (subset) {
            case "FEMALE":
                try {
                    return "Female Database: Relative Risk = "
                            + df.format(fullDatabase.measureOfAssociation(RELATIVE_RISK, FEMALE));
                } catch (UndefinedNumberException e) {
                    return "Female Database: Relative Risk = ";
                }
            case "MALE":
                try {
                    return "Male Database: Relative Risk = "
                            + df.format(fullDatabase.measureOfAssociation(RELATIVE_RISK, MALE));
                } catch (UndefinedNumberException e) {
                    return "Male Database: Relative Risk = ";
                }
            default:
                try {
                    return "Relative Risk = "
                            + df.format(fullDatabase.measureOfAssociation(RELATIVE_RISK, FULL));
                } catch (UndefinedNumberException e) {
                    return "Relative Risk = ";
                }
        }
    }

    // Calculates the relative risk analysis via button click
    private void calculateRelativeRiskAnalysis() {
        rrTextFullDB.setText(relativeRiskAnalysisText("UNSPECIFIED"));
        rrTextFemaleDB.setText(relativeRiskAnalysisText("FEMALE"));
        rrTextMaleDB.setText(relativeRiskAnalysisText("MALE"));
    }

    // Returns the graph for the odds ratio analysis
    private ScatterChart<String, Number> orGraphSexSubset() {
        getObservablePatientList();
        initializeOddsRatioGraphSexSubset();

        XYChart.Series orSeries = new XYChart.Series();
        orSeries.setName("Odds Ratio");
        try {
            orSeries.getData().add(new XYChart.Data("Full\ndatabase",
                    fullDatabase.measureOfAssociation(ODDS_RATIO, FULL)));
        } catch (UndefinedNumberException e) {
            //
        }
        try {
            orSeries.getData().add(new XYChart.Data("Female\nsubset",
                    fullDatabase.measureOfAssociation(ODDS_RATIO, FEMALE)));
        } catch (UndefinedNumberException e) {
            //
        }
        try {
            orSeries.getData().add(new XYChart.Data("Male\nsubset",
                    fullDatabase.measureOfAssociation(ODDS_RATIO, MALE)));
        } catch (UndefinedNumberException e) {
            //
        }

        orPlot.getData().addAll(orSeries);
        GridPane.setConstraints(orPlot, 6, 10, 5, 10);

        return orPlot;
    }

    // Initializes the elements in the graph for the odds ratio
    private void initializeOddsRatioGraphSexSubset() {
        orGraphXAxis = new CategoryAxis();
        orGraphYAxis = new NumberAxis(-10, 10, 2);
        orPlot = new ScatterChart<>(orGraphXAxis, orGraphYAxis);

        orPlot.setTitle("Association between Eczema\nand Food Allergy");
        orGraphXAxis.setLabel("Sex Subset");
        orGraphXAxis.setCategories(FXCollections.<String>observableArrayList(
                Arrays.asList("Full\ndatabase", "Female\nsubset", "Male\nsubset")));
        orGraphYAxis.setLabel("Magnitude of Statistical Association");
    }



    // Returns the graph for the relative risk analysis
    private ScatterChart<String, Number> rrGraphSexSubset() {
        getObservablePatientList();
        initializeRelativeRiskGraphSexSubset();

        XYChart.Series rrSeries = new XYChart.Series();
        rrSeries.setName("Relative Risk");
        try {
            rrSeries.getData().add(new XYChart.Data("Full\ndatabase",
                    fullDatabase.measureOfAssociation(RELATIVE_RISK, FULL)));
        } catch (UndefinedNumberException e) {
            //
        }
        try {
            rrSeries.getData().add(new XYChart.Data("Female\nsubset",
                    fullDatabase.measureOfAssociation(RELATIVE_RISK, FEMALE)));
        } catch (UndefinedNumberException e) {
            //
        }
        try {
            rrSeries.getData().add(new XYChart.Data("Male\nsubset",
                    fullDatabase.measureOfAssociation(RELATIVE_RISK, MALE)));
        } catch (UndefinedNumberException e) {
            //
        }

        rrPlot.getData().addAll(rrSeries);
        GridPane.setConstraints(rrPlot, 6, 22, 5, 10);

        return rrPlot;
    }

    // Initializes the elements in the graph for the relative risk
    private void initializeRelativeRiskGraphSexSubset() {
        rrGraphXAxis = new CategoryAxis();
        rrGraphYAxis = new NumberAxis(-10, 10, 2);
        rrPlot = new ScatterChart<>(rrGraphXAxis, rrGraphYAxis);

        rrPlot.setTitle("Association between Eczema\nand Food Allergy");
        rrGraphXAxis.setLabel("Sex Subset");
        rrGraphXAxis.setCategories(FXCollections.<String>observableArrayList(
                Arrays.asList("Full\ndatabase", "Female\nsubset", "Male\nsubset")));
        rrGraphYAxis.setLabel("Magnitude of Statistical Association");
    }

    // Creates the scene for the Update Database page
    private Scene updateDatabaseScene() {
        updateDBObjects();
        createTableViewListOfPatients();

        MenuBar updateDatabaseMenuBar = createMenuBar("Update Patient Database");

        GridPane updateDatabaseGrid = gridPanePresets();
        updateDatabaseGrid.getChildren().addAll(updateDatabaseLabel, searchIDLabel, searchIDInput, searchPatientButton,
                addPatientButton, selectedPatientLabel, deleteSelectedPatientButton, refreshTableViewButton,
                viewDatabaseTable);

        BorderPane updateDatabaseBorderPane = new BorderPane();
        updateDatabaseBorderPane.setTop(updateDatabaseMenuBar);
        updateDatabaseBorderPane.setCenter(updateDatabaseGrid);

        Scene scene = new Scene(updateDatabaseBorderPane, 1000, 600);
        return scene;
    }

    // Initializes tableview columns of collection (Database)
    private void initializeTableColumns() {
        idColumn = new TableColumn<>("ID");
        idColumn.setMinWidth(100);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("iD"));

        ageColumn = new TableColumn<>("Age");
        ageColumn.setMinWidth(100);
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        sexColumn = new TableColumn<>("Sex");
        sexColumn.setMinWidth(100);
        sexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));

        eczemaColumn = new TableColumn<>("Eczema");
        eczemaColumn.setMinWidth(100);
        eczemaColumn.setCellValueFactory(new PropertyValueFactory<>("hasEczema"));

        allergyColumn = new TableColumn<>("Food Allergy");
        allergyColumn.setMinWidth(100);
        allergyColumn.setCellValueFactory(new PropertyValueFactory<>("hasFoodAllergy"));

        countryColumn = new TableColumn<>("Country of Residence");
        countryColumn.setMinWidth(150);
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("countryOfResidence"));

        ethnicityColumn = new TableColumn<>("Ethnicity");
        ethnicityColumn.setMinWidth(150);
        ethnicityColumn.setCellValueFactory(new PropertyValueFactory<>("ethnicity"));
    }

    // Creates the TableView for viewing the collection of patients in the Database
    private void createTableViewListOfPatients() {
        initializeTableColumns();
        viewDatabaseTable = new TableView<>();
        viewDatabaseTable.setItems(getObservablePatientList());
        viewDatabaseTable.getColumns().addAll(idColumn, ageColumn, sexColumn, eczemaColumn, allergyColumn,
                countryColumn, ethnicityColumn);
        GridPane.setConstraints(viewDatabaseTable, 0, 4, 12, 6);
        viewDatabaseTable.getSelectionModel().selectFirst();
    }

    // Initializes objects in the Update Database page
    private void initializeUpdateDBObjects() {
        updateDatabaseLabel = new Label(updateDBLabel);
        GridPane.setConstraints(updateDatabaseLabel, 0, 0, 12, 2);
        searchIDLabel = new Label("Find Patient");
        GridPane.setConstraints(searchIDLabel, 1, 3);
        searchIDInput = new TextField();
        GridPane.setConstraints(searchIDInput, 2, 3);
        searchIDInput.setPromptText("ID number");

        searchPatientButton = new Button("Search");
        GridPane.setConstraints(searchPatientButton, 3, 3);
        addPatientButton = new Button("Add Patient");
        GridPane.setConstraints(addPatientButton, 0, 11);
        selectedPatientLabel = new Label("Selected:");
        GridPane.setConstraints(selectedPatientLabel, 10, 3);
        selectedPatientLabel.setMinWidth(75);
        deleteSelectedPatientButton = new Button("Delete");
        GridPane.setConstraints(deleteSelectedPatientButton,11, 3);
        deleteSelectedPatientButton.setMinWidth(75);
        refreshTableViewButton = new Button("Refresh");
        GridPane.setConstraints(refreshTableViewButton,0, 3);
    }

    // Sets button actions for the Update databases page
    private void updateDBObjects() {
        initializeUpdateDBObjects();
        searchPatientButton.setOnAction(e -> scrollToSearchedPatientID());

        addPatientButton.setOnAction(e -> {
            Patient newPatient = ui.NewPatientPopupBox.display("Add New Patient File.",
                    "Creating new patient file...");
            addNewPatientToTable(newPatient);
        });

        deleteSelectedPatientButton.setOnAction(e -> {
            Boolean initializeDelete = ui.DeletePatientPopupBox.display("Would you like to remove the selected "
                    + "patient file?", "Delete selected patient file from database?");
            if (initializeDelete) {
                deleteSelectedPatient();
            }
        });

        refreshTableViewButton.setOnAction(e -> viewDatabaseTable.setItems(getObservablePatientList()));
    }

    // Scrolls to patient upon searching for their ID in the search bar in the update database page
    private void scrollToSearchedPatientID() {
        if (validInput(searchIDInput, searchIDInput.getText())) {
            int patientID = Integer.parseInt(searchIDInput.getText());
            try {
                if (fullDatabase.isExistingPatient(fullDatabase.getPatientFileByID(patientID))) {
                    System.out.println("Patient file located.\n");
                }
                viewDatabaseTable.getItems().stream()
                        .filter(item -> item.getID() == patientID)
                        .findAny()
                        .ifPresent(item -> {
                            viewDatabaseTable.getSelectionModel().select(item);
                            viewDatabaseTable.scrollTo(item);
                        });
                searchIDInput.clear();
            } catch (NoPatientFoundException e) {
                System.out.println("Patient file not found.\n");
                searchIDInput.clear();
            }
        }
    }

    // MenuItem for loading the original state of the database
    private void loadOriginalState() {
        Boolean answer = LoadConfirmBox.display("Initialize Load", "Load original database?\n");
        if (answer) {
            init();
            try {
                patientList = Reader.readDatabase(new File(ORIGINAL_FULL_DATABASE_FILE));
                for (Patient p : patientList) {
                    try {
                        fullDatabase.addPatient(p);
                    } catch (ExistingPatientException e) {
                        System.out.println("Duplicate patient files detected.");
                    }
                }
                ObservableList<Patient> patientObservableList = FXCollections.observableList(patientList);
                viewDatabaseTable.setItems(patientObservableList);
            } catch (IOException e) {
                e.printStackTrace();
                init();
            }
        }
    }

    // Delete the selected patient in the TableView
    private void deleteSelectedPatient() {
        Patient selectedPatient = viewDatabaseTable.getSelectionModel().getSelectedItem();
        viewDatabaseTable.getItems().remove(selectedPatient);
        try {
            fullDatabase.removePatient(selectedPatient.getID());
        } catch (NoPatientFoundException e) {
            System.out.println("Error: mismatch between database and Table (selected patient not found in database.");
        }
        if (!fullDatabase.isExistingPatient(selectedPatient)) {
            System.out.println("Patient was successfully removed.");
        }
        if (fullDatabase.isExistingPatient(selectedPatient)) {
            System.out.println("Unsuccessful removal of selected patient file.");
        }
    }

    // Button action for adding a new patient to the TableView/Database
    private void addNewPatientToTable(Patient newPatient) {
        try {
            fullDatabase.addPatient(newPatient);
            viewDatabaseTable.getItems().add(newPatient);
            System.out.println("Patient was successfully added.\n");
        } catch (NullPointerException e) {
            System.out.println("Patient entry cancelled.\n");
        } catch (ExistingPatientException e) {
            System.out.println("There is already an existing file with the input ID.");
            System.out.println("Patient was not added to the database.\n");
        }
    }

    // Determining whether the input ID is a valid integer (in the search bar text field)
    private boolean validInput(TextField searchIDInput, String text) {
        try {
            int id = Integer.parseInt(searchIDInput.getText());
            System.out.println("Searching for patient with file ID: " + id + "...");
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error: '" + text + "' is not a valid ID number.\n");
            return false;
        }
    }

    // Closing the program, prompts user to save before exiting.
    private void closeProgram() {
        int answer = SaveConfirmBox.display("Initialize Save", "Save before exiting?\n");
        if (answer == 1) {
            saveProgram();
            System.out.println("Database has been saved.\n");
            window.close();
        } else if (answer == 2) {
            window.close();
        } else {
            System.out.println("Process of quitting application cancelled.\n");
        }
        //window.close();
    }

    // Data persistence
    private void saveProgram() {
        try {
            Writer writerFull = new Writer(new File(FULL_DATABASE_FILE));
            for (Patient p : fullDatabase.patientListData()) {
                writerFull.write(p);
            }
            writerFull.close();
            System.out.println("Patient databases saved to file " + FULL_DATABASE_FILE);

        } catch (FileNotFoundException e) {
            System.out.println("Unable to save patient databases to " + FULL_DATABASE_FILE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to programming error
        }
    }

    // Converts the loaded patient list (from the text file) into an ObservableList for the TableView
    protected ObservableList<Patient> getObservablePatientList() {
        ObservableList<Patient> patientObservableList = FXCollections.observableList(getPatientList());
        return patientObservableList;
    }

    // Retrieves the saved information from the text file and adds them into the database; returns a list
    private List<Patient> getPatientList() {
        init();
        try {
            patientList = Reader.readDatabase(new File(FULL_DATABASE_FILE));
            for (Patient p : patientList) {
                try {
                    fullDatabase.addPatient(p);
                } catch (ExistingPatientException e) {
                    System.out.println("Duplicate patient files detected.\n");
                }
            }
            return patientList;
        } catch (IOException e) {
            e.printStackTrace();
            init();
        }
        return patientList;
    }
}

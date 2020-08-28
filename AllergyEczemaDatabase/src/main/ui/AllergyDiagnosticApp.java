package ui;

/**
 * This class represents the diagnostic application that calculates a number of statistical features regarding the
 * probability of having medical conditions eczema and/or a food allergy.
 * Note: this application code is modeled after the TellerApp, specifically the prompts for displaying the main menu,
 * calling on the subsets of databases, and the utilization of while loops. Additionally, the beginning of the
 * database application's persistence has been based off of that found in the TellerApp -- modifications are
 * likely to come once it is covered by course material.
 */

import model.*;
import model.exception.ExistingPatientException;
import model.exception.NoPatientFoundException;
import model.exception.UndefinedNumberException;
import persistence.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import static model.enumerations.ConditionalProbabilityCases.ALLERGY_GIVEN_ECZEMA;
import static model.enumerations.DatabaseSubsets.*;
import static model.enumerations.MeasureOfAssociation.ODDS_RATIO;
import static model.enumerations.MeasureOfAssociation.RELATIVE_RISK;
import static model.enumerations.MedicalCondition.ECZEMA;
import static model.enumerations.MedicalCondition.ALLERGY;

// Conditional Allergy Diagnostic app dependent on Eczema
public class AllergyDiagnosticApp {
    private static final String FULL_DATABASE_FILE = "./data/full-database.txt";
    private static final int AGE_CUTOFF = 10;

    private Database fullDatabase;
    private Patient patientEntry;
    private Scanner input;

    private int id;
    private String sex;
    private int age;
    private boolean hasEczema;
    private boolean hasFoodAllergy;
    boolean updateEczemaStatus;
    boolean updateFoodAllergyStatus;
    private List<String> randomCountryList;
    private List<String> randomEthnicityList;
    private boolean keepGoing;
    private String command;
    private boolean initiateSave;
    private String saveCommand;

    private static final String conditionalProbInfo = "Run analyses on the conditional probability of developing "
            + "a food allergy given a positive diagnosis with eczema.\nIn addition, measures of association between "
            + "the presence of eczema and developing a food allergy\nare to be provided in the form of an odds "
            + "ratio and relative risk assessment.";
    private static final String oddsRatioInfo = "The odds ratio is a measure on the association between two "
            + "properties. In this case,\nwe are investigating how the presence (or absence) of eczema affects the "
            + "development of\nfood allergies. In this sense, we can identify whether eczema is a potential risk "
            + "factor in\ndeveloping food allergies.\n\nInterpretation:\n    OR > 1: there is a higher odds for "
            + "developing food allergies given the presence of eczema.\n    OR < 1: there is a lower odds for"
            + " developing food allergies given the presence of eczema.\n    OR = 1: the presence of eczema does not "
            + "affect the odds of developing a food allergy";
    private static final String relativeRiskInfo = "The relative risk is a measure on the association between two "
            + "properties: the 'exposure'\nand the 'outcome'. In this case, it is the ratio of the probability of "
            + "developing a food\nallergy given the presence of eczema, to the probability of developing a food "
            + "allergy given\nno prior history of eczema. In this sense, we can identify whether eczema is a potential "
            + "risk\nfactor in developing food allergies.\n\nInterpretation:\n    RR > 1: the risk of developing a "
            + "food allergy is increased given the prior history of eczema.\n    RR < 1: the risk of developing a "
            + "food allergy is decreased given no prior history of eczema.\n    RR = 1: the presence of eczema does "
            + "not affect the probability of developing a food allergy";

    // EFFECTS: runs the allergy diagnostic application
    public AllergyDiagnosticApp() {
        runDiagnostic();
    }

    private void initializeRunDiagnostic() {
        keepGoing = true;
        loadDatabases();
        command = null;

        initiateSave = true;
        saveCommand = null;
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runDiagnostic() {
        initializeRunDiagnostic();
        input = new Scanner(System.in);

        while (keepGoing) {
            displayMenu();
            command = input.next().toUpperCase();

            if (command.equals("QUIT")) {
                while (initiateSave) {
                    toggleSaveBeforeQuit();
                    saveCommand = input.next().toUpperCase();
                    initiateSave();
                }
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nDiagnostic application has stopped.");
    }

    private void initiateSave() {
        if (saveCommand.equals("Y")) {
            saveDatabases();
            initiateSave = false;
            keepGoing = false;
        } else if (saveCommand.equals("N")) {
            initiateSave = false;
            keepGoing = false;
        } else {
            System.out.println("Selection not valid ...");
        }
    }

    private void toggleSaveBeforeQuit() {
        System.out.println("\nWould you like to save the database before exiting?\n");
        System.out.println("Y            -> Yes, save database.");
        System.out.println("N            -> Do NOT save changes made.");
    }

    // MODIFIES: this
    // EFFECTS: loads database from DATABASE_FILE, if that file exists;
    //          otherwise, initializes an empty database.
    private void loadDatabases() {
        init();
        try {
            List<Patient> fullPatientDatabase = Reader.readDatabase(new File(FULL_DATABASE_FILE));
            for (Patient p : fullPatientDatabase) {
                try {
                    fullDatabase.addPatient(p);
                } catch (ExistingPatientException e) {
                    System.out.println("Duplicate patient files detected");
                }
            }
        } catch (IOException e) {
            init();
        }
    }

    // EFFECTS: saves state of patient database to DATABASE_FILE. Note that you are only prompted to save upon quitting
    private void saveDatabases() {
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

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("N")) {
            printDatabaseSize();
        } else if (command.equals("ADD")) {
            addNewPatientFile();
        } else if (command.equals("UPDATE")) {
            updatePatientInformation();
        } else if (command.equals("REMOVE")) {
            removePatientFile();
        } else if (command.equals("VIEW")) {
            printPatientFile();
        } else if (command.equals("ANALYZE")) {
            runAnalysis();
        } else if (command.equals("P")) {
            doIndividualProbabilityAnalysis();
        } else if (command.equals("OR")) {
            oddsRatio();
        } else if (command.equals("RR")) {
            relativeRisk();
        } else if (command.equals("INFO")) {
            getInfo();
        } else {
            System.out.println("Selection not valid ...");
        }
    }

    // EFFECTS: initializes database
    private void init() {
        fullDatabase = new Database();
    }

    // EFFECTS: displays menus of options to user
    private void displayMenu() {
        System.out.println("\nSelect from the following options:\n");
        System.out.println("N                 -> Number of patient entries in database");
        System.out.println("ADD               -> Add a new patient file to the database");
        System.out.println("UPDATE            -> Update an existing patient file");
        System.out.println("REMOVE            -> Delete an existing patient file");
        System.out.println("VIEW              -> View an existing patient file");
        System.out.println("ANALYZE           -> Run analysis");
        System.out.println("P                 -> Find individual probability of having eczema or food allergy");
        System.out.println("OR                -> Odds ratio for developing food allergies"
                + " given presence/absence of eczema");
        System.out.println("RR                -> Relative risk associated with development of food allergies"
                + " given presence/absence of eczema\"");
        System.out.println("INFO              -> More information about the analysis options");
        System.out.println("QUIT");
    }

    // EFFECTS: prints information about the odds ratio and relative risk analyses.
    private void getInfo() {
        boolean viewInfoOptions = true;
        String infoCommand = null;

        chooseInfoOptions();
        infoCommand = input.next();
        infoCommand = infoCommand.toUpperCase();

        if (infoCommand.equals("OR")) {
            System.out.print("\n" + oddsRatioInfo + "\n");
            viewInfoOptions = false;
        } else if (infoCommand.equals("RR")) {
            System.out.print("\n" + relativeRiskInfo + "\n");
            viewInfoOptions = false;
        } else {
            System.out.println("\nSelection not valid ...");
        }
    }

    private void chooseInfoOptions() {
        System.out.println("OR          -> Odds ratio");
        System.out.println("RR          -> Relative risk");
    }

    // MODIFIES: this
    // EFFECTS: adds a new patient to the database
    private void addNewPatientFile() {
        patientEntry = createPatientFileWithPrompts();

        try {
            fullDatabase.addPatient(patientEntry);
            System.out.println("Patient file added successfully.");
        } catch (ExistingPatientException e) {
            System.out.println("Unable to enter patient file.");
            System.out.println("There already exists a patient with the specified identification number.");
        }
    }

    // EFFECTS: create new patient with the given prompts. Helper method for addNewPatientFile
    protected Patient createPatientFileWithPrompts() {
        System.out.println("Enter patient information:\n\nPatient ID:\n");
        id = specifyID();

        System.out.println("Patient sex:\n");
        sex = selectSex();

        System.out.println("Patient age: \n");
        age = specifyAge();

        System.out.println("Does patient show presence of eczema?");
        hasEczema = selectEczemaStatus();

        System.out.println("Does patient have any food allergies?");
        hasFoodAllergy = selectFoodAllergyStatus();

        System.out.println("\nAdding patient file to database...");
        Patient createdPatient = new Patient(id, age, sex, hasEczema, hasFoodAllergy,
                pickRandomCountry(), pickRandomEthnicity());

        return createdPatient;
    }

    // EFFECTS: when generating a new patient file, a random country from this list will be assigned
    //          as their country of residence. Just for the purpose of sample data.
    private String pickRandomCountry() {
        randomCountryList = new ArrayList<>();
        randomCountryList.add("United States of America");
        randomCountryList.add("Canada");
        randomCountryList.add("China");
        randomCountryList.add("United Kingdom");
        randomCountryList.add("Japan");
        randomCountryList.add("Mexico");
        randomCountryList.add("Philippines");
        randomCountryList.add("New Zealand");
        randomCountryList.add("Australia");
        randomCountryList.add("Denmark");

        Random random = new Random();
        return randomCountryList.get(random.nextInt(randomCountryList.size()));
    }

    // EFFECTS: when generating a new patient file, a random country from this list will be assigned
    //          as their country of residence. Just for the purpose of sample data.
    private String pickRandomEthnicity() {
        randomEthnicityList = new ArrayList<>();
        randomEthnicityList.add("Caucasian");
        randomEthnicityList.add("Asian");
        randomEthnicityList.add("African American");
        randomEthnicityList.add("Indian");
        randomEthnicityList.add("Latin");
        randomEthnicityList.add("Multiracial");

        Random random = new Random();
        return randomEthnicityList.get(random.nextInt(randomEthnicityList.size()));
    }

    // REQUIRES: ID must be an integer
    // EFFECTS: prompts user to input an Identification number
    private int specifyID() {
        int identificationCode = 0;

        identificationCode = input.nextInt();
        System.out.println("Patient ID entered: " + identificationCode + "\n");
        return identificationCode;
    }

    // EFFECTS: prompts user to select sex
    private String selectSex() {
        String sexSelection = ""; // force entry into loop

        while (!(sexSelection.equals("F") || sexSelection.equals("M") || sexSelection.equals("U"))) {
            System.out.println("F -> FEMALE");
            System.out.println("M -> MALE");
            System.out.println("U -> prefer not to specify");
            sexSelection = input.next();
            sexSelection = sexSelection.toUpperCase();
        }

        if (sexSelection.equals("F")) {
            System.out.println("Selected: FEMALE\n");
            return "FEMALE";
        } else if (sexSelection.equals("M")) {
            System.out.println("Selected: MALE\n");
            return "MALE";
        } else {
            System.out.println("Selected: UNSPECIFIED\n");
            return "UNSPECIFIED";
        }
    }

    // REQUIRES: ages range from 1 to 10 years old
    // EFFECTS: prompts user to input age of patient
    public int specifyAge() { //throws AgeException {
        int ageSpecification;
        ageSpecification = input.nextInt();
        System.out.println("Patient Age entered: " + ageSpecification + "\n");
        return ageSpecification;
    }

    // EFFECTS: prompts user to indicate whether patient has eczema
    private boolean selectEczemaStatus() {
        String eczemaConditionSelection = ""; // force entry into loop

        while (!(eczemaConditionSelection.equals("Y") || eczemaConditionSelection.equals("N"))) {
            System.out.println("Y -> Yes");
            System.out.println("N -> No");
            eczemaConditionSelection = input.next();
            eczemaConditionSelection = eczemaConditionSelection.toUpperCase();
        }

        if (eczemaConditionSelection.equals("Y")) {
            System.out.println("Answered yes.\n");
            return true;
        } else {
            System.out.println("Answered no.\n");
            return false;
        }
    }

    // EFFECTS: prompts user to indicate whether patient has any food allergies
    private boolean selectFoodAllergyStatus() {
        String hasFoodAllergy = ""; // force entry into loop

        while (!(hasFoodAllergy.equals("Y") || hasFoodAllergy.equals("N"))) {
            System.out.println("Y -> Yes");
            System.out.println("N -> No");
            hasFoodAllergy = input.next();
            hasFoodAllergy = hasFoodAllergy.toUpperCase();
        }

        if (hasFoodAllergy.equals("Y")) {
            System.out.println("Answered yes.\n");
            return true;
        } else {
            System.out.println("Answered no.\n");
            return false;
        }
    }

    // EFFECTS: prints database size to the screen
    private void printDatabaseSize() {
        System.out.println("Do you want a particular subset of the database?");
        String selected = selectAnalysisSubset();
        if (selected == "FEMALE") {
            System.out.println("Number of entries in the female patient database:\n");
            System.out.println(fullDatabase.getDatabaseSize(FEMALE));
        } else if (selected == "MALE") {
            System.out.println("Number of entries in the male patient database:\n");
            System.out.println(fullDatabase.getDatabaseSize(MALE));
        } else {
            System.out.println("Number of entries in the patient database:\n");
            System.out.println(fullDatabase.getDatabaseSize());
        }
    }

    // MODIFIES: this
    // EFFECTS: modifies an existing patient file (in every database that it appears in)
    private void updatePatientInformation() {
        System.out.println("Identification number of patient file to be modified:");
        id = specifyID();
        System.out.println("Has patient developed eczema?");
        updateEczemaStatus = selectEczemaStatus();

        System.out.println("Has patient developed any food allergies?");
        updateFoodAllergyStatus = selectFoodAllergyStatus();

        System.out.println("Updating patient file ...\n");
        fullDatabase.modifyExistingPatient(id, updateEczemaStatus, updateFoodAllergyStatus);
        System.out.println("Patient file has been updated.\n");
    }

    // MODIFIES: this
    // EFFECTS: removes the patient file with the corresponding ID number (in every database that it appears in)
    private void removePatientFile() {
        System.out.println("Identification number of patient file to be removed:");
        id = specifyID();
        System.out.println("Removing patient file ...\n");
        try {
            Patient selectedPatient = fullDatabase.getPatientFileByID(id);
            if (fullDatabase.isExistingPatient(selectedPatient)) {
                fullDatabase.removePatient(id);
                System.out.println("Patient file has been removed.\n");
            }
            fullDatabase.patientListData();
            fullDatabase.subsetDatabases();
        } catch (NoPatientFoundException e) {
            System.out.println("No patient file with the corresponding ID was found.");
        }
    }

    // EFFECTS: prints contents of the requested patient file
    private void printPatientFile() {
        Patient selectedPatient;
        System.out.println("Identification number of patient file to viewed:\n");
        id = specifyID();

        try {
            selectedPatient = fullDatabase.getPatientFileByID(id);
            System.out.println("ID: " + selectedPatient.getID());
            System.out.println("AGE: " + selectedPatient.getAge());
            System.out.println("SEX: " + selectedPatient.getSex());
            System.out.println("PATIENT HAS ECZEMA: " + selectedPatient.getHasEczema());
            System.out.println("PATIENT HAS FOOD ALLERGY: " + selectedPatient.getHasFoodAllergy());
            System.out.println("COUNTRY OF RESIDENCE: " + selectedPatient.getCountryOfResidence());
            System.out.println("ETHNICITY: " + selectedPatient.getEthnicity());
        } catch (NoPatientFoundException e) {
            System.out.println("No patient file was found with the entered ID number.\n");
        }
    }

    // EFFECTS: carries out analysis to find the probability of food allergy development given a
    //          positive diagnosis of eczema in infants
    private void runAnalysis() {
        String selected = selectAnalysisSubset();
        System.out.println("Probability of developing a food allergy given a positive diagnosis of eczema:\n");

        try {
            if (selected == "FEMALE") {
                //System.out.println(fullDatabase.calcProbAllergyGivenEczema("FEMALE"));
                System.out.println(fullDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, FEMALE));
            } else if (selected == "MALE") {
                //System.out.println(fullDatabase.calcProbAllergyGivenEczema("MALE"));
                System.out.println(fullDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, MALE));
            } else {
                //System.out.println(fullDatabase.calcProbAllergyGivenEczema());
                System.out.println(fullDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, FULL));
            }
        } catch (UndefinedNumberException e) {
            System.out.println("Answer is undefined.");
        }
    }

    private void oddsRatio() {
        String selected = selectAnalysisSubset();
        System.out.println("The ratio of the odds of developing a food allergy given the presence of eczema "
                + "and the odds of developing a food allergy given the absence of eczema.");
        try {
            if (selected == "FEMALE") {
                //System.out.println(fullDatabase.calcOddsRatio("FEMALE"));
                System.out.println(fullDatabase.measureOfAssociation(ODDS_RATIO, FEMALE));
            } else if (selected == "MALE") {
                //System.out.println(fullDatabase.calcOddsRatio("MALE"));
                System.out.println(fullDatabase.measureOfAssociation(ODDS_RATIO, MALE));
            } else {
                //System.out.println(fullDatabase.calcOddsRatio());
                System.out.println(fullDatabase.measureOfAssociation(ODDS_RATIO, FULL));

            }
        } catch (UndefinedNumberException e) {
            System.out.println("Answer is undefined.");
        }
    }

    private void relativeRisk() {
        String selected = selectAnalysisSubset();
        System.out.println("The risk factor for developing food allergies given presence/absence of eczema.");

        try {
            if (selected == "FEMALE") {
                //System.out.println(fullDatabase.calcRelativeRisk("FEMALE"));
                System.out.println(fullDatabase.measureOfAssociation(RELATIVE_RISK, FEMALE));
            } else if (selected == "MALE") {
                //System.out.println(fullDatabase.calcRelativeRisk("MALE"));
                System.out.println(fullDatabase.measureOfAssociation(RELATIVE_RISK, MALE));
            } else {
                //System.out.println(fullDatabase.calcRelativeRisk());
                System.out.println(fullDatabase.measureOfAssociation(RELATIVE_RISK, FULL));
            }
        } catch (UndefinedNumberException e) {
            System.out.println("Answer is undefined.");
        }
    }

    // EFFECTS: prompts user to select one of the following choices to do analysis on:
    //          full database, female subset, male subset, and returns a string that will be used to
    //          subset the analysis appropriately
    private String selectAnalysisSubset() {
        fullDatabase.subsetDatabases();
        String selection = ""; // force entry into loop

        while (!(selection.equals("FULL") || selection.equals("F") || selection.equals("M"))) {
            System.out.println("FULL    -> for analysis of entire database");
            System.out.println("F       -> to analyze female subset in database");
            System.out.println("M       -> to analyze male subset in database");
            selection = input.next();
            selection = selection.toUpperCase();
        }

        if (selection.equals("Full")) {
            return "";
        } else if (selection.equals("F")) {
            return "FEMALE";
        } else if (selection.equals("M")) {
            return "MALE";
        } else {
            return "";
        }
    }

    // EFFECTS: carries out analysis to find the probability of food allergy development given a
    //          positive diagnosis of eczema in infants
    private void doIndividualProbabilityAnalysis() {
        String selected = selectAnalysisSubset();
        String selectCondition = selectIndividualProbability();

        if (selected == "FEMALE" && selectCondition == "ECZEMA") {
            printSexSubsettedIndividualProbability("FEMALE", "ECZEMA");
        } else if (selected == "FEMALE" && selectCondition == "FOOD ALLERGY") {
            printSexSubsettedIndividualProbability("FEMALE", "FOOD ALLERGY");
        } else if (selected == "MALE" && selectCondition == "ECZEMA") {
            printSexSubsettedIndividualProbability("MALE", "ECZEMA");
        } else if (selected == "MALE" && selectCondition == "FOOD ALLERGY") {
            printSexSubsettedIndividualProbability("MALE", "FOOD ALLERGY");
        } else {
            try {
                if (selectCondition == "ECZEMA") {
                    System.out.println("Proportion of patients with eczema:\n");
                    //System.out.println(fullDatabase.calcProbEczema());
                    System.out.println(fullDatabase.probabilityMedicalCondition(ECZEMA, FULL));
                } else {
                    System.out.println("Proportion of patients with a food allergy:");
                    //System.out.println(fullDatabase.calcProbFoodAllergy());
                    System.out.println(fullDatabase.probabilityMedicalCondition(ALLERGY, FULL));
                }
            } catch (UndefinedNumberException e) {
                System.out.println("Error: Answer is undefined.");
            }
        }
    }

    // EFFECTS: prompts user to select one of the following choices to do analysis on:
    //          full database, female subset, male subset.
    private String selectIndividualProbability() {
        String selection = ""; // force entry into loop
        System.out.println("Select the medical condition for which probability you would like to calculate?\n");

        while (!(selection.equals("E") || selection.equals("FA"))) {
            System.out.println("E         -> analyze proportion with eczema");
            System.out.println("FA        -> analyze proportion with a food allergy");
            selection = input.next();
            selection = selection.toUpperCase();
        }

        if (selection.equals("E")) {
            System.out.println("Selected: (E)czema");
            System.out.println("Analyzing the proportion of patients with eczema...");
            return "ECZEMA";
        } else { //if (selection.equals("FA")) {
            System.out.println("Selected: (F)ood (A)llergy");
            System.out.println("Analyzing the proportion of patients with a food allergy...");
            return "FOOD ALLERGY";
        }
    }

    // EFFECTS: prints the probability of having the specified medical condition for the database, which can
    //          be optionally subsetted in terms of sex.
    private void printSexSubsettedIndividualProbability(String sex, String condition) {
        try {
            if (sex == "FEMALE") {
                if (condition.equals("ECZEMA")) {
                    System.out.println("Proportion of patients with eczema:\n");
                    //System.out.println(fullDatabase.calcProbEczema("FEMALE"));
                    System.out.println(fullDatabase.probabilityMedicalCondition(ECZEMA, FEMALE));
                } else {
                    System.out.println("Proportion of patients with a food allergy:");
                    //System.out.println(fullDatabase.calcProbFoodAllergy("FEMALE"));
                    System.out.println(fullDatabase.probabilityMedicalCondition(ALLERGY, FEMALE));
                }
            } else if (sex == "MALE") {
                if (condition == "ECZEMA") {
                    System.out.println("Proportion of patients with eczema:\n");
                   // System.out.println(fullDatabase.calcProbEczema("MALE"));
                    System.out.println(fullDatabase.probabilityMedicalCondition(ECZEMA, MALE));
                } else {
                    System.out.println("Proportion of patients with a food allergy:");
                   // System.out.println(fullDatabase.calcProbFoodAllergy("MALE"));
                    System.out.println(fullDatabase.probabilityMedicalCondition(ALLERGY, MALE));
                }
            }
        } catch (UndefinedNumberException e) {
            System.out.println("");
        }
    }
}

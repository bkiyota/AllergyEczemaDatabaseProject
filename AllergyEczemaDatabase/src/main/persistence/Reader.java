package persistence;

/**
 * This class, alongside the other two classes in the persistence package, represent the method by which data
 * persistence is implemented in this application. In particular, the reader is the component that reads off the
 * data stored in the txt file and re-introduces it into the active program in the form of a list. This way,
 * we can save patient files and re-access them at a later date.
 * */

import model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// A reader that can read a patient database from a file
public class Reader {
    public static final String DELIMITER = ",";
    public static final String LINE_DELIMITER = "\n";

    // EFFECTS: returns a list of databases parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static List<Patient> readDatabase(File file) throws IOException {
        List<String> fileContent = readFile(file);
        return parseContent(fileContent);
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: returns a list of accounts parsed from list of strings
    // where each string contains data for one account
    private static List<Patient> parseContent(List<String> fileContent) {
        List<Patient> patientDatabase = new ArrayList<>();

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            patientDatabase.add(parseDatabase(lineComponents));
        }
        return patientDatabase;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: components has size 7 where element 0 represents the id of the patient file to be
    //           constructed, element 1 represents the patient's age, elements 2 represents their sex,
    //           element 3 represents whether they have eczema, element 4 represents whether they have a food allergy,
    //           element 5 represents the country they live in, and element 6 represents their ethnicity
    // EFFECTS: returns a patient constructed from components
    private static Patient parseDatabase(List<String> components) {
        int id = Integer.parseInt(components.get(0));
        int age = Integer.parseInt(components.get(1));
        String sex = components.get(2);
        Boolean eczema = Boolean.parseBoolean(components.get(3));
        Boolean allergy = Boolean.parseBoolean(components.get(4));
        String country = components.get(5);
        String ethnicity = components.get(6);

        return new Patient(id, age, sex, eczema, allergy, country, ethnicity);
    }
}

package model;

import persistence.Reader;
import persistence.Saveable;
import java.io.PrintWriter;

/**
 * This class contains the code necessary to create patient files that will later be input into the database. There are
 *a number of parameters that can be specified for each patient, which will eventually lead to the ability to subset
 * the database to obtain a more customizable analysis. The methods composing this class are primarily getters with
 * which patient data can be analyzed.
 * */

public class Patient implements Saveable {
    private int id;
    private int age;
    private String sex;
    private boolean hasEczema;
    private boolean hasFoodAllergy;
    private String countryOfResidence;
    private String ethnicity;

    // REQUIRES: Identification number must be >0
    // EFFECTS: constructs a patient with description about their ID, age, sex, location, ethnicity,
    //          date of their last medical examination, and whether they have eczema or food allergies.
    public Patient(int id, int age, String sex, boolean eczema, boolean allergy,
                   String country, String ethnicity) {
        this.id = id;
        this.age = age;
        this.sex = sex;
        hasEczema = eczema;
        hasFoodAllergy = allergy;
        countryOfResidence = country;
        this.ethnicity = ethnicity;
    }

    // Getters
    public int getID() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public boolean getHasEczema() {
        return hasEczema;
    }

    public boolean getHasFoodAllergy() {
        return hasFoodAllergy;
    }

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    // MODIFIES: this
    // EFFECTS: sets the status of the patient in regard to whether they have been diagnosed with eczema.
    public void setHasEczema(boolean b) {
        hasEczema = b;
    }

    // MODIFIES: this
    // EFFECTS: sets the status of the patient in regard to whether they have developed any food allergy.
    public void setHasFoodAllergy(boolean b) {
        hasFoodAllergy = b;
    }

    // EFFECTS: writes a given patient file into the txt file for the purpose of data persistence
    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(getID());
        printWriter.print(Reader.DELIMITER);
        printWriter.print(getAge());
        printWriter.print(Reader.DELIMITER);
        printWriter.print(getSex());
        printWriter.print(Reader.DELIMITER);
        printWriter.print(getHasEczema());
        printWriter.print(Reader.DELIMITER);
        printWriter.print(getHasFoodAllergy());
        printWriter.print(Reader.DELIMITER);
        printWriter.print(getCountryOfResidence());
        printWriter.print(Reader.DELIMITER);
        printWriter.print(getEthnicity());
        printWriter.print(Reader.LINE_DELIMITER);
    }
}

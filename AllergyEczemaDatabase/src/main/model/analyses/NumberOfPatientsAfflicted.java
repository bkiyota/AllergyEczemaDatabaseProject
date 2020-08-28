package model.analyses;

/**
 * This class contains methods required to get the number of patients afflicted with the different combinations of
 * medical conditions (eczema and food allergy). Given the set of patients as its parameter, these methods reside in
 * a separate class to improve the cohesion and readability. Furthermore, they can be called as static methods since
 * they are performing static operation on the given set of data.
 *
 * The Challenge in reducing the code duplication lies in the fact that the differences between the methods entails
 * calls to the Patient class.
 * */

import model.Database;
import model.Patient;
import java.util.Set;

public class NumberOfPatientsAfflicted extends Database {

    // EFFECTS: creates a new Database object
    public NumberOfPatientsAfflicted() {
        super();
    }

    // EFFECTS: returns the number of Patients in the Set of patients that is afflicted with Eczema.
    public static int numEczemaPatients(Set<Patient> data) {
        int numPatients = 0;
        for (Patient p : data) {
            if (p.getHasEczema()) {
                numPatients++;
            }
        }
        return numPatients;
    }

    // EFFECTS: returns the number of Patients in the Set of patients that is not affected by Eczema.
    public static int numNoEczemaPatients(Set<Patient> data) {
        int numPatients = 0;
        for (Patient p : data) {
            if (!p.getHasEczema()) {
                numPatients++;
            }
        }
        return numPatients;
    }

    // EFFECTS: returns the number of Patients in the Set of patients that is afflicted with Food Allergy.
    public static int numAllergyPatients(Set<Patient> data) {
        int numPatients = 0;
        for (Patient p : data) {
            if (p.getHasFoodAllergy()) {
                numPatients++;
            }
        }
        return numPatients;
    }

    // EFFECTS: returns the number of Patients in the Set of patients that is not affected by a Food Allergy.
    public static int numNoAllergyPatients(Set<Patient> data) {
        int numPatients = 0;
        for (Patient p : data) {
            if (!p.getHasFoodAllergy()) {
                numPatients++;
            }
        }
        return numPatients;
    }

    // EFFECTS: returns the number of Patients in the Set of patients that is afflicted with Eczema and Food Allergy.
    public static int numEczemaAndAllergyPatients(Set<Patient> data) {
        int numPatients = 0;
        for (Patient p : data) {
            if (p.getHasEczema() && p.getHasFoodAllergy()) {
                numPatients++;
            }
        }
        return numPatients;
    }

    // EFFECTS: returns the number of Patients in the Set of patients that is afflicted with Food Allergy but not
    //          eczema.
    public static int numNoEczemaAndAllergyPatients(Set<Patient> data) {
        int numPatients = 0;
        for (Patient p : data) {
            if (!p.getHasEczema() && p.getHasFoodAllergy()) {
                numPatients++;
            }
        }
        return numPatients;
    }

    // EFFECTS: returns the number of Patients in the Set of patients that is afflicted with Eczema but not a food
    //          allergy.
    public static int numEczemaAndNoAllergyPatients(Set<Patient> data) {
        int numPatients = 0;
        for (Patient p : data) {
            if (p.getHasEczema() && !p.getHasFoodAllergy()) {
                numPatients++;
            }
        }
        return numPatients;
    }

    // EFFECTS: returns the number of Patients in the Set of patients that is afflicted neither eczema nor a food
    //          allergy.
    public static int numNoEczemaAndNoAllergyPatients(Set<Patient> data) {
        int numPatients = 0;
        for (Patient p : data) {
            if (!p.getHasEczema() && !p.getHasFoodAllergy()) {
                numPatients++;
            }
        }
        return numPatients;
    }
}

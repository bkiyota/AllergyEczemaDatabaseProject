package model;

/**
 * This class represents the collection of Patient objects that are in the collection (database). Statistical analyses
 * will be conducted at the database level; furthermore, there will be functionality to add/remove/edit new or existing
 * patient files in the database.
 * */

import model.analyses.NumberOfPatientsAfflicted;
import model.enumerations.ConditionalProbabilityCases;
import model.enumerations.DatabaseSubsets;
import model.enumerations.MeasureOfAssociation;
import model.enumerations.MedicalCondition;
import model.exception.*;
import java.util.*;
import static model.enumerations.ConditionalProbabilityCases.*;
import static model.enumerations.MedicalCondition.*;

public class Database {
    Set<Patient> patientData;
    Set<Patient> femalePatientData;
    Set<Patient> malePatientData;

    // EFFECTS: constructs an empty database to store patient information. Only the full database is initialized
    //          as an empty set. The femalePatientData and malePatientData aren't initialized until the first patient
    //          gets added.
    public Database() {
        patientData = new HashSet<>();
    }

    // REQUIRES: database is not empty
    // EFFECTS: return true if the ID of the patient input matches that of one of the existing patient files
    //          located in the database, false otherwise.
    public boolean isExistingPatient(Patient patient) {
        boolean existingPatient = false;
        for (Patient p : patientData) {
            if (p.getID() == patient.getID()) {
                existingPatient = true;
            }
        }
        return existingPatient;
    }

    // MODIFIES: this
    // EFFECTS: adds a new patient to the database if the patient's ID number is not already in the system.
    //          If the patient is already in the system, nothing will happen since that ID has already been filled.
    //          The addition of a new patient also triggers the subset of databases - i.e. the addition of the patient
    //          file into a subsetted database according to their sex
    public void addPatient(Patient patient) throws ExistingPatientException {

        if (isExistingPatient(patient)) {
            throw new ExistingPatientException();
        } else {
            patientData.add(patient);
            subsetDatabases();
        }
    }

    // MODIFIES: this
    // EFFECTS: stratify the full patient database into more specific subsets by producing new Sets of files
    public void subsetDatabases() {
        femalePatientData = new HashSet<>();
        malePatientData = new HashSet<>();
        for (Patient p : patientData) {
            if (p.getSex().equals("FEMALE")) {
                femalePatientData.add(p);
            }
            if (p.getSex().equals("MALE")) {
                malePatientData.add(p);
            }
        }
    }

    // EFFECTS: returns the number patients that are incorporated into the database.
    public int getDatabaseSize() {
        return patientData.size();
    }

    // EFFECTS: returns the number patients that are incorporated into the database. The
    //          parameter can be optionally used to subset the patient data by sex. If a sex other than female or male
    //          is specified, then the method will get the size of the full database
    public int getDatabaseSize(DatabaseSubsets subset) {
        subsetDatabases();
        switch (subset) {
            case FEMALE:
                return femalePatientData.size();
            case MALE:
                return malePatientData.size();
            default:
                return getDatabaseSize();
        }
    }

    // MODIFIES: this
    // EFFECTS: given the ID code for a patient whose file exists in the database, change the status of either
    //          the eczema or food allergy diagnosis. If no patient is found with the corresponding ID code, do
    //          nothing.
    //          Since this modifies a patient in the full database, we must refactor that change to be reflected in the
    //          appropriate subsetted database, if required.
    //          Note that this action is replaced in the GUI via deleting and remaking the Patient file
    public void modifyExistingPatient(int id, boolean eczema, boolean allergy) {
        for (Patient p : patientData) {
            if (p.getID() == id) {
                p.setHasEczema(eczema);
                p.setHasFoodAllergy(allergy);
            }
        }
        subsetDatabases();
    }

    // MODIFIES: this
    // EFFECTS: given an identification code, remove the corresponding patient file from the database.
    //          If no matching ID is found, the NoPatientFoundException is thrown. Also removes the patient file from
    //          the subsetted database, if required
    public void removePatient(int id) throws NoPatientFoundException {
        Patient patientToRemove = null;
        for (Patient p : patientData) {
            if (p.getID() == id) {
                patientToRemove = p;
            }
        }
        if (patientToRemove == null) {
            throw new NoPatientFoundException();
        } else {
            patientData.remove(patientToRemove);
            subsetDatabases();
        }
    }

    // EFFECTS: given an identification code, return the corresponding patient file, which is referenced to
    //          the field called selectedPatient. If no patient is found for the specified ID, throw the
    //          NoPatientFoundException.
    public Patient getPatientFileByID(int id) throws NoPatientFoundException {
        Patient selectedPatient = null;
        for (Patient p : patientData) {
            if (p.getID() == id) {
                selectedPatient = p;
            }
        }
        if (selectedPatient == null) {
            throw new NoPatientFoundException();
        } else {
            return selectedPatient;
        }
    }

    // MODIFIES: this
    // EFFECTS: converts the set of patient files into a list of patient files, primarily for the purpose of data
    //          persistence so that patients can be retrieved via their indices (order does not matter).
    public List<Patient> patientListData() {
        List<Patient> patientDataList = new ArrayList<>();
        for (Patient p : patientData) {
            patientDataList.add(p);
        }
        return patientDataList;
    }

    // EFFECTS: Returns the number of patients afflicted with the specified conditions. This method requires two
    //          parameters, one of which specifies the desired medical condition (or specific combination of conditions)
    //          the second determines the subset of the database that the operation will be performed on. Once the case
    //          is appropriately matched, the corresponding method in the NumberOfPatientsAfflicted class will be called
    //          Note that the default case is NO_ECZEMA_AND_NO_FOOD_ALLERGY.
    public int numPatientsCondition(MedicalCondition condition, DatabaseSubsets subset) {
        Set<Patient> data = selectedSubset(subset);
        switch (condition) {
            case ECZEMA:
                return NumberOfPatientsAfflicted.numEczemaPatients(data);
            case NO_ECZEMA:
                return NumberOfPatientsAfflicted.numNoEczemaPatients(data);
            case ALLERGY:
                return NumberOfPatientsAfflicted.numAllergyPatients(data);
            case NO_ALLERGY:
                return NumberOfPatientsAfflicted.numNoAllergyPatients(data);
            case ECZEMA_AND_ALLERGY:
                return NumberOfPatientsAfflicted.numEczemaAndAllergyPatients(data);
            case NO_ECZEMA_AND_ALLERGY:
                return NumberOfPatientsAfflicted.numNoEczemaAndAllergyPatients(data);
            case ECZEMA_AND_NO_ALLERGY:
                return NumberOfPatientsAfflicted.numEczemaAndNoAllergyPatients(data);
            default:
                return NumberOfPatientsAfflicted.numNoEczemaAndNoAllergyPatients(data);
        }
    }

    // EFFECTS: calculates the proportion of patients afflicted with a particular medical condition (or combination of
    //          medical conditions) and outputs a double. This method requires two
    //          parameters, one of which specifies the desired medical condition (or specific combination of conditions)
    //          the second determines the subset of the database that the operation will be performed on. Once the case
    //          is appropriately matched, the corresponding method in the NumberOfPatientsAfflicted class will be called
    //          Note that the default case is NO_ECZEMA_AND_NO_FOOD_ALLERGY.
    //          If the denominator of the return line as a denominator equal to zero, throws the
    //          UndefinedNumberException
    public double probabilityMedicalCondition(MedicalCondition condition, DatabaseSubsets subset) throws
            UndefinedNumberException {
        Set<Patient> data = validSubset(selectedSubset(subset));
        switch (condition) {
            case ECZEMA:
                return (double) NumberOfPatientsAfflicted.numEczemaPatients(data) / data.size();
            case NO_ECZEMA:
                return (double) NumberOfPatientsAfflicted.numNoEczemaPatients(data) / data.size();
            case ALLERGY:
                return (double) NumberOfPatientsAfflicted.numAllergyPatients(data) / data.size();
            case NO_ALLERGY:
                return (double) NumberOfPatientsAfflicted.numNoAllergyPatients(data) / data.size();
            case ECZEMA_AND_ALLERGY:
                return (double) NumberOfPatientsAfflicted.numEczemaAndAllergyPatients(data) / data.size();
            case NO_ECZEMA_AND_ALLERGY:
                return (double) NumberOfPatientsAfflicted.numNoEczemaAndAllergyPatients(data) / data.size();
            case ECZEMA_AND_NO_ALLERGY:
                return (double) NumberOfPatientsAfflicted.numEczemaAndNoAllergyPatients(data) / data.size();
            default:
                return (double) NumberOfPatientsAfflicted.numNoEczemaAndNoAllergyPatients(data) / data.size();
        }
    }

    // EFFECTS: Calculates the conditional probability given the input combination of two medical conditions, as
    //          specified by the ConditionalProbabilityCases enumeration. This method requires two
    //          parameters, one of which specifies the desired Case of conditional probability, and
    //          the second determines the subset of the database that the operation will be performed on. Once the case
    //          is appropriately matched, the corresponding method will be called
    //          Note that the default case is NO_ALLERGY_GIVEN_NO_ECZEMA
    //          If the denominator of the return line as a denominator equal to zero, throws the
    //          UndefinedNumberException
    public double conditionalProbability(ConditionalProbabilityCases c, DatabaseSubsets subset) throws
            UndefinedNumberException {
        switch (c) {
            case ALLERGY_GIVEN_ECZEMA:
                return probabilityMedicalCondition(ECZEMA_AND_ALLERGY, subset)
                        / validDenominator(probabilityMedicalCondition(ECZEMA, subset));
            case NO_ALLERGY_GIVEN_ECZEMA:
                return probabilityMedicalCondition(ECZEMA_AND_NO_ALLERGY, subset)
                        / validDenominator(probabilityMedicalCondition(ECZEMA, subset));
            case ALLERGY_GIVEN_NO_ECZEMA:
                return probabilityMedicalCondition(NO_ECZEMA_AND_ALLERGY, subset)
                        / validDenominator(probabilityMedicalCondition(NO_ECZEMA, subset));
            default:
                return probabilityMedicalCondition(NO_ECZEMA_AND_NO_ALLERGY, subset)
                        / validDenominator(probabilityMedicalCondition(NO_ECZEMA, subset));
        }
    }

    // EFFECTS: calculates a measure of association between the two medical conditions in the form of an odds ratio
    //          or relative risk, depending on the MeasureOfAssociation enumeration.
    //          Note that the default case is relative risk.
    //          IF the denominator of the return line is zero in either case, the UndefinedNumberException is thrown.
    public double measureOfAssociation(MeasureOfAssociation measure, DatabaseSubsets subset) throws
            UndefinedNumberException {
        switch (measure) {
            case ODDS_RATIO:
                return (conditionalProbability(ALLERGY_GIVEN_ECZEMA, subset)
                        / conditionalProbability(NO_ALLERGY_GIVEN_ECZEMA, subset))
                        / validDenominator(conditionalProbability(ALLERGY_GIVEN_NO_ECZEMA, subset)
                        / conditionalProbability(NO_ALLERGY_GIVEN_NO_ECZEMA, subset));
            default:
                return (conditionalProbability(ALLERGY_GIVEN_ECZEMA, subset)
                        / (conditionalProbability(ALLERGY_GIVEN_ECZEMA, subset)
                        + conditionalProbability(NO_ALLERGY_GIVEN_ECZEMA, subset)))
                        / (conditionalProbability(ALLERGY_GIVEN_NO_ECZEMA, subset)
                        / validDenominator(conditionalProbability(ALLERGY_GIVEN_NO_ECZEMA, subset)
                        + conditionalProbability(NO_ALLERGY_GIVEN_NO_ECZEMA, subset)));
        }
    }

    // EFFECTS: this helper method takes in the DatabaseSubsets enumeration, and returns the appropriate set of patients
    //          as its output. This method bypasses the need to overload methods with each possible case of subset
    //          needing to be defined. The default case is the main database.
    private Set<Patient> selectedSubset(DatabaseSubsets subset) {
        switch (subset) {
            case FEMALE:
                return femalePatientData;
            case MALE:
                return malePatientData;
            default:
                return patientData;
        }
    }

    // EFFECTS: This helper method is a check to ensure that the subset is not empty, since it will be used in further
    //          calculations (in the denominator). If it is empty, the UndefinedNumberException will be thrown. IF not,
    //          it will return the set of patients.
    private Set<Patient> validSubset(Set<Patient> data) throws UndefinedNumberException {
        if (data.size() == 0) {
            throw new UndefinedNumberException();
        }
        return data;
    }

    // EFFECTS: This helper method is a check for the calculations of the conditional probability and association
    //          analyses. If it is equal to 0.0, the UndefinedNumberException will be thrown. IF not, it will return
    //          double that was input.
    private double validDenominator(double d) throws UndefinedNumberException {
        if (d == 0.0) {
            throw new UndefinedNumberException();
        }
        return d;
    }
}

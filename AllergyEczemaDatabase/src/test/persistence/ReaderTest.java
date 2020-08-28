package persistence;

/**
 * This class is responsible for testing the methods of the Reader class.
 * */

import model.Patient;
import model.Database;
import model.exception.ExistingPatientException;
import model.exception.NoPatientFoundException;
import model.exception.UndefinedNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static model.enumerations.ConditionalProbabilityCases.ALLERGY_GIVEN_ECZEMA;
import static model.enumerations.DatabaseSubsets.*;
import static model.enumerations.DatabaseSubsets.MALE;
import static model.enumerations.MedicalCondition.ECZEMA;
import static model.enumerations.MedicalCondition.ALLERGY;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ReaderTest {
    Database testDatabase;

    @BeforeEach
    void runBefore() {
        testDatabase = new Database();
    }

    @Test
    void testParsePatientDatabaseFile2SingleFemalePatient() {
        Reader testReader = new Reader();
        try {
            List<Patient> testDatabaseFile = Reader.readDatabase(new File ("./data/testDatabaseFile2.txt"));

            for (int i = 0; i < testDatabaseFile.size(); i++) {
                try {
                    testDatabase.addPatient(testDatabaseFile.get(i));
                } catch (ExistingPatientException e) {
                    fail("Exception was thrown");
                }
            }

            assertEquals(1, testDatabase.getDatabaseSize());
            assertEquals(1, testDatabase.getDatabaseSize(FULL));
            assertEquals(1, testDatabase.getDatabaseSize(FEMALE));
            assertEquals(0, testDatabase.getDatabaseSize(MALE));

            assertEquals(1.0, testDatabase.probabilityMedicalCondition(ECZEMA, FULL));
            assertEquals(1.0, testDatabase.probabilityMedicalCondition(ECZEMA, FULL));
            assertEquals(1.0, testDatabase.probabilityMedicalCondition(ECZEMA, FEMALE));

            assertEquals(0.0, testDatabase.probabilityMedicalCondition(ALLERGY, FULL));
            assertEquals(0.0, testDatabase.probabilityMedicalCondition(ALLERGY, FULL));
            assertEquals(0.0, testDatabase.probabilityMedicalCondition(ALLERGY, FEMALE));

            assertEquals(0.0, testDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, FULL));
            assertEquals(0.0, testDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, FULL));
            assertEquals(0.0,testDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, FEMALE));

            assertEquals(5, testDatabase.getPatientFileByID(5).getID());
            assertEquals(6, testDatabase.getPatientFileByID(5).getAge());
            assertEquals("FEMALE", testDatabase.getPatientFileByID(5).getSex());
            assertEquals("New Zealand", testDatabase.getPatientFileByID(5).getCountryOfResidence());
            assertEquals("African American", testDatabase.getPatientFileByID(5).getEthnicity());
            assertTrue(testDatabase.getPatientFileByID(5).getHasEczema());
            assertFalse(testDatabase.getPatientFileByID(5).getHasFoodAllergy());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        } catch (NoPatientFoundException e) {
            fail("NoPatientFoundException should not have been thrown.");
        } catch (UndefinedNumberException e) {
            fail("UndefinedNumberException should not have been thrown");
        }
    }

    @Test
    void testParsePatientDatabaseFile1FivePatients() {
        try {
            List<Patient> testDatabaseFile = Reader.readDatabase(new File ("./data/testDatabaseFile1.txt"));

            for (int i = 0; i < testDatabaseFile.size(); i++) {
                try {
                    testDatabase.addPatient(testDatabaseFile.get(i));
                } catch (ExistingPatientException e) {
                    fail("Exception was thrown");
                }
            }

            assertEquals(5, testDatabase.getDatabaseSize());
            assertEquals(5, testDatabase.getDatabaseSize(FULL));
            assertEquals(2, testDatabase.getDatabaseSize(FEMALE));
            assertEquals(2, testDatabase.getDatabaseSize(MALE));

            assertEquals(0.6, testDatabase.probabilityMedicalCondition(ECZEMA, FULL));
            assertEquals(0.6, testDatabase.probabilityMedicalCondition(ECZEMA, FULL));
            assertEquals(0.5, testDatabase.probabilityMedicalCondition(ECZEMA, FEMALE));
            assertEquals(0.5, testDatabase.probabilityMedicalCondition(ECZEMA, MALE));

            assertEquals(0.2, testDatabase.probabilityMedicalCondition(ALLERGY, FULL));
            assertEquals(0.2, testDatabase.probabilityMedicalCondition(ALLERGY, FULL));
            assertEquals(0.0, testDatabase.probabilityMedicalCondition(ALLERGY, FEMALE));
            assertEquals(0.0, testDatabase.probabilityMedicalCondition(ALLERGY, MALE));

            assertEquals(0.33333333333333337, testDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, FULL));
            assertEquals(0.33333333333333337, testDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, FULL));
            assertEquals(0.0, testDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, FEMALE));
            assertEquals(0.0, testDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, MALE));
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        } catch (UndefinedNumberException e) {
            fail("UndefinedNumberException should not have been thrown");
        }
    }

    @Test
    void testParsePatientDatabaseFile3TenPatients() {
        try {
            List<Patient> testDatabaseFile = Reader.readDatabase(new File ("./data/testDatabaseFile3.txt"));

            for (int i = 0; i < testDatabaseFile.size(); i++) {
                try {
                    testDatabase.addPatient(testDatabaseFile.get(i));
                } catch (ExistingPatientException e) {
                    fail("Exception was thrown");
                }
            }

            assertEquals(10, testDatabase.getDatabaseSize());
            assertEquals(10, testDatabase.getDatabaseSize(FULL));
            assertEquals(5, testDatabase.getDatabaseSize(FEMALE));
            assertEquals(5, testDatabase.getDatabaseSize(MALE));

            assertEquals(0.5, testDatabase.probabilityMedicalCondition(ECZEMA, FULL));
            assertEquals(1.0, testDatabase.probabilityMedicalCondition(ECZEMA, MALE));

            assertEquals(0.3, testDatabase.probabilityMedicalCondition(ALLERGY, FULL));
            assertEquals(0.2, testDatabase.probabilityMedicalCondition(ALLERGY, FEMALE));
            assertEquals(0.4, testDatabase.probabilityMedicalCondition(ALLERGY, MALE));

            assertEquals(0.4, testDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, FULL));
            assertEquals(0.4, testDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, MALE));
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        } catch (UndefinedNumberException e) {
            fail("UndefinedNumberException should not have been thrown");
        }
    }

    @Test
    void testIOException() {
        try {
            Reader.readDatabase(new File("./path/does/not/exist/testDatabase4.txt"));
        } catch (IOException e) {
            // expected
        }
    }

}

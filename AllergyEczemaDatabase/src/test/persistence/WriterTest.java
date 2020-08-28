package persistence;

/**
 * This class is responsible for testing the methods of the Reader class.
 * */

import model.Patient;
import model.Database;
import model.exception.ExistingPatientException;
import model.exception.UndefinedNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static model.enumerations.ConditionalProbabilityCases.ALLERGY_GIVEN_ECZEMA;
import static model.enumerations.DatabaseSubsets.*;
import static model.enumerations.MedicalCondition.ECZEMA;
import static model.enumerations.MedicalCondition.ALLERGY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WriterTest {
    private static final String TEST_FILE = "./data/testDatabase3.txt";
    private Writer testWriter;
    private Database testDatabase;
    private Database loadingTestDatabase;

    private Patient p1;
    private Patient p2;
    private Patient p3;
    private Patient p4;
    private Patient p5;
    private Patient p6;
    private Patient p7;
    private Patient p8;
    private Patient p9;
    private Patient p10;

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new Writer(new File(TEST_FILE));
        testDatabase = new Database();
        loadingTestDatabase = new Database();

        p1 = new Patient(1, 6, "FEMALE",
                false, false, "Canada", "Caucasian");
        p2 = new Patient(2, 3, "MALE",
                true, false, "United States", "Mexican");
        p3 = new Patient(3, 2, "FEMALE",
                false, false, "China", "Asian");
        p4 = new Patient(4, 4, "MALE",
                true, true, "Japan", "Asian");
        p5 = new Patient(5, 6, "FEMALE",
                false, false, "Canada", "Caucasian");
        p6 = new Patient(6, 7, "MALE",
                true, true, "Canada", "African America");
        p7 = new Patient(7, 4, "FEMALE",
                false, false, "United States", "Indian");
        p8 = new Patient(8, 5, "MALE",
                true, false, "France", "Caucasian");
        p9 = new Patient(9, 3, "FEMALE",
                false, true, "England", "Indian");
        p10 = new Patient(10, 6, "MALE",
                true, false, "Canada", "Caucasian");

        try {
            testDatabase.addPatient(p1);
            testDatabase.addPatient(p2);
            testDatabase.addPatient(p3);
            testDatabase.addPatient(p4);
            testDatabase.addPatient(p5);
            testDatabase.addPatient(p6);
            testDatabase.addPatient(p7);
            testDatabase.addPatient(p8);
            testDatabase.addPatient(p9);
            testDatabase.addPatient(p10);
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        }
    }

    @Test
    void testWriteDatabase() {
        // save test database to file
        for (Patient p : testDatabase.patientListData()) {
            testWriter.write(p);
        }
        testWriter.close();

        // now read the patient files back in and verify that the database contains the expected values
        try {
            List<Patient> testDatabaseFile = Reader.readDatabase(new File (TEST_FILE));
            for (int i = 0; i < testDatabaseFile.size(); i++) {
                try {
                    loadingTestDatabase.addPatient(testDatabaseFile.get(i));
                } catch (ExistingPatientException e) {
                    fail("The ExistingPatientException was inappropriately thrown!");
                }
            }
            assertEquals(10, loadingTestDatabase.getDatabaseSize());
            assertEquals(10, loadingTestDatabase.getDatabaseSize(FULL));
            assertEquals(5, loadingTestDatabase.getDatabaseSize(FEMALE));
            assertEquals(5, loadingTestDatabase.getDatabaseSize(MALE));

            assertEquals(0.5, loadingTestDatabase.probabilityMedicalCondition(ECZEMA, FULL));
            assertEquals(0.0, loadingTestDatabase.probabilityMedicalCondition(ECZEMA, FEMALE));
            assertEquals(1.0, loadingTestDatabase.probabilityMedicalCondition(ECZEMA, MALE));

            assertEquals(0.3, loadingTestDatabase.probabilityMedicalCondition(ALLERGY, FULL));
            assertEquals(0.2, loadingTestDatabase.probabilityMedicalCondition(ALLERGY, FEMALE));
            assertEquals(0.4, loadingTestDatabase.probabilityMedicalCondition(ALLERGY, MALE));

            assertEquals(0.4, loadingTestDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, FULL));
            assertEquals(0.4, loadingTestDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, MALE));
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        } catch (UndefinedNumberException e) {
            fail("UndefinedNumberException should not have been thrown");
        }
    }
}

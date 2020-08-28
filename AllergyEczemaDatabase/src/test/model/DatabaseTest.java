package model;

/**
 * This class provides a suite of tests for the methods in the Database class.
 * */

import model.analyses.NumberOfPatientsAfflicted;
import model.exception.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static model.enumerations.ConditionalProbabilityCases.*;
import static model.enumerations.ConditionalProbabilityCases.NO_ALLERGY_GIVEN_ECZEMA;
import static model.enumerations.DatabaseSubsets.*;
import static model.enumerations.MeasureOfAssociation.ODDS_RATIO;
import static model.enumerations.MeasureOfAssociation.RELATIVE_RISK;

import static model.enumerations.MedicalCondition.*;
import static org.junit.jupiter.api.Assertions.*;
import java.text.DecimalFormat;

public class DatabaseTest {
    private Patient p1;
    private Patient p1Modified;
    private Patient p2;
    private Patient p3;
    private Patient p4;
    private Patient p5;
    private Patient p6;
    private Patient p7;
    private Patient p8;
    private Patient p9;
    private Patient p10;

    private Patient p11;
    private Patient p12;
    private Patient p13;
    private Patient p14;
    private Patient p15;
    private Patient p16;
    private Patient p17;
    private Patient p18;
    private Patient p19;
    private Patient p20;

    private Patient p21;
    private Patient p22;
    private Patient p23;
    private Patient p24;
    private Patient p25;
    private Patient p26;
    private Patient p27;
    private Patient p28;
    private Patient p29;
    private Patient p30;

    private Patient p31;
    private Patient p32;
    private Patient p33;
    private Patient p34;
    private Patient p35;
    private Patient p36;
    private Patient p37;
    private Patient p38;
    private Patient p39;
    private Patient p40;


    private Database testDatabase;
    private static DecimalFormat df = new DecimalFormat("0.00");

    @BeforeEach
    void runBefore() {
        testDatabase = new Database();

        p1 = new Patient(1, 6, "FEMALE",
                false, false, "Canada", "Caucasian");
        p1Modified = new Patient(1, 6, "FEMALE",
                true, true, "Canada", "Caucasian");

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

        p11 = new Patient(11, 6, "FEMALE",
                false, true, "Canada", "Caucasian");
        p12 = new Patient(12, 3, "MALE",
                true, false, "United States", "Mexican");
        p13 = new Patient(13, 2, "FEMALE",
                false, true, "China", "Asian");
        p14 = new Patient(14, 4, "MALE",
                false, false, "Japan", "Asian");
        p15 = new Patient(15, 6, "FEMALE",
                true, false, "Canada", "Caucasian");
        p16 = new Patient(16, 7, "MALE",
                true, true, "Canada", "African America");
        p17 = new Patient(17, 4, "FEMALE",
                true, false, "United States", "Indian");
        p18 = new Patient(18, 5, "MALE",
                false, false, "France", "Caucasian");
        p19 = new Patient(19, 3, "FEMALE",
                true, true, "England", "Indian");
        p20 = new Patient(20, 6, "MALE",
                false, false, "Canada", "Caucasian");

        p21 = new Patient(21, 6, "FEMALE",
                true, false, "Canada", "Caucasian");
        p22 = new Patient(22, 3, "MALE",
                true, false, "United States", "Mexican");
        p23 = new Patient(23, 2, "FEMALE",
                false, false, "China", "Asian");
        p24 = new Patient(24, 4, "MALE",
                true, true, "Japan", "Asian");
        p25 = new Patient(25, 6, "FEMALE",
                true, false, "Canada", "Caucasian");
        p26 = new Patient(26, 7, "MALE",
                false, true, "Canada", "African America");
        p27 = new Patient(27, 4, "FEMALE",
                false, false, "United States", "Indian");
        p28 = new Patient(28, 5, "MALE",
                true, false, "France", "Caucasian");
        p29 = new Patient(29, 3, "FEMALE",
                true, true, "England", "Indian");
        p30 = new Patient(30, 6, "MALE",
                true, false, "Canada", "Caucasian");

        p31 = new Patient(31, 6, "UNSPECIFIED",
                false, false, "Canada", "Caucasian");
        p32 = new Patient(32, 3, "UNSPECIFIED",
                true, false, "United States", "Mexican");
        p33 = new Patient(33, 2, "UNSPECIFIED",
                true, false, "China", "Asian");
        p34 = new Patient(34, 4, "UNSPECIFIED",
                false, false, "Japan", "Asian");
        p35 = new Patient(35, 6, "UNSPECIFIED",
                true, true, "Canada", "Caucasian");
        p36 = new Patient(36, 7, "UNSPECIFIED",
                true, false, "Canada", "African America");
        p37 = new Patient(37, 4, "UNSPECIFIED",
                false, true, "United States", "Indian");
        p38 = new Patient(38, 5, "UNSPECIFIED",
                false, false, "France", "Caucasian");
        p39 = new Patient(39, 3, "UNSPECIFIED",
                true, false, "England", "Indian");
        p40 = new Patient(40, 6, "UNSPECIFIED",
                true, true, "Canada", "Caucasian");
    }

    @Test
    void testConstructor() {
        Database testDB = new Database();
        NumberOfPatientsAfflicted test = new NumberOfPatientsAfflicted();
        assertEquals(0, testDB.getDatabaseSize());
        assertEquals(0, test.getDatabaseSize());
    }

    @Test
    void testIsExistingPatient() {
        try {
            testDatabase.addPatient(p1);
            assertTrue(testDatabase.isExistingPatient(p1));
            assertFalse(testDatabase.isExistingPatient(p2));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        }
    }

    // Adding patients to database
    @Test
    void testAddSinglePatient() {
        try {
            testDatabase.addPatient(p1);
            assertEquals(1, testDatabase.getDatabaseSize());
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        }
    }

    @Test
    void testAddMultiplePatients() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.addPatient(p2);
            testDatabase.addPatient(p3);
            testDatabase.addPatient(p4);
            testDatabase.addPatient(p5);
            assertEquals(5, testDatabase.getDatabaseSize());
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        }
    }

    @Test
    void testAddSamePatient() {
        try {
            testDatabase.addPatient(p1);
            assertEquals(1, testDatabase.getDatabaseSize());

            testDatabase.addPatient(p1);
            fail("No exception was thrown");
            //assertEquals(1, testDatabase.getDatabaseSize());
        } catch (ExistingPatientException e) {
            // expected
        }
    }

    // Getting database size for the method overload to specify subset
    @Test
    void testGetDatabaseSizeSubset() {
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

            assertEquals(5, testDatabase.getDatabaseSize(FEMALE));
            assertEquals(5, testDatabase.getDatabaseSize(MALE));
            assertEquals(10, testDatabase.getDatabaseSize(FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        }
    }

    // Modifying an existing patient file
    @Test
    void testModifyExistingPatientFirstIndex() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.modifyExistingPatient(1, true, true);

            assertEquals(p1Modified.getID(), testDatabase.getPatientFileByID(1).getID());
            assertEquals(p1Modified.getAge(), testDatabase.getPatientFileByID(1).getAge());
            assertEquals(p1Modified.getSex(), testDatabase.getPatientFileByID(1).getSex());
            assertEquals(p1Modified.getHasEczema(), testDatabase.getPatientFileByID(1).getHasEczema());
            assertEquals(p1Modified.getHasFoodAllergy(), testDatabase.getPatientFileByID(1).getHasFoodAllergy());
            assertEquals(p1Modified.getCountryOfResidence(), testDatabase.getPatientFileByID(1).getCountryOfResidence());
            assertEquals(p1Modified.getEthnicity(), testDatabase.getPatientFileByID(1).getEthnicity());
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (NoPatientFoundException e) {
            fail("The NoPatientFoundException was inappropriately thrown!");
        }
    }

    @Test
    void testModifyExistingPatientLaterIndex() {
        try {
            testDatabase.addPatient(p6);
            testDatabase.addPatient(p7);
            testDatabase.addPatient(p8);
            testDatabase.addPatient(p9);
            testDatabase.addPatient(p10);
            testDatabase.addPatient(p1);

            testDatabase.modifyExistingPatient(1, true, true);

            assertEquals(p1Modified.getID(), testDatabase.getPatientFileByID(1).getID());
            assertEquals(p1Modified.getAge(), testDatabase.getPatientFileByID(1).getAge());
            assertEquals(p1Modified.getSex(), testDatabase.getPatientFileByID(1).getSex());
            assertEquals(p1Modified.getHasEczema(), testDatabase.getPatientFileByID(1).getHasEczema());
            assertEquals(p1Modified.getHasFoodAllergy(), testDatabase.getPatientFileByID(1).getHasFoodAllergy());
            assertEquals(p1Modified.getCountryOfResidence(), testDatabase.getPatientFileByID(1).getCountryOfResidence());
            assertEquals(p1Modified.getEthnicity(), testDatabase.getPatientFileByID(1).getEthnicity());
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (NoPatientFoundException e) {
            fail("The NoPatientFoundException was inappropriately thrown!");
        }
    }

    // Removing a patient
    @Test
    void testRemovePatientFound() {

        try {
            testDatabase.addPatient(p1);
            testDatabase.addPatient(p2);
            testDatabase.addPatient(p3);
            assertEquals(3, testDatabase.getDatabaseSize());
            assertTrue(testDatabase.isExistingPatient(p2));

            testDatabase.removePatient(2);
            assertEquals(2, testDatabase.getDatabaseSize());
            assertFalse(testDatabase.isExistingPatient(p2));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (NoPatientFoundException e) {
            fail("The NoPatientFoundException was inappropriately thrown!");
        }
    }

    @Test
    void testRemovePatientNotFound() {
        try {
            testDatabase.addPatient(p7);
            testDatabase.addPatient(p8);
            testDatabase.addPatient(p9);
            assertEquals(3, testDatabase.getDatabaseSize());
            assertFalse(testDatabase.isExistingPatient(p2));

            testDatabase.removePatient(2);
            fail("the NoPatientFoundException should have been thrown!");
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (NoPatientFoundException e) {
            // Expected
        }
        assertEquals(3, testDatabase.getDatabaseSize());
        assertFalse(testDatabase.isExistingPatient(p2));
    }

    @Test
    void testRemovePatientSubsetDatabases() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.addPatient(p9);
            testDatabase.addPatient(p15);
            testDatabase.addPatient(p19);
            testDatabase.addPatient(p20);
            testDatabase.addPatient(p26);
            testDatabase.addPatient(p2);
            testDatabase.addPatient(p4);
            assertEquals(8, testDatabase.getDatabaseSize(FULL));
            assertEquals(4, testDatabase.getDatabaseSize(FEMALE));
            assertEquals(4, testDatabase.getDatabaseSize(MALE));

            testDatabase.removePatient(4);
            assertEquals(7, testDatabase.getDatabaseSize(FULL));
            assertEquals(4, testDatabase.getDatabaseSize(FEMALE));
            assertEquals(3, testDatabase.getDatabaseSize(MALE));

            testDatabase.removePatient(1);
            assertEquals(6, testDatabase.getDatabaseSize(FULL));
            assertEquals(3, testDatabase.getDatabaseSize(FEMALE));
            assertEquals(3, testDatabase.getDatabaseSize(MALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (NoPatientFoundException e) {
            fail("The NoPatientFoundException was inappropriately thrown!");
        }
    }

    // Retrieving a patient file
    @Test
    void testGetPatientFileFound() {
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

            assertEquals(1, testDatabase.getPatientFileByID(1).getID());
            assertEquals(6, testDatabase.getPatientFileByID(1).getAge());
            assertEquals("FEMALE", testDatabase.getPatientFileByID(1).getSex());
            assertEquals("Canada", testDatabase.getPatientFileByID(1).getCountryOfResidence());
            assertEquals("Caucasian", testDatabase.getPatientFileByID(1).getEthnicity());

            assertFalse(testDatabase.getPatientFileByID(1).getHasEczema());
            assertFalse(testDatabase.getPatientFileByID(1).getHasFoodAllergy());
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (NoPatientFoundException e) {
            fail("The NoPatientFoundException was inappropriately thrown!");
        }
    }

    @Test
    void testGetPatientFileNotFound() {
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

            testDatabase.getPatientFileByID(11);
            fail("The NoPatientFoundException should have been thrown");
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (NoPatientFoundException e) {
            // Expected
        }
    }


    @Test
    void testPatientListData() {
        testDatabase.patientListData();
        assertEquals(0, testDatabase.getDatabaseSize());
        assertEquals(0, testDatabase.getDatabaseSize(FULL));

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

            testDatabase.patientListData();
            assertEquals(10, testDatabase.getDatabaseSize());
            assertEquals(10, testDatabase.getDatabaseSize(FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        }
    }












    @Test
    void testGetNumEczema() {
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
            assertEquals(5, testDatabase.numPatientsCondition(ECZEMA, FULL));
            assertEquals(5, testDatabase.numPatientsCondition(NO_ECZEMA, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        }
    }

    @Test
    void testGetNumEczemaSubsetAllBranches() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.addPatient(p9);
            testDatabase.addPatient(p15);
            testDatabase.addPatient(p19);
            testDatabase.addPatient(p20);
            testDatabase.addPatient(p26);
            testDatabase.addPatient(p2);
            testDatabase.addPatient(p4);
            assertEquals(4, testDatabase.numPatientsCondition(ECZEMA, FULL));
            assertEquals(2, testDatabase.numPatientsCondition(ECZEMA, FEMALE));
            assertEquals(2, testDatabase.numPatientsCondition(ECZEMA, MALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        }
    }

    @Test
    void testGetNumAllergy() {
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
            assertEquals(3, testDatabase.numPatientsCondition(ALLERGY, FULL));
            assertEquals(7, testDatabase.numPatientsCondition(NO_ALLERGY, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        }
    }

    @Test
    void testGetNumAllergySubsetAllBranches() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.addPatient(p9);
            testDatabase.addPatient(p15);
            testDatabase.addPatient(p19);
            testDatabase.addPatient(p20);
            testDatabase.addPatient(p26);
            testDatabase.addPatient(p2);
            testDatabase.addPatient(p4);
            assertEquals(4, testDatabase.numPatientsCondition(ALLERGY, FULL));
            assertEquals(2, testDatabase.numPatientsCondition(ALLERGY, FEMALE));
            assertEquals(2, testDatabase.numPatientsCondition(ALLERGY, MALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        }
    }

    @Test
    void testGetNumEczemaAndNoAllergy() {
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
            assertEquals(3, testDatabase.numPatientsCondition(ECZEMA_AND_NO_ALLERGY, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        }
    }

    @Test
    void testGetNumEczemaAndNoAllergySubsets() {
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
            assertEquals(0, testDatabase.numPatientsCondition(ECZEMA_AND_NO_ALLERGY, FEMALE));
            assertEquals(3, testDatabase.numPatientsCondition(ECZEMA_AND_NO_ALLERGY, MALE));

            testDatabase.addPatient(p15);
            assertEquals(1, testDatabase.numPatientsCondition(ECZEMA_AND_NO_ALLERGY, FEMALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        }
    }

    @Test
    void testGetNumEczemaAndNoAllergySubsetsAllBranches() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.addPatient(p9);
            testDatabase.addPatient(p15);
            testDatabase.addPatient(p19);
            testDatabase.addPatient(p20);
            testDatabase.addPatient(p26);
            testDatabase.addPatient(p2);
            testDatabase.addPatient(p4);

            assertEquals(2, testDatabase.numPatientsCondition(ECZEMA_AND_NO_ALLERGY, FULL));
            assertEquals(1, testDatabase.numPatientsCondition(ECZEMA_AND_NO_ALLERGY, FEMALE));
            assertEquals(1, testDatabase.numPatientsCondition(ECZEMA_AND_NO_ALLERGY, MALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        }
    }

    @Test
    void testGetNumNoEczemaAndNoAllergy() {
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
            assertEquals(4, testDatabase.numPatientsCondition(NO_ECZEMA_AND_NO_ALLERGY, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        }
    }

    @Test
    void testGetNumNoEczemaAndNoAllergySubsets() {
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
            assertEquals(4, testDatabase.numPatientsCondition(NO_ECZEMA_AND_NO_ALLERGY, FEMALE));
            assertEquals(0, testDatabase.numPatientsCondition(NO_ECZEMA_AND_NO_ALLERGY, MALE));

            testDatabase.addPatient(p14);
            assertEquals(1, testDatabase.numPatientsCondition(NO_ECZEMA_AND_NO_ALLERGY, MALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        }
    }

    @Test
    void testGetNumNoEczemaAndNoAllergySubsetsAllBranches() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.addPatient(p9);
            testDatabase.addPatient(p15);
            testDatabase.addPatient(p19);
            testDatabase.addPatient(p20);
            testDatabase.addPatient(p26);
            testDatabase.addPatient(p2);
            testDatabase.addPatient(p4);
            assertEquals(2, testDatabase.numPatientsCondition(NO_ECZEMA_AND_NO_ALLERGY, FULL));
            assertEquals(1, testDatabase.numPatientsCondition(NO_ECZEMA_AND_NO_ALLERGY, FEMALE));
            assertEquals(1, testDatabase.numPatientsCondition(NO_ECZEMA_AND_NO_ALLERGY, MALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        }
    }

    @Test
    void testGetNumEczemaAndAllergy() {
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
            assertEquals(2, testDatabase.numPatientsCondition(ECZEMA_AND_ALLERGY, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        }
    }

    @Test
    void testGetNumNoEczemaAndAllergy() {
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
            assertEquals(1, testDatabase.numPatientsCondition(NO_ECZEMA_AND_ALLERGY, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        }
    }

    // Calculation of the proportion of existing patients who have eczema
    @Test
    void testCalcProbEczemaOnePatientHasEczema() {
        try {
            testDatabase.addPatient(p2);
            assertEquals(1.0, testDatabase.probabilityMedicalCondition(ECZEMA, FULL));

            assertEquals(1.0, testDatabase.probabilityMedicalCondition(ECZEMA, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbEczemaOnePatientHasNoEczema() {
        try {
            testDatabase.addPatient(p1);
            assertEquals(0.0,0, testDatabase.probabilityMedicalCondition(ECZEMA, FULL));
            assertEquals(0.0, testDatabase.probabilityMedicalCondition(ECZEMA, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbEczemaTenPatients() {
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
            assertEquals(0.5, testDatabase.probabilityMedicalCondition(ECZEMA, FULL));

            assertEquals(0.5, testDatabase.probabilityMedicalCondition(ECZEMA, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbEczemaPatientsHasNoEczema() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.addPatient(p3);
            testDatabase.addPatient(p5);
            testDatabase.addPatient(p7);
            testDatabase.addPatient(p9);
            assertEquals(0.0, testDatabase.probabilityMedicalCondition(ECZEMA, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbEczemaUndefinedNumberException() {
        try {
            testDatabase.probabilityMedicalCondition(ECZEMA, FULL);
            testDatabase.probabilityMedicalCondition(ECZEMA, FULL);
            fail("Exception should have been thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        }
    }
    @Test
    void testCalcProbEczemaUndefinedNumberExceptionSubsets() {
        try {
            testDatabase.probabilityMedicalCondition(ECZEMA, FULL);
            fail("Exception should have been thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        }
    }
    @Test
    void testCalcProbEczemaUndefinedNumberExceptionFemaleSubset() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.probabilityMedicalCondition(ECZEMA, MALE);

            testDatabase.probabilityMedicalCondition(ECZEMA, MALE);

            fail("Exception should have been thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        } catch (ExistingPatientException e) {
            fail("Exception should not have been thrown!");
        }
    }

    @Test
    void testCalcProbEczemaUndefinedNumberExceptionMaleSubset() {
        try {
            testDatabase.addPatient(p2);
            testDatabase.probabilityMedicalCondition(ECZEMA, FEMALE);
            testDatabase.probabilityMedicalCondition(ECZEMA, FEMALE);
            fail("Exception should have been thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        } catch (ExistingPatientException e) {
            fail("Exception should not have been thrown!");
        }
    }





    // Calculation of the proportion of existing patients who DO NOT have eczema
    @Test
    void testCalcProbNoEczemaOnePatientHasEczema() {
        try {
            testDatabase.addPatient(p2);
            assertEquals(0.0, testDatabase.probabilityMedicalCondition(NO_ECZEMA, FULL));

            assertEquals(0.0, testDatabase.probabilityMedicalCondition(NO_ECZEMA, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbNoEczemaOnePatientHasNoEczema() {
        try {
            testDatabase.addPatient(p1);
            assertEquals(1.0,0, testDatabase.probabilityMedicalCondition(NO_ECZEMA, FULL));

            assertEquals(1.0, testDatabase.probabilityMedicalCondition(NO_ECZEMA, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbNoEczemaTenPatients() {
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
            assertEquals(0.5, testDatabase.probabilityMedicalCondition(NO_ECZEMA, FULL));

            assertEquals(0.5, testDatabase.probabilityMedicalCondition(NO_ECZEMA, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    // Tests for the probability of having eczema subsetted by gender
    @Test
    void testCalcProbEczemaFemale() {
        try {
            testDatabase.addPatient(p1Modified);
            testDatabase.addPatient(p3);
            testDatabase.addPatient(p5);
            testDatabase.addPatient(p7);
            testDatabase.addPatient(p9);
            assertEquals(5, testDatabase.getDatabaseSize(FEMALE));
            assertEquals(0.2, testDatabase.probabilityMedicalCondition(ECZEMA, FEMALE));

            assertEquals(0.2, testDatabase.probabilityMedicalCondition(ECZEMA, FEMALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbEczemaFemaleNoEczema() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.addPatient(p3);
            testDatabase.addPatient(p5);
            testDatabase.addPatient(p7);
            testDatabase.addPatient(p9);
            assertEquals(0.0, testDatabase.probabilityMedicalCondition(ECZEMA, FEMALE));

            assertEquals(0.0, testDatabase.probabilityMedicalCondition(ECZEMA, FEMALE));

        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbNoEczemaFemale() {
        try {
            testDatabase.addPatient(p1Modified);
            testDatabase.addPatient(p3);
            testDatabase.addPatient(p5);
            testDatabase.addPatient(p7);
            testDatabase.addPatient(p9);
            assertEquals(5, testDatabase.getDatabaseSize(FEMALE));
            assertEquals(0.8, testDatabase.probabilityMedicalCondition(NO_ECZEMA, FEMALE));
            assertEquals(0.8, testDatabase.probabilityMedicalCondition(NO_ECZEMA, FEMALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbNoEczemaFemaleNoEczema() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.addPatient(p3);
            testDatabase.addPatient(p5);
            testDatabase.addPatient(p7);
            testDatabase.addPatient(p9);
            assertEquals(1.0, testDatabase.probabilityMedicalCondition(NO_ECZEMA, FEMALE));
            assertEquals(1.0, testDatabase.probabilityMedicalCondition(NO_ECZEMA, FEMALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbEczemaMale() {
        try {
            testDatabase.addPatient(p2);
            testDatabase.addPatient(p4);
            testDatabase.addPatient(p6);
            testDatabase.addPatient(p8);
            testDatabase.addPatient(p10);
            assertEquals(5, testDatabase.getDatabaseSize(MALE));
            assertEquals(1.0, testDatabase.probabilityMedicalCondition(ECZEMA, MALE));
            assertEquals(1.0, testDatabase.probabilityMedicalCondition(ECZEMA, MALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbEczemaMaleNoEczema() {
        try {
            testDatabase.addPatient(new Patient(12, 4, "MALE",
                    false, false, "", ""));
            assertEquals(1, testDatabase.getDatabaseSize(MALE));
            assertEquals(0.0, testDatabase.probabilityMedicalCondition(ECZEMA, MALE));
            assertEquals(0.0, testDatabase.probabilityMedicalCondition(ECZEMA, MALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbNoEczemaMale() {
        try {
            testDatabase.addPatient(p2);
            testDatabase.addPatient(p4);
            testDatabase.addPatient(p6);
            testDatabase.addPatient(p8);
            testDatabase.addPatient(p10);
            assertEquals(5, testDatabase.getDatabaseSize(MALE));
            assertEquals(0.0, testDatabase.probabilityMedicalCondition(NO_ECZEMA, MALE));
            assertEquals(0.0, testDatabase.probabilityMedicalCondition(NO_ECZEMA, MALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbNoEczemaMaleNoEczema() {
        try {
            testDatabase.addPatient(new Patient(12, 4, "MALE",
                    false, false, "", ""));
            assertEquals(1, testDatabase.getDatabaseSize(MALE));
            assertEquals(1.0, testDatabase.probabilityMedicalCondition(NO_ECZEMA, MALE));
            assertEquals(1.0, testDatabase.probabilityMedicalCondition(NO_ECZEMA, MALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbEczemaSubset() {
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
            assertEquals(10, testDatabase.getDatabaseSize(FULL));
            assertEquals(0.5, testDatabase.probabilityMedicalCondition(ECZEMA, FULL));
            assertEquals(0.5, testDatabase.probabilityMedicalCondition(ECZEMA, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbNoEczemaSubset() {
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
            assertEquals(10, testDatabase.getDatabaseSize(FULL));
            assertEquals(0.5, testDatabase.probabilityMedicalCondition(NO_ECZEMA, FULL));
            assertEquals(0.5, testDatabase.probabilityMedicalCondition(ECZEMA, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }


















    // Calculates the proportion of existing patients in the full database with food allergy
    @Test
    void testCalcProbFoodAllergyTenPatients() {
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
            assertEquals(0.3, testDatabase.probabilityMedicalCondition(ALLERGY, FULL));
            assertEquals(0.3, testDatabase.probabilityMedicalCondition(ALLERGY, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("UndefinedNumberException  was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbFoodAllergyNoAllergyPatients() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.addPatient(p2);
            testDatabase.addPatient(p3);
            testDatabase.addPatient(p5);
            testDatabase.addPatient(p7);
            testDatabase.addPatient(p8);
            testDatabase.addPatient(p10);
            assertEquals(0.0, testDatabase.probabilityMedicalCondition(ALLERGY, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("UndefinedNumberException should not have been thrown");
        }
    }

    @Test
    void testCalcProbFoodAllergyUndefinedNumberException() {
        try {
            testDatabase.probabilityMedicalCondition(ALLERGY, FULL);
            fail("Exception should have been thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        }
    }
    @Test
    void testCalcProbFoodAllergyUndefinedNumberExceptionSubsets() {
        try {
            testDatabase.probabilityMedicalCondition(ALLERGY, FULL);
            fail("Exception should have been thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        }
    }
    @Test
    void testCalcProbFoodAllergyUndefinedNumberExceptionFemaleSubset() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.probabilityMedicalCondition(ALLERGY, MALE);
            fail("Exception should have been thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        } catch (ExistingPatientException e) {
            fail("Exception should not have been thrown!");
        }
    }

    @Test
    void testCalcProbFoodAllergyUndefinedNumberExceptionMaleSubset() {
        try {
            testDatabase.addPatient(p2);
            testDatabase.probabilityMedicalCondition(ALLERGY, FEMALE);
            fail("Exception should have been thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        } catch (ExistingPatientException e) {
            fail("Exception should not have been thrown!");
        }
    }

    // calculates proportion of existing patients in the full database with NO food allergy
    @Test
    void testCalcProbNoFoodAllergyTenPatients() {
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
            assertEquals(0.7, testDatabase.probabilityMedicalCondition(NO_ALLERGY, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("UndefinedNumberException should not have been thrown");
        }
    }

    @Test
    void testCalcProbNoFoodAllergyNoAllergyPatients() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.addPatient(p2);
            testDatabase.addPatient(p3);
            testDatabase.addPatient(p5);
            testDatabase.addPatient(p7);
            testDatabase.addPatient(p8);
            testDatabase.addPatient(p10);
            assertEquals(1.0, testDatabase.probabilityMedicalCondition(NO_ALLERGY, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("UndefinedNumberException should not have been thrown");
        }
    }

    // Tests for the probability of having food allergy subsetted by gender
    @Test
    void testCalcProbFoodAllergyFemale() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.addPatient(p3);
            testDatabase.addPatient(p5);
            testDatabase.addPatient(p7);
            testDatabase.addPatient(p9);
            assertEquals(0.2, testDatabase.probabilityMedicalCondition(ALLERGY, FEMALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("UndefinedNumberException should not have been thrown");
        }
    }

    @Test
    void testCalcProbFoodAllergyFemaleNoAllergy() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.addPatient(p3);
            testDatabase.addPatient(p5);
            testDatabase.addPatient(p7);
            assertEquals(0.0, testDatabase.probabilityMedicalCondition(ALLERGY, FEMALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("UndefinedNumberException should not have been thrown");
        }
    }

    @Test
    void testCalcProbNoFoodAllergyFemale() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.addPatient(p3);
            testDatabase.addPatient(p5);
            testDatabase.addPatient(p7);
            testDatabase.addPatient(p9);
            assertEquals(0.8, testDatabase.probabilityMedicalCondition(NO_ALLERGY, FEMALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("UndefinedNumberException should not have been thrown");
        }
    }

    @Test
    void testCalcProbNoFoodAllergyFemaleNoAllergy() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.addPatient(p3);
            testDatabase.addPatient(p5);
            testDatabase.addPatient(p7);
            assertEquals(1.0, testDatabase.probabilityMedicalCondition(NO_ALLERGY, FEMALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("UndefinedNumberException should not have been thrown");
        }
    }

    @Test
    void testCalcProbFoodAllergyMale() {
        try {
            testDatabase.addPatient(p2);
            testDatabase.addPatient(p4);
            testDatabase.addPatient(p6);
            testDatabase.addPatient(p8);
            testDatabase.addPatient(p10);
            assertEquals(0.4, testDatabase.probabilityMedicalCondition(ALLERGY, MALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("UndefinedNumberException should not have been thrown");
        }
    }

    @Test
    void testCalcProbFoodAllergyMaleNoAllergy() {
        try {
            testDatabase.addPatient(p2);
            testDatabase.addPatient(p8);
            testDatabase.addPatient(p10);
            assertEquals(0.0, testDatabase.probabilityMedicalCondition(ALLERGY, MALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("UndefinedNumberException should not have been thrown");
        }
    }
    @Test
    void testCalcProbNoFoodAllergyMale() {
        try {
            testDatabase.addPatient(p2);
            testDatabase.addPatient(p4);
            testDatabase.addPatient(p6);
            testDatabase.addPatient(p8);
            testDatabase.addPatient(p10);
            assertEquals(0.6, testDatabase.probabilityMedicalCondition(NO_ALLERGY, MALE));
            assertEquals(0.6, testDatabase.probabilityMedicalCondition(NO_ALLERGY, MALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("UndefinedNumberException should not have been thrown");
        }
    }

    @Test
    void testCalcProbNoFoodAllergyMaleNoAllergy() {
        try {
            testDatabase.addPatient(p2);
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbFoodAllergySubset() {
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
            assertEquals(0.3, testDatabase.probabilityMedicalCondition(ALLERGY, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("UndefinedNumberException should not have been thrown");
        }
    }

    @Test
    void testCalcProbNoFoodAllergySubset() {
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
            assertEquals(0.7, testDatabase.probabilityMedicalCondition(NO_ALLERGY, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("UndefinedNumberException should not have been thrown");
        }
    }





    @Test
    void testCalcProbEczemaAndAllergy() {
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

            assertEquals(0.2, testDatabase.probabilityMedicalCondition(ECZEMA_AND_ALLERGY, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbEczemaAndAllergyUndefinedNumberException() {
        try {
            testDatabase.probabilityMedicalCondition(ECZEMA_AND_ALLERGY, FULL);
            fail("Exception should have been thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        }
    }
    @Test
    void testCalcProbEczemaAndAllergyUndefinedNumberExceptionSubsets() {
        try {
            testDatabase.probabilityMedicalCondition(ECZEMA_AND_ALLERGY, FULL);
            fail("Exception should have been thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        }
    }
    @Test
    void testCalcProbEczemaAndAllergyUndefinedNumberExceptionFemaleSubset() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.probabilityMedicalCondition(ECZEMA_AND_ALLERGY, MALE);
            fail("Exception should have been thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        } catch (ExistingPatientException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCalcProbEczemaAndAllergyUndefinedNumberExceptionMaleSubset() {
        try {
            testDatabase.addPatient(p2);
            testDatabase.probabilityMedicalCondition(ECZEMA_AND_ALLERGY, FEMALE);
            fail("Exception should have been thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        } catch (ExistingPatientException e) {
            e.printStackTrace();
        }
    }











    @Test
    void testCalcProbNoEczemaAndAllergy() {
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

            assertEquals(0.1, testDatabase.probabilityMedicalCondition(NO_ECZEMA_AND_ALLERGY, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbNoEczemaAndAllergyUndefinedNumberException() {
        try {
            testDatabase.probabilityMedicalCondition(NO_ECZEMA_AND_ALLERGY, FULL);
            fail("Exception should have been thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        }
    }

    @Test
    void testCalcProbNoEczemaAndAllergyUndefinedNumberExceptionFemaleSubset() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.probabilityMedicalCondition(NO_ECZEMA_AND_ALLERGY, MALE);
            fail("Exception should have been thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        } catch (ExistingPatientException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCalcProbNoEczemaAndAllergyUndefinedNumberExceptionMaleSubset() {
        try {
            testDatabase.addPatient(p2);
            testDatabase.probabilityMedicalCondition(NO_ECZEMA_AND_ALLERGY, FEMALE);
            fail("Exception should have been thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        } catch (ExistingPatientException e) {
            e.printStackTrace();
        }
    }

    // <--- P(Allergy|Eczema) --->
    @Test
    void testCalcProbAllergyGivenEczemaTenPatients() {
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
            assertEquals(0.4, testDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, FULL));
            assertEquals(0.4, testDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbAllergyGivenEczemaNoEczemaPatients() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.addPatient(p3);
            testDatabase.addPatient(p5);
            testDatabase.addPatient(p7);
            testDatabase.addPatient(p9);
            assertEquals(0.0, testDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, FULL));
            fail("The UndefinedNumberException should have been thrown!");
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        }
    }

    @Test
    void testCalcConditionalProbAllergyGivenEczemaSubset() {
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
            assertEquals(0.4, testDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, FULL));
            assertEquals(0.4, testDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, MALE));

            testDatabase.modifyExistingPatient(1, true, false);
            assertEquals(0.0, testDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, FEMALE));

            testDatabase.removePatient(1);
            testDatabase.addPatient(p1Modified);
            assertEquals(1.0, testDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, FEMALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (NoPatientFoundException e) {
            fail("The NoPatientFoundException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }

    }

    // <--- P(~Allergy|Eczema) --->
    @Test
    void testCalcProbNoAllergyGivenEczemaTenPatients() {
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
            assertEquals(0.6, testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_ECZEMA, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbNoAllergyGivenEczemaNoEczemaPatients() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.addPatient(p3);
            testDatabase.addPatient(p5);
            testDatabase.addPatient(p7);
            testDatabase.addPatient(p9);
            assertEquals(1.0, testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_ECZEMA, FULL));
            fail("The UndefinedNumberException should have been thrown!");
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        }
    }






    // SECTION II: Calculation of the probability of allergy development given the presence of eczema. This section
    // consists of the tests for the FEMALE, MALE, and UNSPECIFIED (or full) databases.

    // FEMALE: <--- P(Allergy|Eczema) --->
    @Test
    void testCalcProbAllergyGivenEczemaFemaleSubset() {
        try {
            testDatabase.addPatient(p1Modified);
            testDatabase.addPatient(p3);
            testDatabase.addPatient(p5);
            testDatabase.addPatient(p7);
            testDatabase.addPatient(p9);
            assertEquals(1.0, testDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, FEMALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbAllergyGivenEczemaFemaleSubsetNoAllergy() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.addPatient(p3);
            testDatabase.addPatient(p5);
            testDatabase.addPatient(p7);
            assertEquals(0.0, testDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, FEMALE));
            fail("The UndefinedNumberException was inappropriately thrown!");
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        }
    }

    // MALE: <--- P(Allergy|Eczema) --->
    @Test
    void testCalcProbAllergyGivenEczemaMaleSubsetSingleMale() {
        try {
            testDatabase.addPatient(p6);
            assertEquals(1.0, testDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, MALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbAllergyGivenEczemaMaleSubset() {
        try {
            testDatabase.addPatient(p2);
            testDatabase.addPatient(p4);
            testDatabase.addPatient(p6);
            testDatabase.addPatient(p8);
            testDatabase.addPatient(p10);
            assertEquals(0.4, testDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, MALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbAllergyGivenEczemaMaleSubsetNoEczema() {
        try {
            testDatabase.addPatient(p2);
            testDatabase.modifyExistingPatient(2, false, false);
            testDatabase.addPatient(p4);
            testDatabase.modifyExistingPatient(4, false, false);
            testDatabase.addPatient(p6);
            testDatabase.modifyExistingPatient(6, false, false);
            testDatabase.addPatient(p8);
            testDatabase.modifyExistingPatient(8, false, false);
            testDatabase.addPatient(p10);
            testDatabase.modifyExistingPatient(10, false, false);
            assertEquals(0.0, testDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, MALE));
            fail("The UndefinedNumberException should have been thrown");
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        }
    }

    @Test
    void testCalcProbAllergyGivenEczemaMaleSubsetNoAllergy() {
        try {
            testDatabase.addPatient(p2);
            testDatabase.addPatient(p8);
            testDatabase.addPatient(p10);
            assertEquals(0.0, testDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, MALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }


    // FEMALE: <--- P(~Allergy|Eczema) --->
    @Test
    void testCalcProbNoAllergyGivenEczemaFemaleSubset() {
        try {
            testDatabase.addPatient(p1Modified);
            testDatabase.addPatient(p3);
            testDatabase.addPatient(p5);
            testDatabase.addPatient(p7);
            testDatabase.addPatient(p9);
            assertEquals(0.0, testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_ECZEMA, FEMALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbNoAllergyGivenEczemaFemaleSubsetNoAllergy() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.addPatient(p3);
            testDatabase.addPatient(p5);
            testDatabase.addPatient(p7);
            assertEquals(1.0, testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_ECZEMA, FEMALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        }
    }

    // MALE: <--- P(~Allergy|Eczema) --->
    @Test
    void testCalcProbNoAllergyGivenEczemaMaleSubsetSingleMaleNoAllergy() {
        try {
            testDatabase.addPatient(p6);
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        }
        try {
            assertEquals(0.0, testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_ECZEMA, MALE));
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbNoAllergyGivenEczemaMaleSubset() {
        try {
            testDatabase.addPatient(p2);
            testDatabase.addPatient(p4);
            testDatabase.addPatient(p6);
            testDatabase.addPatient(p8);
            testDatabase.addPatient(p10);
            assertEquals(0.6, testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_ECZEMA, MALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbNoAllergyGivenEczemaMaleSubsetNoEczema() {
        try {
            testDatabase.addPatient(p2);
            testDatabase.modifyExistingPatient(2, false, false);
            testDatabase.addPatient(p4);
            testDatabase.modifyExistingPatient(4, false, false);
            testDatabase.addPatient(p6);
            testDatabase.modifyExistingPatient(6, false, false);
            testDatabase.addPatient(p8);
            testDatabase.modifyExistingPatient(8, false, false);
            testDatabase.addPatient(p10);
            testDatabase.modifyExistingPatient(10, false, false);
            assertEquals(1.0, testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_ECZEMA, MALE));
            fail("The UndefinedNumberException should have been thrown!");
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        }
    }

    @Test
    void testCalcProbNoAllergyGivenEczemaMaleSubsetNoAllergy() {
        try {
            testDatabase.addPatient(p2);
            testDatabase.addPatient(p8);
            testDatabase.addPatient(p10);
            assertEquals(1.0, testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_ECZEMA, MALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcConditionalProbNoAllergyGivenEczemaSubset() {
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
            assertEquals(0.6, testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_ECZEMA, FULL));
            assertEquals(0.6, testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_ECZEMA, MALE));

            testDatabase.modifyExistingPatient(1, true, false);
            assertEquals(1.0, testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_ECZEMA, FEMALE));

            testDatabase.removePatient(1);
            testDatabase.addPatient(p1Modified);
            assertEquals(0.0, testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_ECZEMA, FEMALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (NoPatientFoundException e) {
            fail("The NoPatientFoundException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }


    // SECTION I: Calculation of the probability of having a food allergy given the no history of eczema. This section
    // consists of the tests for the full database (no subsets are evaluated).

    // <--- P(Allergy|~Eczema) --->
    @Test
    void testCalcProbAllergyGivenNoEczemaTenPatients() {
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
            assertEquals(0.2, testDatabase.conditionalProbability(ALLERGY_GIVEN_NO_ECZEMA, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbAllergyGivenNoEczemaAllEczemaPatients() {
        try {
            testDatabase.addPatient(p2);
            testDatabase.addPatient(p4);
            testDatabase.addPatient(p6);
            testDatabase.addPatient(p8);
            testDatabase.addPatient(p10);
            assertEquals(0.0, testDatabase.conditionalProbability(ALLERGY_GIVEN_NO_ECZEMA, FULL));
            fail("The UndefinedNumberException should have been thrown");
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        }
    }

    @Test
    void testCalcConditionalProbAllergyGivenNoEczemaSubset() {
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
            assertEquals(0.2, testDatabase.conditionalProbability(ALLERGY_GIVEN_NO_ECZEMA, FULL));
            //assertEquals(0.0, testDatabase.calculateConditionalProbability(ALLERGY_GIVEN_NO_ECZEMA, MALE));

            testDatabase.modifyExistingPatient(1, true, false);
            assertEquals(0.25, testDatabase.conditionalProbability(ALLERGY_GIVEN_NO_ECZEMA, FEMALE));

            testDatabase.removePatient(1);
            testDatabase.addPatient(p1Modified);
            assertEquals(0.25, testDatabase.conditionalProbability(ALLERGY_GIVEN_NO_ECZEMA, FEMALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (NoPatientFoundException e) {
            fail("The NoPatientFoundException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    // <--- P(~Allergy|~Eczema) --->
    @Test
    void testCalcProbNoAllergyGivenNoEczemaTenPatients() {
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
            assertEquals(0.8, testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_NO_ECZEMA, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }


    @Test
    void testCalcProbNoAllergyGivenNoEczema() {
        try {
            testDatabase.addPatient(p1Modified);
            testDatabase.addPatient(p3);
            testDatabase.addPatient(p5);
            testDatabase.addPatient(p7);
            testDatabase.addPatient(p9);
            assertEquals(0.75, Math.round(testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_NO_ECZEMA, FULL) * 100.0) / 100.0);
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbNoAllergyGivenNoEczemaNoEczemaPatients() {
        try {
            testDatabase.addPatient(p1);
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        }
    }

    // SECTION II: Calculation of the probability of allergy development given the absence of eczema. This section
    // consists of the tests for the FEMALE, MALE, and UNSPECIFIED (or full) databases.

    // FEMALE: <--- P(Allergy|~Eczema) --->
    @Test
    void testCalcProbAllergyGivenNoEczemaFemaleSubset() {
        try {
            testDatabase.addPatient(p1Modified);
            testDatabase.addPatient(p3);
            testDatabase.addPatient(p5);
            testDatabase.addPatient(p7);
            testDatabase.addPatient(p9);
            assertEquals(0.25, testDatabase.conditionalProbability(ALLERGY_GIVEN_NO_ECZEMA, FEMALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbAllergyGivenNoEczemaFemaleSubsetNoAllergy() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.addPatient(p3);
            testDatabase.addPatient(p5);
            testDatabase.addPatient(p7);
            assertEquals(0.0, testDatabase.conditionalProbability(ALLERGY_GIVEN_NO_ECZEMA, FEMALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbAllergyGivenNoEczemaFemaleSubsetNoEczema() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.modifyExistingPatient(1, true, false);
            testDatabase.addPatient(p3);
            testDatabase.modifyExistingPatient(3, true, true);
            testDatabase.addPatient(p5);
            testDatabase.modifyExistingPatient(5, true, true);
            testDatabase.addPatient(p7);
            testDatabase.modifyExistingPatient(7, true, true);
            assertEquals(0.0, testDatabase.conditionalProbability(ALLERGY_GIVEN_NO_ECZEMA, FEMALE));
            fail("The UndefinedNumberException should have been thrown!");
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        }
    }


    // MALE: <--- P(Allergy|~Eczema) --->
    @Test
    void testCalcProbAllergyGivenNoEczemaMaleSubsetSingleMale() {
        try {
            testDatabase.addPatient(p6);
            assertEquals(0.0, testDatabase.conditionalProbability(ALLERGY_GIVEN_NO_ECZEMA, MALE));
            fail("The UndefinedNumberException should have been thrown!");
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        }
    }

    @Test
    void testCalcProbAllergyGivenNoEczemaMaleSubset() {
        try {
            testDatabase.addPatient(p2);
            testDatabase.addPatient(p4);
            testDatabase.addPatient(p6);
            testDatabase.addPatient(p8);
            testDatabase.addPatient(p10);
            assertEquals(0.0, testDatabase.conditionalProbability(ALLERGY_GIVEN_NO_ECZEMA, MALE));
            fail("The UndefinedNumberException should have been thrown!");
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        }
    }

    @Test
    void testCalcProbAllergyGivenNoEczemaMaleSubsetNoEczema() {
        try {
            testDatabase.addPatient(p2);
            testDatabase.modifyExistingPatient(2, false, false);
            testDatabase.addPatient(p4);
            testDatabase.modifyExistingPatient(4, false, false);
            testDatabase.addPatient(p6);
            testDatabase.modifyExistingPatient(6, false, false);
            testDatabase.addPatient(p8);
            testDatabase.modifyExistingPatient(8, false, true);
            testDatabase.addPatient(p10);
            testDatabase.modifyExistingPatient(10, false, true);
            assertEquals(0.4, testDatabase.conditionalProbability(ALLERGY_GIVEN_NO_ECZEMA, MALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    // FEMALE: <--- P(~Allergy|~Eczema) --->
    @Test
    void testCalcProbNoAllergyGivenNoEczemaFemaleSubset() {
        try {
            testDatabase.addPatient(p1Modified);
            testDatabase.addPatient(p3);
            testDatabase.addPatient(p5);
            testDatabase.addPatient(p7);
            testDatabase.addPatient(p9);
            assertEquals(0.75, Math.round(testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_NO_ECZEMA, FEMALE) * 100.0) / 100.0);
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbNoAllergyGivenNoEczemaFemaleSubsetNoAllergy() {
        try {
            testDatabase.addPatient(p1);
            testDatabase.addPatient(p3);
            testDatabase.addPatient(p5);
            testDatabase.addPatient(p7);
            assertEquals(1.0, testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_NO_ECZEMA, FEMALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    // MALE: <--- P(~Allergy|~Eczema) --->
    @Test
    void testCalcProbNoAllergyGivenNoEczemaMaleSubsetSingleMaleNoAllergy() {
        try {
            testDatabase.addPatient(p6);
            assertEquals(1.0, testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_NO_ECZEMA, MALE));
            fail("The UndefinedNumberException was inappropriately thrown!");
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        }
    }

    @Test
    void testCalcProbNoAllergyGivenNoEczemaMaleSubset() {
        try {
            testDatabase.addPatient(p2);
            testDatabase.addPatient(p4);
            testDatabase.addPatient(p6);
            testDatabase.addPatient(p8);
            testDatabase.addPatient(p10);
            assertEquals(1.0, testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_NO_ECZEMA, MALE));
            fail("The UndefinedNumberException should have been thrown!");
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        }
    }

    @Test
    void testCalcProbNoAllergyGivenNoEczemaMaleSubsetNoEczema() {
        try {
            testDatabase.addPatient(p2);
            testDatabase.modifyExistingPatient(2, false, false);
            testDatabase.addPatient(p4);
            testDatabase.modifyExistingPatient(4, false, false);
            testDatabase.addPatient(p6);
            testDatabase.modifyExistingPatient(6, false, false);
            testDatabase.addPatient(p8);
            testDatabase.modifyExistingPatient(8, false, false);
            testDatabase.addPatient(p10);
            testDatabase.modifyExistingPatient(10, false, false);
            assertEquals(1.0, testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_NO_ECZEMA, MALE));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testCalcProbNoAllergyGivenNoEczemaMaleSubsetNoAllergy() {
        try {
            testDatabase.addPatient(p2);
            testDatabase.addPatient(p8);
            testDatabase.addPatient(p10);
            assertEquals(1.0, testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_NO_ECZEMA, MALE));
            fail("The UndefinedNumberException should have been thrown!");
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            // Expected
        }
    }


    // UNSPECIFIED:
    @Test
    void testCalcConditionalProbNoAllergyGivenNoEczemaSubset() {
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
            assertEquals(0.8, testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_NO_ECZEMA, FULL));
            //assertEquals(1.0, testDatabase.calculateConditionalProbability(NO_ALLERGY_GIVEN_NO_ECZEMA, MALE));

            testDatabase.modifyExistingPatient(1, true, false);
            assertEquals(0.75, Math.round(testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_NO_ECZEMA, FEMALE) * 100.0) / 100.0);

            testDatabase.removePatient(1);
            testDatabase.addPatient(p1Modified);
            assertEquals(0.75, Math.round(testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_NO_ECZEMA, FEMALE) * 100.0) / 100.0);
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (NoPatientFoundException e) {
            fail("The NoPatientFoundException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }













    // Calculate odds ratio
    @Test
    void testCalcOddsRatio() {
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
            assertEquals( 2.666666666666667, testDatabase.measureOfAssociation(ODDS_RATIO, FULL));
            assertEquals( 2.666666666666667, testDatabase.measureOfAssociation(ODDS_RATIO, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }


    @Test
    void testCalcRelativeRisk() {
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
            assertEquals( 2.0, testDatabase.measureOfAssociation(RELATIVE_RISK, FULL));
            assertEquals( 2.0, testDatabase.measureOfAssociation(RELATIVE_RISK, FULL));
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("The UndefinedNumberException was inappropriately thrown!");
        }
    }

    @Test
    void testRunAnalyses() {
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
            testDatabase.addPatient(p11);
            testDatabase.addPatient(p12);
            testDatabase.addPatient(p13);
            testDatabase.addPatient(p14);
            testDatabase.addPatient(p15);
            testDatabase.addPatient(p16);
            testDatabase.addPatient(p17);
            testDatabase.addPatient(p18);
            testDatabase.addPatient(p19);
            testDatabase.addPatient(p20);
            testDatabase.addPatient(p21);
            testDatabase.addPatient(p22);
            testDatabase.addPatient(p23);
            testDatabase.addPatient(p24);
            testDatabase.addPatient(p25);
            testDatabase.addPatient(p26);
            testDatabase.addPatient(p27);
            testDatabase.addPatient(p28);
            testDatabase.addPatient(p29);
            testDatabase.addPatient(p30);
            testDatabase.addPatient(p31);
            testDatabase.addPatient(p32);
            testDatabase.addPatient(p33);
            testDatabase.addPatient(p34);
            testDatabase.addPatient(p35);
            testDatabase.addPatient(p36);
            testDatabase.addPatient(p37);
            testDatabase.addPatient(p38);
            testDatabase.addPatient(p39);
            testDatabase.addPatient(p40);

            assertEquals(40, testDatabase.getDatabaseSize(FULL));
            assertEquals(40, testDatabase.getDatabaseSize());
            assertEquals(0.57, Math.round(testDatabase.probabilityMedicalCondition(ECZEMA, FULL) * 100.0) / 100.0);
            assertEquals(0.43, Math.round(testDatabase.probabilityMedicalCondition(NO_ECZEMA, FULL) * 100.0) / 100.0);
            assertEquals(0.33, Math.round(testDatabase.probabilityMedicalCondition(ALLERGY, FULL) * 100.0) / 100.0);
            assertEquals(0.68, Math.round(testDatabase.probabilityMedicalCondition(NO_ALLERGY, FULL) * 100.0) / 100.0);
            assertEquals(0.35, Math.round(testDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, FULL) * 100.0) / 100.0);
            assertEquals(0.65, Math.round(testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_ECZEMA, FULL) * 100.0) / 100.0);
            assertEquals(0.29, Math.round(testDatabase.conditionalProbability(ALLERGY_GIVEN_NO_ECZEMA, FULL) * 100.0) / 100.0);
            assertEquals(0.71, Math.round(testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_NO_ECZEMA, FULL) * 100.0) / 100.0);
            assertEquals(1.28, Math.round(testDatabase.measureOfAssociation(ODDS_RATIO, FULL) * 100.0) / 100.0);
            assertEquals(1.18, Math.round(testDatabase.measureOfAssociation(RELATIVE_RISK, FULL) * 100.0) / 100.0);

            assertEquals(15, testDatabase.getDatabaseSize(FEMALE));
            assertEquals(0.4, testDatabase.probabilityMedicalCondition(ECZEMA, FEMALE));
            assertEquals(0.6, testDatabase.probabilityMedicalCondition(NO_ECZEMA, FEMALE));
            assertEquals(0.33, Math.round(testDatabase.probabilityMedicalCondition(ALLERGY, FEMALE) * 100.0) / 100.0);
            assertEquals(0.67, Math.round(testDatabase.probabilityMedicalCondition(NO_ALLERGY, FEMALE) * 100.0) / 100.0);
            assertEquals(0.33, Math.round(testDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, FEMALE) * 100.0) / 100.0);
            assertEquals(0.67, Math.round(testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_ECZEMA, FEMALE) * 100.0) / 100.0);
            assertEquals(0.33, Math.round(testDatabase.conditionalProbability(ALLERGY_GIVEN_NO_ECZEMA, FEMALE) * 100.0) / 100.0);
            assertEquals(0.67, Math.round(testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_NO_ECZEMA, FEMALE) * 100.0) / 100.0);
            assertEquals(1.0, Math.round(testDatabase.measureOfAssociation(ODDS_RATIO, FEMALE) * 100.0) / 100.0);
            assertEquals(1.0, Math.round(testDatabase.measureOfAssociation(RELATIVE_RISK, FEMALE) * 100.0) / 100.0);

            assertEquals(15, testDatabase.getDatabaseSize(MALE));
            assertEquals(0.73, Math.round(testDatabase.probabilityMedicalCondition(ECZEMA, MALE) * 100.0) / 100.0);
            assertEquals(0.27, Math.round(testDatabase.probabilityMedicalCondition(NO_ECZEMA, MALE) * 100.0) / 100.0);
            assertEquals(0.33, Math.round(testDatabase.probabilityMedicalCondition(ALLERGY, MALE) * 100.0) / 100.0);
            assertEquals(0.67, Math.round(testDatabase.probabilityMedicalCondition(NO_ALLERGY, MALE) * 100.0) / 100.0);
            assertEquals(0.36, Math.round(testDatabase.conditionalProbability(ALLERGY_GIVEN_ECZEMA, MALE) * 100.0) / 100.0);
            assertEquals(0.64, Math.round(testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_ECZEMA, MALE) * 100.0) / 100.0);
            assertEquals(0.25, Math.round(testDatabase.conditionalProbability(ALLERGY_GIVEN_NO_ECZEMA, MALE) * 100.0) / 100.0);
            assertEquals(0.75, Math.round(testDatabase.conditionalProbability(NO_ALLERGY_GIVEN_NO_ECZEMA, MALE) * 100.0) / 100.0);
            assertEquals(1.71, Math.round(testDatabase.measureOfAssociation(ODDS_RATIO, MALE) * 100.0) / 100.0);
            assertEquals(1.45, Math.round(testDatabase.measureOfAssociation(RELATIVE_RISK, MALE) * 100.0) / 100.0);
        } catch (ExistingPatientException e) {
            fail("The ExistingPatientException was inappropriately thrown!");
        } catch (UndefinedNumberException e) {
            fail("UndefinedNumberException should not have been thrown");
        }
    }
}

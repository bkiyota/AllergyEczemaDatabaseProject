package model;

/**
 * This class provides a suite of tests for the methods in the Patient class.
 * */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PatientTest {
    private Patient p1;

    @BeforeEach
    void runBefore() {
        p1 = new Patient(1, 6, "Male",
                false, false, "Canada", "Caucasian");
    }

    @Test
    void testConstructor() {
        Patient testPatient = new Patient(3, 2, "Female",
                false, false, "China", "Asian");
        assertEquals(3, testPatient.getID());
        assertEquals(2, testPatient.getAge());
        assertEquals("Female", testPatient.getSex());
        assertEquals("China", testPatient.getCountryOfResidence());
        assertEquals("Asian", testPatient.getEthnicity());

        assertFalse(testPatient.getHasEczema());
        assertFalse(testPatient.getHasFoodAllergy());
    }

    @Test
    void testSetHasEczema() {
        assertFalse(p1.getHasEczema());
        p1.setHasEczema(true);
        assertTrue(p1.getHasEczema());
    }

    @Test
    void testSetHasFoodAllergy() {
        assertFalse(p1.getHasFoodAllergy());
        p1.setHasFoodAllergy(true);
        assertTrue(p1.getHasFoodAllergy());
    }
}

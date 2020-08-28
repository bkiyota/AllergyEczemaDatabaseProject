package model.enumerations;

/**
 * This class presents the medical conditions (and their possible combinations) in the form of an enumeration. This
 * framework helps to improve readability when being passed into methods, but also allows for different conditions to
 * be added in the future in a clear manner.
 * */

public enum MedicalCondition {
    ECZEMA, NO_ECZEMA,
    ALLERGY, NO_ALLERGY,
    ECZEMA_AND_ALLERGY, NO_ECZEMA_AND_ALLERGY,
    ECZEMA_AND_NO_ALLERGY, NO_ECZEMA_AND_NO_ALLERGY
}

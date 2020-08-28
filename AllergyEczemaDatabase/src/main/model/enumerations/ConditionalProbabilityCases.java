package model.enumerations;

/**
 * This class consists of the different cases of conditional probability with the medical conditions eczema and
 * food allergy. */

public enum ConditionalProbabilityCases {
    ALLERGY_GIVEN_ECZEMA, NO_ALLERGY_GIVEN_ECZEMA,
    ALLERGY_GIVEN_NO_ECZEMA, NO_ALLERGY_GIVEN_NO_ECZEMA
}

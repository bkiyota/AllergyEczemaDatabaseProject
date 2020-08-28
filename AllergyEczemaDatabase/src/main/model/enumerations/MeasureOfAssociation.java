package model.enumerations;

/**
 * This class presents the different statistical measures of association that are currently supported by the
 * application. Similar to the case of DatabaseSubsets, having this form of an Enumeration creates a framework that is
 * easily built off of when the addition of new functionality in the form of statistical tests in mandated.
 * */

public enum MeasureOfAssociation {
    ODDS_RATIO, RELATIVE_RISK
}

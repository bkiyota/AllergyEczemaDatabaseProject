package model.exception;

/**
 * This class is thrown when attempting to add a new Patient file to the Database with an ID that already exists in the
 * database. It prevents Patient files sharing the same ID number in the database.
 * */

public class ExistingPatientException extends Exception {

}

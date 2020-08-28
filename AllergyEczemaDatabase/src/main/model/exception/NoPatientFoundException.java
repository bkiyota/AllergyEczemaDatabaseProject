package model.exception;

/**
 * This exception is thrown in scenarios when trying to either a retrieve a patient file that does not exist in the
 * database, or removing a patient that does not exist in the database.*/

public class NoPatientFoundException extends Exception {
}

package model.exception;

/**
 * This exception is thrown in the statistical analyses where there is a case that results in an undefined result
 * (i.e. the denominator of a fraction is zero) so as to prevent ambiguous or confusing outputs.
 * */

public class UndefinedNumberException extends Exception {
}

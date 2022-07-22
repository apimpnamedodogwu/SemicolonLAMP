package africa.semicolon.semicolonlamp.services.lampExceptions;

public class ExistingEmailException extends RuntimeException{
    public ExistingEmailException (String message) {
        super(message);
    }
}

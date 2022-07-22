package africa.semicolon.semicolonlamp.services.lampExceptions;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException (String message) {
        super(message);
    }
}

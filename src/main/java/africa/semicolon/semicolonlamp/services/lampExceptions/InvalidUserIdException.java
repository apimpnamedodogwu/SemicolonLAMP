package africa.semicolon.semicolonlamp.services.lampExceptions;

public class InvalidUserIdException extends RuntimeException{
    public InvalidUserIdException(String message) {
        super(message);
    }
}

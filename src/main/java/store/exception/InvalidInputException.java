package store.exception;

public class InvalidInputException extends RuntimeException{
    public InvalidInputException(String input) {
        super(ErrorMessage.INVALID_INPUT.getMessage(input));
    }
}

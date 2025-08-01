package sat.exception;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {}
    public UserNotFoundException(String message) {
        super(message);
    }
}

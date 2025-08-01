package sat.exception;

public class CategoryNotFoundException extends NotFoundException {
    public CategoryNotFoundException() {}
    public CategoryNotFoundException(String message) {
        super(message);
    }
}

package sat.exception;

public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException() {}
    public ProductNotFoundException(String message) {
        super(message);
    }
}

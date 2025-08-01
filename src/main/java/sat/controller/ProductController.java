package sat.controller;

import java.io.IOException;

public interface ProductController {
    void getAllProducts() throws IOException;
    void getAllProductsByCategory() throws IOException;
    void findAndDisplayProductByName() throws IOException;
}

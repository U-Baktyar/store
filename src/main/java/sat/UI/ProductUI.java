package sat.UI;

import sat.entity.product.Product;
import sat.exception.CategoryNotFoundException;

import java.io.IOException;
import java.util.List;

public interface ProductUI {
    void showAllProducts(List<Product> products) throws IOException;
    void showProductByName(String productName) throws IOException, CategoryNotFoundException;
    void showAllProductByCategoryName(String categoryName) throws IOException, CategoryNotFoundException;
    void showNoProductsAvailable() throws IOException;
}

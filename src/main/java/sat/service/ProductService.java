package sat.service;

import sat.entity.product.Product;
import sat.exception.CategoryNotFoundException;
import sat.exception.ProductNotFoundException;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<Product> getAllProducts();
    Map<String, List<Product>> getAllProductsByCategory(String CategoryName) throws CategoryNotFoundException;
    Product findAndDisplayProductByName(String productName) throws ProductNotFoundException;
}

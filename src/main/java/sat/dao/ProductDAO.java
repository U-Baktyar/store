package sat.dao;
import sat.entity.product.Product;

import java.util.List;
import java.util.Map;

public interface ProductDAO {
    List<Product> findAllProducts();
    Map<String, List<Product>> getAllProductsByCategory(String categoryName);
    Product findAndDisplayProductByName(String productName);

}

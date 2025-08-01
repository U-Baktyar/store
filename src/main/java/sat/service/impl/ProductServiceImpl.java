package sat.service.impl;

import sat.configuration.annotation.Autowired;
import sat.configuration.annotation.Component;
import sat.dao.ProductDAO;
import sat.entity.product.Product;
import sat.exception.CategoryNotFoundException;
import sat.exception.ProductNotFoundException;
import sat.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ProductServiceImpl implements ProductService {

    private ProductDAO productDAO;

    @Autowired
    public ProductServiceImpl(
            ProductDAO productDAO
    ) {
        this.productDAO = productDAO;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productDAO.findAllProducts();
        return products;
    }

    @Override
    public Map<String, List<Product>> getAllProductsByCategory(String CategoryName) throws CategoryNotFoundException {
        return Map.of();
    }

    @Override
    public Product findAndDisplayProductByName(String productName) throws ProductNotFoundException {
        return null;
    }
}

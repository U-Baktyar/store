package sat.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import sat.configuration.annotation.Autowired;
import sat.configuration.annotation.Component;
import sat.dao.ProductDAO;
import sat.entity.product.Product;
import sat.exception.DataAccessException;

import java.util.List;
import java.util.Map;

@Component
public class ProductDAOImpl implements ProductDAO {


    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public ProductDAOImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    @Override
    public  List<Product> findAllProducts() {
        try(
                EntityManager entityManager = entityManagerFactory.createEntityManager();
        ){
             return entityManager
                    .createQuery("SELECT p FROM Product p", Product.class)
                    .getResultList();

        } catch (Exception e) {
            throw new DataAccessException("Ошибки при потытки открытие entityManager" + e.getMessage());
        }
    }

    @Override
    public Map<String, List<Product>> getAllProductsByCategory(String categoryName) {
        return Map.of();
    }

    @Override
    public Product findAndDisplayProductByName(String productName) {
        return null;
    }
}

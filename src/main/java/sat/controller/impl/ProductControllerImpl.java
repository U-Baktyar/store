package sat.controller.impl;

import sat.UI.ProductUI;
import sat.configuration.annotation.Autowired;
import sat.configuration.annotation.Component;
import sat.controller.ProductController;
import sat.entity.product.Product;
import sat.service.ProductService;
import sat.session.SessionUser;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class ProductControllerImpl implements ProductController {

    private final ProductService productService;
    private final ProductUI productUI;
    private final SessionUser sessionUser;
    private final UUID sessionId;

    public ProductControllerImpl(
            ProductService productService,
            ProductUI productUI,
            SessionUser sessionUser,
            UUID sessionId
    ) {
        this.productService = productService;
        this.productUI = productUI;
        this.sessionUser = sessionUser;
        this.sessionId = sessionId;
    }

    @Override
    public void findAndDisplayProductByName() {

    }

    @Override
    public void getAllProductsByCategory() {

    }

    public void getAllProducts() throws IOException {
        List<Product> products = productService.getAllProducts();
        if(products.isEmpty()) {
            productUI.showNoProductsAvailable();
            return;
        }
        productUI.showAllProducts(products);
    }
}

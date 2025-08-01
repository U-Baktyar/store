package sat.UI.impl;

import sat.UI.ProductUI;
import sat.entity.product.Product;
import sat.exception.CategoryNotFoundException;
import sat.validation.UserValidation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public class ProductUIImpl implements ProductUI {

    private final BufferedReader reader;
    private final BufferedWriter writer;
    private final UserValidation userValidation;

    public ProductUIImpl(
            BufferedReader reader,
            BufferedWriter writer,
            UserValidation userValidation
    ) {
        this.reader = reader;
        this.writer = writer;
        this.userValidation = userValidation;
    }

    @Override
    public void showAllProducts(List<Product> products) throws IOException {
        for (Product product : products) {
            writer.write(product.infoProduct() + "\n");
        }
    }

    @Override
    public void showProductByName(String productName) throws IOException, CategoryNotFoundException {

    }

    @Override
    public void showAllProductByCategoryName(String categoryName) throws IOException, CategoryNotFoundException {

    }

    @Override
    public void showNoProductsAvailable() throws IOException {
        writer.write("Список товаров пуст \n");
    }
}

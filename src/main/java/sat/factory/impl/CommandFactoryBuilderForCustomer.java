package sat.factory.impl;

import sat.UI.AuthUI;
import sat.UI.InfoUI;
import sat.UI.ProductUI;
import sat.UI.UserRegistrationUI;
import sat.UI.impl.AuthUIImpl;
import sat.UI.impl.InfoUIImpl;
import sat.UI.impl.ProductUIImpl;
import sat.UI.impl.UserRegistrationUImpl;
import sat.configuration.annotation.Autowired;
import sat.configuration.annotation.Component;
import sat.controller.AuthController;
import sat.controller.InfoController;
import sat.controller.ProductController;
import sat.controller.RegistrationUserController;
import sat.controller.impl.InfoControllerImpl;
import sat.controller.impl.AuthControllerImpl;
import sat.controller.impl.ProductControllerImpl;
import sat.controller.impl.RegistrationUserControllerImpl;
import sat.factory.CommandFactory;
import sat.factory.CommandFactoryBuilder;
import sat.service.ProductService;
import sat.service.UserService;
import sat.session.SessionUser;
import sat.validation.UserValidation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.UUID;


@Component
public class CommandFactoryBuilderForCustomer implements CommandFactoryBuilder {

    private final UserService userService;
    private final ProductService productService;
    public final UserValidation userValidation;
    private final SessionUser sessionUser;


    @Autowired
    public CommandFactoryBuilderForCustomer(
            UserService userService,
            ProductService productService,
            UserValidation userValidation,
            SessionUser sessionUser
    ) {
        this.userService = userService;
        this.productService = productService;
        this.userValidation = userValidation;
        this.sessionUser = sessionUser;
    }

    @Override
    public CommandFactory buildCommandFactoryForUser(BufferedReader reader, BufferedWriter writer, UUID sessionID) {
        InfoUI infoUI = new InfoUIImpl(writer);
        AuthUI authUI = new AuthUIImpl(reader, writer, userValidation);
        UserRegistrationUI userRegistrationUI = new UserRegistrationUImpl(reader, writer, userValidation);
        ProductUI productUI = new ProductUIImpl(reader, writer, userValidation);

        InfoController infoController = new InfoControllerImpl(infoUI);
        AuthController authController = new AuthControllerImpl(userService, authUI, sessionUser, sessionID);
        RegistrationUserController registrationUserController = new RegistrationUserControllerImpl(userService, sessionUser, userRegistrationUI, sessionID);
        ProductController productController = new ProductControllerImpl(productService, productUI, sessionUser, sessionID);
        return new  CommandFactoryImpl(infoController, authController, registrationUserController, productController);
    }
}

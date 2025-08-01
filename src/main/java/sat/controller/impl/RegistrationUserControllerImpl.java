package sat.controller.impl;

import sat.DTO.CustomerDto;
import sat.DTO.WorkerDto;
import sat.UI.UserRegistrationUI;
import sat.UI.impl.UserRegistrationUImpl;
import sat.controller.RegistrationUserController;

import sat.entity.user.Role;
import sat.entity.user.User;
import sat.exception.ValidateException;
import sat.factory.create_user.CreatUserFactory;
import sat.factory.create_user.CreateCustomer;
import sat.factory.create_user.CreateWorker;
import sat.service.UserService;
import sat.session.SessionUser;


import java.io.IOException;
import java.util.UUID;

public class RegistrationUserControllerImpl implements RegistrationUserController {

    private final UserService userService;
    private final UserRegistrationUI userRegistrationUI;
    private final SessionUser sessionUser;
    private final UUID sessionId;

    public RegistrationUserControllerImpl(
            UserService userService,
            SessionUser sessionUser,
            UserRegistrationUI userRegistrationUI,
            UUID sessionId
    ) {
        this.userService = userService;
        this.userRegistrationUI = userRegistrationUI;
        this.sessionUser = sessionUser;
        this.sessionId = sessionId;
    }

    @Override
    public void register() throws IOException{
        try {
            if(sessionUser.isAuthenticated(sessionId)) {
                if(sessionUser.getAuthentication(sessionId).getRole() == Role.SUPER_ADMIN) {
                    CreatUserFactory<WorkerDto> creatUserFactory = new CreateWorker();
                    String login = userRegistrationUI.readAndValidateLogin();
                    if(userService.isUserExist(login)) {
                        userRegistrationUI.showIsUserExist();
                        return;
                    }
                    User user = creatUserFactory.getUser(userRegistrationUI.fillWorkerDtoFields(login));
                    userService.saveNewUser(user);
                    userRegistrationUI.showUserSavedSuccess(user.getLogin());
                }else {
                    userRegistrationUI.showPermissionDenied(sessionUser.getAuthentication(sessionId).getRole().toString());
                }

            }
            if(!sessionUser.isAuthenticated(sessionId)) {
                CreatUserFactory<CustomerDto> creatUserFactory = new CreateCustomer();
                String login = userRegistrationUI.readAndValidateLogin();
                if(userService.isUserExist(login)) {
                    userRegistrationUI.showIsUserExist();
                    return;
                }
                User user = creatUserFactory.getUser(userRegistrationUI.fillCustomerDtoFields(login));
                userService.saveNewUser(user);
                userRegistrationUI.showUserSavedSuccess(user.getLogin());
            }
        }catch (ValidateException e) {
            userRegistrationUI.showValidationError(e.getMessage());
        }
    }

}

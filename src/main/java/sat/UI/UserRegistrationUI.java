package sat.UI;

import sat.DTO.CustomerDto;
import sat.DTO.WorkerDto;
import sat.exception.ValidateException;

import java.io.IOException;
import java.time.LocalDate;

public interface UserRegistrationUI {

    CustomerDto fillCustomerDtoFields(String login) throws IOException, ValidateException;

    WorkerDto fillWorkerDtoFields(String login) throws IOException, ValidateException;

    void showValidationError(String message) throws IOException;

    void showUserSavedSuccess(String login) throws IOException;

    void showPermissionDenied(String role) throws IOException;

    String readAndValidateLogin() throws IOException, ValidateException;

    void showIsUserExist()throws IOException;
}

package sat.validation;

import sat.exception.ValidateException;

import java.time.LocalDate;

public interface UserValidation {
    String checkLogin(String login) throws ValidateException;
    String checkPassword(String password) throws ValidateException;
    String checkEmail(String email) throws ValidateException;
    LocalDate checkData(String date) throws ValidateException;
    String checkName(String name) throws ValidateException;
    String validatePhoneNumber(String phoneNumber) throws ValidateException;
    String validateAddress(String address) throws ValidateException;
    String checkRole(String role) throws ValidateException;
}

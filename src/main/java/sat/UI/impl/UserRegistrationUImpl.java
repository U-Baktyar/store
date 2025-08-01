package sat.UI.impl;

import sat.DTO.CustomerDto;
import sat.DTO.WorkerDto;
import sat.UI.UserRegistrationUI;
import sat.exception.ValidateException;
import sat.validation.UserValidation;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.IOException;
import java.time.LocalDate;

public class UserRegistrationUImpl implements UserRegistrationUI {
    private final BufferedReader reader;
    private final BufferedWriter writer;
    private final UserValidation userValidation;


    public UserRegistrationUImpl(
            BufferedReader reader,
            BufferedWriter writer,
            UserValidation userValidation
    ) {
        this.reader = reader;
        this.writer = writer;
        this.userValidation = userValidation;
    }


    public CustomerDto fillCustomerDtoFields(String login) throws IOException, ValidateException {
        CustomerDto customerDto = new CustomerDto();

        String password  = this.readAndValidatePassword();
        String email = this.readAndValidateEmail();
        LocalDate dateOfBirth = this.readAndValidateBirthDate();

        customerDto.setLogin(login);
        customerDto.setPassword(password);
        customerDto.setEmail(email);
        customerDto.setBirthDate(dateOfBirth);
        return customerDto;
    }

    public WorkerDto fillWorkerDtoFields(String login) throws IOException, ValidateException {
        WorkerDto workerDto = new WorkerDto();

        String password  = this.readAndValidatePassword();
        String email = this.readAndValidateEmail();
        LocalDate dateOfBirth = this.readAndValidateBirthDate();

        writer.append("Введите имя: ");
        writer.flush();
        String first_name =  reader.readLine();
        first_name = userValidation.checkName(first_name);


        writer.append("Введите Фамилию: ");
        writer.flush();
        String last_name =  reader.readLine();
        last_name = userValidation.checkName(last_name);

        writer.append("Введите номер телефона: ");
        writer.flush();
        String phoneNumber = reader.readLine();
        phoneNumber  = userValidation.validatePhoneNumber(phoneNumber);

        writer.append("Введите роль: ");
        writer.flush();
        String role = reader.readLine();
        role = userValidation.checkRole(role);

        workerDto.setLogin(login);
        workerDto.setPassword(password);
        workerDto.setEmail(email);
        workerDto.setBirthDate(dateOfBirth);
        workerDto.setFirstName(first_name);
        workerDto.setLastName(last_name);
        workerDto.setPhoneNumber(phoneNumber);
        workerDto.setUserRole(role);
        return workerDto;
    }

    @Override
    public void showValidationError(String message) throws IOException {
        writer.write("Ошибка валидации: " + message + "\n");
        writer.flush();
    }

    @Override
    public void showPermissionDenied(String role) throws IOException {
        writer.write("Доступ запрещён. У вас нет прав для выполнения действия в роли: " + role + ".\n");
        writer.flush();
    }

    @Override
    public void showUserSavedSuccess(String login) throws IOException {
        writer.write("Пользователь с логином '" + login + "' успешно зарегистрирован.\n");
        writer.flush();
    }

    public String readAndValidateLogin() throws IOException, ValidateException {
        writer.write("Введите логин: ");
        writer.flush();
        String login = reader.readLine();
        return userValidation.checkLogin(login);
    }

    private String readAndValidatePassword() throws IOException, ValidateException {
        writer.write("Введите пароль: ");
        writer.flush();
        String password = reader.readLine();
        return userValidation.checkPassword(password);
    }

    private String readAndValidateEmail() throws IOException, ValidateException {
        writer.write("Введите email: ");
        writer.flush();
        String email = reader.readLine();
        return userValidation.checkEmail(email);
    }

    private LocalDate readAndValidateBirthDate() throws IOException, ValidateException {
        writer.write("Введите дату рождения (формат: гггг-мм-дд): ");
        writer.flush();
        String dateInput = reader.readLine();
        return userValidation.checkData(dateInput);
    }

    @Override
    public void showIsUserExist() throws IOException {
        writer.write("Пользователь с таким логином уже существует. Пожалуйста, выберите другой логин.\n");
        writer.flush();
    }
}

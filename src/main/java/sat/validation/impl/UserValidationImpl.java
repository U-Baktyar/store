package sat.validation.impl;

import sat.configuration.annotation.Autowired;
import sat.configuration.annotation.Component;
import sat.entity.user.Role;
import sat.exception.ValidateException;
import sat.validation.UserValidation;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;


@Component
public class UserValidationImpl implements UserValidation {

    @Autowired
    public UserValidationImpl() {}

    public String checkLogin(String login) throws ValidateException {
        if (login == null || login.trim().isEmpty()) {
            throw new ValidateException("Логин не может быть пустым");
        }

        login = login.trim();

        if (login.length() < 4 || login.length() > 20) {
            throw new ValidateException("Логин должен быть от 4 до 20 символов");
        }

        if (login.contains(" ")) {
            throw new ValidateException("Логин не должен содержать пробелы");
        }

//        Set<String> stopWords = new HashSet<>(Set.of("sudo", "admin", "root"));
//        if (stopWords.contains(login.toLowerCase())) {
//            throw new ValidateException("Логин зарезервирован системой как ключевое слово, выберите другой логин");
//        }
        return login;
    }

    public String checkPassword(String password) throws ValidateException {
        if (password == null || password.trim().isEmpty()) {
            throw new ValidateException("Пароль не может быть пустым");
        }

        password = password.trim();

        if (password.length() < 8 || password.length() > 20) {
            throw new ValidateException("Пароль должен быть от 8 до 20 символов");
        }

        if (password.contains(" ")) {
            throw new ValidateException("Пароль не должен содержать пробелы");
        }
        return password;
    }

    public String checkEmail(String email) throws ValidateException {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidateException("Email не может быть пустым");
        }
        email = email.trim();

        String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(emailRegex)) {
            throw new ValidateException("Некорректный формат email");
        }
        return email;
    }

    @Override
    public LocalDate checkData(String date) throws ValidateException {
        if(date == null || date.trim().isEmpty()) {
            throw new ValidateException("Дата рождения не может быть пустой");
        }
        date = date.trim();

        String regex = "^\\d{4}-\\d{2}-\\d{2}$";

        if (!date.matches(regex)) {
            throw new ValidateException("Неверно введена дата рождения");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(date, formatter);

        if (birthDate.isAfter(LocalDate.now())) {
            throw new ValidateException("Дата рождения не может быть в будущем");
        }
        LocalDate before = LocalDate.of(1900, 1, 1);
        if (birthDate.isBefore(before)) {
            throw new ValidateException("Дата рождения не может настолько в прошлом");
        }
        return birthDate;
    }

    @Override
    public String checkName(String name) throws ValidateException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidateException("Имя не может быть пустым");
        }
        name = name.trim();
        return name;
    }

    @Override
    public String validatePhoneNumber(String phoneNumber) throws ValidateException {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new ValidateException("Номер телефона не может быть пустым");
        }

        phoneNumber = phoneNumber.trim();

        String regex = "^(70|50|77|99|22|55|75|54)\\d{7}$";

        if (!phoneNumber.matches(regex)) {
            throw new ValidateException("Номер телефона должен содержать 9 цифр и начинаться с допустимого кода");
        }

        return phoneNumber;
    }

    @Override
    public String checkRole(String role) throws ValidateException {
        if (role == null || role.trim().isEmpty()) {
            throw new ValidateException("Роль не может быть пустой");

        }
        role = role.trim().toUpperCase();
        System.out.println(role);
        for (Role r : Role.values()) {
            if(r == Role.valueOf(role)) {
                return role;
            }
        }
        throw new ValidateException("Нет такой роли");
    }

    @Override
    public String validateAddress(String address) throws ValidateException {
        if (address == null || address.trim().isBlank()) {
            throw new ValidateException("адрес не быть пустой");

        }
        address = address.trim();
        return address;
    }

    private String isBlankOrNull(String data) throws ValidateException {
        if (data == null || data.trim().isEmpty()) {
            throw new ValidateException("Роль рождения не может быть пустой");
        }
        data = data.trim();
        return data;
    }
}


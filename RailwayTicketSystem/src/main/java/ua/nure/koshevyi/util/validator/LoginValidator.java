package ua.nure.koshevyi.util.validator;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.db.entity.User;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;

public class LoginValidator {

    private static final Logger LOGGER = Logger.getLogger(LoginValidator.class);

    private static final String LOGIN_REG_EXP = "^([a-zA-Zа-яА-Я][a-zA-Zа-яА-Я0-9_\\-]{2,}[a-zA-Zа-яА-Я0-9])$";

    private static final String PASSWORD_REG_EXP = "^([a-zA-Zа-яА-Я0-9][a-zA-Zа-яА-Я0-9_\\-]{2,}[a-zA-Zа-яА-Я0-9])$";

    public Map<String, String> validateLogin(User user) {
        Map<String, String> errors = new HashMap<>();
        if (!isMatch(LOGIN_REG_EXP, user.getLogin())) {
            errors.put("Login", "Has invalid format");
            LOGGER.trace(errors.get("Login"));
        }
        if (!isMatch(PASSWORD_REG_EXP, user.getPassword())) {
            errors.put("Password", "Has invalid format");
            LOGGER.trace(errors.get("Password"));
        }
        return errors;
    }

    public static boolean isMatch(String regex, String field) {
        return Pattern.compile(regex).matcher(field).find();
    }
}
import org.junit.Test;
import ua.nure.koshevyi.db.entity.User;
import ua.nure.koshevyi.util.validator.LoginValidator;

import static org.junit.Assert.*;

public class TestValidator {

    @Test
    public void validateTrue() {
        LoginValidator validator = new LoginValidator();
        User user = new User();
        user.setLogin("uzver14");
        user.setPassword("jksfdj3fds");
        assertTrue(validator.validateLogin(user).isEmpty());
    }

    @Test
    public void validateFalse() {
        LoginValidator validator = new LoginValidator();
        User user = new User();
        user.setLogin("_dsdf111 daw");
        user.setPassword("da  dwadafd");
        assertFalse(validator.validateLogin(user).isEmpty());
    }
}

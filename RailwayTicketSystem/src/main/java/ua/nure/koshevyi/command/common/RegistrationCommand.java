package ua.nure.koshevyi.command.common;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.command.Command;
import ua.nure.koshevyi.db.dao.impl.mysql.UserDAOImpl;
import ua.nure.koshevyi.db.entity.User;
import ua.nure.koshevyi.exception.AppException;
import ua.nure.koshevyi.util.Path;
import ua.nure.koshevyi.util.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class RegistrationCommand extends Command {

    private static final long serialVersionUID = 3342788937229411870L;

    private static final Logger LOGGER = Logger.getLogger(RegistrationCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {
        LOGGER.debug("Command starts");

        HttpSession session = request.getSession();

        User user = new User();
        user.setLogin(request.getParameter("Login"));
        user.setPassword(request.getParameter("Password"));
        user.setFirstName(request.getParameter("First Name"));
        user.setLastName(request.getParameter("Last Name"));
        user.setRoleId(2);

        String errorMessage = null;
        String forward = Path.PAGE_ERROR_PAGE;

        List<String> logins = new UserDAOImpl().getAllLogins();

        if (!logins.contains(user.getLogin())) {
            new UserDAOImpl().create(user);

            Role userRole = Role.getRole(user);
            LOGGER.trace("userRole --> " + userRole);
            LOGGER.trace("user name --> " + user.getFirstName() + " " + user.getLastName());
            String login = request.getParameter("Login");

            session.setAttribute("user", user);
            session.setAttribute("userRole", userRole);
            LOGGER.trace("Request parameter: loging --> " + login);
            LOGGER.trace("Set the session attribute: user --> " + user);

            forward = Path.PAGE_MAIN;
        } else {
            errorMessage = "User with this login already exists!";
            request.setAttribute("errorMessage", errorMessage);
            LOGGER.error("errorMessage --> " + errorMessage);
            return forward;
        }

        LOGGER.debug("Command finished");
        return forward;
    }
}
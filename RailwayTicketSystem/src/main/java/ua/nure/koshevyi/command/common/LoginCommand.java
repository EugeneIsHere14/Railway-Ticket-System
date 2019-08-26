package ua.nure.koshevyi.command.common;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.command.Command;
import ua.nure.koshevyi.db.bean.RouteBean;
import ua.nure.koshevyi.db.dao.impl.mysql.RouteBeanDAOImpl;
import ua.nure.koshevyi.db.dao.impl.mysql.StationDAOImpl;
import ua.nure.koshevyi.db.dao.impl.mysql.UserDAOImpl;
import ua.nure.koshevyi.db.entity.Station;
import ua.nure.koshevyi.util.Role;
import ua.nure.koshevyi.db.entity.User;
import ua.nure.koshevyi.exception.AppException;
import ua.nure.koshevyi.util.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class LoginCommand extends Command {

    private static final long serialVersionUID = -4598154445220670007L;

    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {
        LOGGER.debug("Command starts");

        HttpSession session = request.getSession();

        String login = request.getParameter("Login");
        LOGGER.trace("Request parameter: loging --> " + login);

        String password = request.getParameter("Password");

        String errorMessage = null;
        String forward = Path.PAGE_ERROR_PAGE;

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            errorMessage = "Login/password cannot be empty";
            request.setAttribute("errorMessage", errorMessage);
            LOGGER.error("errorMessage --> " + errorMessage);
            forward = Path.PAGE_ERROR_PAGE;
            return forward;
        }

        User user = new UserDAOImpl().findUserByLogin(login);
        LOGGER.trace("Found in DB: user --> " + user);

        if (user == null || !password.equals(user.getPassword())) {
            errorMessage = "Cannot find user with such login/password";
            request.setAttribute("errorMessage", errorMessage);
            LOGGER.error("errorMessage --> " + errorMessage);
            forward = Path.PAGE_ERROR_PAGE;
            return forward;
        } else {
            Role userRole = Role.getRole(user);
            LOGGER.trace("userRole --> " + userRole);

            if (userRole == Role.ADMIN) {
                List<Station> stations = new StationDAOImpl().getAllStations();
                LOGGER.trace("Found in DB: listStations --> " + stations);

                List<RouteBean> arrInitStations = new RouteBeanDAOImpl().getInitArrStations();
                LOGGER.trace("Found in DB: list arr - init Stations info --> " + arrInitStations);


                request.setAttribute("stations", stations);
                LOGGER.trace("Set the request attribute: stations --> " + stations);

                request.setAttribute("routes", arrInitStations);
                LOGGER.trace("Set the request attribute: routes --> " + arrInitStations);

                int routesCount = new RouteBeanDAOImpl().getRoutesCount();
                request.setAttribute("countRoutes", routesCount);

                forward = Path.PAGE_ADMIN;
            }

            if (userRole == Role.USER) {
                forward = Path.PAGE_MAIN;
            }

            session.setAttribute("user", user);
            LOGGER.trace("Set the session attribute: user --> " + user);

            session.setAttribute("userRole", userRole);
            LOGGER.trace("Set the session attribute: userRole --> " + userRole);

            LOGGER.info("User '" + user.getLogin() + "' logged as " + userRole.toString().toLowerCase());

            // work with i18n
//            String userLocaleName = user.getLocaleName();
//            LOGGER.trace("userLocalName --> " + userLocaleName);

//            if (userLocaleName != null && !userLocaleName.isEmpty()) {
//                Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", userLocaleName);
//
//                session.setAttribute("defaultLocale", userLocaleName);
//                LOGGER.trace("Set the session attribute: defaultLocaleName --> " + userLocaleName);
//
//                LOGGER.info("Locale for user: defaultLocale --> " + userLocaleName);
//            }
        }

        LOGGER.debug("Command finished");
        return forward;
    }
}
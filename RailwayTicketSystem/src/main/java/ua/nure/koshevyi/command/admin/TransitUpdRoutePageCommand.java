package ua.nure.koshevyi.command.admin;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.command.Command;
import ua.nure.koshevyi.db.dao.impl.mysql.RouteDAOImpl;
import ua.nure.koshevyi.db.entity.Route;
import ua.nure.koshevyi.exception.AppException;
import ua.nure.koshevyi.util.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TransitUpdRoutePageCommand extends Command {

    private static final long serialVersionUID = 2351935776025761143L;

    private static final Logger LOGGER = Logger.getLogger(TransitUpdRoutePageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        LOGGER.debug("Command starts");

        String errorMessage = null;
        String forward = Path.PAGE_ERROR_PAGE;

        String routeId = request.getParameter("routeId");

        if (routeId != null) {
            Route route = new RouteDAOImpl().read(Integer.parseInt(routeId));
            request.setAttribute("route", route);

            forward = Path.PAGE_UPDATE_ROUTE;
        } else {
            errorMessage = "You have not chosen route to edit!";
            request.setAttribute("errorMessage", errorMessage);
            LOGGER.error("errorMessage --> " + errorMessage);
            return forward;
        }

        LOGGER.trace("Transition to edit route page... ");

        LOGGER.debug("Commands finished");

        return forward;
    }
}
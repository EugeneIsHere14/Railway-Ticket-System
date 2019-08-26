package ua.nure.koshevyi.command.admin;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.command.Command;
import ua.nure.koshevyi.db.dao.impl.mysql.RouteStationDAOImpl;
import ua.nure.koshevyi.db.entity.RouteStation;
import ua.nure.koshevyi.exception.AppException;
import ua.nure.koshevyi.util.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TransitUpdRouteStPageCommand extends Command {

    private static final long serialVersionUID = 6365854767699130266L;

    private static final Logger LOGGER = Logger.getLogger(TransitUpdRouteStPageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        LOGGER.debug("Command starts");

        String errorMessage = null;
        String forward = Path.PAGE_ERROR_PAGE;

        String routeStationId = request.getParameter("routeStationId");

        if (routeStationId != null) {
            RouteStation routeStation = new RouteStationDAOImpl().read(Integer.parseInt(routeStationId));
            request.setAttribute("rs", routeStation);

            forward = Path.PAGE_UPDATE_ROUTE_STATION;
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
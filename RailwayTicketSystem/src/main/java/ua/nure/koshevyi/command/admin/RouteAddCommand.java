package ua.nure.koshevyi.command.admin;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.command.Command;
import ua.nure.koshevyi.db.dao.impl.mysql.RouteDAOImpl;
import ua.nure.koshevyi.db.entity.Route;
import ua.nure.koshevyi.db.entity.Station;
import ua.nure.koshevyi.exception.AppException;
import ua.nure.koshevyi.util.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

public class RouteAddCommand extends Command {

    private static final long serialVersionUID = -3186066216378476102L;

    private static final Logger LOGGER = Logger.getLogger(RouteAddCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        LOGGER.debug("Command starts");

        String errorMessage = null;
        String forward = Path.PAGE_ERROR_PAGE;

        Route route = new Route();
        route.setDepartTime(Timestamp.valueOf(request.getParameter("Departure Time")));
        route.setArrivalTime(Timestamp.valueOf(request.getParameter("Arrival Time")));

        Station init = new Station();
        init.setName(request.getParameter("Initial Station"));

        Station arrival = new Station();
        arrival.setName(request.getParameter("Arrival Station"));

        if (!init.getName().equals(arrival.getName())) {

            new RouteDAOImpl().createNewRoute(route, init, arrival);

            request.setAttribute("routeAdd", route);

            LOGGER.trace("Route added --> " + route);

            forward = Path.PAGE_ADD_ROUTE;
        } else {
            errorMessage = "You can not make route with the same init and arrival stations!";
            request.setAttribute("errorMessage", errorMessage);
            LOGGER.error("errorMessage --> " + errorMessage);
            return forward;
        }

        LOGGER.debug("Command finished");

        return forward;
    }
}
package ua.nure.koshevyi.command.admin;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.command.Command;
import ua.nure.koshevyi.db.dao.impl.mysql.RouteBeanDAOImpl;
import ua.nure.koshevyi.db.dao.impl.mysql.RouteStationDAOImpl;
import ua.nure.koshevyi.db.entity.RouteStation;
import ua.nure.koshevyi.db.entity.Station;
import ua.nure.koshevyi.exception.AppException;
import ua.nure.koshevyi.util.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public class RouteStationAddCommand extends Command {

    private static final long serialVersionUID = 6469768719930710953L;

    private static final Logger LOGGER = Logger.getLogger(RouteStationAddCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        LOGGER.debug("Command starts");

        String errorMessage = null;
        String forward = Path.PAGE_ERROR_PAGE;

        RouteStation routeStation = new RouteStation();
        routeStation.setRouteId(Integer.parseInt(request.getParameter("Route ID")));
        routeStation.setArrivalTime(Timestamp.valueOf(request.getParameter("Arrival Time")));
        routeStation.setDepartTime(Timestamp.valueOf(request.getParameter("Departure Time")));
        routeStation.setWaitTime(Integer.parseInt(request.getParameter("Waiting Time")));

        Station station = new Station();
        station.setName(request.getParameter("Route Station"));

        List<String> rsIds = new RouteStationDAOImpl().getAllRouteStationNames(routeStation.getRouteId());

        for (String i : rsIds) {
            LOGGER.trace("rs Names loop --> " + i);
        }
        LOGGER.trace("Route station name --> " + routeStation.getStationId());

        if (!rsIds.contains(station.getName())) {

            new RouteStationDAOImpl().createNewRouteStation(routeStation, station);

            request.setAttribute("routeStationAdd", routeStation);

            LOGGER.trace("Route station added --> " + routeStation);

            int routesCount = new RouteBeanDAOImpl().getRoutesCount();
            request.setAttribute("countRoutes", routesCount);

            forward = Path.PAGE_ADD_ROUTE_STATION;
        } else {
            errorMessage = "This station is already exists on current route!";
            request.setAttribute("errorMessage", errorMessage);
            LOGGER.error("errorMessage --> " + errorMessage);
            return forward;
        }

        LOGGER.debug("Command finished");

        return forward;
    }
}
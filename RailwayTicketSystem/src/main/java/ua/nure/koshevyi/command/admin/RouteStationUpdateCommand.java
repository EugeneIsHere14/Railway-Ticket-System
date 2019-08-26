package ua.nure.koshevyi.command.admin;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.command.Command;
import ua.nure.koshevyi.db.bean.RouteBean;
import ua.nure.koshevyi.db.dao.impl.mysql.RouteBeanDAOImpl;
import ua.nure.koshevyi.db.dao.impl.mysql.RouteStationDAOImpl;
import ua.nure.koshevyi.db.dao.impl.mysql.StationDAOImpl;
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

public class RouteStationUpdateCommand extends Command {

    private static final long serialVersionUID = 4654555145034306710L;

    private static final Logger LOGGER = Logger.getLogger(RouteStationUpdateCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        LOGGER.debug("Command starts");

        String errorMessage = null;
        String forward = Path.PAGE_ERROR_PAGE;

        RouteStation routeStation = new RouteStation();
        routeStation.setId(Integer.parseInt(request.getParameter("routeStationId")));
        LOGGER.trace("route station ID = " + routeStation.getId());
        routeStation.setRouteId(Integer.parseInt(request.getParameter("Route ID")));
        routeStation.setArrivalTime(Timestamp.valueOf(request.getParameter("Arrival Time")));
        routeStation.setWaitTime(Integer.parseInt(request.getParameter("Waiting Time")));
        routeStation.setDepartTime(Timestamp.valueOf(request.getParameter("Departure Time")));

        Station station = new Station();
        station.setName(request.getParameter("Route Station"));

        if (routeStation.getDepartTime() != null) {

            new RouteStationDAOImpl().updateRouteStationViaStationName(routeStation, station);

            request.setAttribute("routeAddName", station);
            request.setAttribute("rs", routeStation);

            LOGGER.trace("Route Station updated --> " + station.getName());

            List<Station> stations = new StationDAOImpl().getAllStations();
            request.setAttribute("stations", stations);

            List<RouteBean> arrInitStations = new RouteBeanDAOImpl().getInitArrStations();
            request.setAttribute("routes", arrInitStations);

            int routesCount = new RouteBeanDAOImpl().getRoutesCount();
            request.setAttribute("countRoutes", routesCount);

            forward = Path.PAGE_ADMIN;
        } else {
            errorMessage = "Some error occurred while editing!";
            request.setAttribute("errorMessage", errorMessage);
            LOGGER.error("errorMessage --> " + errorMessage);
            return forward;
        }

        LOGGER.debug("Command finished");

        return forward;
    }
}
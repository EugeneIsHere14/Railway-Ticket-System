package ua.nure.koshevyi.command.admin;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.command.Command;
import ua.nure.koshevyi.db.bean.RouteBean;
import ua.nure.koshevyi.db.dao.impl.mysql.RouteBeanDAOImpl;
import ua.nure.koshevyi.db.dao.impl.mysql.RouteDAOImpl;
import ua.nure.koshevyi.db.dao.impl.mysql.StationDAOImpl;
import ua.nure.koshevyi.db.entity.Route;
import ua.nure.koshevyi.db.entity.Station;
import ua.nure.koshevyi.exception.AppException;
import ua.nure.koshevyi.util.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public class RouteUpdateCommand extends Command {

    private static final long serialVersionUID = -4445751066419800842L;

    private static final Logger LOGGER = Logger.getLogger(RouteUpdateCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        LOGGER.debug("Command starts");

        String errorMessage = null;
        String forward = Path.PAGE_ERROR_PAGE;

        Route route = new Route();
        route.setId(Integer.parseInt(request.getParameter("Route Id")));
        route.setDepartTime(Timestamp.valueOf(request.getParameter("Departure Time")));
        route.setArrivalTime(Timestamp.valueOf(request.getParameter("Arrival Time")));

        Station init = new Station();
        init.setName(request.getParameter("Initial Station"));

        Station arrival = new Station();
        arrival.setName(request.getParameter("Arrival Station"));

        if (route.getDepartTime() != null && !init.getName().equals(arrival.getName())) {
            new RouteDAOImpl().updateRouteViaStationNames(route, init, arrival);

            request.setAttribute("routeAddName", init);
            request.setAttribute("routeAddName2", arrival);
            request.setAttribute("route", route);

            LOGGER.trace("Route updated --> id = " + route.getId());

            List<Station> stations = new StationDAOImpl().getAllStations();
            request.setAttribute("stations", stations);

            List<RouteBean> arrInitStations = new RouteBeanDAOImpl().getInitArrStations();
            request.setAttribute("routes", arrInitStations);

            int routesCount = new RouteBeanDAOImpl().getRoutesCount();
            request.setAttribute("countRoutes", routesCount);

            forward = Path.PAGE_ADMIN;
        } else {
            errorMessage = "Some errors occurred while editing!";
            request.setAttribute("errorMessage", errorMessage);
            LOGGER.error("errorMessage --> " + errorMessage);
            return forward;
        }

        LOGGER.debug("Command finished");

        return forward;
    }
}
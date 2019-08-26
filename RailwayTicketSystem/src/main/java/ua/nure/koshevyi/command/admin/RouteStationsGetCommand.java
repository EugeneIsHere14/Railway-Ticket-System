package ua.nure.koshevyi.command.admin;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.command.Command;
import ua.nure.koshevyi.db.bean.RouteBean;
import ua.nure.koshevyi.db.dao.impl.mysql.RouteBeanDAOImpl;
import ua.nure.koshevyi.db.dao.impl.mysql.StationDAOImpl;
import ua.nure.koshevyi.db.entity.Station;
import ua.nure.koshevyi.exception.AppException;
import ua.nure.koshevyi.util.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RouteStationsGetCommand extends Command {

    private static final long serialVersionUID = 3944127342495924189L;

    private static final Logger LOGGER = Logger.getLogger(RouteStationsGetCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        LOGGER.debug("Command starts");

        String errorMessage = null;
        String forward = Path.PAGE_ERROR_PAGE;

        String routeId = request.getParameter("routeId");

        if (routeId != null) {
            List<Station> stations = new StationDAOImpl().getAllStations();
            request.setAttribute("stations", stations);

            List<RouteBean> arrInitStations = new RouteBeanDAOImpl().getInitArrStations();
            request.setAttribute("routes", arrInitStations);

            List<RouteBean> rsList = new RouteBeanDAOImpl().getAllRStationsByRouteId(Integer.parseInt(routeId));
            LOGGER.trace("Found Route ID ==> " + routeId);
            request.setAttribute("rstations", rsList);

            int routesCount = new RouteBeanDAOImpl().getRoutesCount();
            request.setAttribute("countRoutes", routesCount);

            LOGGER.trace("Station(s) displayed ");

            forward =  Path.PAGE_ADMIN;
        } else {
            errorMessage = "You have not chosen station to see its route stations!";
            request.setAttribute("errorMessage", errorMessage);
            LOGGER.error("errorMessage --> " + errorMessage);
            return forward;
        }

        LOGGER.debug("Command finished");

        return forward;
    }
}
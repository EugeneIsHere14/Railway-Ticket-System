package ua.nure.koshevyi.command.admin;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.command.Command;
import ua.nure.koshevyi.db.bean.RouteBean;
import ua.nure.koshevyi.db.dao.impl.mysql.RouteBeanDAOImpl;
import ua.nure.koshevyi.db.dao.impl.mysql.RouteDAOImpl;
import ua.nure.koshevyi.db.dao.impl.mysql.StationDAOImpl;
import ua.nure.koshevyi.db.entity.Station;
import ua.nure.koshevyi.exception.AppException;
import ua.nure.koshevyi.util.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RouteDeleteCommand extends Command {

    private static final long serialVersionUID = -8189169329120206090L;

    private static final Logger LOGGER = Logger.getLogger(RouteDeleteCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        LOGGER.debug("Command starts");

        String errorMessage = null;
        String forward = Path.PAGE_ERROR_PAGE;

        String routeId = request.getParameter("routeId");

        if (routeId != null) {
            new RouteDAOImpl().delete(Integer.parseInt(routeId));

            LOGGER.trace("Route was removed ");

            List<Station> stations = new StationDAOImpl().getAllStations();
            request.setAttribute("stations", stations);

            List<RouteBean> arrInitStations = new RouteBeanDAOImpl().getInitArrStations();
            request.setAttribute("routes", arrInitStations);

            int routesCount = new RouteBeanDAOImpl().getRoutesCount();
            request.setAttribute("countRoutes", routesCount);

            forward = Path.PAGE_ADMIN;
        } else {
            errorMessage = "You have not chosen route to delete!";
            request.setAttribute("errorMessage", errorMessage);
            LOGGER.error("errorMessage --> " + errorMessage);
            return forward;
        }

        LOGGER.debug("Command finished");
        return forward;
    }
}
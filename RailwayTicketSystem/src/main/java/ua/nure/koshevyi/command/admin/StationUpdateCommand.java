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

public class StationUpdateCommand extends Command {

    private static final long serialVersionUID = 5609699283418835840L;

    private static final Logger LOGGER = Logger.getLogger(StationUpdateCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {

        LOGGER.debug("Command starts");

        Station station = new Station();
        station.setId(Integer.parseInt(request.getParameter("Station Id")));
        station.setName(request.getParameter("Station Name"));
        station.setCity(request.getParameter("City"));
        station.setRegion(request.getParameter("Region"));
        station.setCountry(request.getParameter("Country"));

        String errorMessage;
        String forward = Path.PAGE_ERROR_PAGE;

        List<String> stationName = new StationDAOImpl().getAllStationsNames();

        if (station.getName() != null) {
            new StationDAOImpl().update(station);

            request.setAttribute("station", station);

            LOGGER.trace("Station updated --> id = " + station.getId() + ", name = " + station.getName());

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
package ua.nure.koshevyi.command.admin;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.command.Command;
import ua.nure.koshevyi.db.dao.impl.mysql.StationDAOImpl;
import ua.nure.koshevyi.db.entity.Station;
import ua.nure.koshevyi.exception.AppException;
import ua.nure.koshevyi.util.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class StationAddCommand extends Command {

    private static final long serialVersionUID = -7856780711648956482L;

    private static final Logger LOGGER = Logger.getLogger(StationAddCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        LOGGER.debug("Command starts");

        Station station = new Station();
        station.setName(request.getParameter("Station Name"));
        station.setCity(request.getParameter("City"));
        station.setRegion(request.getParameter("Region"));
        station.setCountry(request.getParameter("Country"));

        String errorMessage = null;
        String forward = Path.PAGE_ERROR_PAGE;

        List<String> stationName = new StationDAOImpl().getAllStationsNames();

        if (!stationName.contains(station.getName())) {
            new StationDAOImpl().create(station);

            request.setAttribute("station", station);

            LOGGER.trace("Station added --> " + station.getName());

            forward = Path.PAGE_ADD_STATION;
        } else {
            errorMessage = "Station with this name already exists!";
            request.setAttribute("errorMessage", errorMessage);
            LOGGER.error("errorMessage --> " + errorMessage);
            return forward;
        }

        LOGGER.debug("Command finished");
        return forward;
    }
}
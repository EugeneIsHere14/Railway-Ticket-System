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

public class TransitUpdStationPageCommand extends Command {

    private static final long serialVersionUID = -3288089090244441260L;

    private static final Logger LOGGER = Logger.getLogger(TransitUpdStationPageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        LOGGER.debug("Command starts");

        String errorMessage = null;
        String forward = Path.PAGE_ERROR_PAGE;

        String stationId = request.getParameter("stationId");

        if (stationId != null) {
            Station station = new StationDAOImpl().read(Integer.parseInt(stationId));
            request.setAttribute("station", station);

            forward = Path.PAGE_UPDATE_STATION;
        } else {
            errorMessage = "You have not chosen station to edit!";
            request.setAttribute("errorMessage", errorMessage);
            LOGGER.error("errorMessage --> " + errorMessage);
            return forward;
        }

        LOGGER.trace("Transition to edit station page... ");

        LOGGER.debug("Commands finished");

        return forward;
    }
}
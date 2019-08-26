package ua.nure.koshevyi.command.admin;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.command.Command;
import ua.nure.koshevyi.exception.AppException;
import ua.nure.koshevyi.util.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TransitAddStationPageCommand extends Command {

    private static final long serialVersionUID = -263517919779768792L;

    private static final Logger LOGGER = Logger.getLogger(TransitAddStationPageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        LOGGER.debug("Command starts");

        LOGGER.trace("Transition to add station page... ");

        LOGGER.debug("Commands finished");

        return Path.PAGE_ADD_STATION;
    }
}
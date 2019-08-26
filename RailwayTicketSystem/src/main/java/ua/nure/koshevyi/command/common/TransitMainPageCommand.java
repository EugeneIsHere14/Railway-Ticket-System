package ua.nure.koshevyi.command.common;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.command.Command;
import ua.nure.koshevyi.exception.AppException;
import ua.nure.koshevyi.util.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TransitMainPageCommand  extends Command {

    private static final long serialVersionUID = -4954003102525060904L;

    private static final Logger LOGGER = Logger.getLogger(TransitMainPageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        LOGGER.debug("Command starts");

        LOGGER.trace("Transition to main page... ");

        LOGGER.debug("Commands finished");

        return Path.PAGE_MAIN;
    }
}
package ua.nure.koshevyi.command.common;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.command.Command;
import ua.nure.koshevyi.exception.AppException;
import ua.nure.koshevyi.util.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TransitLoginPageCommand extends Command {

    private static final long serialVersionUID = 5096642992578081707L;

    private static final Logger LOGGER = Logger.getLogger(TransitLoginPageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        LOGGER.debug("Command starts");

        LOGGER.trace("Transition to login page... ");

        LOGGER.debug("Commands finished");

        return Path.PAGE_LOGIN;
    }
}
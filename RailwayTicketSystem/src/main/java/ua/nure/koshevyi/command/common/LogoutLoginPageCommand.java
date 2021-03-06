package ua.nure.koshevyi.command.common;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.command.Command;
import ua.nure.koshevyi.exception.AppException;
import ua.nure.koshevyi.util.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutLoginPageCommand extends Command {

    private static final long serialVersionUID = -5224830062721744871L;

    private static final Logger LOGGER = Logger.getLogger(LogoutLoginPageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        LOGGER.debug("Command starts");

        HttpSession session = request.getSession(false);

        LOGGER.trace("Current session --> " + session);

        if (session != null) {
            session.invalidate();
        }

        LOGGER.debug("Command finished");

        return Path.PAGE_LOGIN;
    }
}
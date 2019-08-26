package ua.nure.koshevyi.controller;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.command.Command;
import ua.nure.koshevyi.command.CommandContainer;
import ua.nure.koshevyi.exception.AppException;
import ua.nure.koshevyi.util.Path;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet
public class Controller extends HttpServlet {

    private static final long serialVersionUID = -8587471075281282719L;

    private static final Logger LOGGER = Logger.getLogger(Controller.class);

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request,
                         HttpServletResponse response) throws IOException, ServletException {

        LOGGER.debug("Controller starts");

        String commandName = request.getParameter("command");
        LOGGER.trace("Request parameter: command --> " + commandName);

        Command command = CommandContainer.get(commandName);
        LOGGER.trace("Obtained command --> " + command);

        String forward = null;

        try {
            forward = command.execute(request, response);
        } catch (AppException | SQLException e) {
            e.printStackTrace();
        }
        LOGGER.trace("Forward address --> " + forward);

        LOGGER.debug("Controller finished, now go to forward address --> " + forward);

        if (forward != null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
            dispatcher.forward(request, response);
        }
    }
}
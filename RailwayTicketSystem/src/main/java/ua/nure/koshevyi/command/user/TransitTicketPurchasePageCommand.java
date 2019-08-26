package ua.nure.koshevyi.command.user;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.command.Command;
import ua.nure.koshevyi.db.bean.TicketTrainRouteBean;
import ua.nure.koshevyi.db.dao.impl.mysql.TicketTrainRouteBeanDAOImpl;
import ua.nure.koshevyi.exception.AppException;
import ua.nure.koshevyi.util.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TransitTicketPurchasePageCommand extends Command {

    private static final long serialVersionUID = -1053397058024573943L;

    private static final Logger LOGGER = Logger.getLogger(TransitTicketPurchasePageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        LOGGER.debug("Command starts");

        String errorMessage = null;
        String forward = Path.PAGE_ERROR_PAGE;

        String trainId = request.getParameter("Train Id");

        LOGGER.trace("Train id = " + trainId);

        if (trainId != null) {

            List<TicketTrainRouteBean> bean = new TicketTrainRouteBeanDAOImpl()
                    .getAllFreeSeats(Integer.parseInt(trainId));

            request.setAttribute("seatsTable", bean);

            LOGGER.trace("Transition to ticket purchase page... ");

            forward = Path.PAGE_TICKET_PURCHASE;
        } else {
            errorMessage = "You have not chosen the train!";
            request.setAttribute("errorMessage", errorMessage);
            LOGGER.error("errorMessage --> " + errorMessage);
            return forward;
        }

        LOGGER.debug("Commands finished");

        return forward;
    }
}
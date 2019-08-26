package ua.nure.koshevyi.command.user;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.command.Command;
import ua.nure.koshevyi.db.bean.TicketTrainRouteBean;
import ua.nure.koshevyi.db.dao.impl.mysql.CarriageDAOImpl;
import ua.nure.koshevyi.db.dao.impl.mysql.TicketDAOImpl;
import ua.nure.koshevyi.db.dao.impl.mysql.TicketTrainRouteBeanDAOImpl;
import ua.nure.koshevyi.db.entity.Carriage;
import ua.nure.koshevyi.db.entity.Ticket;
import ua.nure.koshevyi.db.entity.User;
import ua.nure.koshevyi.db.transaction.TransactionManager;
import ua.nure.koshevyi.exception.AppException;
import ua.nure.koshevyi.util.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class BuyTicketsCommand extends Command {

    private static final long serialVersionUID = -9196612266918899864L;

    private static final Logger LOGGER = Logger.getLogger(BuyTicketsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException, SQLException {

        LOGGER.debug("Command starts");

        String errorMessage = null;
        String forward = Path.PAGE_ERROR_PAGE;

        HttpSession session = request.getSession();

        User currentUser = (User) session.getAttribute("user");

        int ticketsQuantity = Integer.parseInt(request.getParameter("Tickets Quantity"));

        Carriage carriage = new Carriage();
        carriage.setId(Integer.parseInt(request.getParameter("choiceTicketType")));

        String carriageType = request.getParameter("Carriage Type");

        int routeId = new TicketDAOImpl().findRouteId(Integer.parseInt(request.getParameter("Train Id")));
        int userId = new TicketDAOImpl().findUserId(currentUser.getLastName());
        double totalPrice = new TicketDAOImpl()
                .findTicketPrice(Integer.parseInt(request.getParameter("choiceTicketType")));

        Ticket ticket = new TicketDAOImpl().
                findCarriageTypeId(Integer.parseInt(request.getParameter("choiceTicketType")));
        ticket.setUserId(userId);
        ticket.setTrainId(Integer.parseInt(request.getParameter("Train Id")));
        ticket.setRouteId(routeId);
        ticket.setCarriageId(Integer.parseInt(request.getParameter("choiceTicketType")));
        ticket.setTotalPrice(totalPrice);

        LOGGER.trace("Session attribute Last Name: " + currentUser.getLastName());

        int freeSeats = new CarriageDAOImpl().findFreeSeatQuanByCarriageId(carriage);

        LOGGER.trace("Tickets Available: " + freeSeats);
        LOGGER.trace("Chosen Quantity: " + ticketsQuantity);

        if (carriageType != null && ticketsQuantity > 0 && ticketsQuantity <= freeSeats) {

            boolean transactionResult = new TransactionManager()
                    .purchaseTicketTransaction(carriage, ticketsQuantity, ticket);

            TicketTrainRouteBean bean = new TicketTrainRouteBean();

            if (transactionResult) {
                bean = new TicketTrainRouteBeanDAOImpl().getTicketInfo();
            }

            session.setAttribute("ticketsQuantity", ticketsQuantity);

            request.setAttribute("ticketInfo", bean);

            forward = Path.PAGE_TICKET_INFO;
        } else {
            errorMessage = "You have not chosen tickets quantity you want" +
                    " to buy or chosen wrong number of them!";
            request.setAttribute("errorMessage", errorMessage);
            LOGGER.error("errorMessage --> " + errorMessage);
            return forward;
        }

        LOGGER.debug("Commands finished");

        return forward;
    }
}
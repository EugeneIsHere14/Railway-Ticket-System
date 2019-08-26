package ua.nure.koshevyi.db.transaction;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.db.connection.ConnectionManager;
import ua.nure.koshevyi.db.dao.impl.mysql.CarriageDAOImpl;
import ua.nure.koshevyi.db.dao.impl.mysql.TicketDAOImpl;
import ua.nure.koshevyi.db.entity.Carriage;
import ua.nure.koshevyi.db.entity.Ticket;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {

    private static final Logger LOGGER = Logger.getLogger(TransactionManager.class);

    public boolean purchaseTicketTransaction(Carriage carriage, int ticketsQuantity, Ticket ticket)
            throws SQLException {
        boolean result;
        Connection connection = null;

        CarriageDAOImpl carriageDAO = new CarriageDAOImpl();
        TicketDAOImpl ticketDAO = new TicketDAOImpl();

        try {
            connection = ConnectionManager.getInstance().getConnection();

            result = carriageDAO.updateCarriage(carriage, ticketsQuantity) &&
                    ticketDAO.createTicket(ticket, ticketsQuantity);

//            ConnectionManager.getInstance().commitAndClose(connection);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
//            ConnectionManager.getInstance().rollbackAndClose(connection);
            LOGGER.error(e);
            throw new RuntimeException(e);
        } finally {
            connection.close();
        }

        return result;
    }
}
package ua.nure.koshevyi.db.dao.impl.mysql;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.db.connection.ConnectionManager;
import ua.nure.koshevyi.db.dao.TicketDAO;
import ua.nure.koshevyi.db.entity.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketDAOImpl implements TicketDAO {

    private static final Logger LOGGER = Logger.getLogger(TicketDAOImpl.class);

    private static final String CREATE_TICKET = "INSERT INTO tickets (user_id, train_id, route_id, " +
            "type_id, carriage_id, total_price) " +
            "values (?, ?, ?, " +
            "?, ?, ?)";

    private static final String FIND_ROUTE_ID = "SELECT routes.id FROM routes JOIN trains " +
            "ON routes.id = trains.route_id WHERE trains.id = ?";

    private static final String FIND_CARRIAGE_TYPE_ID_BY_NAME = "SELECT id FROM carriage_types WHERE name = ?";

    private static final String FIND_USER_ID = "select id from users where last_name = ?";

    private static final String FIND_CARRIAGE_TYPE_ID_BY_ID_CARRAIGE = "select carriage_types.id from " +
            "carriage_types join carriages on carriage_types.id = carriages.type_id " +
            "where carriages.id = ?";

    private static final String FIND_TICKET_PRICE = "SELECT price FROM carriages WHERE id = ?";

    @Override
    public double findTicketPrice(int id) {
        double price = 0;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(FIND_TICKET_PRICE);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                price = resultSet.getDouble(1);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            ConnectionManager.getInstance().rollbackAndClose(connection);
            LOGGER.error(e);
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.getInstance().commitAndClose(connection);
        }
        return price;
    }

    @Override
    public Ticket findCarriageTypeId(int carriageId) {
        Ticket ticket = new Ticket();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(FIND_CARRIAGE_TYPE_ID_BY_ID_CARRAIGE);
            preparedStatement.setInt(1, carriageId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ticket.setTypeId(resultSet.getInt(1));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            ConnectionManager.getInstance().rollbackAndClose(connection);
            LOGGER.error(e);
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.getInstance().commitAndClose(connection);
        }
        return ticket;
    }

    @Override
    public int findRouteId(int trainId) {
        int routeId = 0;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(FIND_ROUTE_ID);
            preparedStatement.setInt(1, trainId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                routeId = resultSet.getInt(1);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            ConnectionManager.getInstance().rollbackAndClose(connection);
            LOGGER.error(e);
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.getInstance().commitAndClose(connection);
        }
        return routeId;
    }

    @Override
    public int findUserId(String lastName) {
        int userId = 0;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(FIND_USER_ID);
            preparedStatement.setString(1, lastName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt(1);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            ConnectionManager.getInstance().rollbackAndClose(connection);
            LOGGER.error(e);
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.getInstance().commitAndClose(connection);
        }
        return userId;
    }

    @Override
    public boolean createTicket(Ticket entity, int quantity) {
        boolean result = false;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(CREATE_TICKET, PreparedStatement.RETURN_GENERATED_KEYS);
            int k = 1;
            preparedStatement.setInt(k++, entity.getUserId());
            preparedStatement.setInt(k++, entity.getTrainId());
            preparedStatement.setInt(k++, entity.getRouteId());
            preparedStatement.setInt(k++, entity.getTypeId());
            preparedStatement.setInt(k++, entity.getCarriageId());
            preparedStatement.setDouble(k, entity.getTotalPrice() * quantity);
            if(preparedStatement.executeUpdate() > 0) {
                result = true;
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    entity.setId(resultSet.getInt(1));
                }
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            ConnectionManager.getInstance().rollbackAndClose(connection);
            LOGGER.error(e);
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.getInstance().commitAndClose(connection);
        }
        return result;
    }

    @Override
    public boolean create(Ticket entity) {
        return false;
    }

    @Override
    public Ticket read(int id) {
        return null;
    }

    @Override
    public boolean update(Ticket entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
package ua.nure.koshevyi.db.dao.impl.mysql;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.db.connection.ConnectionManager;
import ua.nure.koshevyi.db.dao.CarriageDAO;
import ua.nure.koshevyi.db.entity.Carriage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarriageDAOImpl implements CarriageDAO {

    private static final Logger LOGGER = Logger.getLogger(CarriageDAOImpl.class);

    private static final String UPDATE_CARRIAGE = "UPDATE carriages SET free_seats = (free_seats - ?) WHERE id = ?";

    private static final String SELECT_FREE_SEATS_QUANTITY= "SELECT free_seats FROM carriages WHERE id = ?";

    @Override
    public boolean create(Carriage entity) {
        return false;
    }

    @Override
    public Carriage read(int id) {
        return null;
    }

    @Override
    public boolean update(Carriage entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean updateCarriage(Carriage entity, int quantity) {
        boolean result = false;

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_CARRIAGE);

            int k = 1;
            preparedStatement.setInt(k++, entity.getFreeSeats() + quantity);
            preparedStatement.setInt(k, entity.getId());
            if (preparedStatement.executeUpdate() > 0) {
                result = true;
            }
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
    public int findFreeSeatQuanByCarriageId(Carriage entity) {
        int freeSeats = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SELECT_FREE_SEATS_QUANTITY);
            preparedStatement.setInt(1, entity.getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                freeSeats = resultSet.getInt("free_seats");
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
        return freeSeats;
    }
}
package ua.nure.koshevyi.db.dao.impl.mysql;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.db.bean.TicketTrainRouteBean;
import ua.nure.koshevyi.db.connection.ConnectionManager;
import ua.nure.koshevyi.db.dao.TicketTrainRouteBeanDAO;
import ua.nure.koshevyi.util.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketTrainRouteBeanDAOImpl implements TicketTrainRouteBeanDAO {

    private static final Logger LOGGER = Logger.getLogger(TicketTrainRouteBeanDAOImpl.class);

    private static final String FIND_FREE_SEATS = "SELECT carriage_types.name, carriages.free_seats,  " +
            "carriages.price, trains.id, carriages.id  " +
            "FROM carriage_types JOIN carriages ON carriage_types.id = carriages.type_id  " +
            "JOIN trains ON carriages.train_id = trains.id WHERE trains.id = ? ";

    private static final String GET_TICKET_INFO = "SELECT users.first_name, users.last_name, trains.id," +
            " carriage_types.name, r.id, si.name AS depart, r.depart_time, sa.name AS arrival, " +
            "r.arrival_time, tickets.total_price " +
            "FROM users JOIN tickets ON users.id = tickets.user_id " +
            "JOIN trains ON tickets.train_id = trains.id " +
            "JOIN carriage_types ON tickets.type_id = carriage_types.id " +
            "JOIN routes AS r ON tickets.route_id = r.id " +
            "JOIN stations AS si ON  si.id = r.init_station_id " +
            "JOIN stations AS sa ON sa.id = r.arrival_station_id " +
            "WHERE tickets.id = (SELECT MAX(tickets.id) FROM tickets)";

    @Override
    public List<TicketTrainRouteBean> getAllFreeSeats(int trainId) {
        List<TicketTrainRouteBean> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            FreeSeatsExtractor extractor = new FreeSeatsExtractor();
            preparedStatement = connection.prepareStatement(FIND_FREE_SEATS);
            preparedStatement.setInt(1, trainId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(extractor.mapEntity(resultSet));
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
        return list;
    }

    @Override
    public TicketTrainRouteBean getTicketInfo() {
        TicketTrainRouteBean bean = new TicketTrainRouteBean();

        Connection connection = null;
        Statement statement;
        ResultSet resultSet;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            TicketInfoExtractor extractor = new TicketInfoExtractor();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(GET_TICKET_INFO);
            while (resultSet.next()) {
                bean = extractor.mapEntity(resultSet);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            ConnectionManager.getInstance().rollbackAndClose(connection);
            e.printStackTrace();
        } finally {
            ConnectionManager.getInstance().commitAndClose(connection);
        }
        return bean;
    }

    public List<TicketTrainRouteBean> getAllFreeSeatsCall(int trainId) {
        List<TicketTrainRouteBean> list = new ArrayList<>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            FreeSeatsExtractor extractor = new FreeSeatsExtractor();
            callableStatement = connection.prepareCall("{call getSeatsType(?)}");
            callableStatement.setInt(1, trainId);
            callableStatement.execute();
            resultSet = callableStatement.getResultSet();
//            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                list.add(extractor.mapEntity(resultSet));
            }
            resultSet.close();
            callableStatement.close();
        } catch (SQLException e) {
            ConnectionManager.getInstance().rollbackAndClose(connection);
            LOGGER.error(e);
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.getInstance().commitAndClose(connection);
        }
        return list;
    }

    public List<TicketTrainRouteBean> getAllFreeSeatsCallOut(int trainId) {
        List<TicketTrainRouteBean> list = new ArrayList<>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            FreeSeatsExtractor extractor = new FreeSeatsExtractor();
            callableStatement = connection.prepareCall("{call getSeatsType(?,?,?,?,?)}");
            callableStatement.setInt(1, trainId);
            callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(3, java.sql.Types.INTEGER);
            callableStatement.registerOutParameter(4, java.sql.Types.DOUBLE);
            callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                list.add(extractor.mapEntity(resultSet));
            }
            resultSet.close();
            callableStatement.close();
        } catch (SQLException e) {
            ConnectionManager.getInstance().rollbackAndClose(connection);
            LOGGER.error(e);
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.getInstance().commitAndClose(connection);
        }
        return list;
    }

    @Override
    public boolean create(TicketTrainRouteBean entity) {
        boolean result = false;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        return false;
    }

    @Override
    public TicketTrainRouteBean read(int id) {
        return null;
    }

    @Override
    public boolean update(TicketTrainRouteBean entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    private static class FreeSeatsExtractor implements EntityMapper<TicketTrainRouteBean> {

        @Override
        public TicketTrainRouteBean mapEntity(ResultSet resultSet) {
            try {
                TicketTrainRouteBean bean = new TicketTrainRouteBean();
                bean.setCarriageType(resultSet.getString(1));
                bean.setFreeSeats(resultSet.getInt(2));
                bean.setPrice(resultSet.getDouble(3));
                bean.setTrainId(resultSet.getInt(4));
                bean.setCarriageId(resultSet.getInt(5));

                return bean;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private static class TicketInfoExtractor implements EntityMapper<TicketTrainRouteBean> {

        @Override
        public TicketTrainRouteBean mapEntity(ResultSet resultSet) {
            try {
                TicketTrainRouteBean bean = new TicketTrainRouteBean();
                bean.setFirstName(resultSet.getString(1));
                bean.setLastName(resultSet.getString(2));
                bean.setTrainId(resultSet.getInt(3));
                bean.setCarriageType(resultSet.getString(4));
                bean.setRouteId(resultSet.getInt(5));
                bean.setInitStation(resultSet.getString(6));
                bean.setDepartDate(resultSet.getTimestamp(7));
                bean.setArrivalStation(resultSet.getString(8));
                bean.setArrivalDate(resultSet.getTimestamp(9));
                bean.setPrice(resultSet.getDouble(10));

                return bean;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
package ua.nure.koshevyi.db.dao.impl.mysql;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.db.connection.ConnectionManager;
import ua.nure.koshevyi.db.dao.RouteDAO;
import ua.nure.koshevyi.db.entity.Route;
import ua.nure.koshevyi.db.entity.Station;
import ua.nure.koshevyi.util.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RouteDAOImpl implements RouteDAO {

    private static final Logger LOGGER = Logger.getLogger(RouteDAOImpl.class);

    private static final String CREATE_ROUTE = "INSERT INTO routes (init_station_id, depart_time, arrival_station_id, " +
            "arrival_time) VALUES ((SELECT id FROM stations WHERE name = ?), ?, " +
            "(SELECT id FROM stations WHERE name = ?), ?)";

    private static final String DELETE_ROUTE = "DELETE FROM routes WHERE id = ?";

    private static final String UPDATE_ROUTE = "UPDATE routes SET init_station_id = " +
            "(SELECT id FROM stations WHERE name = ?), depart_time = ?, arrival_station_id = " +
            "(SELECT id FROM stations WHERE name = ?), arrival_time = ? WHERE id = ?";

    private static final String FIND_ROUTE_BY_ID = "SELECT * FROM routes WHERE id = ?";

    private static final String GET_ALL_ROUTE_IDS = "SELECT id FROM routes";

    @Override
    public boolean createNewRoute(Route entity, Station initStation, Station arrivalStation) {
        boolean result = false;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(CREATE_ROUTE, PreparedStatement.RETURN_GENERATED_KEYS);
            int k = 1;
            preparedStatement.setString(k++, initStation.getName());
            preparedStatement.setTimestamp(k++, entity.getDepartTime());
            preparedStatement.setString(k++, arrivalStation.getName());
            preparedStatement.setTimestamp(k, entity.getArrivalTime());
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
    public boolean updateRouteViaStationNames(Route entity, Station init, Station arrival) {
        boolean result = false;

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_ROUTE);

            int k = 1;
            preparedStatement.setString(k++, init.getName());
            preparedStatement.setTimestamp(k++, entity.getDepartTime());
            preparedStatement.setString(k++, arrival.getName());
            preparedStatement.setTimestamp(k++, entity.getArrivalTime());
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
    public List<Integer> getAllRouteIds() {
        List<Integer> id = new ArrayList<>();

        Connection connection = null;
        Statement statement;
        ResultSet resultSet;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(GET_ALL_ROUTE_IDS);
            while (resultSet.next()) {
                id.add(resultSet.getInt(1));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            ConnectionManager.getInstance().rollbackAndClose(connection);
            LOGGER.error(e);
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.getInstance().commitAndClose(connection);
        }
        return id;
    }

    @Override
    public boolean create(Route entity) {
        return false;
    }

    @Override
    public Route read(int id) {
        Route route = new Route();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            RouteExtractor extractor = new RouteExtractor();
            preparedStatement = connection.prepareStatement(FIND_ROUTE_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                route = extractor.mapEntity(resultSet);
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
        return route;
    }

    @Override
    public boolean update(Route entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(DELETE_ROUTE);
            preparedStatement.setInt(1, id);
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

    private static class RouteExtractor implements EntityMapper<Route> {

        @Override
        public Route mapEntity(ResultSet resultSet) {
            try {
                Route route = new Route();
                route.setId(resultSet.getInt(1));
                route.setInitStationId(resultSet.getInt(2));
                route.setDepartTime(resultSet.getTimestamp(3));
                route.setArrivalStationId(resultSet.getInt(4));
                route.setArrivalTime(resultSet.getTimestamp(5));
                return route;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
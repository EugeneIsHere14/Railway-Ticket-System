package ua.nure.koshevyi.db.dao.impl.mysql;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.db.connection.ConnectionManager;
import ua.nure.koshevyi.db.dao.RouteStationDAO;
import ua.nure.koshevyi.db.entity.RouteStation;
import ua.nure.koshevyi.db.entity.Station;
import ua.nure.koshevyi.util.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RouteStationDAOImpl implements RouteStationDAO {

    private static final Logger LOGGER = Logger.getLogger(RouteStationDAOImpl.class);

    private static final String CREATE_ROUTE_STATION = "INSERT INTO route_stations (route_id, station_id, " +
            "arrival_time, depart_time, wait_time) VALUES (?, (SELECT id FROM stations WHERE name = ?), ?, ?, ?)";

    private static final String DELETE_ROUTE_STATION = "DELETE FROM route_stations WHERE id = ?";

    private static final String GET_ALL_ROUTE_STATION_NAMES = "SELECT stations.name FROM stations " +
            "JOIN route_stations ON stations.id = route_stations.station_id WHERE route_id = ?";

    private static final String FIND_ROUTE_STATION_BY_ID = "SELECT * FROM route_stations WHERE id = ?";

    private static final String UPDATE_ROUTE_STATION = "UPDATE route_stations SET route_id = ?, " +
            "station_id = (SELECT id FROM stations WHERE name = ?), arrival_time = ?, " +
            "depart_time = ?, wait_time = ? WHERE id = ?";

    @Override
    public boolean createNewRouteStation(RouteStation entity, Station station) {

        boolean result = false;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(CREATE_ROUTE_STATION, PreparedStatement.RETURN_GENERATED_KEYS);
            int k = 1;
            preparedStatement.setInt(k++, entity.getRouteId());
            preparedStatement.setString(k++, station.getName());
            preparedStatement.setTimestamp(k++, entity.getArrivalTime());
            preparedStatement.setTimestamp(k++, entity.getDepartTime());
            preparedStatement.setInt(k, entity.getWaitTime());
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
    public List<String> getAllRouteStationNames(int routeId) {
        List<String> id = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_ALL_ROUTE_STATION_NAMES);
            preparedStatement.setInt(1,routeId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id.add(resultSet.getString("name"));
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
        return id;
    }

    @Override
    public boolean updateRouteStationViaStationName(RouteStation entity, Station station) {
        boolean result = false;

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_ROUTE_STATION);

            int k = 1;
            preparedStatement.setInt(k++, entity.getRouteId());
            preparedStatement.setString(k++, station.getName());
            preparedStatement.setTimestamp(k++, entity.getArrivalTime());
            preparedStatement.setTimestamp(k++, entity.getDepartTime());
            preparedStatement.setInt(k++, entity.getWaitTime());
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
    public boolean create(RouteStation entity) {
        return false;
    }

    @Override
    public RouteStation read(int id) {
        RouteStation routeStation = new RouteStation();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            StationExtractor extractor = new StationExtractor();
            preparedStatement = connection.prepareStatement(FIND_ROUTE_STATION_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                routeStation = extractor.mapEntity(resultSet);
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
        return routeStation;
    }

    @Override
    public boolean update(RouteStation entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(DELETE_ROUTE_STATION);
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

    private static class StationExtractor implements EntityMapper<RouteStation> {

        @Override
        public RouteStation mapEntity(ResultSet resultSet) {
            try {
                RouteStation routeStation = new RouteStation();
                routeStation.setId(resultSet.getInt(1));
                routeStation.setRouteId(resultSet.getInt(2));
                routeStation.setStationId(resultSet.getInt(3));
                routeStation.setArrivalTime(resultSet.getTimestamp(4));
                routeStation.setDepartTime(resultSet.getTimestamp(5));
                routeStation.setWaitTime(resultSet.getInt(6));
                return routeStation;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
package ua.nure.koshevyi.db.dao.impl.mysql;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.db.bean.RouteBean;
import ua.nure.koshevyi.db.connection.ConnectionManager;
import ua.nure.koshevyi.db.dao.RouteBeanDAO;
import ua.nure.koshevyi.util.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RouteBeanDAOImpl implements RouteBeanDAO {

    private static final Logger LOGGER = Logger.getLogger(RouteBeanDAOImpl.class);

    private static final String GET_INIT_ARR_STATION_INFO = "SELECT r.id, si.name AS init, r.depart_time, sa.name AS depart, r.arrival_time " +
            "FROM routes AS r INNER JOIN stations AS si ON  si.id = r.init_station_id  " +
            "INNER JOIN stations AS sa ON sa.id = r.arrival_station_id";

    private static final String FIND_ROUTE_STATION_BY_ROUTE_ID = "SELECT route_stations.route_id," +
            " stations.name, route_stations.arrival_time, " +
            "route_stations.depart_time, route_stations.wait_time, route_stations.id " +
            "FROM stations JOIN route_stations ON route_stations.station_id = stations.id " +
            "WHERE route_stations.route_id = ?";

    private static final String GET_ALL_COUNTS = "SELECT COUNT(id) FROM routes";

    @Override
    public List<RouteBean> getInitArrStations() {
        List<RouteBean> initArrStations = new ArrayList<>();

        Connection connection = null;
        Statement statement;
        ResultSet resultSet;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            InitArrStationExtractor extractor = new InitArrStationExtractor();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(GET_INIT_ARR_STATION_INFO);
            while (resultSet.next()) {
                initArrStations.add(extractor.mapEntity(resultSet));
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
        return initArrStations;
    }

    @Override
    public List<RouteBean> getAllRStationsByRouteId(int id) {
        List<RouteBean> routeStations = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            RouteStationExtractor extractor = new RouteStationExtractor();
            preparedStatement = connection.prepareStatement(FIND_ROUTE_STATION_BY_ROUTE_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                routeStations.add(extractor.mapEntity(resultSet));
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
        return routeStations;
    }

    @Override
    public int getRoutesCount() {
        int count = 0;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            RouteStationExtractor extractor = new RouteStationExtractor();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(GET_ALL_COUNTS);
            while (resultSet.next()) {
                count = resultSet.getInt(1);
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
        return count;
    }

    @Override
    public boolean create(RouteBean entity) {
        return false;
    }

    @Override
    public RouteBean read(int id) {
        RouteBean routeStation = new RouteBean();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            RouteStationExtractor extractor = new RouteStationExtractor();
            preparedStatement = connection.prepareStatement(FIND_ROUTE_STATION_BY_ROUTE_ID);
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
    public boolean update(RouteBean entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    private static class InitStationExtractor implements EntityMapper<RouteBean> {

        @Override
        public RouteBean mapEntity(ResultSet resultSet) {
            try {
                RouteBean bean = new RouteBean();
                bean.setId(resultSet.getInt(1));
                bean.setInitStation(resultSet.getString(2));
                bean.setDepartTime(resultSet.getTimestamp(3));
                return bean;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private static class ArrivalStationExtractor implements EntityMapper<RouteBean> {

        @Override
        public RouteBean mapEntity(ResultSet resultSet) {
            try {
                RouteBean bean = new RouteBean();
                bean.setId(resultSet.getInt(1));
                bean.setArrivalStation(resultSet.getString(2));
                bean.setArrivalTime(resultSet.getTimestamp(3));
                return bean;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private static class InitArrStationExtractor implements EntityMapper<RouteBean> {

        @Override
        public RouteBean mapEntity(ResultSet resultSet) {
            try {
                RouteBean bean = new RouteBean();
                bean.setId(resultSet.getInt(1));
                bean.setInitStation(resultSet.getString(2));
                bean.setDepartTime(resultSet.getTimestamp(3));
                bean.setArrivalStation(resultSet.getString(4));
                bean.setArrivalTime(resultSet.getTimestamp(5));
                return bean;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private static class RouteStationExtractor implements EntityMapper<RouteBean> {

        @Override
        public RouteBean mapEntity(ResultSet resultSet) {
            try {
                RouteBean bean = new RouteBean();
                bean.setId(resultSet.getInt(1));
                bean.setRouteStation(resultSet.getString(2));
                bean.setRouteStationDepart(resultSet.getTimestamp(3));
                bean.setRouteStationArrival(resultSet.getTimestamp(4));
                bean.setWaitTime(resultSet.getInt(5));
                bean.setRouteStationId(resultSet.getInt(6));
                return bean;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
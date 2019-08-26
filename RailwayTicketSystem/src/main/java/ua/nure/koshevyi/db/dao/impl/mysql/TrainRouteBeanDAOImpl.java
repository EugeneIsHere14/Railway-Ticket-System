package ua.nure.koshevyi.db.dao.impl.mysql;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.db.bean.RouteBean;
import ua.nure.koshevyi.db.bean.TrainRouteBean;
import ua.nure.koshevyi.db.connection.ConnectionManager;
import ua.nure.koshevyi.db.dao.TrainRouteBeanDAO;
import ua.nure.koshevyi.exception.AppException;
import ua.nure.koshevyi.util.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainRouteBeanDAOImpl implements TrainRouteBeanDAO {

    private static final Logger LOGGER = Logger.getLogger(TrainRouteBeanDAOImpl.class);

    private static final String GET_ALL_AVAILABLE_TRAINS = "SELECT trains.id, si.name AS depart, r.depart_time, " +
            "sa.name AS arrival, r.arrival_time, (unix_timestamp(arrival_time) - unix_timestamp(depart_time)) / 3600 " +
            "AS way_time, r.id, " +
            "cp.free_seats AS coupe, rs.free_seats AS rsp, com.free_seats AS common " +
            "FROM routes AS r " +
            "JOIN stations AS si ON  si.id = r.init_station_id " +
            "JOIN stations AS sa ON sa.id = r.arrival_station_id " +
            "JOIN trains ON r.id = trains.route_id " +
            "JOIN carriages AS cp ON cp.train_id = trains.id " +
            "JOIN carriages AS rs ON rs.train_id = trains.id " +
            "JOIN carriages AS com ON com.train_id = trains.id " +
            "WHERE si.name = ? AND sa.name = ? AND r.depart_time LIKE ? " +
            "AND cp.type_id = 1 AND rs.type_id = 2 AND com.type_id = 3 ORDER BY r.depart_time;";

    private static final String FIND_STATION_DATE_BY_ROUTE_ID = "SELECT si.name AS depart, r.depart_time, " +
            "sa.name AS arrival, r.arrival_time FROM routes AS r JOIN stations AS si ON  si.id = r.init_station_id " +
            "JOIN stations AS sa ON sa.id = r.arrival_station_id WHERE r.id = ?";

    @Override
    public List<TrainRouteBean> getAvailableTrainRouteViews(String init, String arrival, String date) throws AppException {
        List<TrainRouteBean> trainRouteBeans = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            TrainRouteExtractor extractor = new TrainRouteExtractor();
            preparedStatement = connection.prepareStatement(GET_ALL_AVAILABLE_TRAINS);
            preparedStatement.setString(1, init);
            preparedStatement.setString(2, arrival);
            preparedStatement.setString(3, date + "%");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                trainRouteBeans.add(extractor.mapEntity(resultSet));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            ConnectionManager.getInstance().rollbackAndClose(connection);
            LOGGER.error(e);
            throw new AppException("Some error occured");
        } finally {
            ConnectionManager.getInstance().commitAndClose(connection);
        }
        return trainRouteBeans;
    }

    @Override
    public boolean create(TrainRouteBean entity) {
        return false;
    }

    @Override
    public TrainRouteBean read(int id) {
        TrainRouteBean bean = new TrainRouteBean();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            RouteStationDateExtractor extractor = new RouteStationDateExtractor();
            preparedStatement = connection.prepareStatement(FIND_STATION_DATE_BY_ROUTE_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                bean = extractor.mapEntity(resultSet);
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
        return bean;
    }

    @Override
    public boolean update(TrainRouteBean entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    private static class TrainRouteExtractor implements EntityMapper<TrainRouteBean> {

        @Override
        public TrainRouteBean mapEntity(ResultSet resultSet) {
            try {
                TrainRouteBean bean = new TrainRouteBean();
                bean.setTrainId(resultSet.getInt(1));
                bean.setInitStation(resultSet.getString(2));
                bean.setDepartTime(resultSet.getTimestamp(3));
                bean.setArrivalStation(resultSet.getString(4));
                bean.setArrivalTime(resultSet.getTimestamp(5));
                bean.setWayTime(resultSet.getDouble(6));
                bean.setRouteId(resultSet.getInt(7));
                bean.setCoupe(resultSet.getInt(8));
                bean.setReservedSeat(resultSet.getInt(9));
                bean.setCommon(resultSet.getInt(10));
                return bean;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private static class RouteStationDateExtractor implements EntityMapper<TrainRouteBean> {

        @Override
        public TrainRouteBean mapEntity(ResultSet resultSet) {
            try {
                TrainRouteBean bean = new TrainRouteBean();
                bean.setInitStation(resultSet.getString(1));
                bean.setDepartTime(resultSet.getTimestamp(2));
                bean.setArrivalStation(resultSet.getString(3));
                bean.setArrivalTime(resultSet.getTimestamp(4));
                return bean;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
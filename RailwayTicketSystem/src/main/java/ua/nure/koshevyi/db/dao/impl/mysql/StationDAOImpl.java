package ua.nure.koshevyi.db.dao.impl.mysql;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.db.connection.ConnectionManager;
import ua.nure.koshevyi.db.dao.StationDAO;
import ua.nure.koshevyi.db.entity.Station;
import ua.nure.koshevyi.util.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StationDAOImpl implements StationDAO {

    private static final Logger LOGGER = Logger.getLogger(StationDAOImpl.class);

    private static final String GET_ALL_STATIONS_NAMES = "SELECT name FROM stations";
    private static final String GET_ALL_STATIONS = "SELECT * FROM stations";
    private static final String CREATE_STATION = "INSERT INTO stations (name, city, region, country) " +
            "VALUES (?, ?, ?, ?)";
    private static final String DELETE_STATION = "DELETE FROM stations WHERE id = ?";
    private static final String UPDATE_STATION = "UPDATE stations SET name = ?, city = ?, region = ?, " +
            "country = ? WHERE id = ?";
    private static final String FIND_STATION_BY_ID = "SELECT * FROM stations WHERE id = ?";
    private static final String GET_ALL_STATIONS_ID = "SELECT id FROM stations";
    private static final String GET_ALL_STATIONS_NAMES_ADN_ID = "SELECT name, id FROM stations";

    @Override
    public List<String> getAllStationsNames() {
        List<String> stations = new ArrayList<>();

        Connection connection = null;
        Statement statement;
        ResultSet resultSet;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(GET_ALL_STATIONS_NAMES);
            while (resultSet.next()) {
                stations.add(resultSet.getString("name"));
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
        return stations;
    }

    @Override
    public List<Station> getStationByName() {
        return null;
    }

    @Override
    public List<Station> getAllStations() {
        List<Station> stations = new ArrayList<>();

        Connection connection = null;
        Statement statement;
        ResultSet resultSet;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            StationExtractor extractor = new StationExtractor();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(GET_ALL_STATIONS);
            while (resultSet.next()) {
                stations.add(extractor.mapEntity(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            ConnectionManager.getInstance().rollbackAndClose(connection);
            e.printStackTrace();
        } finally {
            ConnectionManager.getInstance().commitAndClose(connection);
        }
        return stations;
    }

    @Override
    public List<Integer> getAllStationId() {
        List<Integer> id = new ArrayList<>();

        Connection connection = null;
        Statement statement;
        ResultSet resultSet;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(GET_ALL_STATIONS_ID);
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
    public Map<String, Integer> getAllStationsNamesAndId() {
        Map<String, Integer> map = new HashMap<>();
        Connection connection = null;
        Statement statement;
        ResultSet resultSet;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(GET_ALL_STATIONS_NAMES_ADN_ID);
            while (resultSet.next()) {
                map.put(resultSet.getString("name"), resultSet.getInt("id"));
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
        return map;
    }

    @Override
    public boolean create(Station entity) {
        boolean result = false;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(CREATE_STATION, PreparedStatement.RETURN_GENERATED_KEYS);
            int k = 1;
            preparedStatement.setString(k++, entity.getName());
            preparedStatement.setString(k++, entity.getCity());
            preparedStatement.setString(k++, entity.getRegion());
            preparedStatement.setString(k, entity.getCountry());
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
    public Station read(int id) {
        Station station = new Station();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            StationExtractor extractor = new StationExtractor();
            preparedStatement = connection.prepareStatement(FIND_STATION_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                station = extractor.mapEntity(resultSet);
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
        return station;
    }

    @Override
    public boolean update(Station entity) {
        boolean result = false;

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_STATION);

            int k = 1;
            preparedStatement.setString(k++, entity.getName());
            preparedStatement.setString(k++, entity.getCity());
            preparedStatement.setString(k++, entity.getRegion());
            preparedStatement.setString(k++, entity.getCountry());
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
    public boolean delete(int id) {
        boolean result = false;

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(DELETE_STATION);
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

    private static class StationExtractor implements EntityMapper<Station> {

        @Override
        public Station mapEntity(ResultSet resultSet) {
            try {
                Station station = new Station();
                station.setId(resultSet.getInt(1));
                station.setName(resultSet.getString(2));
                station.setCity(resultSet.getString(3));
                station.setRegion(resultSet.getString(4));
                station.setCountry(resultSet.getString(5));
                return station;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
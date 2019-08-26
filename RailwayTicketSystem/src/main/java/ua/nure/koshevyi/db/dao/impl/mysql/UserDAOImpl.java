package ua.nure.koshevyi.db.dao.impl.mysql;

import java.sql.*;

import org.apache.log4j.Logger;
import ua.nure.koshevyi.db.connection.ConnectionManager;
import ua.nure.koshevyi.db.dao.UserDAO;
import ua.nure.koshevyi.util.EntityMapper;
import ua.nure.koshevyi.db.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class);

    private static final String CREATE_USER = "INSERT INTO users (login, password, first_name, last_name, role_id) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
    private static final String GET_ALL_LOGINS = "SELECT login FROM users";

    @Override
    public boolean isUserExists(int userId) {
        return false;
    }

    @Override
    public User findUserByLogin(String login) {
        User user = new User();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionManager.getInstance().getConnection();
            UserExtractor extractor = new UserExtractor();
            preparedStatement = connection.prepareStatement(FIND_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = extractor.mapEntity(resultSet);
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
        return user;
    }

    @Override
    public List<String> getAllLogins() {
        List<String> logins = new ArrayList<>();

        Connection connection = null;
        Statement statement;
        ResultSet resultSet;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(GET_ALL_LOGINS);
            while (resultSet.next()) {
                logins.add(resultSet.getString("login"));
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
        return logins;
    }

    @Override
    public boolean create(User entity) {
        boolean result = false;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(CREATE_USER, PreparedStatement.RETURN_GENERATED_KEYS);
            int k = 1;
            preparedStatement.setString(k++, entity.getLogin());
            preparedStatement.setString(k++, entity.getPassword());
            preparedStatement.setString(k++, entity.getFirstName());
            preparedStatement.setString(k++, entity.getLastName());
            preparedStatement.setInt(k++, entity.getRoleId());
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
    public User read(int id) {
        User user = new User();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            UserExtractor extractor = new UserExtractor();
            preparedStatement = connection.prepareStatement(FIND_USER_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = extractor.mapEntity(resultSet);
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
        return user;
    }

    @Override
    public boolean update(User entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    private static class UserExtractor implements EntityMapper<User> {

        @Override
        public User mapEntity(ResultSet resultSet) {
            try {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setFirstName(resultSet.getString(4));
                user.setLastName(resultSet.getString(5));
                user.setRoleId(resultSet.getInt(6));
                return user;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
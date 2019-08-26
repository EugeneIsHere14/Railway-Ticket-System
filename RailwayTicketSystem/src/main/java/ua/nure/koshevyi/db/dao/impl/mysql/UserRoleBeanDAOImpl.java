package ua.nure.koshevyi.db.dao.impl.mysql;

import ua.nure.koshevyi.db.connection.ConnectionManager;
import ua.nure.koshevyi.db.dao.UserRoleBeanDAO;
import ua.nure.koshevyi.util.EntityMapper;
import ua.nure.koshevyi.db.bean.UserRoleBean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserRoleBeanDAOImpl implements UserRoleBeanDAO {

    private static final String GET_ALL_USERS_WITH_ROLES = "SELECT users.id, users.login, " +
            "users.first_name, users.last_name, roles.role FROM users JOIN roles ON users.role_id = roles.id";

    @Override
    public List<UserRoleBean> getAllUsersWithRoles() {
        List<UserRoleBean> list = new ArrayList<>();

        Connection connection = null;
        Statement statement;
        ResultSet resultSet;

        try {
            connection = ConnectionManager.getInstance().getConnection();
            UserWithRoleExtractor extractor = new UserWithRoleExtractor();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(GET_ALL_USERS_WITH_ROLES);
            while (resultSet.next()) {
                list.add(extractor.mapEntity(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            ConnectionManager.getInstance().rollbackAndClose(connection);
            e.printStackTrace();
        } finally {
            ConnectionManager.getInstance().commitAndClose(connection);
        }
        return list;
    }

    @Override
    public boolean create(UserRoleBean entity) {
        return false;
    }

    @Override
    public UserRoleBean read(int id) {
        return null;
    }

    @Override
    public boolean update(UserRoleBean entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    private static class UserWithRoleExtractor implements EntityMapper<UserRoleBean> {

        @Override
        public UserRoleBean mapEntity(ResultSet resultSet) {
            try {
                UserRoleBean userRole = new UserRoleBean();
                userRole.setId(resultSet.getInt(1));
                userRole.setLogin(resultSet.getString(2));
                userRole.setFirstName(resultSet.getString(3));
                userRole.setLastName(resultSet.getString(4));
                userRole.setRole(resultSet.getString(5));
                return userRole;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
package ua.nure.koshevyi.db.dao;

import ua.nure.koshevyi.db.entity.User;

import java.util.List;

public interface UserDAO extends GenericDAO<User> {

    boolean isUserExists(int userId);

    User findUserByLogin(String login);

    List<String> getAllLogins();
}
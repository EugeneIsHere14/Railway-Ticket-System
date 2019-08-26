package ua.nure.koshevyi.db.dao;

import ua.nure.koshevyi.db.bean.UserRoleBean;

import java.util.List;

public interface UserRoleBeanDAO extends GenericDAO<UserRoleBean> {

    List<UserRoleBean> getAllUsersWithRoles();
}
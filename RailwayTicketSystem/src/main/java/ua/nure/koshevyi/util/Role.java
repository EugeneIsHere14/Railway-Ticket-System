package ua.nure.koshevyi.util;

import ua.nure.koshevyi.db.entity.User;

import java.io.Serializable;

public enum  Role {

    ADMIN, USER;

    public static Role getRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId-1];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
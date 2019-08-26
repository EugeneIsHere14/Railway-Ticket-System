package ua.nure.koshevyi.util;

import java.sql.ResultSet;

public interface EntityMapper<E> {

    E mapEntity(ResultSet resultSet);
}

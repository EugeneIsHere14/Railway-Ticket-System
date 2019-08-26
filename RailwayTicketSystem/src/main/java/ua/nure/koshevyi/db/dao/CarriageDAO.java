package ua.nure.koshevyi.db.dao;

import ua.nure.koshevyi.db.entity.Carriage;

public interface CarriageDAO extends GenericDAO<Carriage> {

    boolean updateCarriage(Carriage entity, int quantity);
    int findFreeSeatQuanByCarriageId(Carriage entity);
}
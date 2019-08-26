package ua.nure.koshevyi.db.dao;

public interface GenericDAO<E> {

    boolean create(E entity);

    E read(int id);

    boolean update(E entity);

    boolean delete(int id);

}
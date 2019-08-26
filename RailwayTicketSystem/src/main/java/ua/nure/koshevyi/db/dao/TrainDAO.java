package ua.nure.koshevyi.db.dao;

import ua.nure.koshevyi.db.entity.Train;

import java.util.List;

public interface TrainDAO extends GenericDAO<Train> {

    List<Train> getAllTrains();
}
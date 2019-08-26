package ua.nure.koshevyi.db.dao;

import ua.nure.koshevyi.db.bean.TrainRouteBean;
import ua.nure.koshevyi.exception.AppException;

import java.util.List;

public interface TrainRouteBeanDAO extends GenericDAO<TrainRouteBean> {

    List<TrainRouteBean> getAvailableTrainRouteViews(String init, String arrival, String date) throws AppException;
}
package ua.nure.koshevyi.db.dao;

import ua.nure.koshevyi.db.bean.RouteBean;

import java.util.List;

public interface RouteBeanDAO extends GenericDAO<RouteBean> {

    List<RouteBean> getInitArrStations();

    List<RouteBean> getAllRStationsByRouteId(int id);

    int getRoutesCount();
}
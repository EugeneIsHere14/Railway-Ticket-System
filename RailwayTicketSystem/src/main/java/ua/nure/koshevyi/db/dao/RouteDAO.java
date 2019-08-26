package ua.nure.koshevyi.db.dao;

import ua.nure.koshevyi.db.entity.Route;
import ua.nure.koshevyi.db.entity.Station;

import java.util.List;

public interface RouteDAO extends GenericDAO<Route> {

    boolean createNewRoute(Route entity, Station init, Station arrival);

    boolean updateRouteViaStationNames(Route entity, Station init, Station arrival);

    List<Integer> getAllRouteIds();
}
package ua.nure.koshevyi.db.dao;

import ua.nure.koshevyi.db.entity.RouteStation;
import ua.nure.koshevyi.db.entity.Station;

import java.util.List;

public interface RouteStationDAO extends GenericDAO<RouteStation> {

    boolean createNewRouteStation(RouteStation entity, Station station);

    List<String> getAllRouteStationNames(int routeId);

    boolean updateRouteStationViaStationName(RouteStation entity, Station station);
}
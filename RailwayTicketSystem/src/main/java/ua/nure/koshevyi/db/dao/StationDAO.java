package ua.nure.koshevyi.db.dao;

import ua.nure.koshevyi.db.entity.Station;

import java.util.List;
import java.util.Map;

public interface StationDAO extends GenericDAO<Station> {

    List<String> getAllStationsNames();

    List<Station> getStationByName();

    List<Station> getAllStations();

    List<Integer> getAllStationId();

    Map<String, Integer> getAllStationsNamesAndId();
}
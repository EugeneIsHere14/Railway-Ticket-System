package ua.nure.koshevyi.db.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class RouteStation implements Serializable {

    private static final long serialVersionUID = 7932612449325450699L;

    private int id;
    private int routeId;
    private int stationId;
    private Timestamp arrivalTime;
    private Timestamp departTime;
    private int waitTime;

    public RouteStation() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Timestamp arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Timestamp getDepartTime() {
        return departTime;
    }

    public void setDepartTime(Timestamp departTime) {
        this.departTime = departTime;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteStation that = (RouteStation) o;
        return routeId == that.routeId &&
                stationId == that.stationId &&
                arrivalTime == that.arrivalTime &&
                departTime == that.departTime &&
                waitTime == that.waitTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(routeId, stationId, arrivalTime, departTime, waitTime);
    }

    @Override
    public String toString() {
        return "RouteStation{" +
                "id=" + id +
                ", routeId=" + routeId +
                ", stationId=" + stationId +
                ", arrivalTime=" + arrivalTime +
                ", departTime=" + departTime +
                ", waitTime=" + waitTime +
                '}';
    }
}
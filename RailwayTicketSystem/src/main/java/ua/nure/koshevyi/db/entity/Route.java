package ua.nure.koshevyi.db.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class Route implements Serializable {

    private static final long serialVersionUID = -3428821417861852826L;

    private int id;
    private int initStationId;
    private Timestamp departTime;
    private int arrivalStationId;
    private Timestamp arrivalTime;

    public Route() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInitStationId() {
        return initStationId;
    }

    public void setInitStationId(int initStationId) {
        this.initStationId = initStationId;
    }

    public Timestamp getDepartTime() {
        return departTime;
    }

    public void setDepartTime(Timestamp departTime) {
        this.departTime = departTime;
    }

    public int getArrivalStationId() {
        return arrivalStationId;
    }

    public void setArrivalStationId(int arrivalStationId) {
        this.arrivalStationId = arrivalStationId;
    }

    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Timestamp arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return id == route.id &&
                initStationId == route.initStationId &&
                departTime == route.departTime &&
                arrivalStationId == route.arrivalStationId &&
                arrivalTime == route.arrivalTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, initStationId, departTime, arrivalStationId, arrivalTime);
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", initStationId=" + initStationId +
                ", departTime=" + departTime +
                ", arrivalStationId=" + arrivalStationId +
                ", arrivalTime=" + arrivalTime +
                '}';
    }
}
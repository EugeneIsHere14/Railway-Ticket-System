package ua.nure.koshevyi.db.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class TrainRouteBean implements Serializable {

    private static final long serialVersionUID = -7630125775013850473L;

    private int trainId;
    private int routeId;
    private int coupe;
    private int reservedSeat;
    private int common;
    private String initStation;
    private Timestamp departTime;
    private String arrivalStation;
    private Timestamp arrivalTime;
    private double wayTime;
    private int typeId;

    public TrainRouteBean(){}

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getCoupe() {
        return coupe;
    }

    public void setCoupe(int coupe) {
        this.coupe = coupe;
    }

    public int getReservedSeat() {
        return reservedSeat;
    }

    public void setReservedSeat(int reservedSeat) {
        this.reservedSeat = reservedSeat;
    }

    public int getCommon() {
        return common;
    }

    public void setCommon(int common) {
        this.common = common;
    }

    public String getInitStation() {
        return initStation;
    }

    public void setInitStation(String initStation) {
        this.initStation = initStation;
    }

    public Timestamp getDepartTime() {
        return departTime;
    }

    public void setDepartTime(Timestamp departTime) {
        this.departTime = departTime;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Timestamp arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getWayTime() {
        return wayTime;
    }

    public void setWayTime(double wayTime) {
        this.wayTime = wayTime;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainRouteBean that = (TrainRouteBean) o;
        return trainId == that.trainId &&
                routeId == that.routeId &&
                coupe == that.coupe &&
                reservedSeat == that.reservedSeat &&
                common == that.common &&
                Double.compare(that.wayTime, wayTime) == 0 &&
                Objects.equals(initStation, that.initStation) &&
                Objects.equals(departTime, that.departTime) &&
                Objects.equals(arrivalStation, that.arrivalStation) &&
                Objects.equals(arrivalTime, that.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainId, routeId, coupe, reservedSeat, common, initStation, departTime, arrivalStation, arrivalTime, wayTime);
    }

    @Override
    public String toString() {
        return "TrainRouteBean{" +
                "trainId=" + trainId +
                ", routeId=" + routeId +
                ", coupe=" + coupe +
                ", reservedSeat=" + reservedSeat +
                ", common=" + common +
                ", initStation='" + initStation + '\'' +
                ", departTime=" + departTime +
                ", arrivalStation='" + arrivalStation + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", wayTime=" + wayTime +
                '}';
    }
}
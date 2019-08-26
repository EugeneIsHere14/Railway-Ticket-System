package ua.nure.koshevyi.db.bean;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class RouteBean implements Serializable {

    private static final long serialVersionUID = 7671898420274161049L;

    private int id;
    private int routeStationId;
    private String initStation;
    private Timestamp departTime;
    private String arrivalStation;
    private Timestamp arrivalTime;
    private String routeStation;
    private Timestamp routeStationArrival;
    private int waitTime;
    private Timestamp routeStationDepart;

    public RouteBean() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRouteStationId() {
        return routeStationId;
    }

    public void setRouteStationId(int routeStationId) {
        this.routeStationId = routeStationId;
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

    public String getRouteStation() {
        return routeStation;
    }

    public void setRouteStation(String routeStation) {
        this.routeStation = routeStation;
    }

    public Timestamp getRouteStationArrival() {
        return routeStationArrival;
    }

    public void setRouteStationArrival(Timestamp routeStationArrival) {
        this.routeStationArrival = routeStationArrival;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public Timestamp getRouteStationDepart() {
        return routeStationDepart;
    }

    public void setRouteStationDepart(Timestamp routeStationDepart) {
        this.routeStationDepart = routeStationDepart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteBean routeBean = (RouteBean) o;
        return id == routeBean.id &&
                waitTime == routeBean.waitTime &&
                Objects.equals(initStation, routeBean.initStation) &&
                Objects.equals(departTime, routeBean.departTime) &&
                Objects.equals(arrivalStation, routeBean.arrivalStation) &&
                Objects.equals(arrivalTime, routeBean.arrivalTime) &&
                Objects.equals(routeStation, routeBean.routeStation) &&
                Objects.equals(routeStationArrival, routeBean.routeStationArrival) &&
                Objects.equals(routeStationDepart, routeBean.routeStationDepart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, initStation, departTime, arrivalStation, arrivalTime, routeStation, routeStationArrival, waitTime, routeStationDepart);
    }

    @Override
    public String toString() {
        return "RouteBean{" +
                "id=" + id +
                ", initStation='" + initStation + '\'' +
                ", departTime=" + departTime +
                ", arrivalStation='" + arrivalStation + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", routeStation='" + routeStation + '\'' +
                ", routeStationArrival=" + routeStationArrival +
                ", waitTime=" + waitTime +
                ", routeStationDepart=" + routeStationDepart +
                '}';
    }
}
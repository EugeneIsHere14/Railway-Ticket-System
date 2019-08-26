package ua.nure.koshevyi.db.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class TicketTrainRouteBean implements Serializable {

    private static final long serialVersionUID = 7615231928892865730L;

    private int ticketId;
    private int carriageId;
    private String initStation;
    private Timestamp departDate;
    private String arrivalStation;
    private Timestamp arrivalDate;
    private int trainId;
    private int freeSeats;
    private String firstName;
    private String lastName;
    private String carriageType;
    private double price;
    private double sumTicketPrice;
    private int routeId;


    public TicketTrainRouteBean() {}

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getCarriageId() {
        return carriageId;
    }

    public void setCarriageId(int carriageId) {
        this.carriageId = carriageId;
    }

    public String getInitStation() {
        return initStation;
    }

    public void setInitStation(String initStation) {
        this.initStation = initStation;
    }

    public Timestamp getDepartDate() {
        return departDate;
    }

    public void setDepartDate(Timestamp departDate) {
        this.departDate = departDate;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public Timestamp getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Timestamp arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public int getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCarriageType() {
        return carriageType;
    }

    public void setCarriageType(String carriageType) {
        this.carriageType = carriageType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSumTicketPrice() {
        return sumTicketPrice;
    }

    public void setSumTicketPrice(double sumTicketPrice) {
        this.sumTicketPrice = sumTicketPrice;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketTrainRouteBean that = (TicketTrainRouteBean) o;
        return ticketId == that.ticketId &&
                trainId == that.trainId &&
                Double.compare(that.price, price) == 0 &&
                Objects.equals(initStation, that.initStation) &&
                Objects.equals(departDate, that.departDate) &&
                Objects.equals(arrivalStation, that.arrivalStation) &&
                Objects.equals(arrivalDate, that.arrivalDate) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(carriageType, that.carriageType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, initStation, departDate, arrivalStation, arrivalDate,
                trainId, firstName, lastName, carriageType, price);
    }

    @Override
    public String toString() {
        return "TicketTrainRouteBean{" +
                "ticketId=" + ticketId +
                ", initStation='" + initStation + '\'' +
                ", departDate=" + departDate +
                ", arrivalStation='" + arrivalStation + '\'' +
                ", arrivalDate=" + arrivalDate +
                ", trainId=" + trainId +
                ", freeSeats=" + freeSeats +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", carriageType='" + carriageType + '\'' +
                ", price=" + price +
                '}';
    }
}
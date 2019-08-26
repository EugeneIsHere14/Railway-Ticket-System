package ua.nure.koshevyi.db.entity;

import java.io.Serializable;
import java.util.Objects;

public class Ticket implements Serializable {

    private static final long serialVersionUID = -432105661030036309L;

    private int id;
    private int userId;
    private int trainId;
    private int routeId;
    private int typeId;
    private int carriageId;
    private double totalPrice;

    public Ticket() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public int getCarriageId() {
        return carriageId;
    }

    public void setCarriageId(int carriageId) {
        this.carriageId = carriageId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id &&
                userId == ticket.userId &&
                trainId == ticket.trainId &&
                carriageId == ticket.carriageId &&
                routeId == ticket.routeId &&
                Double.compare(ticket.totalPrice, totalPrice) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, trainId, carriageId, routeId, totalPrice);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", userId=" + userId +
                ", trainId=" + trainId +
                ", carriageId=" + carriageId +
                ", routeId=" + routeId +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
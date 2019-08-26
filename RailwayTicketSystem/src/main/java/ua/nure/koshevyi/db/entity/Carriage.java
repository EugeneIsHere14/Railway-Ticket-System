package ua.nure.koshevyi.db.entity;

import java.io.Serializable;
import java.util.Objects;

public class Carriage implements Serializable {

    private static final long serialVersionUID = 3745145549197320046L;

    private int id;
    private int typeId;
    private int freeSeats;
    private int trainId;
    private double price;

    public Carriage() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carriage carriage = (Carriage) o;
        return id == carriage.id &&
                typeId == carriage.typeId &&
                freeSeats == carriage.freeSeats &&
                trainId == carriage.trainId &&
                Double.compare(carriage.price, price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeId, freeSeats, trainId, price);
    }

    @Override
    public String toString() {
        return "Carriage{" +
                "id=" + id +
                ", typeId=" + typeId +
                ", freeSeats=" + freeSeats +
                ", trainId=" + trainId +
                ", price=" + price +
                '}';
    }
}
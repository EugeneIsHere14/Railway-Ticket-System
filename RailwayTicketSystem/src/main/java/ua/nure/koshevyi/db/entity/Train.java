package ua.nure.koshevyi.db.entity;

import java.io.Serializable;
import java.util.Objects;

public class Train implements Serializable {

    private static final long serialVersionUID = 328941078135679620L;

    private int id;
    private int routeId;

    public Train() {}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Train train = (Train) o;
        return id == train.id &&
                routeId == train.routeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, routeId);
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", routeId=" + routeId +
                '}';
    }
}
package ua.nure.koshevyi.db.entity;

import java.io.Serializable;
import java.util.Objects;

public class Station implements Serializable {

    private static final long serialVersionUID = -9220610047458490720L;

    private int id;
    private String name;
    private String city;
    private String region;
    private String country;

    public Station() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return id == station.id &&
                Objects.equals(name, station.name) &&
                Objects.equals(city, station.city) &&
                Objects.equals(region, station.region) &&
                Objects.equals(country, station.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, city, region, country);
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
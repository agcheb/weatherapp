package com.agcheb.weatherapp;

/**
 * Created by agcheb on 27.12.17.
 */

class City {
    private long id;
    private String city;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String toString(){return city;}
}

package com.izelozarslan.weatherapp.openweathermap;

import java.util.ArrayList;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {

    public int cnt;

    public ArrayList<List> list;

    public City city;

    public String cod;

    public String message;

    @Getter
    @Setter
    public static class City{
        public int id;
        public String name;
        public Coord coord;
        public String country;
        public int population;
        public int timezone;
        public int sunrise;
        public int sunset;
    }


    @Getter
    @Setter
    public static class List{
        public int dt;
        public Main main;
        public ArrayList<Weather> weather;
        public Clouds clouds;
        public Wind wind;
        public int visibility;
        public double pop;
        public Rain rain;
        public Sys sys;
        public String dt_txt;
    }


    @Getter
    @Setter
    public static class Coord{
        public double lat;
        public double lon;
    }

    @Getter
    @Setter
    public static class Main{
        public double temp;
        public double feels_like;
        public double temp_min;
        public double temp_max;
        public int pressure;
        public int sea_level;
        public int grnd_level;
        public int humidity;
        public double temp_kf;
    }

    @Getter
    @Setter
    public static class Weather{
        public int id;
        public String main;
        public String description;
        public String icon;
    }


    @Getter
    @Setter
    public static class Clouds{
        public int all;
    }


    @Getter
    @Setter
    public static class Wind{
        public double speed;
        public int deg;
        public double gust;
    }


    @Getter
    @Setter
    public static class Rain{
        @JsonProperty("3h")
        public double _3h;
    }


    @Getter
    @Setter
    public static class Sys{
        public String pod;

}
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherData that = (WeatherData) o;
        return cnt == that.cnt &&
                Objects.equals(list, that.list) &&
                Objects.equals(city, that.city) &&
                Objects.equals(cod, that.cod) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cnt, list, city, cod, message);
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "cnt=" + cnt +
                ", list=" + list +
                ", city=" + city +
                ", cod='" + cod + '\'' +
                 ", message='" + message + '\'' +
        '}';
    }

}

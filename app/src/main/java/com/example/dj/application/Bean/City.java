package com.example.dj.application.Bean;

/**
 * Created by DJ on 2015/4/22.
 */
public class City {
    private String province;
    private String city;
    private String number;
    private String allfirstpy;

    public String getFirstpy() {
        return firstpy;
    }

    public void setFirstpy(String firstpy) {
        this.firstpy = firstpy;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAllfirstpy() {
        return allfirstpy;
    }

    public void setAllfirstpy(String allfirstpy) {
        this.allfirstpy = allfirstpy;
    }

    public String getAllpy() {
        return allpy;
    }

    public void setAllpy(String allpy) {
        this.allpy = allpy;
    }

    private String firstpy;
    private String allpy;

    public City(String province, String city, String number, String allpy, String allfirstpy, String firstpy) {
        this.province = province;
        this.city = city;
        this.number = number;
        this.allpy = allpy;
        this.allfirstpy = allfirstpy;
        this.firstpy = firstpy;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}

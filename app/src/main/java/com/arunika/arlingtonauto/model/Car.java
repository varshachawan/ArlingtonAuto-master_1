package com.arunika.arlingtonauto.model;
import java.io.Serializable;

public class Car implements Serializable {

    private static final long serialVersionUID = 2L;

    private int id;
    private String name;
    private int capacity;
    private double weekdayRate;
    private double weekendRate;
    private double weeklyRate;
    private double dailyGps;
    private double dailyOnstar;
    private double dailySirius;

    public int getId() { return id; }
    public void setId(int id) { this.id=id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public double getWeekdayRate() {return weekdayRate;}
    public void setWeekdayRate(double weekdayRate) {this.weekdayRate = weekdayRate;}

    public double getWeekendRate() {return weekendRate;}
    public void setWeekendRate(double weekendRate) {this.weekendRate = weekendRate;}

    public double getWeeklyRate() {return weeklyRate;}
    public void setWeeklyRate(double weeklyRate) {this.weeklyRate = weeklyRate;}

    public double getDailyGps() {return dailyGps;}
    public void setDailyGps(double dailyGps) {this.dailyGps = dailyGps;}

    public double getDailyOnstar() {return dailyOnstar;}
    public void setDailyOnstar(double dailyOnstar) {this.dailyOnstar = dailyOnstar;}

    public double getDailySirius() {return dailySirius;}
    public void setDailySirius(double dailySirius) {this.dailySirius = dailySirius;}

}

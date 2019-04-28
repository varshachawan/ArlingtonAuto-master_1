package com.arunika.arlingtonauto.model;

import android.annotation.TargetApi;
import android.os.Build;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Reservation implements Serializable {

    private static final long serialVersionUID = 3L;
    private int id;
    private int carId;
    private int userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int hasGps;
    private int hasOnstar;
    private int hasSirius;
    private double totalPrice;
    private String startTimeAsString;
    private String endTimeAsString;
    private String additionalFeatures;

    public String getAdditionalFeatures() {
        additionalFeatures="";
        if(hasGps==1)
            additionalFeatures += "Camera| ";
        if(hasOnstar==1)
            additionalFeatures += "Cart | ";
        if(hasSirius==1)
            additionalFeatures += "History | ";
        return additionalFeatures;
    }

    public void setStartTimeAsString(String startTime) {
        this.startTimeAsString=startTime;
    }
    public String getStartTimeAsString() {
        return startTimeAsString;
    }
    public void setEndTimeAsString(String endTime) {
        this.endTimeAsString=endTime;
    }
    public String getEndTimeAsString() {
        return endTimeAsString;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
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
    public int getCarId(){
        return carId;
    }
    public void setCarId(int carId){
        this.carId=carId;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime=startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime=endTime;
    }
    public int getHasGps() {
        return hasGps;
    }
    public void setHasGps(int hasGps){
        this.hasGps=hasGps;
    }
    public int getHasOnstar() {
        return hasOnstar;
    }
    public void setHasOnstar(int hasOnstar){
        this.hasOnstar=hasOnstar;
    }
    public int getHasSirius() {
        return hasSirius;
    }
    public void setHasSirius(int hasSirius){
        this.hasSirius=hasSirius;
    }

    public void setStartAndEndTimes(String startTimeAsString, String endTimeAsString) {
        this.startTimeAsString = startTimeAsString;
        this.endTimeAsString = endTimeAsString;
        this.startTime = convertStringToDate(startTimeAsString);
        this.endTime = convertStringToDate(endTimeAsString);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private LocalDateTime convertStringToDate(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(string, formatter);
        return dateTime;
    }

    //without add-ons, tax, discount
    @TargetApi(Build.VERSION_CODES.O)
    public double calculateTotalPrice (Car car) {
        double price = 0;
        int days = daysBetween(startTime,endTime);
        if(days<1) //less than 24hr rental
            days=1; //charge for full day
        int numOfWeeks = 0;
        numOfWeeks = days/7;
        days = days - (7*numOfWeeks);
        //apply weekly (if any)
        price += numOfWeeks * car.getWeeklyRate();

        LocalDateTime eachDay = startTime;
        for(int i=1; i<=days; i++) {
            if(eachDay.toLocalDate().getDayOfWeek().name().equals("SATURDAY") ||
                    eachDay.toLocalDate().getDayOfWeek().name().equals("SUNDAY")) //weekend
                price += car.getWeekendRate();
            else //weekday
                price += car.getWeekdayRate();
            // move to next day
            eachDay = LocalDateTime.from(startTime.plusDays(i));
        }
        //truncate to 2 dp
        price = Math.floor(price * 100) / 100;
        return price;
    }

    //with add-ons,tax,discount
    public double calculateFinalPrice (Car car, int applyDiscount) {
        int days = daysBetween(startTime,endTime);
        double discount=0;
        //raw price from car rates
        double finalPrice = calculateTotalPrice(car);
        //add add-on prices
        if(hasGps==1)
            finalPrice += (days * car.getDailyGps());
        if(hasOnstar==1)
            finalPrice += (days * car.getDailyOnstar());
        if(hasSirius==1)
            finalPrice += (days * car.getDailySirius());
        //apply discount
        if(applyDiscount==1) {
            discount = (0.10 * finalPrice);
            finalPrice -= discount;
        }
        //add 8.25% tax
        double tax = (0.0825 * finalPrice);
        finalPrice += tax;
        //truncate to 2dp
        finalPrice = Math.floor(finalPrice * 100) / 100;
        return finalPrice;
    }

    @TargetApi(Build.VERSION_CODES.O)
    private int daysBetween(LocalDateTime start, LocalDateTime end) {
        double secondsBetween = ChronoUnit.SECONDS.between(start, end);
        double daysBetween = secondsBetween/86400.00; //IF EVEN 1 SEC MORE THAN 24 HOURS, count as 2 days
        return (int) Math.ceil(daysBetween);
    }
}
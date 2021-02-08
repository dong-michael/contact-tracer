package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class Person {

    private String name;
    private String phoneNumber;
    private LocalDateTime currentDate;
    private Boolean status;



    public Person(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.currentDate = LocalDateTime.now();
        this.status = false;

    }

    //getters

    public String getName() {
        return this.name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public boolean getStatus() {
        return status;
    }

    public LocalDateTime getDate() {
        return this.currentDate;
    }

    public String getTime() {
        LocalTime time = this.currentDate.toLocalTime();
        return String.valueOf(time.getHour()) + ":" + String.valueOf(time.getMinute());
    }


    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: sets the time of the person to timeString, in HH/mm
    public void setTime(String timeString) {
        LocalTime localTime = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
        LocalDate localDate = this.currentDate.toLocalDate();
        currentDate = localDate.atTime(localTime);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: sets the date of visit in format dd/MM/yyyy
    public void setDate(String dateString) {
        LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalTime time = this.currentDate.toLocalTime();
        currentDate = localDate.atTime(time);
    }

    //MODIFIES: this
    //EFFECTS: sets the status of the person to positive for COVID
    public void setStatusPositive() {
        this.status = true;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: sets the time of the person visited, in 24 hour format
    public void setStatusNegative() {
        this.status = false;
    }





}

package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/*
This Person class is designed to represent each individual, that the user wishes to record or keep track of.
It allows the user to create a person with name, phone number, status, along with the local time and date of when
the individual arrived.
*/

public class Person {

    private String name;
    private String phoneNumber;
    private LocalDateTime currentDate;
    private Boolean status;  //positive or negative for COVID19


    //CONSTRUCTOR
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

    //REQUIRES: person != null
    //EFFECTS: returns the recorded time of the person as a string in format HH:mm
    public String getTime() {
        LocalTime time = this.currentDate.toLocalTime();
        return String.valueOf(time.getHour()) + ":" + String.valueOf(time.getMinute());
    }


    //REQUIRES: string entered must be in format "HH:mm"
    //MODIFIES: this
    //EFFECTS: sets the time of the person to timeString, in HH/mm in a 24 hour format.
    public void setTime(String timeString) {
        LocalTime localTime = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
        LocalDate localDate = this.currentDate.toLocalDate();
        currentDate = localDate.atTime(localTime);
    }


    //REQUIRES: date entered must be in format dd/MM/yyyy
    //MODIFIES: this
    //EFFECTS: sets the date of visit in format dd/MM/yyyy
    public void setDate(String dateString) {
        LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalTime time = this.currentDate.toLocalTime();
        currentDate = localDate.atTime(time);
    }


    //MODIFIES: this
    //EFFECTS: sets the status of the person to positive for COVID19
    public void setStatusPositive() {
        this.status = true;
    }


    //MODIFIES: this
    //EFFECTS: sets the status of the person to negative for COVID19
    public void setStatusNegative() {
        this.status = false;
    }


}

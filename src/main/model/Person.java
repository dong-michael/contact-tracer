package model;

import exception.InvalidInputFormatException;
import org.json.JSONObject;
import persistence.WriteableJ;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;


/*
This Person class is designed to represent each individual, that the user wishes to record or keep track of.
It allows the user to create a person with name, phone number, status, along with the local time and date of when
the individual arrived.
*/

public class Person implements WriteableJ {

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        String formatTime = time.format(formatter);
        return formatTime;

    }



    //MODIFIES: this
    //EFFECTS: throws InvalidInputFormatException if timeString is not in HH/mm, otherwise
    // sets the time of the person to timeString
    public void setTime(String timeString) throws InvalidInputFormatException {

        if (!timeString.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
            throw new InvalidInputFormatException("Please enter the information in the correct format.");
        }

        LocalTime localTime = LocalTime.parse(timeString, ofPattern("HH:mm"));
        LocalDate localDate = this.currentDate.toLocalDate();
        currentDate = localDate.atTime(localTime);
    }



    //MODIFIES: this
    //EFFECTS: throws InvalidInputFormatException if dateString is not in format dd/MM/yyyy,
    // otherwise sets the date of visit to dateString
    public void setDate(String dateString) throws InvalidInputFormatException {

        if (!dateString.matches("^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$")) {
            throw new InvalidInputFormatException("Please enter the information in the correct format.");
        }
        LocalDate localDate = LocalDate.parse(dateString, ofPattern("dd/MM/yyyy"));
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


    //modifies: this
    //EFFECTS: sets the name of the person
    public void setName(String name) {
        this.name = name;
    }


    //modifies: this
    //EFFECTS: throws InvalidInputFormatException if number is not of format NNNNNNNNN,
    // otherwise sets the phonenumber of the person to number
    public void setPhoneNumber(String number) throws InvalidInputFormatException {

        if (!number.matches("^\\d{10}$")) {
            throw new InvalidInputFormatException("Please enter the information in the correct format.");
        }

        this.phoneNumber = number;
    }


    @Override
    //EFFECTS: creates a new JSONObject, and stores all fields within the object
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        LocalTime time2 = currentDate.toLocalTime();
        LocalDate date2 = currentDate.toLocalDate();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String timeFormated = time2.format(formatter);
        String dateFormated = date2.format(formatterDate);

        jsonObject.put("name", name);
        jsonObject.put("phoneNumber", phoneNumber);
        jsonObject.put("time", timeFormated);
        jsonObject.put("date", dateFormated);
        jsonObject.put("status", status);

        return jsonObject;

    }
}

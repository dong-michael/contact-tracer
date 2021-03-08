package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.WriteableJ;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/*
This class is designed to store all of the people that have been recorded by the user. It includes methods that is only,
related to this VisitorsList class such as the removal or addition of a Person to this list.
*/
public class VisitorsList implements WriteableJ {

    private List<Person> tracking;


    //CONSTRUCTOR
    public VisitorsList() {
        tracking = new ArrayList<>();
    }


    //REQUIRES: Person must be in VisitorsList
    //EFFECTS: returns the name of the person in the list, null otherwise
    public Person getPerson(String name) {
        for (Person p : tracking) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    //REQUIRES: requires Person to be in the list
    //MODIFIES: this
    //EFFECTS: removes person from visitors list
    public void removePerson(Person person) {
        tracking.remove(person);
    }


    //REQUIRES: person != null
    //MODIFIES: this
    //EFFECTS: adds the person to visitor's list
    public void addPerson(Person person) {
        tracking.add(person);
    }


    //EFFECTS: returns true if Person is in list, false otherwise
    public boolean containsPerson(Person person) {
        return tracking.contains(person);
    }


    //EFFECTS: returns the number of persons in the list
    public int getSize() {
        return tracking.size();
    }


    //REQUIRES: date must be a valid day in format dd/MM/yyyy
    //MODIFIES: this
    //EFFECTS: returns a list of all the people who visited on date entered
    public List<Person> getVisitorsOnDay(String date) {
        List<Person> seenBefore = new ArrayList<>();

        LocalDate referenceDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        for (Person p : tracking) {
            if (p.getDate().toLocalDate().isEqual(referenceDate)) {
                seenBefore.add(p);
            }
        }
        return seenBefore;
    }


    //MODIFIES: this
    //EFFECTS: returns a list of people whose status is positive
    public List<Person> getPositivePersons() {
        List<Person> positive = new ArrayList<>();

        for (Person p : tracking) {
            if (p.getStatus()) {
                positive.add(p);
            }
        }
        return positive;
    }


    //REQUIRES: time must be entered in format HH:mm in 24 hour format
    //MODIFIES: this
    //EFFECTS: returns a list of all people who were present on dateString within (timeString - 2) and (timeString + 2)
    public List<Person> getVisitorsAtTime(String dateString, String timeString) {
        List<Person> exposed = new ArrayList<>();

        LocalDate referenceDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalTime referenceTime = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));

        LocalDateTime dt = LocalDateTime.of(referenceDate, referenceTime);

        LocalDateTime upperBound = dt.plusHours(1);
        LocalDateTime lowerBound = dt.minusHours(1);

        for (Person p : tracking) {
            if ((p.getDate().isAfter(lowerBound) || p.getDate().isEqual(lowerBound))) {
                if ((p.getDate().isBefore(upperBound) || p.getDate().isEqual(upperBound))) {
                    exposed.add(p);
                }
            }

        }
        return exposed;
    }


    //MODIFIES: this
    //EFFECTS: returns a list of all the names of all people who have stored
    public List<String> getAllNames() {
        List<String> allNames = new ArrayList<>();

        for (Person p : tracking) {
            allNames.add(p.getName());
        }
        return allNames;
    }


    @Override
    //EFFECTS: creates new JSON object and adds contents of tracking to it
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("tracking", trackingToJson());
        return json;
    }

    // EFFECTS: returns things in this visitorslist as a JSON array
    private JSONArray trackingToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Person p : tracking) {
            jsonArray.put(p.toJson());
        }
        return jsonArray;
    }


    //EFFECTS: returns a list of all persons
    public List<Person> getAllPersons() {
        return tracking;
    }
}




package model;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class VisitorsList {

    private List<Person> tracking;


    //CONSTRUCTOR
    public VisitorsList() {
        tracking = new ArrayList<Person>();
    }


    //REQUIRES: requires Person to be present in list
    //MODIFIES: this
    //EFFECTS: removes visitor from visitors list
    public void removePerson(Person person) {
        tracking.remove(person);
    }


    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: adds a person to visitors list
    public void addPerson(Person person) {
        tracking.add(person);
    }


    public boolean containsPerson(Person person) {
        return tracking.contains(person);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: returns the of persons in the VisitorsList
    public int getSize() {
        return tracking.size();
    }


    //REQUIRES:
    //MODIFIES:
    //EFFECTS: returns a list of all the people who visited on that day
    public VisitorsList getVisitorsOnDay(String date) {
        VisitorsList seenBefore = new VisitorsList();

        LocalDate referenceDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        for (Person p : tracking) {
            if (p.getDate().toLocalDate().isEqual(referenceDate)) {
                seenBefore.addPerson(p);
            }
        }
        return seenBefore;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: returns a list of people who are whose status is positive
    public VisitorsList getPositivePersons() {
        VisitorsList positive = new VisitorsList();

        for (Person p : tracking) {
            if (p.getStatus()) {
                positive.addPerson(p);
            }
        }
        return positive;
    }


    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: returns a list of all the people who were present on dateString within hour - (hour + 2)
    public VisitorsList getVisitorsAtTime(String dateString, String timeString) {
        VisitorsList exposed = new VisitorsList();

        LocalDate referenceDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalTime referenceTime = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime timeplustwo = referenceTime.plusHours(2);

        for (Person p : tracking) {
            if (p.getDate().toLocalDate().isEqual(referenceDate)
                    && (p.getDate().toLocalTime().equals(referenceTime)
                    || p.getDate().toLocalTime().isAfter(referenceTime))
                    && p.getDate().toLocalTime().isBefore(timeplustwo)) {
                exposed.addPerson(p);
            }
        }
        return exposed;
    }


}

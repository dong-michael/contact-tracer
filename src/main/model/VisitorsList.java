package model;


import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    //REQUIRES: person to be in VisitorsList
    //MODIFIES: this
    //EFFECTS: returns the person in the list, void otherwise
    public Person getPerson(String name) {
        for (Person p : tracking) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
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

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: returns a list of people who are whose status is positive
    public List<Person> getPositivePersons() {
        List<Person> positive = new ArrayList<>();

        for (Person p : tracking) {
            if (p.getStatus()) {
                positive.add(p);
            }
        }
        return positive;
    }


    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: returns a list of all the people who were present on dateString within (timeString - 2) and (timeString + 2)
    public List<Person> getVisitorsAtTime(String dateString, String timeString) {
        List<Person> exposed = new ArrayList<>();

        LocalDate referenceDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalTime referenceTime = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));

        LocalDateTime dt = LocalDateTime.of(referenceDate, referenceTime);

        LocalDateTime upperBound = dt.plusHours(1);
        LocalDateTime lowerBound = dt.minusHours(1);

        for (Person p : tracking) {
            if ((p.getDate().isAfter(lowerBound) || p.getDate().isEqual(lowerBound))
                    && ((p.getDate().isBefore(upperBound) || p.getDate().isEqual(upperBound)))) {
                exposed.add(p);
            }
        }
        return exposed;


//        LocalDate referenceDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//        LocalTime referenceTime = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
//
//        LocalTime upperBound = referenceTime.plusHours(2);
//        LocalTime lowerBound = referenceTime.minusHours(2);
//        for (Person p : tracking) {
//            if (p.getDate().toLocalDate().isEqual(referenceDate) && (p.getDate().toLocalTime().isAfter(lowerBound)
//                    && p.getDate().toLocalTime().isBefore(upperBound))) {
//                exposed.add(p);
//            }
//        }
//        return exposed;
    }


    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: returns a list of all the names of the person who has visited

    public List<String> getAllNames() {
        List<String> allNames = new ArrayList();

        for (Person p : tracking) {
            allNames.add(p.getName());
        }

        return allNames;
    }


}




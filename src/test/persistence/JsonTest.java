package persistence;

import model.Person;
import java.time.Month;
import static org.junit.jupiter.api.Assertions.assertEquals;


// Code taken and modified from JsonSerializationDemo. URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonTest {

    protected void checkPerson(String name, String phoneNumber, String time, int day, Enum<Month> month, int year, Boolean status, Person person) {
        assertEquals(name, person.getName());
        assertEquals(phoneNumber, person.getPhoneNumber());
        assertEquals(time, person.getTime());

        assertEquals(day, person.getDate().getDayOfMonth());
        assertEquals(month, person.getDate().getMonth());
        assertEquals(year, person.getDate().getYear());
        assertEquals(status, person.getStatus());
    }
}


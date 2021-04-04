package persistence;

import exception.InvalidInputFormatException;
import model.Person;
import model.VisitorsList;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Code taken and modified from JsonSerializationDemo. URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonReaderTest extends JsonTest{


    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            VisitorsList vl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        } catch (InvalidInputFormatException e){
            fail();
        }
    }

    @Test
    void testReaderEmptyEmptyVisitorsList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyVisitorsList.json");
        try {
            VisitorsList vl = reader.read();
            assertEquals(0, vl.getAllPersons().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }catch (InvalidInputFormatException e){
            fail("Exception should not have been thrown");
        }
    }


    @Test
    void testReaderVisitorsList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralVisitorsList.json");
        try {

            VisitorsList vl = reader.read();

            List<Person> persons = vl.getAllPersons();
            assertEquals(2, persons.size());

            checkPerson("John Kav", "7782231254","10:00", 25, Month.OCTOBER, 2021,
                    true, persons.get(0));

//            checkPerson("Sam Noei", "6045239283", "22:21", 22, Month.JANUARY, 2020,
//                    false, persons.get(1));

        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (InvalidInputFormatException e){
            //expected
        }
    }

    @Test
    void testReaderVisitorsListExceptionThrown() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralVisitorsList.json");
        try {

            VisitorsList vl = reader.read();

            List<Person> persons = vl.getAllPersons();
            assertEquals(2, persons.size());

            checkPerson("John Kav", "7782231254","10:00", 25, Month.OCTOBER, 2021,
                    true, persons.get(0));

//            checkPerson("Sam Noei", "6045239283", "22:21", 22, Month.JANUARY, 2020,
//                    false, persons.get(1));

        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (InvalidInputFormatException e){
            //Expected
        }
    }
}

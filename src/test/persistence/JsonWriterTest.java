package persistence;

import model.Person;
import model.VisitorsList;
import org.junit.jupiter.api.Test;
import java.time.Month;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Code taken and modified from JsonSerializationDemo. URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonWriterTest extends JsonTest{


    @Test
    void testWriterInvalidFile() {
        try {
            VisitorsList visitorsList = new VisitorsList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            VisitorsList visitorsList = new VisitorsList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyVisitorsList.json");
            writer.open();
            writer.write(visitorsList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyVisitorsList.json");
            visitorsList = reader.read();
            assertEquals(0, visitorsList.getAllNames().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {

        try {

            VisitorsList vl = new VisitorsList();
            Person p1 = new Person("John Kav", "7782231254");
            Person p2 = new Person ("Sam Noei", "6045239283");

            p1.setDate("25/10/2021");
            p1.setStatusPositive();
            p1.setTime("10:00");

            p2.setDate("22/01/2020");
            p2.setStatusNegative();
            p2.setTime("22:21");

            vl.addPerson(p1);
            vl.addPerson(p2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralVisitorsList.json");
            writer.open();
            writer.write(vl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralVisitorsList.json");
            vl = reader.read();
            List<Person> persons = vl.getAllPersons();
            assertEquals(2, persons.size());


            checkPerson("John Kav", "7782231254","10:00", 25, Month.OCTOBER, 2021,
                    true, persons.get(0));
            checkPerson("Sam Noei", "6045239283", "22:21", 22, Month.JANUARY, 2020,
                    false, persons.get(1));


        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

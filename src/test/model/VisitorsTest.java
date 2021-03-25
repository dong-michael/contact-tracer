package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/* This class is used for testing of the VisitorsList class to ensure all methods are working as intended.*/
public class VisitorsTest {

    private VisitorsList testList;
    private VisitorsList emptyList;
    private Person visitor1;
    private Person visitor2;
    private Person visitor3;
    private Person visitor4;
    private Person visitor5;


    @BeforeEach
    public void setUp() {
        testList = new VisitorsList();
        emptyList = new VisitorsList();

        visitor1 = new Person("Jordon Walker", "6042918371");
        visitor2 = new Person("Paula Griffy", "6049165371");
        visitor3 = new Person("Alan Smith", "6042348421");
        visitor4 = new Person("Jake Shields", "6043093920");
        visitor5 = new Person("Nate Dean", "7782394835");

        testList.addPerson(visitor1);
        testList.addPerson(visitor2);


    }

    @Test
    public void removePersonTest() {

        assertTrue(testList.containsPerson(visitor2));
        testList.removePerson(visitor2);
        assertFalse(testList.containsPerson(visitor2));
        testList.removePerson(visitor3);
        assertFalse(testList.containsPerson(visitor3));

        assertFalse(emptyList.containsPerson(visitor2));

    }


    @Test
    public void addPersonTest() {

        assertFalse(testList.containsPerson(visitor3));
        testList.addPerson(visitor3);
        assertTrue(testList.containsPerson(visitor3));
    }


    @Test
    public void getVisitorsOnDayEmptyTest() {

        assertFalse(emptyList.getVisitorsOnDay("21/12/2021").contains(visitor1));
        assertFalse(emptyList.getVisitorsOnDay("29/12/2030").contains(visitor1));
        assertFalse(emptyList.getVisitorsOnDay("25/12/2000").contains(visitor1));

    }


    @Test
    public void getVisitorsOnDayTest() {
        testList.addPerson(visitor3);
        testList.addPerson(visitor4);

        visitor1.setDate("21/12/2021");
        assertTrue(testList.getVisitorsOnDay("21/12/2021").contains(visitor1));
        assertFalse(testList.getVisitorsOnDay("21/12/2021").contains(visitor2));

        visitor1.setDate("20/11/2020");
        assertFalse(testList.getVisitorsOnDay("21/12/2021").contains(visitor1));

        visitor1.setDate("22/09/2021");
        visitor2.setDate("22/09/2021");
        visitor3.setDate("23/09/2021");
        visitor4.setDate("21/09/2021");
        assertTrue(testList.getVisitorsOnDay("22/09/2021").contains(visitor1));
        assertTrue(testList.getVisitorsOnDay("22/09/2021").contains(visitor2));
        assertFalse(testList.getVisitorsOnDay("22/09/2021").contains(visitor3));
        assertFalse(testList.getVisitorsOnDay("22/09/2021").contains(visitor4));

    }


    @Test
    public void getPositiveTest() {

        assertFalse(testList.getPositivePersons().contains(visitor1));
        assertFalse(testList.getPositivePersons().contains(visitor2));
        assertFalse(testList.getPositivePersons().contains(visitor3));

        visitor2.setStatusPositive();
        assertTrue(testList.getPositivePersons().contains(visitor2));
        assertFalse(testList.getPositivePersons().contains(visitor1));
        assertFalse(testList.getPositivePersons().contains(visitor3));

        visitor2.setStatusNegative();
        assertFalse(testList.getPositivePersons().contains(visitor2));
    }


    @Test
    public void getVisitorsBetweenTimeTest() {

        testList.addPerson(visitor4);
        testList.addPerson(visitor5);

        visitor1.setTime("20:21");
        visitor2.setTime("21:15");
        visitor3.setTime("23:15");
        visitor4.setTime("18:40");

        visitor1.setDate("22/09/2021");
        visitor2.setDate("22/09/2021");
        visitor3.setDate("23/09/2021");
        visitor4.setDate("21/09/2021");

        //check when both time and date results in no matches

        assertFalse(testList.getVisitorsAtTime("22/09/2023", "12:32").contains(visitor1));
        assertFalse(testList.getVisitorsAtTime("23/09/2021", "00:29").contains(visitor2));
        assertFalse(testList.getVisitorsAtTime("22/10/2026", "20:12").contains(visitor3));
        assertFalse(testList.getVisitorsAtTime("20/09/2019", "17:11").contains(visitor4));

        assertEquals(0, testList.getVisitorsAtTime("22/09/2023", "12:32").size());
        assertEquals(0, testList.getVisitorsAtTime("23/09/2021", "00:29").size());
        assertEquals(0, testList.getVisitorsAtTime("22/10/2026", "20:12").size());
        assertEquals(0, testList.getVisitorsAtTime("20/09/2019", "17:11").size());


        //before or after time with greater or equal

        assertTrue(testList.getVisitorsAtTime("22/09/2021", "20:19").contains(visitor1));
        assertTrue(testList.getVisitorsAtTime("22/09/2021", "20:19").contains(visitor2));
        assertFalse(testList.getVisitorsAtTime("22/09/2021", "20:19").contains(visitor3));
        assertFalse(testList.getVisitorsAtTime("22/09/2021", "20:19").contains(visitor4));

        //test change in day to match, but not time

        visitor4.setDate("22/09/2021");
        assertFalse(testList.getVisitorsAtTime("22/09/2021", "20:19").contains(visitor4));
        assertEquals(2,testList.getVisitorsAtTime("22/09/2021", "20:19").size());

        //set time to match
        visitor4.setTime("20:30");
        assertTrue(testList.getVisitorsAtTime("22/09/2021", "20:19").contains(visitor4));
        assertEquals(3,testList.getVisitorsAtTime("22/09/2021", "20:19").size());


    }

    @Test
    public void getVisitorsAtTimeBoundaryTest() {

        testList.addPerson(visitor4);
        testList.addPerson(visitor5);

        visitor1.setTime("20:21");
        visitor2.setTime("21:15");
        visitor3.setTime("23:15");

        visitor1.setDate("22/09/2021");
        visitor2.setDate("22/09/2021");
        visitor3.setDate("23/09/2021");


        //set time to be within range
        visitor4.setTime("20:30");
        visitor4.setDate("22/09/2021");
        assertTrue(testList.getVisitorsAtTime("22/09/2021", "20:19").contains(visitor4));

        //lower boundary test
        visitor4.setTime("21:18");
        assertFalse(testList.getVisitorsAtTime("22/09/2021", "23:17").contains(visitor4));
        assertFalse(testList.getVisitorsAtTime("22/09/2021", "20:17").contains(visitor4));
        assertTrue(testList.getVisitorsAtTime("22/09/2021", "20:18").contains(visitor4));

        //exact match test
        visitor4.setTime("20:19");
        assertTrue(testList.getVisitorsAtTime("22/09/2021", "20:19").contains(visitor4));
        assertTrue(testList.getVisitorsAtTime("22/09/2021", "21:19").contains(visitor4));

        //upper boundary test
        visitor4.setTime("21:20");
        assertFalse(testList.getVisitorsAtTime("22/09/2021", "20:19").contains(visitor4));
        assertTrue(testList.getVisitorsAtTime("22/09/2021", "20:20").contains(visitor4));
        assertTrue(testList.getVisitorsAtTime("22/09/2021", "20:21").contains(visitor4));




    }


    @Test
    public void getSizeTest() {

        assertEquals(emptyList.getSize(), 0);

        assertEquals(testList.getSize(), 2);

        testList.addPerson(visitor4);
        assertEquals(testList.getSize(), 3);

        testList.removePerson(visitor1);
        assertEquals(testList.getSize(), 2);


    }


    @Test
    public void getPersonTest() {

        assertEquals(testList.getPerson("Jordon Walker"), visitor1);
        assertEquals(testList.getPerson("Nate Dean"), null);

        testList.addPerson(visitor5);
        assertEquals(testList.getPerson("Nate Dean"), visitor5);

        testList.removePerson(visitor5);
        assertEquals(testList.getPerson("Nate Dean"), null);

    }

    @Test
    public void getAllNamesTest() {

        //testing for list size and content

        assertTrue(testList.getAllNames().contains("Jordon Walker"));
        assertTrue(testList.getAllNames().contains("Paula Griffy"));
        assertEquals(testList.getAllNames().size(), 2);
        assertFalse(testList.getAllNames().contains("Nate Dean"));

        //testing for addition of new person

        testList.addPerson(visitor5);

        assertTrue(testList.getAllNames().contains("Nate Dean"));
        assertEquals(testList.getAllNames().size(), 3);

        //testing for removal of person

        testList.removePerson(visitor2);
        assertFalse(testList.getAllNames().contains("Paula Griffy"));
        assertTrue(testList.getAllNames().contains("Jordon Walker"));

        //testing for empty list

        assertFalse(emptyList.getAllNames().contains("Paula Griffy"));
        assertFalse(emptyList.getAllNames().contains("Jordon Walker"));
        assertEquals(emptyList.getAllNames().size(), 0);

    }

    @Test
    public void getAllPersonsTest(){

        assertEquals(testList.getAllPersons().size(), 2);
        assertTrue(testList.getAllPersons().contains(visitor1));
        assertTrue(testList.getAllPersons().contains(visitor2));
    }

    @Test
    public void clearVisitorsTest(){
        assertEquals(testList.getAllNames().size(), 2);
        testList.clearVisitors();
        assertEquals(testList.getSize(), 0);
    }
}


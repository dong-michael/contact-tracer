package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VisitorsTest {

    private VisitorsList testList;
    private Person visitor1;
    private Person visitor2;
    private Person visitor3;
    private Person visitor4;
    private Person visitor5;

    @BeforeEach

    public void setUp() {
        testList = new VisitorsList();
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


        VisitorsList testListEmpty = new VisitorsList();

        assertFalse(testListEmpty.containsPerson(visitor2));

    }


    @Test
    public void addPersonTest() {

        assertFalse(testList.containsPerson(visitor3));
        testList.addPerson(visitor3);
        assertTrue(testList.containsPerson(visitor3));
    }


    @Test
    public void getVisitorsOnDayEmptyTest() {

        VisitorsList emptyList = new VisitorsList();

        assertFalse(emptyList.getVisitorsOnDay("21/12/2021").containsPerson(visitor1));
        assertFalse(emptyList.getVisitorsOnDay("29/12/2030").containsPerson(visitor1));
        assertFalse(emptyList.getVisitorsOnDay("25/12/2000").containsPerson(visitor1));

    }


    @Test
    public void getVisitorsOnDayTest() {
        testList.addPerson(visitor3);
        testList.addPerson(visitor4);

        visitor1.setDate("21/12/2021");
        assertTrue(testList.getVisitorsOnDay("21/12/2021").containsPerson(visitor1));
        assertFalse(testList.getVisitorsOnDay("21/12/2021").containsPerson(visitor2));

        visitor1.setDate("20/11/2020");
        assertFalse(testList.getVisitorsOnDay("21/12/2021").containsPerson(visitor1));

        visitor1.setDate("22/09/2021");
        visitor2.setDate("22/09/2021");
        visitor3.setDate("23/09/2021");
        visitor4.setDate("21/09/2021");
        assertTrue(testList.getVisitorsOnDay("22/09/2021").containsPerson(visitor1));
        assertTrue(testList.getVisitorsOnDay("22/09/2021").containsPerson(visitor2));
        assertFalse(testList.getVisitorsOnDay("22/09/2021").containsPerson(visitor3));
        assertFalse(testList.getVisitorsOnDay("22/09/2021").containsPerson(visitor4));

    }


    @Test

    public void getPositiveTest() {

        assertFalse(testList.getPositivePersons().containsPerson(visitor1));
        assertFalse(testList.getPositivePersons().containsPerson(visitor2));
        assertFalse(testList.getPositivePersons().containsPerson(visitor3));

        visitor2.setStatusPositive();
        assertTrue(testList.getPositivePersons().containsPerson(visitor2));
        assertFalse(testList.getPositivePersons().containsPerson(visitor1));
        assertFalse(testList.getPositivePersons().containsPerson(visitor3));

        visitor2.setStatusNegative();
        assertFalse(testList.getPositivePersons().containsPerson(visitor2));
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

        //before or after time with greater or equal to then?

        assertTrue(testList.getVisitorsAtTime("22/09/2021", "20:19").containsPerson(visitor1));
        assertTrue(testList.getVisitorsAtTime("22/09/2021", "20:19").containsPerson(visitor2));
        assertFalse(testList.getVisitorsAtTime("22/09/2021", "20:19").containsPerson(visitor3));
        assertFalse(testList.getVisitorsAtTime("22/09/2021", "20:19").containsPerson(visitor4));

        //test change in day to match, but not time

        visitor4.setDate("22/09/2021");
        assertFalse(testList.getVisitorsAtTime("22/09/2021", "20:19").containsPerson(visitor4));

        //set time to match
        visitor4.setTime("20:30");
        assertTrue(testList.getVisitorsAtTime("22/09/2021", "20:19").containsPerson(visitor4));
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
        assertTrue(testList.getVisitorsAtTime("22/09/2021", "20:19").containsPerson(visitor4));

        //lower boundary test
        visitor4.setTime("22:18");
        assertTrue(testList.getVisitorsAtTime("22/09/2021", "20:19").containsPerson(visitor4));

        //exact match test
        visitor4.setTime("20:19");
        assertTrue(testList.getVisitorsAtTime("22/09/2021", "20:19").containsPerson(visitor4));

        //upper boundary test
        visitor4.setTime("22:20");
        assertFalse(testList.getVisitorsAtTime("22/09/2021", "20:19").containsPerson(visitor4));

    }



    @Test
    public void getSizeTest(){

        VisitorsList testEmpty = new VisitorsList();

        assertEquals(testEmpty.getSize(), 0);

        assertEquals(testList.getSize(), 2);

        testList.addPerson(visitor4);
        assertEquals(testList.getSize(), 3);

        testList.removePerson(visitor1);
        assertEquals(testList.getSize(), 2);


    }


}



package model;

import exception.InvalidInputFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

/* This class is used for testing the Person class to ensure that all methods are working as intended.*/
class PersonTest {

    private Person visitor1;
    private Person visitor2;
    private Person visitor3;

    @BeforeEach
    public void setUp() {
        visitor1 = new Person("Jordon Walker", "6042918371");
        visitor2 = new Person("Paula Griffy", "6049165371");
        visitor3 = new Person("Alan Smith", "6042348421");
    }


    @Test
    public void nameAndNumberTest() {
        assertEquals(visitor1.getName(), "Jordon Walker");
        assertEquals(visitor3.getName(), "Alan Smith");
        assertEquals(visitor3.getPhoneNumber(), "6042348421");
        assertEquals(visitor1.getPhoneNumber(), "6042918371");

    }

    @Test
    public void dateTestNoException() {

        try {
            visitor1.setDate("10/12/2020");
            assertEquals(visitor1.getDate().getMonth(), Month.DECEMBER);
            assertEquals(visitor1.getDate().getDayOfMonth(), 10);
            assertEquals(visitor1.getDate().getYear(), 2020);
        } catch (InvalidInputFormatException e) {
            fail("No Exception should be thrown");
        }

    }

    @Test
    public void timeTestNoExceptionLowerThrown() {

        try {
            visitor1.setTime("00:00");
            assertEquals(visitor1.getTime(), "00:00");
        } catch (InvalidInputFormatException e) {
            fail("Exception should not have been thrown");
        }

    }

    @Test
    public void timeTestNoExceptionUpperThrown() {

        try {
            visitor1.setTime("23:59");
            assertEquals(visitor1.getTime(), "23:59");
        } catch (InvalidInputFormatException e) {
            fail("Exception should not have been thrown");
        }

    }

    @Test
    public void timeTestExceptionThrown() {

        try {
            visitor1.setTime("30:45");
            fail("Exception should have been thrown");
        } catch (InvalidInputFormatException e) {
            //all good
        }

    }


    @Test
    public void timeTestExceptionThrownChar() {

        try {
            visitor1.setTime("abc");
            fail("Exception should have been thrown");
        } catch (InvalidInputFormatException e) {
            //all good
        }

    }


    @Test
    public void dateTestExceptionThrown() {

        try {
            visitor1.setDate("12/15/2020");
            fail("Exception should have been thrown");
        } catch (InvalidInputFormatException e) {
            //all good
        }

    }


    @Test
    public void getStatusTest() {

        assertFalse(visitor3.getStatus());
        visitor3.setStatusPositive();
        assertTrue(visitor3.getStatus());
        visitor3.setStatusNegative();
        assertFalse(visitor3.getStatus());
    }


    @Test
    public void setNameTest() {

        visitor2.setName("Lucas Car");
        assertEquals(visitor2.getName(), "Lucas Car");


    }

    @Test
    public void setNumberTestNoExceptions() {

        try {
            visitor2.setPhoneNumber("7782293012");
            assertEquals(visitor2.getPhoneNumber(), "7782293012");
        } catch (InvalidInputFormatException e) {
            fail("No exception should be thrown");
        }


    }

    @Test
    public void setNumberTestPhoneNumberTooLongExceptions() {

        try {
            visitor2.setPhoneNumber("77822930121");
            fail("Exception should have been thrown");
        } catch (InvalidInputFormatException e) {
            //all good;
        }

        assertFalse(visitor2.getPhoneNumber().equals("77822930121"));
    }

    @Test
    public void setNumberTestTooShortExceptions() {

        try {
            visitor2.setPhoneNumber("778229301");
            fail("Exception should have been thrown");
        } catch (InvalidInputFormatException e) {
           //all good
        }

        assertFalse(visitor2.getPhoneNumber().equals("778229301"));
    }

    @Test
    public void setNumberTestInvalidFormatException() {

        try {
            visitor2.setPhoneNumber("a778229309");
            fail("Exception should have been thrown");
        } catch (InvalidInputFormatException e) {
            //all good
        }

        assertFalse(visitor2.getPhoneNumber().equals("a77822930"));
    }

    @Test
    public void setNumberTestThrowBothException() {

        try {
            visitor2.setPhoneNumber("aaaaaaaaaa");
            fail("Exception should have been thrown");
        } catch (InvalidInputFormatException e) {
            //all good
        }

        assertFalse(visitor2.getPhoneNumber().equals("a77822930"));
    }


}
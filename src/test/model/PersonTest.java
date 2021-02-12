package model;

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
    public void setUp(){
        visitor1 = new Person("Jordon Walker", "6042918371");
        visitor2 = new Person("Paula Griffy", "6049165371");
        visitor3 = new Person("Alan Smith", "6042348421");
    }


    @Test
    public void nameAndNumberTest(){
        assertEquals(visitor1.getName(), "Jordon Walker");
        assertEquals(visitor3.getName(), "Alan Smith");
        assertEquals(visitor3.getPhoneNumber(), "6042348421");
        assertEquals(visitor1.getPhoneNumber(), "6042918371");

    }

    @Test
    public void timeAndDateTest(){

        visitor1.setDate("10/12/2020");
        assertEquals(visitor1.getDate().getMonth(), Month.DECEMBER);
        assertEquals(visitor1.getDate().getDayOfMonth(), 10);
        assertEquals(visitor1.getDate().getYear(), 2020);
        visitor1.setTime("16:45");
        assertEquals(visitor1.getTime(), "16:45");
        visitor1.setTime("20:20");
        assertEquals(visitor1.getTime(), "20:20");
    }



    @Test
    public void getStatusTest(){

        assertFalse(visitor3.getStatus());
        visitor3.setStatusPositive();
        assertTrue(visitor3.getStatus());
        visitor3.setStatusNegative();
        assertFalse(visitor3.getStatus());
    }


}
package ui;



/* This calls is used to run the Contact Tracer application. */

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {

        try {
            new ContactTracerApp();
        } catch (FileNotFoundException e) {
            System.out.println("Application could not be run: write file not found");
        }

    }
}

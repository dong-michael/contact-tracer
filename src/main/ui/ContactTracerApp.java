package ui;

import model.Person;
import model.VisitorsList;


import java.util.Scanner;

public class ContactTracerApp {

    private Person jake;
    private Person samantha;
    private Person jordon;
    private VisitorsList store;
    private Scanner input;

    // EFFECTS: runs the ContactTracer application
    public ContactTracerApp() {
        runTracer();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTracer() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next(); //grabs user input
            command = command.toLowerCase(); //makes it lower case

            if (command.equals("q")) { //q for quit
                keepGoing = false;
            } else {
                processCommand(command); //passing command to method
            }
        }

        System.out.println("\nGoodbye and stay safe!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            forAdmin();
        } else if (command.equals("c")) {
            forCustomer();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes stored visitors
    private void init() {

        store = new VisitorsList();
        jake = new Person("Jake Krill", "6045942031");
        samantha = new Person("Samantha Green", "604348321");
        jordon = new Person("Jordon Wicker", "6043249012");

        store.addPerson(jake);
        store.addPerson(samantha);
        store.addPerson(jordon);

        input = new Scanner(System.in);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nPlease select from:");
        System.out.println("\ta -> admin use");
        System.out.println("\tc -> customer use");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: displays menu of options to admin user
    private void forAdmin() {
        System.out.println("Admin Use");
        System.out.println("Please choose from:");
        System.out.println("\tr -> register contact information");
        System.out.println("\tv -> view and modify stored data");
        System.out.println("\tq -> quit");

        String adminInput = input.next();

        if (adminInput.equals("r")) {
            registerContact();
        } else if (adminInput.equals("v")) {
            viewOrModifyPersons();
        } else if (adminInput.equals("q")) {
            System.out.println("underconstruction");
        } else {
            System.out.println("Selection not valid, please try again.");
        }

    }

    private void viewPersons() {
        System.out.println("Viewing Selected");
        System.out.println("Please choose from:");
        System.out.println("\tvd -> View a list of people that came in on a certain day");
        System.out.println("\tvt -> View a list of people that came in on a certain day and time");
        System.out.println("\tvp -> View a list of people who have currently screened positive");

        String choice = input.next();

        if (choice.equals("vd")) {
            System.out.println("Please enter the date you'd like to use in format DD/MM/YYYY");

            String date = input.next();
            System.out.println("Here is a list of the people that came in on " + date + ":");


            System.out.println(store.getVisitorsOnDay(date));

        } else if (choice.equals("vt")) {
            System.out.println("Please enter the date and time you'd like to use in format DD/MM/YYYY HH:MM");

            String dateTime = input.next();
            dateTime += input.nextLine();

            String[] splitStrings = dateTime.split(" ");

            String name = splitStrings[0];
            String time = splitStrings[1];

            System.out.println("Here is a list of the people that came in on/at " + dateTime + ":");
            store.getVisitorsAtTime(name, time);

        } else if (choice.equals("vp")) {
            System.out.println("Here is a list of the people that screened positive so far:");

            store.getPositivePersons();

        } else {
            System.out.println("Selection is not valid please try again.");
        }
    }

    private void modifyPersons() {

        System.out.println("Modify Selected");
        System.out.println("Please choose from:");

        //choose person to modify
        System.out.println("\tu -> Updates a person's status to positive or negative");
        System.out.println("\tr -> Remove a person from contact list");
        System.out.println("\tt -> Change a person's time of arrival");
        System.out.println("\td -> Change a person's date of arrival");


        String choice = input.next();

        if (choice.equals("u")) {
            System.out.println("Please choose a person to update from the following list:");


        } else if (choice.equals("t")) {

            System.out.println("Please choose a person to update from the following list:");

        } else if (choice.equals("d")) {

            System.out.println("Please choose a person to update from the following list:");

        } else {

            System.out.println("Selection is not valid please try again.");
        }
    }

    private void viewOrModifyPersons() {
        System.out.println("Please choose from:");
        System.out.println("\t v -> view");
        System.out.println("\t m -> modify");

        String choice = input.next();

        if (choice.equals("v")) {
            viewPersons();
        } else if (choice.equals("m")) {
            modifyPersons();
        } else {
            System.out.println("Selection not valid, please try again.");
        }
    }


    private void registerContact() {
        System.out.println("Please enter name and phone number in format FIRSTNAME LASTNAME XXXXXXXXXX");

        String line = input.next();
        line += input.nextLine();
        String[] splitStrings = line.split(" ");

        String first = splitStrings[0];
        String last = splitStrings[1];

        String fullName = first + last;
        String phoneNumber = splitStrings[2];


        Person visitor = new Person(fullName, phoneNumber);
        store.addPerson(visitor);

        System.out.println("Registration successful, thank you!");

    }

    //MODIFIES: this
    //EFFECTS: displays menu of options to customer user
    private void forCustomer() {
        System.out.println("Customer Use");
        System.out.println("Please choose from:");
        System.out.println("\tr -> register contact information");
        System.out.println("\tq -> quit");
    }


}


package ui;

import model.Person;
import model.VisitorsList;


import java.util.List;
import java.util.Scanner;

public class ContactTracerApp {

    private Person jake;
    private Person samantha;
    private Person jordon;
    private VisitorsList store;
    private Scanner input;
    private boolean keepGoing;

    // EFFECTS: runs the ContactTracer application
    public ContactTracerApp() {
        runTracer();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTracer() {
        keepGoing = true;
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

        jake.setTime("18:00");
        samantha.setStatusPositive();

        input = new Scanner(System.in);
    }

    // EFFECTS: displays the main menu to user
    private void displayMenu() {
        System.out.println("\nPlease select from:");
        System.out.println("\ta -> admin use (allows user to also view/make changes to contact lists)");
        System.out.println("\tc -> customer use (only allows user to record contact information)");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: displays admin specific menu to user and processes user command
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
            keepGoing = false;
        } else {
            System.out.println("Selection not valid, please try again.");
        }

    }

    //MODIFIES: this
    //EFFECTS: displays view options to users and modifies a person's date, time, or status
    private void viewPersons() {
        System.out.println("Viewing Selected");
        System.out.println("Please choose from:");
        System.out.println("\tvd -> View a list of people that came in on a certain day");
        System.out.println("\tvt -> View a list of people that came in on a certain day and time");
        System.out.println("\tvp -> View a list of people who have currently screened positive");

        String choice = input.next();

        if (choice.equals("vd")) {
            System.out.println("Please enter the date you'd like to use in format DD/MM/YYYY");
            viewCustomersOnDay();

        } else if (choice.equals("vt")) {
            System.out.println("Please enter the date and time you'd like to use in format DD/MM/YYYY HH:MM");
            viewCustomersAtTime();

        } else if (choice.equals("vp")) {
            System.out.println("Here is a list of the people that screened positive so far:");
            viewAllPositive();

        } else {
            System.out.println("Selection is not valid please try again.");
        }
    }


    //MODIFIES: this
    //EFFECTS: displays names of people whose status is positive for COVID
    private void viewAllPositive() {
        List<Person> filteredPositive = store.getPositivePersons();

        for (Person p : filteredPositive) {
            System.out.println(p.getName());
        }
    }


    //MODIFIES: this
    //EFFECTS: displays names of all customer that came in on user a specified date
    private void viewCustomersOnDay() {
        String date = input.next();
        System.out.println("Here is a list of the people that came in on " + date + ":");

        for (Person p : store.getVisitorsOnDay(date)) {
            System.out.println(p.getName());
        }

    }

    //MODIFIES: this
    //EFFECTS: displays a list of people that came in on a user specified date and time
    private void viewCustomersAtTime() {

        String dateTime = input.next();
        dateTime += input.nextLine();

        String[] splitStrings = dateTime.split(" ");

        String name = splitStrings[0];
        String time = splitStrings[1];

        System.out.println("Here is a list of the people that came in on " + name + "at" + " approximately "
                + time + " :");

        List<Person> filtered = store.getVisitorsAtTime(name, time);

        for (Person p : filtered) {
            System.out.println(p.getName());
        }
    }


    //MODIFIES: this
    //EFFECTS: processes user command
    private void modifyPersons() {
        displayModifyPersonMenu();

        String command = input.next();
        if (command.equals("u")) {
            displayAllVisitorsMenu();
            displayAllNames();
            personStatusUpdate();

        } else if (command.equals("r")) {
            displayAllVisitorsMenu();
            displayAllNames();
            personDeleteUpdate();

        } else if (command.equals("t")) {
            displayAllVisitorsMenu();
            displayAllNames();
            personTimeUpdate();


        } else if (command.equals("d")) {
            displayAllVisitorsMenu();
            displayAllNames();
            personDateUpdate();


        } else {
            System.out.println("Selection is not valid please try again.");
        }
    }

    //EFFECTS: prompts user to make a selection from a list of persons
    private void displayAllVisitorsMenu() {
        System.out.println("Please choose a person to update from the following list:");
    }

    //EFFECTS: displays modify menu to user
    private void displayModifyPersonMenu() {
        System.out.println("Modify Selected");
        System.out.println("Please choose from:");
        System.out.println("\tu -> Updates a person's status to positive or negative");
        System.out.println("\tr -> Remove a person from contact list");
        System.out.println("\tt -> Change a person's time of arrival");
        System.out.println("\td -> Change a person's date of arrival");
    }

    //MODIFIES: this
    //EFFECTS: deletes user specified person from storage
    private void personDeleteUpdate() {
        System.out.println("Please enter the name of the person to be deleted in format: Firstname Lastname");

        String userCommand = input.next();
        userCommand += input.nextLine();

        String[] splitStrings = userCommand.split(" ");

        String firstName = splitStrings[0];
        String lastName = splitStrings[1];

        String name = firstName + " " + lastName;

        Person toBeDeleted = store.getPerson(name);

        store.removePerson(toBeDeleted);
        System.out.println(name + " has been deleted successfully.");

    }

    //MODIFIES: this
    //EFFECTS: updates the person's date according to newTime
    private void personTimeUpdate() {

        System.out.println("Please enter the name and the new time of person in format: Firstname Lastname, HH:MM");
        String userCommand = input.next();
        userCommand += input.nextLine();

        String[] splitStrings = userCommand.split(" ");

        String firstName = splitStrings[0];
        String lastName = splitStrings[1];
        String newTime = splitStrings[2];

        String name = firstName + " " + lastName;

        store.getPerson(name).setTime(newTime);
        System.out.println("Time of " + firstName + " " + lastName + " updated successfully.");
    }


    //MODIFIES: this
    //EFFECTS: updates the selected person's date to newDate
    private void personDateUpdate() {

        System.out.println("Please enter the name and the new date of the person in format: "
                + "Firstname Lastname DD/MM/YYYY");

        String userCommand = input.next();
        userCommand += input.nextLine();

        String[] splitStrings = userCommand.split(" ");

        String firstName = splitStrings[0];
        String lastName = splitStrings[1];
        String newDate = splitStrings[2];

        String name = firstName + " " + lastName;

        store.getPerson(name).setDate(newDate);
        System.out.println(firstName + " " + lastName + "'s" + " date has been updated successfully.");
    }


    //MODIFIES: this
    //EFFECTS: updates the person's status to newStatus (positive or negative)
    private void personStatusUpdate() {
        System.out.println("Please enter the new status of the person in format: "
                + "Firstname Lastname positive or Firstname Lastname negative");

        String userCommand = input.next();
        userCommand += input.nextLine();

        String[] splitStrings = userCommand.split(" ");

        String firstName = splitStrings[0];
        String lastName = splitStrings[1];
        String status = splitStrings[2];

        String name = firstName + " " + lastName;

        Person personToBeChanged = store.getPerson(name);

        if (status.equals("positive")) {
            personToBeChanged.setStatusPositive();
        } else if (status.equals("negative")) {
            personToBeChanged.setStatusNegative();
        } else {
            System.out.println("Selection not valid, please try again.");
        }

        System.out.println("The status of " + firstName + " " + lastName + " has been updated successfully, thanks!");
    }

    //MODIFIES: this
    //EFFECTS: displays view or modify menu to user and takes user input
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


    //MODIFIES: this
    //EFFECTS: registers new contact information
    private void registerContact() {
        System.out.println("Please enter name and phone number in format: Firstname Lastname NNNNNNNNNN");

        String line = input.next();
        line += input.nextLine();
        String[] splitStrings = line.split(" ");

        String first = splitStrings[0] + " ";
        String last = splitStrings[1];

        String fullName = first + last;
        String phoneNumber = splitStrings[2];


        Person visitor = new Person(fullName, phoneNumber);
        store.addPerson(visitor);

        System.out.println("Registration successful, thank you!");
    }

    //MODIFIES: this
    //EFFECTS: displays menu of options to customer user and takes user input
    private void forCustomer() {
        System.out.println("Customer Use");
        System.out.println("Please choose from:");
        System.out.println("\tr -> register contact information");
        System.out.println("\tq -> quit");

        String adminInput = input.next();

        if (adminInput.equals("r")) {
            registerContact();
        } else if (adminInput.equals("q")) {
            keepGoing = false;
        } else {
            System.out.println("Selection not valid, please try again.");
        }
    }

    //MODIFIES: this
    //EFFECTS: displays all contact names stored to user

    private void displayAllNames() {
        for (String name : store.getAllNames()) {
            System.out.println(name);
        }
    }


}


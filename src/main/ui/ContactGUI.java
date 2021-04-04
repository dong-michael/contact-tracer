package ui;

import exception.InvalidInputFormatException;
import model.Person;
import model.VisitorsList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

// This class is dedicated to implementation of a GUI for contact tracing
// Play button sound effect developed from code, modified from this tutorial: http://suavesnippets.blogspot.com/2011/06/add-sound-on-jbutton-click-in-java.html
// Table row selection was developed from code, modified from this tutorial: https://www.codejava.net/java-se/swing/jtable-popup-menu-example
public class ContactGUI extends JPanel {

    private JPopupMenu popUp;
    private static JFrame frame;
    private JTable table;
    private JPanel controlPanel;
    private JButton load;
    private JButton save;
    private JButton clear;
    private JMenuItem menuAdd;
    private JMenuItem menuRemove;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private DefaultTableModel myTable;
    private VisitorsList store = null;
    private static final String JSON_STORE = "./data/visitorslist.json";

    //CONSTRUCTOR
    //EFFECTS: initializes new table model, popup menu, data persistence components
    public ContactGUI() {

        super(new BorderLayout());
        init();
        MyTableModel myTable = new MyTableModel();

        table = new JTable(myTable);
        table.setPreferredScrollableViewportSize(new Dimension(600, 200));
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);

        adjustColumns();


        //create new popup menu
        popUp = new JPopupMenu();
        menuAdd = new JMenuItem("Add New Contact");
        menuRemove = new JMenuItem("Remove Contact");

        menuAdd.addActionListener(new PopUpAction());
        menuRemove.addActionListener(new PopUpAction());

        popUp.add(menuAdd);
        popUp.add(menuRemove);

        table.setComponentPopupMenu(popUp);
        table.addMouseListener(new TableMouseListener(table));

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        addButtonsPanel();
        initializeButtonActions();

        //Add the scroll pane to this panel.
        add(scrollPane, BorderLayout.CENTER);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    //EFFECTS: adjust each table column to preferred width
    private void adjustColumns() {
        TableColumn column;
        for (int i = 0; i < 5; i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(80);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else {
                column.setPreferredWidth(50);
            }
        }
    }

    //EFFECTS: creates and add buttons to JPanel
    private void addButtonsPanel() {

        load = new JButton("Load saved data");
        save = new JButton("Save data");
        clear = new JButton("Clear contact list");

        //Create a panel that uses BoxLayout.
        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.LINE_AXIS));
        controlPanel.add(Box.createHorizontalStrut(5));
        controlPanel.add(load);
        controlPanel.add(save);
        controlPanel.add(new JSeparator(SwingConstants.VERTICAL));
        controlPanel.add(Box.createHorizontalStrut(5));
        controlPanel.add(clear);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(controlPanel, BorderLayout.PAGE_END);
    }

    //MODIFIES: this
    //EFFECTS: initializes and loads all buttons
    public void initializeButtonActions() {
        load.setActionCommand("load");
        load.addActionListener(new ButtonAction());
        save.setActionCommand("save");
        save.addActionListener(new ButtonAction());
        clear.setActionCommand("clear");
        clear.addActionListener(new ButtonAction());
    }


    // This class provides the button action behaviour when a button is pressed
    public class ButtonAction extends AbstractAction {

        ButtonAction() {
            super("Button Action");
        }

        @Override
        //EFFECTS: performs load visitorslist, savevistorslist, or clear visitorslist, depending on user action.
        public void actionPerformed(ActionEvent e) {
            if ("load".equals(e.getActionCommand())) {
                loadVisitorsList();
                playSound("data/buttonpressedsound.wav");
                frame.setVisible(false);
                frame.setVisible(true);
            } else if ("save".equals(e.getActionCommand())) {
                playSound("data/buttonpressedsound.wav");
                saveVisitorsList();
                frame.setVisible(false);
                frame.setVisible(true);
            } else if ("clear".equals(e.getActionCommand())) {
                playSound("data/buttonpressedsound.wav");
                store.clearVisitors();
                frame.setVisible(false);
                frame.setVisible(true);

            }

        }
    }

    public class PopUpAction extends AbstractAction {

        //CONSTRUCTOR
        PopUpAction() {
            super("Popup Action");
        }

        @Override
        //EFFECTS: adds a new row to the table if add button is pressed or, removed current row if button is pressed
        public void actionPerformed(ActionEvent event) {

            JMenuItem menu = (JMenuItem) event.getSource();
            if (menu == menuAdd) {
                addNewRow();
            } else if (menu == menuRemove) {
                removeCurrentRow();
            }
        }

    }

    // this class allows the rows on the table to be highlighted
    public class TableMouseListener extends MouseAdapter {

        private JTable table1;

        //CONSTRUCTOR
        public TableMouseListener(JTable table) {
            this.table1 = table;
        }

        @Override
        //EFFECTS: highlights the current row in the table when clicked
        public void mousePressed(MouseEvent e) {
            Point point = e.getPoint();
            int currentRow = table1.rowAtPoint(point);
            try {
                table1.setRowSelectionInterval(currentRow, currentRow);
            } catch (Exception exception) {
                System.out.println("Row index out of range");
            }
        }

    }


    // MODIFIES: this
    // EFFECTS: loads visitorslist from file
    private void loadVisitorsList() {
        try {
            store = jsonReader.read();
            System.out.println("Loaded " + "VisitorsList" + " from " + JSON_STORE);
        } catch (IOException | InvalidInputFormatException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


    // EFFECTS: saves the visitorslist to file
    private void saveVisitorsList() {
        try {
            jsonWriter.open();
            jsonWriter.write(store);
            jsonWriter.close();
            System.out.println("Saved " + "Visitor's List" + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // this class models the data in the form of a table
    class MyTableModel extends AbstractTableModel {

        //CONSTRUCTOR
        public MyTableModel() {
            myTable = new DefaultTableModel();
        }

        //MODIFIES: this
        //EFFECTS: assigns names for columns
        private String[] columnNames =
                {"First and Last Name",
                        "Phone Number",
                        "Arrival Time (24HR)",
                        "Arrival Date",
                        "Positive Status"};

        //EFFECTS: returns the number of columns in the table
        public int getColumnCount() {
            return columnNames.length;
        }

        //EFFECTS: returns the number of rows in the table
        public int getRowCount() {
            return store.getSize();
        }

        //EFFECTS: returns the name of each column
        public String getColumnName(int col) {
            return columnNames[col];
        }

        //MODIFIES: this
        //EFFECTS: return the visitors stored in the visitorslist
        public Object getValueAt(int row, int col) {

            Person selected = store.getAllPersons().get(row);
            DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            switch (col) {
                case 0:
                    return selected.getName();
                case 1:
                    return selected.getPhoneNumber();
                case 2:
                    return selected.getTime();
                case 3:
                    LocalDate date = selected.getDate().toLocalDate();
                    String dateFormated = date.format(formatterDate);
                    return dateFormated;
                case 4:
                    return String.valueOf(selected.getStatus());
                default:
                    return null;
            }
        }

        //EFFECTS: returns the class of each cell in the table
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        //EFFECTS: sets all cells of the table as editable
        public boolean isCellEditable(int row, int col) {
            return true;
        }

        @Override
        //MODIFIES: this, visitorslist
        //EFFECTS: stores the value of the cells in the visitors list
        public void setValueAt(Object value, int row, int col) {
            Person selected = store.getAllPersons().get(row);
            switch (col) {
                case 0:
                    selected.setName(value.toString());
                    break;
                case 1:
                    setPhoneNumberToTable(value, selected);
                    break;
                case 2:
                    setTimeToTable(value, selected);
                    break;
                case 3:
                    setDateToTable(value, selected);
                    break;
                case 4:
                    setStatusToTable(value, selected);
                    break;
            }

        }


        //MODIFIES: this
        //EFFECTS: sets status of person in table according to value
        private void setStatusToTable(Object value, Person selected) {
            if (value.toString().equals("true")) {
                selected.setStatusPositive();
            } else {
                selected.setStatusPositive();
            }
        }


        //MODIFIES: this
        //EFFECTS: sets date of person selected to value
        private void setDateToTable(Object value, Person selected) {
            try {
                selected.setDate(value.toString());
            } catch (InvalidInputFormatException e) {
                System.out.println("Please enter a valid date");
            }
        }

        //MODIFIES: this
        //EFFECTS: sets time of person selected to value
        private void setTimeToTable(Object value, Person selected) {
            try {
                selected.setTime(value.toString());
            } catch (InvalidInputFormatException e) {
                System.out.println("Please enter a valid time HH:mm");
            }
        }


        //MODIFIES: this
        //EFFECTS: sets phone number of person to value
        private void setPhoneNumberToTable(Object value, Person selected) {
            try {
                selected.setPhoneNumber(value.toString());
            } catch (InvalidInputFormatException e) {
                System.out.println("Please enter a valid phone number");
            }
        }
    }


    //EFFECTS: plays sound effect
    public void playSound(String soundName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }


    //EFFECTS: adds new row to table
    public void addNewRow() {
        store.addPerson(new Person(null, null));

    }

    //EFFECTS: remove current row table
    public void removeCurrentRow() {
        int selectedRow = table.getSelectedRow();
        Person toBeDeleted = store.getAllPersons().get(selectedRow);
        store.removePerson(toBeDeleted);
    }


    // MODIFIES: this
    // EFFECTS: initializes stored visitors
    private void init() {
        Person jake;
        Person samantha;
        Person jordon;

        store = new VisitorsList();
        jake = new Person("Jake Krill", "6045942031");
        samantha = new Person("Samantha Green", "6043483218");
        jordon = new Person("Jordon Wicker", "6043249012");

        store.addPerson(jake);
        store.addPerson(samantha);
        store.addPerson(jordon);

        try {
            jake.setTime("18:00");
        } catch (InvalidInputFormatException e) {
            e.printStackTrace();
        }
        samantha.setStatusPositive();
    }


    //EFFECTS: creates and setup GUI
    private static void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("Contact Tracer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new ContactGUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
    }



    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }





}



import javax.swing.*;

import java.util.*;
import java.io.*;

class PersonInfo {
    String name;
    String address;
    String phoneNumber;

    // Parameterized constructor
    PersonInfo(String n, String a, String p) {
        name = n;
        address = a;
        phoneNumber = p;
    }

    // Display on GUI
    void display() {
        JOptionPane.showMessageDialog(null, "Name: " + name + "\nAddress: " + address + "\nPhone: " + phoneNumber);
    }
}

    class AddressBook {
        ArrayList persons;

        // Constructor
        AddressBook() {
            persons = new ArrayList();
            // Load all the records which already exist before
            loadPersons();
        }

        // Adding a Person Object
        void addPerson() {
            String name = JOptionPane.showInputDialog("Enter Name: ");
            String add = JOptionPane.showInputDialog("Enter Address: ");
            String pNum = JOptionPane.showInputDialog("Enter Phone No: ");

            // Created an object of PersonInfo class
            PersonInfo p = new PersonInfo(name, add, pNum);
            // Added the object (p) to the ArrayList named persons
            persons.add(p);
        }

        // Searching for a person
        void searchPerson(String n) {
            for (int i = 0; i < persons.size(); i++) {
                // Downcasted object class to PersonInfo
                PersonInfo p = (PersonInfo) persons.get(i);
                if (n.equals(p.name)) {
                    p.display();
                }
            }
        }

        // Deleting a person record
        void deletePerson(String n) {
            for (int i = 0; i < persons.size(); i++) {
                // Downcasted to the PersonInfo object
                PersonInfo p = (PersonInfo) persons.get(i);
                if (n.equals(p.name)) {
                    persons.remove(i);
                }
            }
        }

        // Saving a person record
        void savePersons() {
            // Using exception handling
            // Also using some kinda io manipulation here thereby imported java.io.*
            try {
                PersonInfo p;
                // Going to associate to 1 line in the text file
                String line;

                // Low level stream
                FileWriter fw = new FileWriter("persons.txt");
                // High level stream
                PrintWriter pw = new PrintWriter(fw);

                for (int i = 0; i < persons.size(); i++) {
                    // Downcasted to the PersonInfo object
                    p = (PersonInfo) persons.get(i);
                    line = p.name + ", " + p.address + ", " + p.phoneNumber;

                    // Write line to persons.text
                    pw.println(line);
                }
                pw.flush();
                pw.close();
                fw.close();

            } catch(IOException ioEx) {
                System.out.println(ioEx);
            }
        }

        
        // Loading Person Record from .txt file
        void loadPersons() {
            String tokens[] = null;
            String name, add, ph;
            try {
                // Low level stream
                FileReader fr = new FileReader("persons.txt");
                // High level stream
                BufferedReader br = new BufferedReader(fr);
                String line = br.readLine();
                while (line != null) {
                    tokens = line.split(", ");
                    name = tokens[0];
                    add = tokens[1];
                    ph = tokens[2];

                    // Created object
                    PersonInfo p = new PersonInfo(name, add, ph);
                    // Added it to ArrayList (persons)
                    persons.add(p);
                    // continuously read the next line until it stops / hits null
                    line = br.readLine();
                }
                br.close();
                fr.close();
            } catch(IOException ioEx) {
                System.out.println(ioEx);
            }
        }

    }

public class Test {
    public static void main(String args[]) {
        // Created an object of the AddressBook class 
        AddressBook ab = new AddressBook();
        String input, s;
        int ch;

        while (true) {
            input = JOptionPane.showInputDialog("Enter 1 to Add\nEnter 2 to Search\nEnter 3 to Delete\nEnter 4 to Exit\n");
            ch = Integer.parseInt(input);

            switch (ch) {
                case 1: 
                    ab.addPerson(); 
                    break;
                case 2: 
                    s = JOptionPane.showInputDialog("Enter name to search:");
                    ab.searchPerson(s);
                    break;
                case 3: 
                    s = JOptionPane.showInputDialog("Enter name to delete:");
                    ab.deletePerson(s);
                    break;
                case 4: 
                    ab.savePersons();
                    // Terminates the currently running JVM
                    // Arguments serves as a status code, non-zero status code indicates abnormal termination.
                    System.exit(0); 
            }
        }

    }
}
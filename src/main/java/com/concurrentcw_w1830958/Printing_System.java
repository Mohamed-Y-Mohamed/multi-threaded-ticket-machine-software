/*
Name: Mohamed Mohamed
 Student ID: w1830958
 */

package com.concurrentcw_w1830958;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Mym19
 */
public class Printing_System {

    public static void main(String[] args) {
           // Initial values for paper and toner levels, and an integer to count the number of passengers on the machine
        int initialPaperLevel = 3;
        int initialTonerLevel = 4;
        int numOfPassengers = 0;

        // Array list with passenger names
        ArrayList<String> random = new ArrayList<>(Arrays.asList("Alice", "Bob", "Charlie", "David"));

        // Scanner to take user input
        Scanner in = new Scanner(System.in);
        Machine machine = new Machine(initialPaperLevel, initialTonerLevel);
        System.out.println("");
        System.out.println("Starting Paper Level: " + machine.getPaperLevel() +", Starting Tuner Level: "+machine.getTonerLevel());
        System.out.println("");
        System.out.println("");

        System.out.println("How many passengers do you want to test with? Maximum amount is 4");
        boolean validInput = false;
        while (!validInput) {
            try {
                numOfPassengers = Integer.parseInt(in.nextLine());
                if (numOfPassengers <= 4 && numOfPassengers >0)
                    validInput = true;
                else
                    System.out.println("A maximum of 4 passengers can be printed at a time.\nTry Again and ensure the input is in range of 1 to 4:");
            } catch (NumberFormatException e) {
                System.out.println("Incorrect type passed. please enter a number from 1 to 4 only:");
            }
        }
        System.out.println("");
        System.out.println("");

        //group threading for passengers.
        ThreadGroup passengerGroup = new ThreadGroup("Passengers");

        // Starting threads for each passenger

        for (int i = 0; i < numOfPassengers; i++) {
            if (i < random.size()) {
                String passengerName = random.get(i);
                Thread passengerThread = new Thread(passengerGroup, new Passenger(passengerName, machine));
                passengerThread.start();
            }
        }

        // Starting paper technician thread as daemon
        Thread paperTechnicianThread = new Thread(new Paper_Technician(machine, initialPaperLevel));
        paperTechnicianThread.setDaemon(true);
        paperTechnicianThread.start();

        // Starting toner technician thread as daemon
        Thread tonerTechnicianThread = new Thread(new Tuner_Technician(machine, initialTonerLevel));
        tonerTechnicianThread.setDaemon(true);
        tonerTechnicianThread.start();


        // Get the status of the machine
        machine.getStatus();
    }
}

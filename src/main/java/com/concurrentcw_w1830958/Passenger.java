/*
Name: Mohamed Mohamed
 Student ID: w1830958
 */
package com.concurrentcw_w1830958;

import java.util.Random;

/**
 *
 * @author Mym19
 */
public class Passenger implements Runnable {
      private String name; // Name of the passenger
    private Machine machine; // Reference to the printing machine
    private Random random; // Random generator for ticket quantity

    // Constructor initializing the passenger with a name and the machine
    public Passenger(String name, Machine machine) {
        this.name = name;
        this.machine = machine;
        this.random = new Random(); // Initializing random generator for ticket quantity
    }

    @Override
    public void run() {
        int ticketsToPrint = random.nextInt(3) + 1; // Randomly generate tickets to print between 1 and 3
        try {
            machine.printTicket(name, ticketsToPrint); // Request the printing of tickets for the passenger
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

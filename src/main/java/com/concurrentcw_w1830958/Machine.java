/*
Name: Mohamed Y Mohamed
 Student ID: w1830958
 */
package com.concurrentcw_w1830958;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mym19
 */
public class Machine {
   //int count = 0;  Counter to track the number of completed prints
    //the count was originally set to track the number of passenger that has been printed is equal to the number of passengers that was set at the start
    //if so it would terminate the project but did not seem to work and for that, I convert the paper and tuner technician
    //to a daemon thread to run it as maintenance program in the program.
    private int paperLevel; // Current paper level in the machine
    private int tonerLevel; // Current toner level in the machine
    private int PaperRefillLevel;
    private int TunerRefillLevel;
    private final Object lock = new Object(); // Object used for synchronization
    private final Map<String, Integer> passengers; // Map to track passengers and their print amounts

    // Constructor initializing the machine with initial paper and toner levels
    public Machine(int initialPaperLevel, int initialTonerLevel) {
        this.paperLevel = initialPaperLevel;
        this.tonerLevel = initialTonerLevel;
        PaperRefillLevel=initialPaperLevel;
             TunerRefillLevel   =  initialTonerLevel;
        this.passengers = new HashMap<>(); // Initializing the passenger map
    }

    // Method to print tickets for a passenger
   public void printTicket(String passengerName, int amount) throws InterruptedException {
        synchronized (lock) {
            while (checkAvailability()) {
                System.out.println("Insufficient Paper and Toner levels for " + passengerName + ". Refilling.");
                lock.wait();
            }
            System.out.println("Lock acquired for " + passengerName + " to print the tickets.");

            passengers.put(passengerName, amount); // Storing passenger and print amount
            for (int i = 0; i < amount; i++) {
                if (tonerLevel == 0 || paperLevel == 0) {
                    if (tonerLevel == 0) {
                        replaceToner(TunerRefillLevel);
                    }
                    if (paperLevel == 0) {
                        refillPaper(PaperRefillLevel);
                    }
                } if (tonerLevel > 0 && paperLevel >0 ) {
                    System.out.println("Ticket number " + (i + 1) + " for " + passengerName + ".");
                    tonerLevel--; // Decreasing toner level after printing each ticket
                    paperLevel--; // Decreasing paper level after printing each ticket
                }
            }
            Tickets ticket = new Tickets(amount, passengerName);
            ticket.printTicket(); // Printing the ticket
            System.out.println("Printing completed for " + passengerName);
            System.out.println("Lock released.");

            lock.notify(); // Notify waiting threads about printing completion
        }
    }

    // Checking if sufficient paper and toner are available for printing
  private boolean checkAvailability() {
    return paperLevel <= 0 || tonerLevel <= 0;
}

    // Method to get the current status of paper and toner levels
    public void getStatus() {
        synchronized (lock) {
            System.out.print("Current Paper level: " + paperLevel+". ");
            System.out.println("Current Toner level: " + tonerLevel + ".");
        }
    }

    // Method to refill paper in the machine
    public void refillPaper(int amount) {
        synchronized (lock) {
            paperLevel += amount; // Increasing paper level after refill
            System.out.println("Lock acquired for refill.");
            System.out.println("Refilling paper by " + amount + " units.");
            System.out.println("Lock released. Paper refill completed.");

            Random random = new Random(); 
            int sleepTime =random.nextInt(6000); // Generating random sleep interval
            try {
                Thread.sleep(sleepTime); // Sleeping for a random period before checking again
            } catch (InterruptedException ex) {
                Logger.getLogger(Machine.class.getName()).log(Level.SEVERE, null, ex);
            }

            lock.notifyAll(); // Notifying waiting threads about the refill
        }
    }

    // Method to replace toner in the machine
    public void replaceToner(int amount) {
        synchronized (lock) {
            System.out.println("Lock acquired for refill.");
            tonerLevel += amount; // Increasing toner level after replacement
            System.out.println("Replacing toner by " + amount + " units.");
            System.out.println("Lock released. Tuner refill completed.");
   Random random = new Random(); 
            int sleepTime =random.nextInt(6000); // Generating random sleep interval
            try {
                Thread.sleep(sleepTime); // Sleeping for a random period before checking again
            } catch (InterruptedException ex) {
                Logger.getLogger(Machine.class.getName()).log(Level.SEVERE, null, ex);
            }
            lock.notifyAll(); // Notifying waiting threads about the toner replacement
        }
    }

    // Getter method to retrieve the current paper level
    public int getPaperLevel() {
        synchronized (lock) {
            return paperLevel;
        }
    }

    // Getter method to retrieve the current toner level
    public int getTonerLevel() {
        synchronized (lock) {
            return tonerLevel;
        }
    }
}


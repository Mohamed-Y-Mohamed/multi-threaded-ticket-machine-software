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
public class Paper_Technician implements Runnable {
   private final Machine machine; // Reference to the machine needing paper refills
    private final int refillAmount; // Amount of paper to be refilled
    private Random random; // Random generator for sleep intervals

    // Constructor initializing the paper technician with the machine and refill amount
    public Paper_Technician(Machine machine, int refillAmount) {
        this.machine = machine;
        random = new Random(); // Initializing random generator for sleep intervals

        this.refillAmount = refillAmount;
    }

    @Override
    public void run() {
        while (true) {
            // Check if paper level is below the threshold for refill
            if (machine.getPaperLevel() == 0) {
                synchronized (machine) {
                    machine.refillPaper(refillAmount); // Refilling paper in the machine
                    System.out.println("Paper level is: " + machine.getPaperLevel()); // Printing current paper level
                }
            }
            try {
                int sleepTime = random.nextInt(6000); // Generating random sleep interval
                Thread.sleep(sleepTime); // Sleeping for a random period before checking again
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

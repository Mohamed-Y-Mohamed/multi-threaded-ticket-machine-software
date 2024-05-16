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
public class Tuner_Technician implements Runnable {
   private Machine machine; // Reference to the machine needing toner replacement
    private int refillAmount; // Amount of toner to be replaced
    private Random random; // Random generator for sleep intervals

    // Constructor initializing the toner technician with the machine and refill amount
    public Tuner_Technician(Machine machine, int refillAmount) {
        this.machine = machine;
        this.refillAmount = refillAmount;
        random = new Random(); // Initializing random generator for sleep intervals
    }

    @Override
    public void run() {
        while (true) {
            // Check if toner level is below the threshold for replacement
            if (machine.getTonerLevel() == 0) {
                synchronized (machine) {
                    machine.replaceToner(refillAmount); // Replacing toner in the machine
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

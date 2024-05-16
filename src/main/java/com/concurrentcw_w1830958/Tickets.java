/*
Name: Mohamed Mohamed
 Student ID: w1830958
 */package com.concurrentcw_w1830958;

/**
 *
 * @author Mym19
 */
public class Tickets implements Ticket{
     private int amount; // Quantity of tickets
    private String name; // Passenger's name

    // Constructor initializing the ticket with the ticket quantity and passenger's name
    public Tickets(int amount, String name) {
        this.amount = amount;
        this.name = name;
    }

    @Override
    public void printTicket() {
        // Printing information about the purchased tickets for a specific passenger
        System.out.println("Printing ticket for passenger " + name + " and has bought a total of: " + amount + " Ticket(s).");
    }
}

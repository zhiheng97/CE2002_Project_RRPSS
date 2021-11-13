package Models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A model that represents a reservation made by a customer for 
 * a meal in the future. The reservation can only be made at least 2 hours in advance.
 * Each reservation will be expired after 15 minutes not checking in.
 * 
 * @author  @Henry-Hoang
 * @since 10 October 2021
 */
public class Reservation {

	private String res_id;
	private int cust_id;
	private Date dateTime;
	private String time;
	private int noPax = 2;

	/**
	 * Constructs the Reservation object by its ID, customer ID, time date, and number of pax.
	 * 
	 * @param 	res_id 		The ID of this reservation.
	 * @param 	cust_id 	The ID of the customer who made this reservation.
	 * @param 	date 		The Date object represents the time date of this reservation.
	 * @param 	pax 		Number of pax for this reservation.
	 */
	public Reservation(String res_id, int cust_id, Date date, int pax) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy HH:mm");
		this.res_id = res_id;
		this.cust_id = cust_id;
		this.dateTime = date;
		this.time = sdf.format(date);
		this.noPax = pax;
	}

	/**
	 * Gets the customer ID of this reservation.
	 * 
	 * @return	the customer ID of this reservation.
	 */
	public int getCustId() { 
		return this.cust_id; 
	}

	/**
	 * Gets the time date as a Date object of this reservation.
	 * 
	 * @return	the time date as a Date object of this reservation.
	 */
	public Date getDate() { 
		return this.dateTime; 
	}

	
	/**
	 * Gets the number of pax of this reservation.
	 * 
	 * @return	the number of pax of this reservation.
	 */
	public int getNoPax() { 
		return this.noPax; 
	}

	/**
	 * Gets the time date as a String object of this reservation.
	 * 
	 * @return	the time date as a String object of this reservation.
	 */
	public String getTime() { return this.time; }

	/**
	 * Gets the ID of this reservation.
	 * 
	 * @return	the ID of this reservation
	 */
	public String getResId() { 
		return this.res_id; 
	}

	/**
	 * Prints the information of this reservation. 
	 */
	public void print() {
		System.out.println("Reservation ID: " + this.res_id);
		System.out.println("Customer ID: " + this.cust_id);
		System.out.println("Date and time of the reservation: " + this.time);
		System.out.println("Number of guests: " + this.noPax);
	}

}
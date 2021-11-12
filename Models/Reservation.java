/**
 * A model that represents the reservations for each table.
 * @author  @Henry-Hoang
 * @since 10 October 2021
 */
package Models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {

	private String res_id;
	private int cust_id;
	private Date dateTime;
	private String time;
	private int noPax = 2;

	/**
	 * res_id,table_id,cust_id,res_datetime,pax
	 * Constructor for Reservation Class
	 * @param res_id 	identifier for reservation object
	 * @param cust_id 	identifier for the customer 
	 * @param date 		Date of reservation, Date Object
	 * @param pax 		Number of guests the reservation was made for
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
	 * Gets the res_id of this reservation
	 * @return res_id 
	 */
	public String getResId() { return this.res_id; }

	/**
	 * Gets the date of this reservation
	 * @return Date object
	 */
	public Date getDate() { return this.dateTime; }

	/**
	 * Gets the customer_id of this reservation
	 * @return cust_id
	 */
	public int getCustId() { return this.cust_id; }
	/**
	 * Gets the number of guests this reservation is made for
	 * @return noPax of this reservation
	 */
	public int getNoPax() { return this.noPax; }

	/**
	 * Gets the time as a string of this reservation
	 * @return time of this reservation
	 */
	public String getTime() { return this.time; }

	/**
	 * Prints the information about this reservation. Calls customer object to print customer information.
	 */
	public void print() {
		System.out.println("Reservation ID: " + this.res_id);
		System.out.println("Customer ID: " + this.cust_id);
		System.out.println("Date and time of the reservation: " + this.time);
		System.out.println("Number of guests: " + this.noPax);
	}

}
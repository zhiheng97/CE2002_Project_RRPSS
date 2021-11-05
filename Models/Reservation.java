package Models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {

	private String res_id;
	private int cust_id;
	private Date dateTime;
	private String time;
	private int noPax = 2;
	private static final String DATETIME_FORMAT_PATTERN = "EEE MMM yy HH:mm:ss z yyyy";

	/**
	 * res_id,table_id,cust_id,res_datetime,pax
	 * Constructor for Reservation Class
	 * @param res_id identifier for reservation object
	 * @param custName Customer's name, for customer object
	 * @param resDate Date of reservation, Date Object
	 * @param contact Customer's mobile number, for customer object
	 * @param pax Number of guests the reservation was made for
	 */
	public Reservation(String res_id, int cust_id, Date resDate, int pax) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT_PATTERN);
		this.res_id = res_id;
		this.cust_id = cust_id;
		this.dateTime = resDate;
		this.time = sdf.format(resDate);
		this.noPax = pax;
	}

	/**
	 * Gets the customer object of this reservation
	 * @return Customer object
	 */
	// public Customer getCustomer() {
	// 	return this.customer;
	// }

	/**
	 * Gets the date of this reservation
	 * @return Date object
	 */
	public Date getDate() {
		return this.dateTime;
	}

	/**
	 * Gets the id of this reservation
	 * @return id of this reservation
	 */
	public String getResId() {
		return this.res_id;
	}
	public int getCustId() {
		return this.cust_id;
	}
	/**
	 * Gets the number of guests this reservation is made for
	 * @return noPax of this reservation
	 */
	public int getNoPax() {
		return this.noPax;
	}

	/**
	 * Gets the time fo this reservation
	 * @return time of this reservation
	 */
	public String getTime() {
		return this.time;
	}

	/**
	 * Prints the information about this reservation. Calls customer object to print customer information.
	 */
	public void print() {
		System.out.println("Reservation ID: " + this.res_id);
		System.out.println("Customer ID: " + this.cust_id);
		System.out.println("Date and time of the reservation: " + this.dateTime.toString());
		System.out.println("Number of guests: " + this.noPax);
	}

	/**
	 * Sets the customer object
	 * @param cust customer object to be set
	 */
	// public void setCustomer(Customer cust) {
	// 	this.customer = cust;
	// }

	/**
	 * Sets the date of this reservation
	 * @param date date of reservation to be set
	 */
	public void setDate(Date date) {
		this.dateTime = date;
	}

	/**
	 * Sets the number of guests for this reservation
	 * @param noOfPax number of guests to be set
	 */
	public void setNoPax(int noOfPax) {
		this.noPax = noOfPax;
	}

	/**
	 * Sets the time of this reservation
	 * @param time time of reservation to be set
	 */
	public void setTime(String time) {
		this.time = time;
	}

}
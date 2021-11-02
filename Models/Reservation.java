package Models;

import java.util.Date;

public class Reservation {

	private int id;
	private Customer customer;
	private Date dateTime;
	private String time;
	private int noPax = 2;

	/**
	 * Constructor for Reservation Class
	 * @param id identifier for reservation object
	 * @param custName Customer's name, for customer object
	 * @param resDate Date of reservation, Date Object
	 * @param contact Customer's mobile number, for customer object
	 * @param pax Number of guests the reservation was made for
	 */
	public Reservation(int id, String custName, Date resDate, int contact, int pax) {
		if (id < 0) {
			throw new UnsupportedOperationException("Invalid customer ID.");
		}
		this.id = id;
		this.customer = new Customer(id, custName, false, contact);
		this.dateTime = resDate;
		this.noPax = pax;
	}

	/**
	 * Gets the customer object of this reservation
	 * @return Customer object
	 */
	public Customer getCustomer() {
		return this.customer;
	}

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
	public int getId() {
		return this.id;
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
		System.out.println("Reservation Id and Customer Id: " + this.id);
		System.out.print("Customer details: ");
		this.customer.print();
		System.out.println("Date of reservation: " + this.dateTime.toString());
		System.out.println("Time of reservation: " + this.time);
		System.out.println("Number of guests: " + this.noPax);
	}

	/**
	 * Sets the customer object
	 * @param cust customer object to be set
	 */
	public void setCustomer(Customer cust) {
		this.customer = cust;
	}

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
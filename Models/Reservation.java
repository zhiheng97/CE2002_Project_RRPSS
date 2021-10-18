package Models;

public class Reservation {

	private int id;
	private Customer customer;
	private String date;
	private String time;
	private int noPax = 2;

	/**
	 * 
	 * @param id
	 * @param custName
	 * @param resDate
	 * @param resTime
	 * @param contact
	 * @param pax
	 */
	public Reservation(int id, String custName, String resDate, String resTime, int contact, int pax) {
		if (id < 0) {
			throw new UnsupportedOperationException("Invalid customer ID.");
		}
		this.id = id;
		this.customer = new Customer(id, custName, false, contact);
		this.date = resDate;
		this.time = resTime;
		this.noPax = pax;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public String getDate() {
		return this.date;
	}

	public int getId() {
		return this.id;
	}

	public int getNoPax() {
		return this.noPax;
	}

	public String getTime() {
		return this.time;
	}

	public void setCustomer(Customer cust) {
		this.customer = cust;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNoPax(int noOfPax) {
		this.noPax = noOfPax;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
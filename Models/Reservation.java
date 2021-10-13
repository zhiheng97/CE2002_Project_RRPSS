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
		// TODO - implement Reservation.Reservation
		throw new UnsupportedOperationException();
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

	public Customer setCustomer() {
		// TODO - implement Reservation.setCustomer
		throw new UnsupportedOperationException();
	}

	public String setDate() {
		// TODO - implement Reservation.setDate
		throw new UnsupportedOperationException();
	}

	public int setId() {
		// TODO - implement Reservation.setId
		throw new UnsupportedOperationException();
	}

	public int setNoPax() {
		// TODO - implement Reservation.setNoPax
		throw new UnsupportedOperationException();
	}

	public String setTime() {
		// TODO - implement Reservation.setTime
		throw new UnsupportedOperationException();
	}

}
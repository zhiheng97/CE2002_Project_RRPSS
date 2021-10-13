package Models;

public class Table {

	private int tableNo;
	private boolean isOccupied;
	private Reservation[] reservation;
	private int seats = 2;
	private Order invoice;

	/**
	 * 
	 * @param table
	 * @param occupied
	 * @param seatAvail
	 */
	public Table(int table, boolean occupied, int seatAvail) {
		// TODO - implement Table.Table
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param reserve
	 */
	public void addReservation(Reservation reserve) {
		// TODO - implement Table.addReservation
		throw new UnsupportedOperationException();
	}

	public Order getInvoice() {
		return this.invoice;
	}

	public boolean getIsOccupied() {
		return this.isOccupied;
	}

	public Reservation[] getReservations() {
		// TODO - implement Table.getReservations
		throw new UnsupportedOperationException();
	}

	public int getSeats() {
		return this.seats;
	}

	public void print() {
		// TODO - implement Table.print
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param order
	 */
	public void setInvoice(Order order) {
		this.invoice = order;
	}

	/**
	 * 
	 * @param occupy
	 */
	public void setIsOccupied(boolean occupy) {
		this.isOccupied = occupy;
	}

	/**
	 * 
	 * @param occupancy
	 */
	public void setSeats(int occupancy) {
		this.seats = occupancy;
	}

}
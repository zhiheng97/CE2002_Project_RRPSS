package Models;

import java.util.ArrayList;
import java.util.List;

public class Table {

	private int tableNo;
	private boolean isOccupied;
	private List<Reservation> reservation = new ArrayList<Reservation>();
	private int seats = 2;
	private Order invoice;

	/**
	 * 
	 * @param table
	 * @param occupied
	 * @param seatAvail
	 */
	public Table(int table, boolean occupied, int seatAvail) {
		this.tableNo = table;
		this.isOccupied = occupied;
		this.reservation = new ArrayList<Reservation>(15);
		this.seats = seatAvail;
	}

	/**
	 * 
	 * @param reserve
	 */
	public void addReservation(Reservation reserve) {
		
	}

	public Order getInvoice() {
		return this.invoice;
	}

	public boolean getIsOccupied() {
		return this.isOccupied;
	}

	public List<Reservation> getReservations() {
		return this.reservation;
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
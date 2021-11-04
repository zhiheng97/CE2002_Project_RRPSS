package Models;

import java.util.ArrayList;
import java.util.List;

public class Table {

	private int tableNo;
	private boolean isOccupied;
	private List<Reservation> reservations;
	private int seats = 2;
	private Order invoice;
	public int noOfReseravtions = 0;

	/**
	 * 
	 * @param table
	 * @param occupied
	 * @param seatAvail
	 */
	public Table(int tableNo, boolean occupied, int seatAvail) {
		this.tableNo = tableNo;
		this.isOccupied = occupied;
		this.reservations = new ArrayList<Reservation>();
		this.seats = seatAvail;
		this.invoice = new Order(null, null);
	}

	public void addToOrder(Item item, int quantity) {
		this.invoice.addToOrder(item, quantity);
	}

	public void addToOrder(Promotion promotion, int quantity) {
		this.invoice.addToOrder(promotion, quantity);
	}

	public boolean removeFromOrder(Item item) {
		return this.invoice.removeFromOrder(item);
	}

	public boolean removeFromOrder(Promotion promotion) {
		return this.invoice.removeFromOrder(promotion);
	}

	/**
	 * 
	 * @param reserve
	 * @return
	 */
	public boolean addReservation(Reservation reserve) {
		if(noOfReseravtions == 15) return false;
		noOfReseravtions++;
		this.reservations.add(reserve);
		return true;
	}

	public Order getInvoice() {
		return this.invoice;
	}

	public boolean getIsOccupied() {
		return this.isOccupied;
	}

	public List<Reservation> getReservations() {
		return this.reservations;
	}

	public int getSeats() {
		return this.seats;
	}

	public int getTableNo() {
		return this.tableNo;
	}

	public void print() {
		System.out.println("The bill of table number: " + this.tableNo);
		this.invoice.print();
	}

	public boolean removeReservation(Reservation reservation) {
		noOfReseravtions--;
		return this.reservations.remove(reservation);
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
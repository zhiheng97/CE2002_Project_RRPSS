package Models;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Table {

	private int tableNo;
	private boolean isOccupied;
	private List<Reservation> reservations;
	private int seats = 2;
	private Order invoice;
	private int noOfReseravtions = 0;
	
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
		this.invoice = new Order(null, null, null);
	}

	/**
	 * add a quantity of Item objects to this table's order
	 * @param item
	 * @param quantity
	 */
	public void addToOrder(Item item, int quantity) {
		this.invoice.addToOrder(item, quantity);
	}

	/**
	 * add a quantity of Promotion objects to this table's order
	 * @param promotion
	 * @param quantity
	 */
	public void addToOrder(Promotion promotion, int quantity) {
		this.invoice.addToOrder(promotion, quantity);
	}

	/**
	 * remove Item object from this table's order
	 * @param item
	 * @return
	 */
	public boolean removeFromOrder(Item item) {
		return this.invoice.removeFromOrder(item);
	}

	/**
	 * remove Promotion object from this table's order
	 * @param promotion
	 * @return
	 */
	public boolean removeFromOrder(Promotion promotion) {
		return this.invoice.removeFromOrder(promotion);
	}

	/**
	 * Use when add new reservation
	 * @params cust_id, res_datetime, pax
	 * @return res_id
	 */
	public String addReservation(int cust_id, Date date, int pax) {
		if(this.noOfReseravtions == 15) return "false";
		
		this.noOfReseravtions++;
		int id=0;
		for (Reservation reservation : this.reservations) {
			String[] temp_id = reservation.getResId().split("-");
			if (id != Integer.parseInt(temp_id[1])) break;
			id++;
		}

		String res_id = String.valueOf(this.tableNo) + "-" + String.valueOf(id);
		this.reservations.add(new Reservation(res_id, cust_id, date, pax));
		return res_id;
	}

	/**
	 * Use when restaurant construction or when cannot update reservation
	 * @param reservation Reservation object
	 */
	public void addReservation(Reservation reservation) {
		this.noOfReseravtions++;
		this.reservations.add(reservation);
	}

	/**
	 * remove reservation by reservation's id of table
	 * Example, res_id is 5-4, then the input id is 4
	 * @param id
	 * @return
	 */
	public boolean removeReservation(String id) {
		noOfReseravtions--;
		for (Reservation res : this.reservations) {
			if (res.getResId().equals(id)) {
				this.reservations.remove(res);
				return true;
			}
		}
		return false;
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

	public int getNoOfReseravtions() {
		return this.noOfReseravtions;
	}
	public void print() {
		System.out.println("The bill of table number: " + this.tableNo);
		this.invoice.print(this.tableNo);
	}

	/**
	 * Use when construction or clear table
	 * set the new invoice for this order
	 * @param order
	 */
	public void setInvoice(Order order) {
		this.invoice = order;
	}

	/**
	 * @param occupy
	 */
	public void setIsOccupied(boolean occupy) {
		this.isOccupied = occupy;
	}

	/**
	 * not sure when to use for now
	 * @param occupancy
	 */
	public void setSeats(int occupancy) {
		this.seats = occupancy;
	}

}
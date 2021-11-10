package Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Table {

	private int tableNo;
	private boolean isOccupied;
	private List<Reservation> reservations;
	private int noOfReseravtions = 0;
	private int seats = 2;
	private Order invoice;
	
	/**
	 * 
	 * @param table
	 * @param occupied
	 * @param noPax
	 */
	public Table(int tableNo, boolean occupied, int noPax) {
		this.tableNo = tableNo;
		this.isOccupied = occupied;
		this.reservations = new ArrayList<Reservation>(15);
		this.seats = noPax;
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
	 * remove Item objects from the order of table tableNo
	 * 
	 * @param item Item to be removed
	 * @param quantity number of Item object to remove
	 * @return 2 if they are removed normally
	 * @return 1 if quantity >= current quantity in order (remove all anw)
	 * @return 0 if there is no Item in this order
	 */
	public int removeFromOrder(Item item, int quantity) {
		return this.invoice.removeFromOrder(item, quantity);
	}

	/**
	 * remove Promotion objects from the order of table tableNo
	 * 
	 * @param promotion 	Promotion to be removed
	 * @param quantity 		number of Promotion object to remove
	 * @return 2 if they are removed normally
	 * @return 1 if quantity is more than current quantity in order (remove all anw)
	 * @return 0 if there is no Promotion in this order
	 */
	public int removeFromOrder(Promotion promotion, int quantity) {
		return this.invoice.removeFromOrder(promotion, quantity);
	}

	/**
	 * print the current order of the table
	 */
	public void printOrder(boolean withPrice) {
		this.invoice.printOrder(withPrice);
	}

	/**
	 * Use when add new reservation
	 * @params cust_id, res_datetime, pax
	 * @return res_id
	 */
	public String addReservation(int cust_id, Date date, int pax) {
		if(this.noOfReseravtions == 15) return "false 1";
		
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
	 * remove reservation by res_id
	 * @param res_id
	 * @return
	 */
	public boolean removeReservation(String res_id) {
		noOfReseravtions--;
		for (Reservation res : this.reservations) {
			if (res.getResId().equals(res_id)) {
				this.reservations.remove(res);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param reservation
	 */
	public Reservation findReservation(String res_id) {
		for (Reservation res : this.reservations) {
			if (res.getResId().equals(res_id)) return res;
		}
		return null;
	}

	/**
	 * check if the table is reserved in the next 5 minutes
	 * upon the time the customer checkin
	 * @return
	 */
	public boolean isReserved() {
		Date date = new Date();
		for (Reservation res : this.reservations) {
			long time_diff = res.getDate().getTime() - date.getTime();
			if (time_diff < 300000) return true;	 
		}
		return false;
	}

	/**
	 * remove the reservation after 1 minute not checking in
	 * @param date
	 */
	public void deleteExpiredReservations(Date date) {
		List<Reservation> toClear = new ArrayList<Reservation>();
		for (Reservation res : this.reservations) {
			long time_diff = date.getTime() - res.getDate().getTime();
			if (time_diff > 60000) toClear.add(res); 
		}
		for (Reservation res : toClear) {
			this.reservations.remove(res);
			this.noOfReseravtions--;
		}
		toClear.clear();
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
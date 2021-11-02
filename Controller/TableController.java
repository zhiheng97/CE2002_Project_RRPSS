package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Models.Item;
import Models.Reservation;
import Models.Table;
import Models.Order;

public class TableController {

	private List<Table> tables = new ArrayList<Table>();

	private int noOfTables;

	/**
	 * 
	 * @param noOfTables
	 */
	public TableController(int noOfTables) {
		this.noOfTables = noOfTables;
		this.tables = new ArrayList<Table>(noOfTables);

		// fake list to test the order operations
		for (int i=0; i<noOfTables; i++) {
			tables.add(new Table(i+1, false, 2));
		}
	}

	public int getNoOfTables() {
		return this.noOfTables;
	}

	public void addToOrder(int tableNo, Item item, int quantity) {
		this.findTableByNo(tableNo).addToOrder(item, quantity);
	}

	public boolean removeFromOrder(int tableNo, Item item) {
		return this.findTableByNo(tableNo).removeFromOrder(item);
	}

	public void viewOrder(int tableNo) {
		Order invoice = this.findTableByNo(tableNo).getInvoice();
		List<Item> items = invoice.getItems();
		Map<Integer, Integer> item2quant = invoice.getOrderItems();

		System.out.println("Your current order is:");
		for (Item item : items) System.out.println(item.getName() + ": " + item2quant.get(item.getId()));
		System.out.printf("--> The current cost for this order is: %.2f\n\n", invoice.getTotal());
	}

	/**
	 * 
	 * @param details
	 */
	public boolean clearReservation(Reservation details) {
		return false;
	}

	/**
	 * 
	 * @param tableNo
	 */
	public Table findTableByNo(int tableNo) {
		if (tableNo < 1 || tableNo > noOfTables) {
			throw new UnsupportedOperationException("This table number is not in the restaurant.");
		}
		return this.tables.get(tableNo);
	}

	public void printAvailableTables() {
		// TODO - implement TableController.printAvailableTables
		throw new UnsupportedOperationException();
	}

	public void printInvoice() {
		// TODO - implement TableController.printInvoice
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 
	 * @param details
	 */
	public boolean reserveTable(String[] details) {
		// TODO - implement TableController.reserveTable
		throw new UnsupportedOperationException();
	}

}
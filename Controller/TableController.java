package Controller;

import java.util.ArrayList;
import java.util.List;

import Models.Item;
import Models.Reservation;
import Models.Table;

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
	}

	public int getNoOfTables() {
		return this.noOfTables;
	}

	/**
	 * 
	 * @param items
	 */
	public boolean addToOrder(Item[] items) {
		// TODO - implement TableController.addToOrder
		throw new UnsupportedOperationException();
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
	 * @param items
	 */
	public boolean removeFromOrder(Item[] items) {
		// TODO - implement TableController.removeFromOrder
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
package Models;

import java.util.ArrayList;
import java.util.List;

public class Order {

	private List<Item> items = new ArrayList<Item>();
	private Staff placedBy;
	private double total = 0.0;
	private String timestamp;
	private Promotion[] promotion;

	/**
	 * 
	 * @param createdBy
	 * @param dateTime
	 */
	public Order(Staff createdBy, String dateTime) {
		// TODO - implement Order.Order
		throw new UnsupportedOperationException();
	}

	public void print() {
		// TODO - implement Order.print
		throw new UnsupportedOperationException();
	}

	public List<Item> getItems() {
		return this.items;
	}

	public String getPlacedBy() {
		// TODO - implement Order.getPlacedBy
		throw new UnsupportedOperationException();
	}

	public String getTimeStamp() {
		// TODO - implement Order.getTimeStamp
		throw new UnsupportedOperationException();
	}

	public int getTotal() {
		// TODO - implement Order.getTotal
		throw new UnsupportedOperationException();
	}

}
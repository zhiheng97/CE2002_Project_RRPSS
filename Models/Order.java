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
		this.timestamp = dateTime;
		this.placedBy = createdBy;
	}

	public void print() {
		// TODO - implement Order.print
		throw new UnsupportedOperationException();
	}

	public List<Item> getItems() {
		return this.items;
	}

	public String getPlacedBy() {
		return this.placedBy.getName();
	}

	public String getTimeStamp() {
		return this.timestamp;
	}

	public double getTotal() {
		return this.total;
	}

}
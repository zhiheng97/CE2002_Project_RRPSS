package Models;

import java.util.ArrayList;
import java.util.List;

public class Order {

	private Staff placedBy;
	private String timestamp;
	private List<Item> items = new ArrayList<Item>();
	private List<Promotion> promotion = new ArrayList<Promotion>();
	private double total = 0.0;

	/**
	 *
	 * @param createdBy
	 * @param dateTime
	 */
	public Order(Staff createdBy, String dateTime, List<Item> items, List<Promotion> promotion, double total) {
		this.timestamp = dateTime;
		this.placedBy = createdBy;
		this.items = items;
		this.promotion = promotion;
		this.total = total;
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

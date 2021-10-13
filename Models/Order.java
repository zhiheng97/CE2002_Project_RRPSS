package Models;

public class Order {

	private Item[] items;
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

	public Item[] getItems() {
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
package Models;

public class Promotion {

	private Item[] items;
	private String description;
	private double price;
	private int id;

	/**
	 * 
	 * @param desc
	 * @param cost
	 * @param itemsInc
	 */
	public Promotion(String desc, double cost, String[] itemsInc) {
		// TODO - implement Promotion.Promotion
		throw new UnsupportedOperationException();
	}

	public int getId() {
		return this.id;
	}

	public Item[] getItems() {
		return this.items;
	}

	/**
	 * 
	 * @param itemId
	 */
	public Item getItem(int itemId) {
		// TODO - implement Promotion.getItem
		throw new UnsupportedOperationException();
	}

	public String getDescription() {
		return this.description;
	}

	public double getPrice() {
		return this.price;
	}

	/**
	 * 
	 * @param itemId
	 */
	public Item lookUp(int itemId) {
		// TODO - implement Promotion.lookUp
		throw new UnsupportedOperationException();
	}

	public void print() {
		// TODO - implement Promotion.print
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param desc
	 */
	public void setDescription(String desc) {
		this.description = desc;
	}

	/**
	 * 
	 * @param cost
	 */
	public void setPrice(double cost) {
		this.price = cost;
	}

}
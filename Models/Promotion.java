package Models;

import java.util.ArrayList;
import java.util.List;

public class Promotion extends Item{
	private int id;
	private String description;
	private double price;
	private List<Item> items = new ArrayList<Item>();
	/**

	 *
	 * @param desc
	 * @param cost
	 * @param itemsInc
	 */
	public Promotion(String desc, double cost, String[] itemsInc) {
		// TODO - implement Promotion.Promotion
		//throw new UnsupportedOperationException();
		this.description = desc;
		this.cost = price;
		this.items = itemsInc;
	}

	public int getId() {
		return this.id;
	}

	public List<Item> getItems() {
		return this.items;
	}

	/**
	 *
	 * @param itemId
	 */
	public Item getItem(int itemId) {
		// TODO - implement Promotion.getItem
		//throw new UnsupportedOperationException();
		int i;
		for(i = 0; i < this.items.length; i++){
			if(this.items[i] == itemId) return items[i];
		}
		return null;
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

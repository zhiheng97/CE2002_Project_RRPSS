package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Order {

	private Staff placedBy;
	private String timestamp;
	private List<Item> items;
	private List<Promotion> promotions;
	private Map<Integer, Integer> item2quantity;
	private Map<Integer, Integer> promo2quantity;
	private double total;

	/**
	 *
	 * @param createdBy
	 * @param dateTime
	 * @param items
	 * @param promotion
	 * @param total
	 */
	public Order(Staff createdBy, String dateTime, double total) {
		this.timestamp = dateTime;
		this.placedBy = createdBy;
		this.items = new ArrayList<Item>();
		this.promotions = new ArrayList<Promotion>();
		this.item2quantity = new HashMap<Integer, Integer>();
		this.promo2quantity = new HashMap<Integer, Integer>();
		this.total = total;
	}

	public void print() {
		int quantity;
		System.out.println("--------------------------");
		System.out.println("Items:");
		for (Item item : this.items) {
			quantity = this.item2quantity.get(item.getId());
			System.out.printf(quantity + " x " + item.getName() + "\t %f\n", item.getPrice() * quantity);
		}

		System.out.println("--------------------------");
		System.out.println("Promotions:");
		for (Promotion promo : this.promotions) {
			quantity = this.promo2quantity.get(promo.getId());
			System.out.printf(quantity + " x " + promo.getName() + "\t %f\n", promo.getPrice() * quantity);
		}

		System.out.println("--------------------------");
		System.out.println("TOTAL AMOUNT: " + this.getTotal());
		System.out.println("Date: " + this.timestamp);
		System.out.println("Created by: " + this.getPlacedBy());
	}

	public void addToOrder(Item item, int quantity) {
		// update price
		this.total += item.getPrice() * quantity;

		// update invoice items
		int id = item.getId();
		if (this.item2quantity.containsKey(id)) 
			this.item2quantity.put(id, this.item2quantity.get(id) + quantity);
		else {
			this.items.add(item);
			this.item2quantity.put(id, quantity);
		}
	}

	public void addToOrder(Promotion promotion, int quantity) {
		// update price
		this.total += promotion.getPrice() * quantity;

		// update invoice items
		int id = promotion.getId();
		if (this.promo2quantity.containsKey(id)) 
			this.promo2quantity.put(id, this.promo2quantity.get(id) + quantity);
		else {
			this.promotions.add(promotion);
			this.promo2quantity.put(id, quantity);
		}
	}

	public boolean removeFromOrder(Item item) {
		// update invoice items
		int id = item.getId();
		if (!this.item2quantity.containsKey(id)) return false;
		
		this.items.remove(items.stream().filter(i -> i.getId() == id).findAny().orElse(null));
		int curQuantity = this.item2quantity.get(id);
		if (curQuantity == 1) this.item2quantity.remove(id);
		else this.item2quantity.replace(id, curQuantity, curQuantity - 1);
		
		// update price
		this.total -= item.getPrice();
		return true;
	}

	public boolean removeFromOrder(Promotion promotion) {
		// update invoice items
		int id = promotion.getId();
		if (!this.promo2quantity.containsKey(id)) return false;

		this.promotions.remove(promotions.stream().filter(p -> p.getId() == id).findAny().orElse(null));
		int curQuantity = this.promo2quantity.get(id);
		if (curQuantity == 1) this.promo2quantity.remove(id);
		else this.promo2quantity.replace(id, curQuantity, curQuantity - 1);
		
		// update price
		this.total -= promotion.getPrice();
		return true;
	}

	public Map<Integer, Integer> getOrderItems() {
		return this.item2quantity;
	}

	public Map<Integer, Integer> getOrderPromos() {
		return this.promo2quantity;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public List<Promotion> getPromo() {
		return this.promotions;
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

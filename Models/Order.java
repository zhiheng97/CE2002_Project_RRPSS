package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Order {

	private Staff placedBy;
	private String timestamp;
	private List<Item> items = new ArrayList<Item>();
	private Map<Integer, Integer> itemId2quantity;
	private List<Promotion> promotions = new ArrayList<Promotion>();
	private double total = 0.0;

	/**
	 *
	 * @param createdBy
	 * @param dateTime
	 * @param items
	 * @param promotion
	 * @param total
	 */
	public Order(Staff createdBy, String dateTime, List<Item> items, List<Promotion> promotion, double total) {
		this.timestamp = dateTime;
		this.placedBy = createdBy;
		this.items = items; //(items == null)? items : new ArrayList<Item>();
		this.itemId2quantity = new HashMap<Integer, Integer>();
		//this.promotions = promotion;
		this.total = total;
	}

	public void print() {
		// TODO - implement Order.print
		throw new UnsupportedOperationException();
	}

	public void addToOrder(Item item, int quantity) {
		// update price
		this.total += item.getPrice() * quantity;

		// update invoice items
		int id = item.getId();
		if (this.itemId2quantity.containsKey(id)) this.itemId2quantity.put(id, this.itemId2quantity.get(id) + quantity);
		else {
			this.items.add(item);
			this.itemId2quantity.put(id, quantity);
		}
	}

	public void addToOrder(Promotion promotion, int quantity) {
		// update price
		this.total += promotion.getPrice() * quantity;

		// update invoice items
		int id = promotion.getId();
		if (this.itemId2quantity.containsKey(id)) 
			this.itemId2quantity.put(id, this.itemId2quantity.get(id) + quantity);
		else {
			this.promotions.add(promotion);
			this.itemId2quantity.put(id, quantity);
		}
	}

	public boolean removeFromOrder(Item item) {
		// update price
		this.total -= item.getPrice();

		// update invoice items
		int id = item.getId();
		if (!this.itemId2quantity.containsKey(id)) return false;
		items.remove(items.stream().filter(i -> i.getId() == id).findAny().orElse(null));
		int curQuantity = this.itemId2quantity.get(id);
		if (curQuantity == 1) this.itemId2quantity.remove(id);
		else this.itemId2quantity.replace(id, curQuantity, curQuantity - 1);
		return true;
	}

	public boolean removeFromOrder(Promotion promotion) {
		// update price
		this.total -= promotion.getPrice();

		// update invoice items
		int id = promotion.getId();
		if (!this.itemId2quantity.containsKey(id)) return false;
		promotions.remove(promotions.stream().filter(p -> p.getId() == id).findAny().orElse(null));
		int curQuantity = this.itemId2quantity.get(id);
		if (curQuantity == 1) this.itemId2quantity.remove(id);
		else this.itemId2quantity.replace(id, curQuantity, curQuantity - 1);
		return true;
	}

	public Map<Integer, Integer> getOrderItems() {
		return this.itemId2quantity;
	}

	public void setItems(List<Item> items) {
		this.items = items;
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

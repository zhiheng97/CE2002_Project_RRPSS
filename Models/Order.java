package Models;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {

	private Staff placedBy;
	private Customer cust;
	private String timestamp;
	private List<Item> items;
	private List<Promotion> promotions;
	private Map<Integer, Integer> item2quantity;
	private Map<Integer, Integer> promo2quantity;
	private double total = 0.0;

	/**
	 * Constructor for Order Object
	 * 
	 * @param createdBy Indicates staff which created order, Staff Object
	 * @param dateTime  Indicates time which order is created, String Object
	 */
	public Order(Staff createdBy, Customer cust, String dateTime) {
		this.timestamp = dateTime;
		this.cust = cust;
		this.placedBy = createdBy;
		this.items = new ArrayList<Item>();
		this.promotions = new ArrayList<Promotion>();
		this.item2quantity = new HashMap<Integer, Integer>();
		this.promo2quantity = new HashMap<Integer, Integer>();
	}

	/**
	 * Prints quantity of each item, quantity of each promo, total amount, date and
	 * staff which placed order
	 */
	public void print(int table_id) {
		int quantity;
        String Itemformat = "  %-25s  ".concat(" %3d  ").concat("   %3$.2f %n");
		
        String line = new String(new char[48]).replace('\0', '-');

        System.out.println(line);
		System.out.printf("%-18s %d%n", "Table ID:", table_id);
		System.out.printf("%-18s %s%n", "Customer:", this.cust.getName());
		System.out.printf("%-18s %d%n", "Customer contact:", this.cust.getMobileNo());
		System.out.printf("%-18s %s%n", "Staff:", this.placedBy.getName());
		System.out.printf("%-18s %s%n", "Date & Time:", this.timestamp);
		System.out.println(line);
		System.out.printf("%-25s   %6s   %6s%n", "Promotion/Item Name", "Qty", "Amount");

		for (Promotion promo : this.promotions) {
			quantity = this.promo2quantity.get(promo.getId());
			System.out.printf(Itemformat, promo.getName(), quantity, promo.getPrice() * quantity);
		}
		
		for (Item item : this.items) {
			quantity = this.item2quantity.get(item.getId());
			System.out.printf(Itemformat, item.getName(), quantity, item.getPrice() * quantity);
		}
		System.out.println(line);
		System.out.printf("%-25s %18.2f%n", "Sub-Total Amount:", this.getTotal());
		System.out.printf("%-25s %18.2f%n", "7% GST:", this.getTotal() * 0.07);
		System.out.printf("%-25s %18.2f%n", "10% Membership Discount:", (this.cust.getIsMember())? this.getTotal() * 0.1 : 0.0);
		System.out.printf("%-25s %18.2f%n", "Total Amount:", (this.cust.getIsMember())? this.getTotal() * 0.97 : this.getTotal() * 1.07);
	}

	/**
	 * Adds an item and indicated quantities of it to Order
	 * 
	 * @param item     Item to be added
	 * @param quantity Quantity of item to be added
	 */
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

	/**
	 * Adds a promo and indicated quantities of it to Order
	 * 
	 * @param promotion Promotion to be added
	 * @param quantity  Quantity of promotion to be added
	 */
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

	/**
	 * Removes item from order
	 * 
	 * @param item Item to be removed
	 * @return True if item removed successfuly, False if otherwise
	 */
	public boolean removeFromOrder(Item item) {
		// update invoice items
		int id = item.getId();
		if (!this.item2quantity.containsKey(id))
			return false;

		int curQuantity = this.item2quantity.get(id);
		if (curQuantity == 1) {
			this.items.remove(item);
			this.item2quantity.remove(id);
		} else
			this.item2quantity.replace(id, curQuantity, curQuantity - 1);

		// update price
		this.total -= item.getPrice();
		return true;
	}

	/**
	 * Removes promotion from order
	 * 
	 * @param promotion Promotion to be removed
	 * @return True if promotion removed successfuly, False if otherwise
	 */
	public boolean removeFromOrder(Promotion promotion) {
		// update invoice items
		int id = promotion.getId();
		if (!this.promo2quantity.containsKey(id))
			return false;

		int curQuantity = this.promo2quantity.get(id);
		if (curQuantity == 1) {
			this.promotions.remove(promotion);
			this.promo2quantity.remove(id);
		} else
			this.promo2quantity.replace(id, curQuantity, curQuantity - 1);

		// update price
		this.total -= promotion.getPrice();
		return true;
	}

	/**
	 * Item Map getter (Key: item_id, Value: Quantity of item)
	 * 
	 * @return Map of (item_id,quantity)
	 */
	public Map<Integer, Integer> getOrderItems() {
		return this.item2quantity;
	}

	/**
	 * Promo Map getter (Key: promo_id, Value: Quantity of promo)
	 * 
	 * @return Map of (promo_name,quantity)
	 */
	public Map<Integer, Integer> getOrderPromos() {
		return this.promo2quantity;
	}

	/**
	 * Item list getter, contains list of items for the order
	 * 
	 * @return List of items
	 */
	public List<Item> getItems() {
		return this.items;
	}

	/**
	 * Promotion List getter
	 * 
	 * @return List of promotions
	 */
	public List<Promotion> getPromo() {
		return this.promotions;
	}

	/**
	 * Gets name of staff which placed the order
	 * 
	 * @return Name of Staff
	 */
	public String getPlacedBy() {
		return this.placedBy.getName();
	}

	/**
	 * Gets timestamp of order creation
	 * 
	 * @return Timestamp of order
	 */
	public String getTimeStamp() {
		return this.timestamp;
	}

	/**
	 * Order total getter
	 * 
	 * @return Total price of order
	 */
	public double getTotal() {
		return this.total;
	}

}

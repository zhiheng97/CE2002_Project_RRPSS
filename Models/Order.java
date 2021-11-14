package Models;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A model that represents the order/invoice of each table. 
 * Each table is allowed to have at most one order/invoice at a time.
 * 
 * @author	@Henry-Hoang
 * @since 	10 October 2021
 */
public class Order {

	private Staff placedBy;
	private Customer cust;
	private Date dateTime;
	private List<Item> items;
	private List<Promotion> promotions;
	private Map<Integer, Integer> item2quantity;
	private Map<Integer, Integer> promo2quantity;
	private double total = 0.0;
	SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
	
	/**
	 * Constructs the Order object.
	 * 
	 * @param 	createdBy 	The Staff object that indicates which staff created this order.
	 * @param 	date  		The Date object that indicates the time date when order is created.
	 */
	public Order(Staff createdBy, Customer cust, Date date) {
		this.dateTime = date;
		this.cust = cust;
		this.placedBy = createdBy;
		this.items = new ArrayList<Item>();
		this.promotions = new ArrayList<Promotion>();
		this.item2quantity = new HashMap<Integer, Integer>();
		this.promo2quantity = new HashMap<Integer, Integer>();
	}

	/**
	 * Adds a quantity of Item objects to the order of this table.
	 * 
	 * @param	item		The Item object to be added.
	 * @param	quantity	The number of Item objects to be added.
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
	 * Adds a quantity of Promotion objects to the order of this table.
	 * 
	 * @param 	promotion	The Promotion object to be added.
	 * @param 	quantity	The number of Promotion objects to be added.
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
	 * Gets the list of all Item objects in this order.
	 * 
	 * @return	A list of Item objects in this order.
	 */
	public List<Item> getItems() {
		return this.items;
	}

	/**
	 * Gets the list of all Promotion objects in this order.
	 * 
	 * @return	A list of Promotion objects in this order.
	 */
	public List<Promotion> getPromo() {
		return this.promotions;
	}

	/**
	 * Gets the map (Key: item id, Value: quantity of the item) in this report.
	 * 
	 * @return	The map (Key: item id, Value: quantity of the item) in this report.
	 */
	public Map<Integer, Integer> getOrderItems() {
		return this.item2quantity;
	}

	/**
	 * Gets the map (Key: promotion id, Value: quantity of the promotion) in this report.
	 * 
	 * @return	The map (Key: promotion id, Value: quantity of the promotion) in this report.
	 */
	public Map<Integer, Integer> getOrderPromos() {
		return this.promo2quantity;
	}

	/**
	 * Gets the name of staff who placed this order.
	 * 
	 * @return 	A String object that indicates the staff who placed this order.
	 */
	public String getPlacedBy() {
		return this.placedBy.getName();
	}

	/**
	 * Gets a Date object that indicates when this Order is created.
	 * 
	 * @return	A Date object that indicates when this order is created.
	 */
	public Date getDatetime() {
		return this.dateTime;
	}

	/**
	 * Gets a String object that indicates when this order is created.
	 * 
	 * @return 	A String object that indicates when this order is created.
	 */
	public String getTimeStamp() {
		return sdf.format(this.dateTime);
	}

	/**
	 * Gets the total price of all items and promotions in this order.
	 * 
	 * @return 	Total price of all items and promotions in this order.
	 */
	public double getTotal() {
		return this.total;
	}

	/**
	 * Prints the final bill of this order that includes 
	 * the quantity of each Item object, the quantity of each Promotion object, 
	 * the total amount, the date and the staff who placed this order.
	 */
	public void printInvoice(int table_id) {
		int quantity;
        String Itemformat = "  %-25s  ".concat(" %3d  ").concat("   %3$.2f %n");
		
        String line = new String(new char[48]).replace('\0', '-');

        System.out.println(line);
		System.out.printf("%-18s %d%n", "Table ID:", table_id);
		System.out.printf("%-18s %s%n", "Customer:", this.cust.getName());
		System.out.printf("%-18s %d%n", "Customer contact:", this.cust.getMobileNo());
		System.out.printf("%-18s %s%n", "Staff:", this.placedBy.getName());
		System.out.printf("%-18s %s%n", "Date & Time:", this.getTimeStamp());
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
	 * Prints the current status of the order of this table.
	 * 
	 * @param	withPrice	true to print the order's price when the customer checks out, false otherwise.
	 */
	public void printOrder(boolean withPrice) {
		System.out.println("The current order:");
		if (this.items.isEmpty() && this.promotions.isEmpty()) System.out.println("There is nothing in this order!");
		else {
			System.out.printf("  %2s  %-20s   %6s%n", "Id", "Name", "Qty");
			for (Item item : this.items) 
				System.out.printf(" %2d %-20s   %6d%n", item.getId(), item.getName(), item2quantity.get(item.getId()));
			for (Promotion promo : this.promotions) 
				System.out.printf(" %2d %-20s   %6d%n", promo.getId(), promo.getName(), promo2quantity.get(promo.getId()));
		}
		
		if (withPrice) System.out.printf("Total amount:  %.2f SGD%n", this.getTotal());
	}
	
	/**
	 * Removes a quantity of Item objects from the order of this table.
	 * 
	 * @param 	item 		The Item object to be removed.
	 * @param 	quantity 	The number of Item objects to be removed.
	 * @return 	2 if a quantity of item is removed from the order,<br>
	 * 			or 1 if all the occurrences of this item are removed from the order,<br>
	 * 			or 0 if cannot remove because there is no occurrence of this item in the order.
	 */
	public int removeFromOrder(Item item, int quantity) {
		int id = item.getId();
		if (!this.item2quantity.containsKey(id))
			return 0;

		int curQuantity = this.item2quantity.get(id);
		if (curQuantity <= quantity) {
			for (Item x : this.items) {
				if (x.getId() == item.getId()) {
					this.items.remove(x);
					break;
				}
			}
			this.item2quantity.remove(id);
			this.total -= item.getPrice() * curQuantity;
			return 1;
		} 
		
		this.item2quantity.replace(id, curQuantity, curQuantity - quantity);
		this.total -= item.getPrice() * quantity;
		return 2;
	}

	/**
	 * Removes a quantity of Promotion objects from the order of this table.
	 * 
	 * @param	promotion 	The Promotion object to be removed.
	 * @param 	quantity 	The number of Promotion objects to be removed.
	 * @return 	2 if a quantity of promotion is removed from the order,<br>
	 * 			or 1 if all the occurrences of this promotion are removed from the order,<br>
	 * 	  		or 0 if cannot remove because there is no occurrence of this promotion in the order.
	 */
	public int removeFromOrder(Promotion promotion, int quantity) {
		int id = promotion.getId();
		if (!this.promo2quantity.containsKey(id))
			return 0;

		int curQuantity = this.promo2quantity.get(id);
		if (curQuantity <= quantity) {
			for (Promotion x : this.promotions) {
				if (x.getId() == promotion.getId()) {
					this.promotions.remove(x);
					break;
				}
			}
			this.promo2quantity.remove(id);
			this.total -= promotion.getPrice() * curQuantity;
			return 1;
		} 
		
		this.promo2quantity.replace(id, curQuantity, curQuantity - quantity);
		this.total -= promotion.getPrice() * quantity;
		return 2;
	}
	

}

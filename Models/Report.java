package Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A model that represents the sales reports.
 * @author  @brianleect
 * @since 	10 October 2021
 */
public class Report {

	private List<Order> invoices = new ArrayList<Order>();
	private String date;
	private double salesRevenue = 0;
	private List<Item> items = new ArrayList<Item>();
	private List<Promotion> promotions = new ArrayList<Promotion>();;
	private Map<Integer, Integer> item2quantity = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> promo2quantity = new HashMap<Integer, Integer>();

	/**
	 * Constructor for Report Object
	 * 
	 * @param calDate // Date of report
	 */
	public Report(String calDate) {
		this.date = calDate;
	}

	/**
	 * Adds a new invoice to the report list.
	 * 
	 * @param	invoice		The Order object to be added to report list.
	 * @return	true if the invoice is added succssfully, false otherwise.
	 */
	public boolean addInvoice(Order invoice) {
		String[] timestamp = invoice.getDatetime().toString().split(" "); // .split(" ")[0]; // Get new date from order
		String invoiceDate = timestamp[0] + " " + timestamp[1] + " " + timestamp[2]; // Saves day month year

		if (!invoiceDate.equals(this.date)) {
			System.out.println("Date of invoice does match current report date.");
			return false; // Date does not match
		}

		int id;
		List<Item> orderItems = invoice.getItems();
		Map<Integer, Integer> tmpItem2quantity = invoice.getOrderItems();
		for (Item tmpItem : orderItems) {

			id = tmpItem.getId(); // Item id

			// Proceed to check for new items not added (Used for item price later)
			if (!this.item2quantity.containsKey(id)) {
				System.out.println("Does not contain " + tmpItem.getName() + "adding to report items");
				this.items.add(tmpItem);
				this.item2quantity.put(id, 0); // Initialize item id in item2quantity
			}

			// Updates item quantity in report
			this.item2quantity.put(id, this.item2quantity.get(id) + tmpItem2quantity.get(id));
		}

		List<Promotion> orderPromo = invoice.getPromo();
		Map<Integer, Integer> tmpPromo2quantity = invoice.getOrderPromos();
		for (Promotion tmpPromo : orderPromo) {

			id = tmpPromo.getId(); // Promo id

			// Proceed to check for new promos not added (Used for promo price later)
			if (!this.promo2quantity.containsKey(id)) {
				System.out.println("Does not contain " + tmpPromo.getName() + "adding to report promos");
				this.promotions.add(tmpPromo);
				this.promo2quantity.put(id, 0); // Initialize promo id in promo2quantity
			}

			// Updates promo quantity in report
			this.promo2quantity.put(id, this.promo2quantity.get(id) + tmpPromo2quantity.get(id));
		}

		this.invoices.add(invoice);
		this.salesRevenue += invoice.getTotal(); // Update sales revenue

		System.out.println("\nOrder of timestamp '" + invoice.getTimeStamp() + "' added successfully");
		this.print();
		return true;

	}

	/**
	 * Prints the daily information regarding, individual sales item (ala carte/promotion) and the total revenue.
	 */
	public void print() {
		int quantity;
		String Itemformat = "  %-25s  ".concat(" %3d  ").concat("   %3$.2f %n");

		System.out.printf("\nDate: %s\nDaily Sales revenue $%.2f\n", date, salesRevenue);

		System.out.println("--------------------------");
		System.out.println("Items:");
		for (Item item : this.items) {
			quantity = this.item2quantity.get(item.getId());
			System.out.printf(Itemformat, item.getName(), quantity, item.getPrice() * quantity);
		}

		System.out.println("--------------------------");
		System.out.println("Promotions:");
		for (Promotion promo : this.promotions) {
			quantity = this.promo2quantity.get(promo.getId());
			System.out.printf(Itemformat, promo.getName(), quantity, promo.getPrice() * quantity);
		}
	}

	/**
	 * Gets the list of all Order objects in this report.
	 * 
	 * @return	The list of all Order objects in this report.
	 */
	public List<Order> getInvoices() {
		return this.invoices;
	}

	/**
	 * Gets the list of all Item objects in this report.
	 * 
	 * @return	The list of all Item objects in this report.
	 */
	public List<Item> getItems() {
		return this.items;
	}

	/**
	 * Gets the list of all Promotion objects in this report.
	 * 
	 * @return	The list of all Promotion objects in this report.
	 */
	public List<Promotion> getPromo() {
		return this.promotions;
	}

	/**
	 * Gets the map (Key: item id, Value: quantity of the item) in this report.
	 * 
	 * @return	The map (Key: item id, Value: quantity of the item) in this report.
	 */
	public Map<Integer, Integer> getReportItems() {
		return this.item2quantity;
	}

	/**
	 * Gets the map (Key: promotion id, Value: quantity of the promotion) in this report.
	 * 
	 * @return	The map (Key: promotion id, Value: quantity of the promotion) in this report.
	 */
	public Map<Integer, Integer> getReportPromos() {
		return this.promo2quantity;
	}

	/**
	 * Gets the time date of this report as a String object.
	 * 
	 * @return	The time date of this report as a String object.
	 */
	public String getDateTime() {
		return this.date;
	}

	/**
	 * Gets the total sales revenue of this report.
	 * 
	 * @return	The total sales revenue of this report.
	 */
	public double getSalesRevenue() {
		return this.salesRevenue;
	}

}
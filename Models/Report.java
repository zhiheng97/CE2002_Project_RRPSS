package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

public class Report {

	private List<Order> invoices = new ArrayList<Order>();
	private String date;
	private double salesRevenue = 0;
	private LinkedHashMap<String, Integer> item_map = new LinkedHashMap<String, Integer>();
	private LinkedHashMap<String, Integer> promo_map = new LinkedHashMap<String, Integer>();

	/**
	 * Constructor for Report Object
	 * 
	 * @param calDate // Date of report
	 */
	public Report(String calDate) {
		this.date = calDate;
	}

	/**
	 * Adds Invoice to report list
	 * 
	 * @param invoice // Invoice to be added to report list
	 * @return True if invoice added, False if otherwise
	 */
	public boolean addInvoice(Order invoice) {
		String[] timestamp = invoice.getTimeStamp().split(" "); // .split(" ")[0]; // Get new date from order
		String invoiceDate = timestamp[0] + " " + timestamp[1] + " " + timestamp[2]; // Saves day month year

		if (!invoiceDate.equals(this.date)) {
			System.out.println("Date of invoice does match current report date.");
			return false; // Date does not match
		}

		invoices.add(invoice);
		this.salesRevenue += invoice.getTotal(); // Update sales revenue

		List<Item> invoice_items = invoice.getItems();
		Map<Integer, Integer> item_quantity_map = invoice.getOrderItems();
		int invoice_size = invoice_items.size();
		String item_name;
		int item_id;
		int count;

		// Increments item in array
		for (int i = 0; i < invoice_size; i++) {
			item_name = invoice_items.get(i).getName();
			item_id = invoice_items.get(i).getId();
			System.out.println("Item added: " + item_name);
			count = item_map.containsKey(item_name) ? item_map.get(item_name) : 0;
			item_map.put(item_name, count + item_quantity_map.get(item_id));
		}

		List<Promotion> promo_items = invoice.getPromo();
		Map<Integer, Integer> promo_quantity_map = invoice.getOrderPromos();
		int promo_size = promo_items.size();
		int promo_id;
		String promo_name;

		// Increments promo in array
		for (int i = 0; i < promo_size; i++) {
			promo_name = promo_items.get(i).getName();
			promo_id = promo_items.get(i).getId();
			count = promo_map.containsKey(promo_name) ? promo_map.get(promo_name) : 0;
			promo_map.put(promo_name, count + promo_quantity_map.get(promo_id));
		}

		System.out.println("\nOrder of timestamp '" + invoice.getTimeStamp() + "' added successfully");
		this.print();
		return true;

	}

	/**
	 * Prints daily information regarding, individual sales item (ala carte/promo) &
	 * total revenue
	 */
	public void print() {
		System.out.printf("\nDate: %s\nDaily Sales revenue $%.2f\n", date, salesRevenue);

		System.out.println("--------------------------");
		System.out.println("Items:");
		for (Map.Entry<String, Integer> e : item_map.entrySet())
			System.out.println(e.getValue() + " x " + e.getKey());
		// System.out.println("Item: " + e.getKey() + " Quantity: " + e.getValue());

		System.out.println("--------------------------");
		System.out.println("Promotions:");
		for (Map.Entry<String, Integer> e : promo_map.entrySet())
			System.out.println(e.getValue() + " x " + e.getKey());
	}

	/**
	 * Orders (order list) getter
	 * 
	 * @return list of all orders in this report
	 */
	public List<Order> getInvoices() {
		return this.invoices;
	}

	/**
	 * Item Map (LinkedHashMap) getter (Key: item name, item quantity)
	 * 
	 * @return LinkedHashMap of (item_name,quantity)
	 */
	// TODO: Not sure if want to change to <Int,Int> map with (item_id,quantity)
	public LinkedHashMap<String, Integer> getItemMap() {
		return this.item_map;
	}

	/**
	 * Promo Map (LinkedHashMap) getter (Key: promo name, Value: quantity of promo)
	 * 
	 * @return LinkedHashMap of (promo_name,quantity)
	 */
	// TODO: Not sure if want to change to <Int,Int> map with (promo_id,quantity)
	public LinkedHashMap<String, Integer> getPromoMap() {
		return this.promo_map;
	}

	/**
	 * Date getter
	 * 
	 * @return Date of current report as a string
	 */
	public String getDate() {
		return this.date;
	}

	/**
	 * Sales Revenue getter
	 * 
	 * @return the sales revenue of this report
	 */
	public double getSalesRevenue() {
		return this.salesRevenue;
	}

}
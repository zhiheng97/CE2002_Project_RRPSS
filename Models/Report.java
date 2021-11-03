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

		if (!invoice.getTimeStamp().split(" ")[0].equals(this.date)) {
			System.out.println("Date of invoice does match current report date.");
			return false; // Date does not match
		}

		invoices.add(invoice);
		this.salesRevenue += invoice.getTotal(); // Update sales revenue

		List<Item> invoice_items = invoice.getItems();
		int invoice_size = invoice_items.size();
		String item_name;
		int count;

		// Increments item in array
		for (int i = 0; i < invoice_size; i++) {
			item_name = invoice_items.get(i).getName();
			count = item_map.containsKey(item_name) ? item_map.get(item_name) : 0;
			item_map.put(item_name, count + 1);
		}

		List<Promotion> promo_items = invoice.getPromo();
		int promo_size = promo_items.size();
		String promo_name;

		// Increments promo in array
		for (int i = 0; i < promo_size; i++) {
			promo_name = promo_items.get(i).getName();
			count = promo_map.containsKey(promo_name) ? promo_map.get(promo_name) : 0;
			promo_map.put(promo_name, count + 1);
		}

		System.out.println("Order of timestamp '" + invoice.getTimeStamp() + "' added successfully");
		return true;

	}

	/**
	 * Prints daily information regarding, individual sales item (ala carte/promo) &
	 * total revenue
	 */
	public void print() {
		System.out.printf("\nDate: %s\nDaily Sales revenue $%.2f\n", date, salesRevenue);
		for (Map.Entry<String, Integer> e : item_map.entrySet())
			System.out.println("Item: " + e.getKey() + " Quantity: " + e.getValue());

		for (Map.Entry<String, Integer> e : promo_map.entrySet())
			System.out.println("Promo: " + e.getKey() + " Quantity: " + e.getValue());
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
package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

public class Report {

	private List<Order> invoices = new ArrayList<Order>();
	private String date;
	private int salesRevenue = 0;

	// TODO: Look into initializing all items/promos names into a hashtable, so we
	// do not have to repeatedly initialize the dictionary for each order
	// We can there proceed to increment quantity of relevant orders
	private LinkedHashMap<String, Integer> item_map = new LinkedHashMap<String, Integer>();

	/**
	 * 
	 * @param calDate // Date of report
	 */
	public Report(String calDate) {
		this.date = calDate;
	}

	/**
	 * 
	 * @param invoice // Invoice to be added to report list
	 */
	public int addInvoice(Order invoice) {

		if (!invoice.getTimeStamp().split(" ")[0].equals(this.date)) {
			System.out.println("Date of invoice does match current report date. Error.");
			return 0; // Date does not match
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

		System.out.println("Invoice successfully added to report");
		return 1;

	}

	// Prints daily information regarding, individual sales item (ala carte/promo) &
	// total revenue
	public void print() {
		System.out.printf("Date: %s\nDaily Sales revenue %d\n", date, salesRevenue);
		for (Map.Entry<String, Integer> e : item_map.entrySet())
			System.out.println("Item: " + e.getKey() + " Quantity: " + e.getValue());
	}

	public List<Order> getInvoices() {
		return this.invoices;
	}

	public String getDate() {
		return this.date;
	}

}
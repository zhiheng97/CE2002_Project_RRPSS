package Controller;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

import Models.Report;
import Models.Order;
import Models.Promotion;
import Models.Item;
import Models.Staff;

public class ReportController {

	private List<Report> reports = new ArrayList<Report>();
	private Report currentReport = new Report("NIL");
	private FileController fileController = new FileController();
	private final static String PATH_TO_ORDERS_FILE = Path.of("./orders.txt").toString();

	/**
	 * Constructor for the ReportController Class
	 */
	public ReportController() {
	}

	/**
	 * Adds an order to the relevant report
	 * 
	 * @param invoice // Order to be added to update Report
	 */
	public void addInvoice(Order invoice) {
		// TODO: Not sure if we are considering cases where completed orders do not come
		// sequentially for date?
		// Solution: Might want to consider creating a linkedhashmap with date as key?
		// Is it relevant though? Will a restaurant be taking orders around midnight?

		// Checks whether order matches the current date of report
		if (currentReport.addInvoice(invoice)) {
			System.out.println("Order of timestamp " + invoice.getTimeStamp() + "added successfully");
		} else {
			String newDate = invoice.getTimeStamp().split(" ")[0]; // Get new date from report

			if (currentReport.getDate() != "NIL")
				reports.add(currentReport); // Adds old report to report list

			currentReport = new Report(newDate); // Create new report for new date
			currentReport.addInvoice(invoice); // Adds order to newly created report fitting the date
		}
	}

	/**
	 * Prints report (monthly or daily) based on byMonth param, consists of start
	 * date , end date, Total price, Quantity of each item / Promo
	 * 
	 * @param byMonth Indicates whether report to be generated is latest month or
	 *                latest day
	 */
	public void print(boolean byMonth) {
		// TODO: Consider GST as output field? Just add 7% tax to total price?

		double salesRevenue = 0;

		if (reports.size() == 0) {
			System.out.println("No reports exist currently. Exiting");
			return;
		} else if (!byMonth)
			// Prints latest daily report if byMonth not specified
			reports.get(reports.size() - 1).print();
		else {

			LinkedHashMap<String, Integer> item_map = new LinkedHashMap<String, Integer>();
			LinkedHashMap<String, Integer> promo_map = new LinkedHashMap<String, Integer>();
			// Get last 30 reports
			int reports_size = reports.size();
			Report tmpReport;

			// Case 1: Report < 30
			if (reports_size < 30) {
				String start_date = reports.get(0).getDate();
				String end_date = reports.get(reports_size - 1).getDate();

				for (int i = 0; i < reports_size; i++) {
					tmpReport = reports.get(i);
					salesRevenue += tmpReport.getSalesRevenue(); // Update sales revenue
					int count;
					String item_name;
					LinkedHashMap<String, Integer> tmpReportItems = tmpReport.getItemMap();
					for (Map.Entry<String, Integer> e : tmpReportItems.entrySet()) {
						item_name = e.getKey();
						count = item_map.containsKey(item_name) ? item_map.get(item_name) : 0;
						item_map.put(item_name, count + e.getValue());
					}

					String promo_name;
					LinkedHashMap<String, Integer> tmpReportPromos = tmpReport.getPromoMap();
					for (Map.Entry<String, Integer> e : tmpReportPromos.entrySet()) {
						promo_name = e.getKey();
						count = promo_map.containsKey(promo_name) ? promo_map.get(promo_name) : 0;
						promo_map.put(promo_name, count + e.getValue());
					}

				}
				System.out.println("\n\n(Monthly Report " + "(" + reports.size() + ") ) Start: " + start_date
						+ " / End: " + end_date);
				System.out.println("Monthly Sales: " + salesRevenue);
				System.out.println("\nItems:");
				for (Map.Entry<String, Integer> e : item_map.entrySet())
					System.out.println("Item: " + e.getKey() + " Quantity: " + e.getValue());

				System.out.println("\nPromos:");
				for (Map.Entry<String, Integer> e : promo_map.entrySet())
					System.out.println("Promo: " + e.getKey() + " Quantity: " + e.getValue());
			}
			// Case 2: > 30 reports
			else if (reports_size > 30) {
				return;
			}

		}
	}
}

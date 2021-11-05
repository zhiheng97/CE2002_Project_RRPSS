package Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Models.Order;
import Models.Report;
import Models.Item;
import Models.Promotion;

public class ReportController {

	private List<Report> reports = new ArrayList<Report>();

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
		Report currentReport;
		String[] timestamp = invoice.getTimeStamp().split(" "); // .split(" ")[0]; // Get new date from order
		String orderDate = timestamp[0] + " " + timestamp[1] + " " + timestamp[2]; // Saves day month year
		System.out.println("Timestamp of date only: " + orderDate);
		int report_size = reports.size();

		System.out.println("(Report Controller) Order date received: " + orderDate);

		// Case 1: If no reports we create new report and add directly
		if (report_size == 0) {
			currentReport = new Report(orderDate); // Create new report for date
			currentReport.addInvoice(invoice); // Adds order to newly created report fitting the date
			this.reports.add(currentReport); // Adds to report list
		}
		// Case 2: Checks whether order matches the date of latest report
		else if (reports.get(report_size - 1).addInvoice(invoice)) {
			System.out.println(
					"(Match Latest Date) Order of timestamp " + invoice.getTimeStamp() + " added successfully");
		}
		// Case 3: Order date does not match.
		else {

			// Case 3a: Order exists in reports and is a day before due to order created
			// near midnight
			if (report_size >= 2 && reports.get(report_size - 2).addInvoice(invoice))
				System.out.println("Non-sequential order detected");
			else {
				// Case 3b: Order does not exist in reports
				System.out.println(
						"(New Report Created) Order of timestamp " + invoice.getTimeStamp() + " added successfully");
				currentReport = new Report(orderDate); // Create new report for date
				currentReport.addInvoice(invoice); // Adds order to newly created report fitting the date
				this.reports.add(currentReport); // Adds to report list
			}
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
		int reports_size = reports.size();

		if (reports.size() == 0) {
			System.out.println("No reports exist currently. Exiting");
			return;
		} else if (!byMonth || reports_size == 1)
			// Prints latest daily report if byMonth not specified
			reports.get(reports.size() - 1).print();
		else {
			double salesRevenue = 0;
			Map<Integer, Integer> item2quantity = new HashMap<Integer, Integer>();
			Map<Integer, Integer> promo2quantity = new HashMap<Integer, Integer>();
			List<Item> items = new ArrayList<Item>();
			List<Promotion> promotions = new ArrayList<Promotion>();
			// Get last 30 reports

			int start_index = reports_size - 1;
			int end_index;
			Report tmpReport;

			if (reports_size < 30) {
				end_index = reports_size - 1; // End will always point to latest date
				start_index = 0;
			} else {
				end_index = reports_size - 1;
				start_index = end_index - 30 + 1;
			}

			String start_date = reports.get(start_index).getDate();
			String end_date = reports.get(end_index).getDate();

			for (int i = 0; i < end_index + 1; i++) {
				tmpReport = reports.get(i);
				salesRevenue += tmpReport.getSalesRevenue(); // Update sales revenue
				int count;
				int id;

				List<Item> reportItems = tmpReport.getItems();
				Map<Integer, Integer> tmpItem2quantity = tmpReport.getReportItems();
				for (Item tmpItem : reportItems) {

					id = tmpItem.getId(); // Item id

					// Proceed to check for new items not added (Used for item price later)
					if (!items.contains(tmpItem)) {
						items.add(tmpItem);
						item2quantity.put(id, 0); // Initialize item id in item2quantity
					}

					// Updates item quantity
					item2quantity.put(id, item2quantity.get(id) + tmpItem2quantity.get(id));
				}

				List<Promotion> orderPromo = tmpReport.getPromo();
				Map<Integer, Integer> tmpPromo2quantity = tmpReport.getReportPromos();
				for (Promotion tmpPromo : orderPromo) {

					id = tmpPromo.getId(); // Promo id

					// Proceed to check for new promos not added (Used for promo price later)
					if (!promotions.contains(tmpPromo)) {
						promotions.add(tmpPromo);
						promo2quantity.put(id, 0); // Initialize promo id in promo2quantity
					}

					// Updates promo quantity in report
					promo2quantity.put(id, promo2quantity.get(id) + tmpPromo2quantity.get(id));
				}

			}
			int quantity;
			String Itemformat = "  %-25s  ".concat(" %3d  ").concat("   %3$.2f %n");

			System.out.println(
					"\n\n(Monthly Report " + "(" + reports.size() + ") ) Start: " + start_date + " / End: " + end_date);
			System.out.println("Monthly Sales: " + salesRevenue);
			System.out.println("--------------------------");
			System.out.println("Items:");
			for (Item item : items) {
				quantity = item2quantity.get(item.getId());
				System.out.printf(Itemformat, item.getName(), quantity, item.getPrice() * quantity);
			}

			System.out.println("--------------------------");
			System.out.println("Promotions:");
			for (Promotion promo : promotions) {
				quantity = promo2quantity.get(promo.getId());
				System.out.printf(Itemformat, promo.getName(), quantity, promo.getPrice() * quantity);
			}
		}
	}
}

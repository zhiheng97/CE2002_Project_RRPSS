package Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Models.Order;
import Models.Report;
import Models.Item;
import Models.Promotion;

/**
 * A controller that is responsible for managing the reports of the restaurant.
 * 
 * @author  @brianleect
 * @since 10 October 2021
 */
public class ReportController {

	private List<Report> reports = new ArrayList<Report>();

	/**
	 * Constructs the ReportController object.
	 */
	public ReportController() {
	}

	/**
	 * Adds an order to the relevant report.
	 *
	 * @param 	invoice		The Order object to be added to the Report.
	 */
	public void addInvoice(Order invoice) {
		Report currentReport;
		String[] timestamp = invoice.getDatetime().toString().split(" ");
		String orderDate = timestamp[0] + " " + timestamp[1] + " " + timestamp[2];

		int report_size = reports.size();

		// Case 1: If no reports we create new report and add directly
		if (report_size == 0) {
			currentReport = new Report(orderDate); // Create new report for date
			currentReport.addInvoice(invoice); // Adds order to newly created report fitting the date
			this.reports.add(currentReport); // Adds to report list
		}
		// Case 2: Checks whether order matches the date of latest report
		else if (reports.get(report_size - 1).addInvoice(invoice)) {
			System.out.println("(Match Latest Date) Order of timestamp " + invoice.getTimeStamp() + " added successfully");
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
	 * Prints the report (monthly or daily) based on a boolean byMonth parameter. 
	 * The report includes the start date, the end date, the total price, and the quantity of each Item or Promotion.
	 *
	 * @param	byMonth		A boolean that indicates whether report to be generated is 
	 * 						latest month or latest day.
	 */
	public void print(boolean byMonth, String timeNow) {

		if (reports.size() == 0) {
			System.out.println("No reports exists at all currently.");
			return;
		}

		int reports_size = reports.size();
		String[] timestamp = timeNow.split(" ");
		String currentDate = timestamp[0] + " " + timestamp[1] + " " + timestamp[2];
		Report latestReport = reports.get(reports.size() - 1);

		// If not byMonth, we assume the user is looking for DAILY report
		if (!byMonth) {
			String latestReportDate = latestReport.getDateTime();
			// Compare latest report date against current date
			if (latestReportDate.equals(currentDate))
				reports.get(reports.size() - 1).print();
			else {
				System.out.println("Last report date: '" + latestReportDate + "'");
				System.out.println("No report available today: '" + currentDate + "'");
			}

			return;
		} else {
			double salesRevenue = 0;
			int number_of_valid_reports = 0;
			Map<Integer, Integer> item2quantity = new HashMap<Integer, Integer>();
			Map<Integer, Integer> promo2quantity = new HashMap<Integer, Integer>();
			List<Item> items = new ArrayList<Item>();
			List<Promotion> promotions = new ArrayList<Promotion>();

			// Gets month of latest report
			String month = timeNow.split(" ")[1];
			Report tmpReport;
			String tmpMonth;
			// We start searching from most recent
			for (int i = reports_size - 1; i > -1; i--) {
				tmpReport = reports.get(i);
				tmpMonth = tmpReport.getDateTime().split(" ")[1];

				if (!tmpMonth.equals(month))
					break;

				number_of_valid_reports++;
				salesRevenue += tmpReport.getSalesRevenue(); // Update sales revenue
				int id;

				List<Item> reportItems = tmpReport.getItems();
				Map<Integer, Integer> tmpItem2quantity = tmpReport.getReportItems();
				for (Item tmpItem : reportItems) {

					id = tmpItem.getId(); // Item id

					// Proceed to check for new items not added (Used for item price later)
					if (!item2quantity.containsKey(id)) {
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
					if (!promo2quantity.containsKey(id)) {
						promotions.add(tmpPromo);
						promo2quantity.put(id, 0); // Initialize promo id in promo2quantity
					}

					// Updates promo quantity in report
					promo2quantity.put(id, promo2quantity.get(id) + tmpPromo2quantity.get(id));
				}
			}

			if (number_of_valid_reports == 0) {
				System.out.println("No reports for the current month found");
				System.out.println("Last report is found in the month of " + latestReport.getDateTime().split(" ")[1]);
				return;
			}

			int quantity;
			String Itemformat = "  %-25s  ".concat(" %3d  ").concat("   %3$.2f %n");

			System.out
					.println("\n\nMonthly Report: " + month + "\nNumber of reports: " + number_of_valid_reports + "\n");
			System.out.println("Monthly Sales: $" + salesRevenue);
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
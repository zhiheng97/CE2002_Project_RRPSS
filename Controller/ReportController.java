package Controller;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import Models.Report;
import Models.Order;
import Models.Promotion;
import Models.Item;
import Models.Staff;

public class ReportController {

	private List<Report> reports = new ArrayList<Report>();
	private FileController fileController = new FileController();
	private final static String PATH_TO_ORDERS_FILE = Path.of("./DB/orders.txt").toString();

	/**
	 * Constructor for the ReportController Class
	 */
	public ReportController() {
		List<String> tokens = fileController.readFile(PATH_TO_ORDERS_FILE);
		String prevCat = "", curCat = "";
		int i = 0;
		do {
			Staff tmpStaff = new Staff(Integer.parseInt(tokens.get(i)), tokens.get(i + 1), tokens.get(i + 2));
			String dateTime = tokens.get(i + 3);
			String dateOnly = dateTime.substring(dateTime.length() - 5, dateTime.length());

			List<Item> items = new ArrayList<Item>();

			int j = i + 4;
			do {
				Item tmpItem = new Item(Integer.parseInt(tokens.get(j)), tokens.get(j + 1), tokens.get(j + 2),
						Double.parseDouble(tokens.get(j + 3)));
				items.add(tmpItem);
				j += 4;
			} while (j < tokens.size() && !tokens.get(j).equals("ENDITEM"));

			int promo_index = 0;
			Promotion[] tmpPromoList;
			do {
				int promo_id = Integer.parseInt(tokens.get(j));
				String promo_name = tokens.get(j + 1);
				String promo_desc = tokens.get(j + 2);
				Double promo_cost = Double.parseDouble(tokens.get(j + 3));

				List<Item> promo_items = new ArrayList<Item>();

				j += 4; // Move to the start of items in promo
				do {
					Item tmpItem = new Item(Integer.parseInt(tokens.get(j)), tokens.get(j + 1), tokens.get(j + 2),
							Double.parseDouble(tokens.get(j + 3)));
					promo_items.add(tmpItem);
					j += 4;
				} while (j < tokens.size() && !tokens.get(j).equals("ENDPROMO"));

				// TODO: Either integrate with the promo constructor string
				// Alternatively, perhaps we could have method overloading but with List<items>
				// instead solving my issue here
				Promotion tmpPromo = new Promotion(promo_id, promo_name, promo_desc, promo_cost, promo_items);
				tmpPromoList[promo_index] = tmpPromo;
				promo_index++;
			} while (j < tokens.size() && !tokens.get(j).equals("ENDORDER"));
			i = j + 1; // Increment
		} while (i < tokens.size() && !tokens.get(i).equals("ENDFILE"));

	}

	/**
	 * @param report // Report to be added to list of reports
	 */
	public void addReport(Report report) {
		reports.add(report);
	}

	/**
	 * 
	 * @param byMonth
	 */
	public void print(boolean byMonth) {
		int salesRevenue = 0;

		// Get last 30? Or only within fixed month // Do we want an option for which
		// month?

		// TODO - implement ReportController.print
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param byMonth
	 */
	private int tabulateSales(boolean byMonth) {
		// TODO - implement ReportController.tabulateSales
		throw new UnsupportedOperationException();
	}

}
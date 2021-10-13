package Models;

import java.util.ArrayList;
import java.util.List;

public class Report {

	private List<Order> invoices = new ArrayList<Order>();
	private String date;

	/**
	 * 
	 * @param calDate
	 */
	public Report(String calDate) {
		// TODO - implement Report.Report
		throw new UnsupportedOperationException();
	}

	public void print() {
		// TODO - implement Report.print
		throw new UnsupportedOperationException();
	}

	public List<Order> getInvoices() {
		return this.invoices;
	}

	public String getDate() {
		return this.date;
	}

}
package Models;

public class Report {

	private Order[] invoices;
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

	public Order[] getInvoices() {
		return this.invoices;
	}

	public String getDate() {
		return this.date;
	}

}
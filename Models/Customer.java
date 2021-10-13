package Models;

public class Customer {

	private int id;
	private String name;
	private boolean isMember;
	private int mobileNo;

	/**
	 * 
	 * @param custId
	 * @param custName
	 * @param cusIsMember
	 * @param cusMobile
	 */
	public Customer(int custId, String custName, boolean cusIsMember, int cusMobile) {
		// TODO - implement Customer.Customer
		throw new UnsupportedOperationException();
	}

	public int getId() {
		return this.id;
	}

	public boolean getIsMember() {
		return this.isMember;
	}

	public int getMobileNo() {
		return this.mobileNo;
	}

	public String getName() {
		return this.name;
	}

}
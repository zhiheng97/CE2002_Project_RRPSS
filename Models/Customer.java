package Models;

public class Customer {

	private int id;
	private String name;
	private boolean isMember;
	private int mobileNo;

	/**
	 * Constructor for customer object
	 * @param custId identifier for customer object
	 * @param custName customer name
	 * @param cusIsMember a flag indicating if the customer is a member
	 * @param cusMobile customer mobile number
	 */
	public Customer(int custId, String custName, boolean cusIsMember, int cusMobile) {
		if (custId < 0) {
			throw new UnsupportedOperationException("Invalid customer ID.");
		}
		this.id = custId;
		this.name = custName;
		this.isMember = cusIsMember;
		this.mobileNo = cusMobile;
	}

	/**
	 * Gets the id of the customer object
	 * @return id of the customer object
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Gets the membership flag of the customer object
	 * @return true if customer is a member, otherwise false
	 */
	public boolean getIsMember() {
		return this.isMember;
	}

	/**
	 * Gets the mobile number of the customer object
	 * @return mobile number of the customer object
	 */
	public int getMobileNo() {
		return this.mobileNo;
	}

	/**
	 * Gets the customer's name
	 * @return name of the customer
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Prints the customer's information
	 */
	public void print() {
		System.out.println("Customer Name: " + this.name);
		System.out.println("Ismember: " + this.isMember);
	}

}
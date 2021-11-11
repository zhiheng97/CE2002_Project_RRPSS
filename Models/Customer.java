/**
 * A model that represents customers patronising the restaurant.
 * @author  @Henry-Hoang
 * @since 10 October 2021
 */
package Models;

public class Customer {

	private int id;
	private String name;
	private boolean isMember;
	private int mobileNo;

	/**
	 * Constructor for customer object
	 * @param cust_id identifier for customer object
	 * @param name customer name
	 * @param isMember a flag indicating if the customer is a member
	 * @param contact customer mobile number
	 */
	public Customer(int cust_id, String name, boolean isMember, int contact) {
		this.id = cust_id;
		this.name = name;
		this.isMember = isMember;
		this.mobileNo = contact;
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
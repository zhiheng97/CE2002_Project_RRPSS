package Models;

/**
 * A model that represents customers patronizing the restaurant.
 * 
 * @author  @Henry-Hoang
 * @since 10 October 2021
 */
public class Customer extends People {

	private int id;
	private String name;
	private boolean isMember;
	private int mobileNo;

	/**
	 * Constructs a Customer object by the ID, name, membership status, and contact number of this customer.
	 * 
	 * @param	cust_id		The ID of this customer.
	 * @param 	name 		The name of this customer.
	 * @param 	isMember 	A flag indicating if the customer has a membership.
	 * @param 	contact 	The mobile number of this customer.
	 */
	public Customer(int cust_id, String name, boolean isMember, int contact) {
		super(cust_id, name);
		this.id = cust_id;
		this.name = name;
		this.isMember = isMember;
		this.mobileNo = contact;
	}

	/**
	 * Gets the ID of this customer.
	 * 
	 * @return 	ID of this customer.
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Gets the membership flag of this customer.
	 * 
	 * @return	true if customer has a membership, false otherwise.
	 */
	public boolean getIsMember() {
		return this.isMember;
	}

	/**
	 * Gets the mobile number of this customer.
	 * 
	 * @return 	mobile number of this customer.
	 */
	public int getMobileNo() {
		return this.mobileNo;
	}

	/**
	 * Gets the name of this customer.
	 * 
	 * @return 	The name of this customer.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Prints the information of this customer.
	 */
	public void print() {
		System.out.println("Customer Name: " + this.name);
		System.out.println("Ismember: " + this.isMember);
	}

}
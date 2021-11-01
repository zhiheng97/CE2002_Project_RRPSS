package Models;

public class Item {

	private int id;
	private String name;
	private String description;
	private double price;

	/**
	 * Constructor for Item object when instantiated
	 * @param itemId The id of the object to create
	 * @param itemName The name of the object to create
	 * @param itemDesc The description of the object to create
	 * @param itemPrice The price of the object to create
	 */
	public Item(int itemId, String itemName, String itemDesc, double itemPrice) {
		this.id = itemId;
		this.name = itemName;
		this.description = itemDesc;
		this.price = itemPrice;
	}

	/**
	 * Gets the id of the item
	 * @return Id of the item
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Gets the name of the item
	 * @return Name of the item
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of the item.
	 * @param name Name to be assigned
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the description of the item
	 * @return Description of the item
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets the description of the item
	 * @param description Description to be assigned
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the price of the item
	 * @return Price of the item
	 */
	public double getPrice() {
		return this.price;
	}

	/**
	 * Sets the price of the item
	 * @param price Price to be assigned
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Prints the information about the item
	 */
	public void print() {
		System.out.println("Item Id: " + this.id);
		System.out.println("Item Name: " + this.name);
		System.out.println("Item Description: " + this.description);
		System.out.println("Item Price: " + this.price);
	}

	/**
	 * Makes a copy of this object
	 * @return Item that has been copied
	 */
	public Item copyOf(){
		return new Item(this.id, this.name, this.description, this.price);
	}

}

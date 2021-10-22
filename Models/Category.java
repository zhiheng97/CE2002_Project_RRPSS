package Models;

import java.util.ArrayList;
import java.util.List;

import Enumerations.*;

public class Category {

	private List<Item> items = new ArrayList<Item>();
	private Categories category;

	/**
	 * Constructor for Category Object
	 * @param categoryType - Indicates the category to be instantiated
	 */
	public Category(Categories categoryType) {
		this.category = categoryType;
	}

	/**
	 * Adds an item to the category. Performs a search in the category for duplicate items
	 * @param itemParams New item parameters
	 * @return True if item is added, false otherwise
	 */
	public boolean addItem(String[] itemParams) {
		int itemId = Integer.parseInt(itemParams[1]);
		if(lookUp(itemId) == null) {
			items.add(new Item
				(
					itemId,
					itemParams[0],
					itemParams[2],
					Double.parseDouble(itemParams[3])
				)
			);
			return true;
		}
		return false;
	}

	/**
	 * Gets the items that belong to this category
	 * @return A list of items
	 */
	public List<Item> getItems() {
		return this.items;
	}

	/**
	 * Gets the category type
	 * @return The category that the object is
	 */
	public Categories getCategory() {
		return this.category;
	}

	/**
	 * Method call to search for an Item in the category using the item's id or name
	 * @param itemId - The item to search for in the category
	 * @return Item that matches either the id or name
	 */
	public Item lookUp(String[] itemParams) {
		return items.stream()
			.filter(
				item -> item.getId() == Integer.parseInt(itemParams[0]) || 
				item.getName().equals(itemParams[1])
			)
			.findFirst()
			.orElse(null);
	}

		/**
	 * Method call to search for an Item in the category using the item's id
	 * @param itemId - The item to search for in the category
	 * @return Item that matches the id
	 */
	public Item lookUp(int itemId) {
		return items.stream()
			.filter(item -> item.getId() == itemId)
			.findFirst()
			.orElse(null);
	}

	/**
	 * Printing out the category information and what item is stored in the category
	 */
	public void print() {
		System.out.println("Category: " + this.category.toString());
		for(Item item : this.items){
			item.print();
		}
	}

	/**
	 * Removes item from the category based on item id
	 * @param itemId Id of item to be removed
	 * @return True if item found and removed, false otherwise
	 */
	public boolean removeItem(int itemId) {
		Item toRemove = lookUp(itemId);
		if(toRemove != null) {
			items.remove(toRemove);
			return true;
		}
		return false;
	}

	/**
	 * Sets the category type
	 * @param categoryType Category to set
	 */
	public void setCategory(Categories categoryType) {
		this.category = categoryType;
	}

}
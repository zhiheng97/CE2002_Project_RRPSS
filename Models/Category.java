/**
 * A model of a category in the restaurant's menu.
 * @author @zhiheng97
 * @since 10 October 2021
 */
package Models;

import java.util.ArrayList;
import java.util.List;

import Enumerations.Categories;

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
		int itemId = Integer.parseInt(itemParams[0]); //Try to parse the input as an integer
		if(lookUp(itemId) == null) { /*Performs a check to see if the itemId exists in the menu.
										If the item does not exist proceed to add it to the category*/
			items.add(new Item
				(
					itemId, //Id
					itemParams[1], //Name
					itemParams[2], //Description
					Double.parseDouble(itemParams[3]) //Price
				) //Adds the new item to the list of items in the category.
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
	 * @param itemParams - The item to search for in the category
	 * @return Item that matches either the id or name
	 */
	public Item lookUp(String[] itemParams) {
		return items.stream() //Performs a iterative search for a match of the itemId or the item's name in the list of items in the category.
			.filter(
				item -> item.getId() == Integer.parseInt(itemParams[0]) || 
				item.getName().equals(itemParams[1])
			)
			.findFirst() //Finds the first match
			.orElse(null); //If no match found, return null
	}

		/**
	 * Method call to search for an Item in the category using the item's id
	 * @param itemId - The item to search for in the category
	 * @return Item that matches the id
	 */
	public Item lookUp(int itemId) {
		return items.stream() //Performs a iterative search for a match of the itemId or the item's name in the list of items in the category.
			.filter(item -> item.getId() == itemId)
			.findFirst() //Finds the first match
			.orElse(null); //If no match found, return null
	}

	/**
	 * Printing out the category information and what item is stored in the category
	 */
	public void print() {
		System.out.println("Category: " + this.category.toString());
		for(Item item : this.items){ //Iterate through the list of items and call's the print method.
			item.print();
		}
	}

	/**
	 * Removes item from the category based on item id
	 * @param itemId Id of item to be removed
	 * @return True if item found and removed, false otherwise
	 */
	public boolean removeItem(int itemId) {
		Item toRemove = lookUp(itemId); //Calls lookUp to search for the item to remove
		if(toRemove != null) { //If the lookUp was successful, proceed to remove the item
			return items.remove(toRemove);
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
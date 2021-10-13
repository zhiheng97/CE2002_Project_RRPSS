package Models;

import java.util.ArrayList;
import java.util.List;

import Enumerations.*;

public class Category {

	private List<Item> items = new ArrayList<Item>();
	private Categories category;

	public List<Item> getItems() {
		return this.items;
	}

	/**
	 * 
	 * @param categoryType
	 */
	public Category(Categories categoryType) {
		// TODO - implement Category.Category
		throw new UnsupportedOperationException();
	}

	public Categories getCategory() {
		return this.category;
	}

	/**
	 * 
	 * @param itemId
	 */
	public Item lookUp(int itemId) {
		// TODO - implement Category.lookUp
		throw new UnsupportedOperationException();
	}

	public void print() {
		// TODO - implement Category.print
		throw new UnsupportedOperationException();
	}

	public void setCategory() {
		// TODO - implement Category.setCategory
		throw new UnsupportedOperationException();
	}

}
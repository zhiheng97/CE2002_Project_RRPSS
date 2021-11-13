/**
 * A model that represents the promotion packages which extends the class Item.
 * @author  @ghotinggoad
 * @since 10 October 2021
 */
package Models;

import java.util.ArrayList;
import java.util.List;

public class Promotion extends Item {
	private List<Item> items = new ArrayList<Item>();

	/**
	 * Constructor for Promotion Class
	 * @param id, id of the promotion
	 * @param name, name of the promotion
	 * @param desc, description of the promotion
	 * @param cost, price of the promotion
	 * @param itemsInc, the items' parameters in the order, id; name; description and price in string list
	 */
	public Promotion(int id, String name, String desc, double cost, List<String> itemsInc) {
		super(id, name, desc, cost);
		int i = 0;
		do{
			items.add(new Item(Integer.parseInt(itemsInc.get(i)), itemsInc.get(i+1), itemsInc.get(i+2), Double.parseDouble(itemsInc.get(i+3))));
			i += 4;
		}while(i < itemsInc.size());
	}

	/**
	* id getter
	* @return id of this promotion
	*/
	@Override
	public int getId() {
		return super.getId();
	}

	/**
	* Items (item list) getter
	* @return list of all items in this promotion
	*/
	public List<Item> getItems() {
		return this.items;
	}

	/**
	 * Item (singular) getter
	 * @param itemId, id of the item to get
	 * @return requested item or NULL if item with requested id do not exist in this promotion
	 */
	public Item getItem(int itemId) {
		int i;
		for(i = 0; i < this.items.size(); i++){
			if(this.items.get(i).getId() == itemId) return items.get(i);
		}
		return null;
	}

	/**
	* Adds an item to this promotion
	* @param itemParams, the items' parameters in the order, id; name; description and price in string list
	*/
	public boolean addItem(List<String> itemParams){
		items.add(new Item(Integer.parseInt(itemParams.get(0)), itemParams.get(1), itemParams.get(2), Double.parseDouble(itemParams.get(3))));
		return true;
	}

	/**
	* Removes an item from this promotion
	* @param itemId, id of the item to remove
	* @return true or false based on success/error
	*/
	public boolean removeItem(int itemId){
		int i;
		for(i = 0; i < items.size(); i++){
			if(items.get(i).getId() == itemId) items.remove(i);
		}
		return true;
	}

	/**
	* Description getter
	* @return the description of this promotion
	*/
	@Override
	public String getDescription() {
		return super.getDescription();
	}

	/**
	* Price getter
	* @return the price of this promotion
	*/
	@Override
	public double getPrice() {
		return super.getPrice();
	}

	/**
	* Prints the attributes of the promotion as well as all its items'
	*/
	@Override
	public void print() {
		int i;
		System.out.println("Promotion Id: " + super.getId());
		System.out.println("Promotion Name: " + super.getName());
		System.out.println("Promotion Description: " + super.getDescription());
		System.out.println("Promotion Price: " + super.getPrice());
		for(i = 0; i < items.size(); i++){
			items.get(i).print();
		}
	}

	/**
	 * Updates the description of this promotion
	 * @param desc, new description string
	 */
	@Override
	public void setDescription(String desc) {
		super.setDescription(desc);
	}

	/**
	 * Updates the price of this promotion
	 * @param cost, new price
	 */
	@Override
	public void setPrice(double cost) {
		if(cost < 0)
			throw new IllegalArgumentException();
		super.setPrice(cost);
	}

}

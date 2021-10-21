package Models;

import java.util.ArrayList;
import java.util.List;

public class Promotion extends Item{
	//private int id;
	//private String description;
	//private double price;
	private List<Item> items = new ArrayList<Item>();
	/**

	 *
	 * @param desc
	 * @param cost
	 * @param itemsInc
	 */
	public Promotion(int id, String name, String desc, double cost, List<String> itemsInc) {
		// TODO - implement Promotion.Promotion
		//throw new UnsupportedOperationException();
		super(id, name, desc, cost);
		int i = 0;
		do{
			items.add(new Item(Integer.parseInt(itemsInc.get(i)), itemsInc.get(i+1), itemsInc.get(i+2), Double.parseDouble(itemsInc.get(i+3))));
			i += 4;
		}while(!itemsInc.get(i).equals("ENDLINE"));
	}

	public int getId() {
		return super.getId();
	}

	public List<Item> getItems() {
		return this.items;
	}

	/**
	 *
	 * @param itemId
	 */
	public Item getItem(int itemId) {
		// TODO - implement Promotion.getItem
		//throw new UnsupportedOperationException();
		int i;
		for(i = 0; i < this.items.size(); i++){
			if(this.items.get(i).getId() == itemId) return items.get(i);
		}
		return null;
	}

	public boolean addItem(String[] itemParams){
		items.add(new Item(Integer.parseInt(itemParams[0]), itemParams[1], itemParams[2], Double.parseDouble(itemParams[3])));
		return true;
	}

	public boolean removeItem(int itemId){
		int i;
		for(i = 0; i < items.size(); i++){
			if(items.get(i).getId() == itemId) items.remove(i);
		}
		return true;
	}

	public String getDescription() {
		return super.getDescription();
	}

	public double getPrice() {
		return super.getPrice();
	}

	/**
	 *
	 * @param itemId
	 */

	public void print() {
		// TODO - implement Promotion.print
		// throw new UnsupportedOperationException();
		int i;
		System.out.println("Promotion Id:" + super.getId());
		System.out.println("Promotion Name:" + super.getName());
		System.out.println("Promotion Description:" + super.getDescription());
		System.out.println("Promotion Price:" + super.getPrice());
		for(i = 0; i < items.size(); i++){
			items.get(i).print();
		}
	}

	/**
	 *
	 * @param desc
	 */
	public void setDescription(String desc) {
		super.setDescription(desc);
	}

	/**
	 *
	 * @param cost
	 */
	public void setPrice(double cost) {
		super.setPrice(cost);
	}

}

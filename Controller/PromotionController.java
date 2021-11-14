package Controller;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import Models.Promotion;
import Models.Item;

/**
 * A controller that is responsible for managing the promotion packages of the restaurant.
 * 
 * @author  @ghotinggoad
 * @since 10 October 2021
 */
public class PromotionController {

	private List<Promotion> promotions = new ArrayList<Promotion>();
	private FileController fileController = new FileController();
	private final static String PATH_TO_PROMOTIONS_FILE = Path.of("./Data/promotion.txt").toString();
	private final static String ESCAPE_STRING_1 = "\\";
	private final static String ESCAPE_STRING_2 = "-1";
	private final static String DELIMITER = ",";

	/**
	 * Constructs the PromotionController object.
	 * It will read the existed promotions that are saved in the promotion.txt, and 
	 * then instantiate the promotions and their respective items ony by one.
	 */
	public PromotionController() {
		List<String> tokens = fileController.readFile(PATH_TO_PROMOTIONS_FILE);
		List<String> promoParams = new ArrayList<String>();
		List<String> itemParams = new ArrayList<String>();
		String next = "PROMO";
		for(int i = 0; i < tokens.size(); i++){
			if(!tokens.get(i).equals("ENDLINE") && !tokens.get(i).equals("ENDFILE")){
				String[] params = tokens.get(i).split(DELIMITER);
				if(next.equals("PROMO")){
					promoParams.add(params[0]);
					promoParams.add(params[1]);
					promoParams.add(params[2]);
					promoParams.add(params[3]);
					next = "ITEM";
				} else if (next.equals("ITEM")){
					itemParams.add(params[0]);
					itemParams.add(params[1]);
					itemParams.add(params[2]);
					itemParams.add(params[3]);
					next = "ITEM";
				}
			} else if(tokens.get(i).equals("ENDFILE"))
				next = "PROMO";
			else {
				addPromotion(promoParams, itemParams);
				promoParams.clear();
				itemParams.clear();
				next = "PROMO";
			}
		}/*
		int i = 0;
		do{
			promoParams.add(tokens.get(i)); 		//id
			promoParams.add(tokens.get(i+1)); //name
			promoParams.add(tokens.get(i+2)); //description
			promoParams.add(tokens.get(i+3)); //price
			i += 4;
			do{
				itemParams.add(tokens.get(i));
				i++;
			}while(i < tokens.size() && !tokens.get(i).equals("ENDLINE"));
			itemParams.add(tokens.get(i));
			addPromotion(promoParams, itemParams);
			promoParams.clear();
			itemParams.clear();
			i++;
		}while(i < tokens.size() && !tokens.get(i).equals("ENDFILE")); */
	}

	/**
	* Converts all current promotions into Strings and overwrite the promotion.txt to update it.
	*
	* @return 	true if the file was successfully updated, false otherwise.
	*/
	private boolean updatePromotionFile() {
		boolean res = false;
		List<String> records = new ArrayList<String>();
		for(Promotion promotion : promotions){
			records.add(String.valueOf(promotion.getId()).concat(DELIMITER));
			records.add(promotion.getName().concat(DELIMITER));
			records.add(promotion.getDescription().concat(DELIMITER));
			records.add(String.valueOf(promotion.getPrice()));
			records.add(System.getProperty("line.separator"));
			for(Item item : promotion.getItems()){
				records.add(String.valueOf(item.getId()).concat(DELIMITER));
				records.add(item.getName().concat(DELIMITER));
				records.add(item.getDescription().concat(DELIMITER));
				records.add(String.valueOf(item.getPrice()));
				records.add(System.getProperty("line.separator"));
			}
			records.add("ENDLINE");
			records.add(System.getProperty("line.separator"));
		}
		records.add("ENDFILE");
		if(fileController.writeFile(records.toArray(new String[records.size()]), PATH_TO_PROMOTIONS_FILE))
			res = true;
		return res;
	}

	/**
	 * Adds a new promotion to the menu.
	 * 
	 * @param	promoParams		A list of String objects that includes the information of this promotion
	 * 							(promotion id, promotion name, promotion description, and promotion price).
	 * @param 	items 			A list of String objects that includes the information of all Item objects
	 * 							in this promotion (item id, item name, item description, and item price).
	 * @return 	true if this promotion is added successfully, false otherwise.
	 */
	public boolean addPromotion(List<String> promoParams, List<String> items) {
		if(this.findPromotionById(Integer.parseInt(promoParams.get(0))) != null){
			System.out.println("Promotion is already in the system!");
			return false;
		}
		else{
			promotions.add(new Promotion(Integer.parseInt(promoParams.get(0)), promoParams.get(1), promoParams.get(2), Double.parseDouble(promoParams.get(3)), items));
			this.updatePromotionFile();
			return true;
		}
	}

	/**
	* Returns a Promotion object which is a copy version of another promotion (different hash identity).
	*
	* @param	promoId		The id of the promotion to be copied.
	* @return 	A Promotion object if this id is existed, null otherwise.
	*/

	public Promotion copyPromotion(int promoId){
		int i, j;
		List<String> items = new ArrayList<String>();;
		Promotion copy;
		for(i = 0; i < promotions.size(); i++){
			if(promotions.get(i).getId() == promoId) break;
		}
		for(j = 0; j < promotions.get(i).getItems().size(); j++){
			items.add(Integer.toString(promotions.get(i).getItems().get(j).getId()));
			items.add(promotions.get(i).getItems().get(j).getName());
			items.add(promotions.get(i).getItems().get(j).getDescription());
			items.add(Double.toString(promotions.get(i).getItems().get(j).getPrice()));
		}
		copy = new Promotion(promotions.get(i).getId(), promotions.get(i).getName(), promotions.get(i).getDescription(), promotions.get(i).getPrice(), items);
		return copy;
	}

	/**
	 * Returns the actual Promotion object in the list (same hash identity).
	 * 
	 * @param	promoId		The id of the promotion to be search.
	 * @return 	The actual Promotion object in the list.
	 */
	public Promotion findPromotionById(int promoId) {
		int i;
		for(i = 0; i < promotions.size(); i++){
			if(promotions.get(i).getId() == promoId) return promotions.get(i);
		}
		return null;
	}

	/**
	* Prints the attributes of all the promotions and their respective items.
	*/
	public void print() {
		int i;
		System.out.print("\n");
		for(i = 0; i < promotions.size(); i++){
			promotions.get(i).print();
		}
		System.out.print("\n");
	}

	/**
	 * Removes a specific promotion from the list.
	 * 
	 * @param	promoId		The id of the promotion to be removed.
	 * @return 	true if the promotion is removed successfully, false otherwise.
	 */
	public boolean removePromotion(int promoId) {
		int i;
		for(i = 0; i < promotions.size(); i++){
			if(promotions.get(i).getId() == promoId){
				promotions.remove(i);
				this.updatePromotionFile();
				return true;
			}
		}
		System.out.println("Promotion " + promoId + " does not exist!");
		return false;
	}

	/**
	 * Adds an item to an existing promotion.
	 * 
	 * @param	promoId		The id of the promotion to be updated.
	 * @param 	itemParams	A list of String objects that includes the information
	 * 						of the item (item id, item name, item description, and item price).
	 * @return 	true if this item is added successfully, false otherwise.
	 */
	public boolean addItem(int promoId, List<String> itemParams) {
		this.findPromotionById(promoId).addItem(itemParams);
		this.updatePromotionFile();
		return true;
	}

	/**
	 * Removes an item from an existing promotion.
	 * 
	 * @param	promoId		The id of the promotion to be updated.
	 * @param 	itemParams	A list of String objects that includes the information
	 * 						of the item (item id, item name, item description, and item price).
	 * @return 	true if this item is added successfully, false otherwise.
	 */
	public boolean removeItem(int promoId, int itemId) {
		int i;
		for(i = 0; i < this.findPromotionById(promoId).getItems().size(); i++){
			if(this.findPromotionById(promoId).getItems().get(i).getId() == itemId){
				this.findPromotionById(promoId).getItems().remove(i);
				this.updatePromotionFile();
				return true;
			}
		}
		System.out.println("Item " + itemId + " does not exist!");
		return false;
	}

	/**
	 * Updates the attributes of items in a promotion that have the same item id.
	 * 
	 * @param 	itemParams	A list of String objects that includes the information
	 * 						of the item (item id, item name, item description, and item price).
	 * @return 	true it is updated successfully, false otherwise.
	 */
	public boolean updateItem(int promoId, List<String> itemParams) {
		int i;
		for(i = 0; i < this.findPromotionById(promoId).getItems().size(); i++){
			if(this.findPromotionById(promoId).getItems().get(i).getId() == Integer.parseInt(itemParams.get(0))){
				if(!itemParams.get(1).equals(ESCAPE_STRING_1)) this.findPromotionById(promoId).getItems().get(i).setName(itemParams.get(1));
				if(!itemParams.get(2).equals(ESCAPE_STRING_1)) this.findPromotionById(promoId).getItems().get(i).setDescription(itemParams.get(2));
				if(!itemParams.get(3).equals(ESCAPE_STRING_2)) this.findPromotionById(promoId).getItems().get(i).setPrice(Double.parseDouble(itemParams.get(3)));
				this.updatePromotionFile();
				return true;
			}
		}
		System.out.println("Item " + itemParams.get(0) + " does not exist!");
		return false;
	}

	/** 
	 * Updates the information of a promotion.
	 * 
	 * @param 	promoParams		A list of String objects that includes the new information of the promotion 
	 * 							(promotion id, promotion name, promotion description, and promotion price).
	 * @return 	true it is updated successfully, false otherwise.
	 */
	public boolean updatePromotion(List<String> promoParams) {
		int i;
		for(i = 0; i < promotions.size(); i++){
			if(promotions.get(i).getId() == Integer.parseInt(promoParams.get(0))){
				if(!promoParams.get(1).equals(ESCAPE_STRING_1)) promotions.get(i).setName(promoParams.get(1));
				if(!promoParams.get(2).equals(ESCAPE_STRING_1)) promotions.get(i).setDescription(promoParams.get(2));
				if(!promoParams.get(3).equals(ESCAPE_STRING_2)) promotions.get(i).setPrice(Double.parseDouble(promoParams.get(3)));
				this.updatePromotionFile();
				// doesn't change items
				return true;
			}
		}
		System.out.println("Promotion " + promoParams.get(0) + " does not exist!");
		return false;
	}

}

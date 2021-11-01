package Controller;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import Models.Promotion;
import Models.Item;

public class PromotionController {

	private List<Promotion> promotions = new ArrayList<Promotion>();
	private FileController fileController = new FileController();
	private final static String PATH_TO_PROMOTIONS_FILE = Path.of("./promotion.txt").toString();
	private final static String ESCAPE_STRING_1 = "\\";
	private final static String ESCAPE_STRING_2 = "-1.0";

	/**
	 * Constructor of the PromotionController Class
	 * It will read the previously created promotions which have been saved to promotion.txt
	 * then instantiate the promotions and their respective items ony by one
	 */
	public PromotionController() {
		List<String> tokens = fileController.readFile(PATH_TO_PROMOTIONS_FILE);
		List<String> promoParams = new ArrayList<String>();
		List<String> itemParams = new ArrayList<String>();
		String prevCat = "", curCat = "";
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
		}while(i < tokens.size() && !tokens.get(i).equals("ENDFILE"));
	}

	/**
	* Convert all promotions into a string and overwrite promotion.txt
	* @return true or false based on success/error
	*/
	private boolean updatePromotionFile() {
		boolean res = false;
		List<String> records = new ArrayList<String>();
		for(Promotion promotion : promotions){
			records.add(String.valueOf(promotion.getId()));
			records.add(promotion.getName());
			records.add(promotion.getDescription());
			records.add(String.valueOf(promotion.getPrice()));
			for(Item item : promotion.getItems()){
				records.add(String.valueOf(item.getId()));
				records.add(item.getName());
				records.add(item.getDescription());
				records.add(String.valueOf(item.getPrice()));
			}
			records.add("ENDLINE");
		}
		records.add("ENDFILE");
		if(fileController.writeFile(records.toArray(new String[records.size()]), PATH_TO_PROMOTIONS_FILE))
			res = true;
		return res;
	}

	/**
	 * Adds a new promotion to promotions[]
	 * @param promoParams, the promotion id; name; description and price in string list
	 * @param items, the items' parameters in the order, id; name; description and price in string list
	 * @return true or false based on success/error
	 */
	public boolean addPromotion(List<String> promoParams, List<String> items) {
		try{
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
		catch(Exception error){
			System.out.println("Error Occured!\nPlease contact RRPCS Support Team for assistance.");
			System.out.println(error);
			return false;
		}
	}

	/**
	* Returns a copy of promotion (different hash identity)
	* @promoId, the promotion id which is used to search for a specific promotion
	* @return copy, copy of the promotion that is being requested
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
		items.add("ENDLINE");
		copy = new Promotion(promotions.get(i).getId(), promotions.get(i).getName(), promotions.get(i).getDescription(), promotions.get(i).getPrice(), items);
		return copy;
	}

	/**
	 * Returns the actual promotion (same hash identity)
	 * @param promoId, the promotion id which is used to search for a specific promotion
	 * @return the actual promotion that is being requested
	 */
	public Promotion findPromotionById(int promoId) {
		int i;
		for(i = 0; i < promotions.size(); i++){
			if(promotions.get(i).getId() == promoId) return promotions.get(i);
		}
		return null;
	}

	/**
	* Prints the attributes of all the promotions and their respective items
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
	 * Removes a specific promotion from promotions[]
	 * @param promoId, the promotion id which is used to search for a specific promotion
	 * @return true or false based on success/error
	 */
	public boolean removePromotion(int promoId) {
		try{
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
		catch(Exception error){
			System.out.println("Error Occured!\nPlease contact RRPCS Support Team for assistance.");
			System.out.println(error);
			return false;
		}
	}

	/**
	 * Adds an item to an existing promotion
	 * @param promoId, the promotion id which is used to search for a specific promotion
	 * @param itemParams, the item's parameters in the order, id; name; description and price in string array
	 * @return true or false based on success/error
	 */
	public boolean addItem(int promoId, List<String> itemParams) {
		try{
			this.findPromotionById(promoId).addItem(itemParams);
			this.updatePromotionFile();
			return true;
		}
		catch(Exception error){
			System.out.println("Promotion " + promoId + " does not exist!");
			return false;
		}
	}

	/**
	 * Removes an item from an existing promotion
	 * @param promoId, the promotion id which is used to search for a specific promotion
	 * @param itemId, the item id which is used to search for a specific item in the promotion
	 * @return true or false based on success/error
	 */
	public boolean removeItem(int promoId, int itemId) {
		int i;
		try{
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
		catch(Exception error){
			System.out.println("Promotion " + promoId + " does not exist!");
			return false;
		}
	}

	/**
	 * Updates the attributes of items in a promotion that have the same itemId
	 * @param itemParams, the item's parameters in the order, id; name; description and price in string array
	 * @return true or false based on success/error
	 */
	public boolean updateItem(int promoId, List<String> itemParams) {
		int i;
		try{
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
		catch(Exception error){
			System.out.println("Promotion " + promoId + " does not exist!");
			return false;
		}
	}

	/**
	 * Updates the promotion's attributes, id; name; description and price
	 * @param promoParams, the promotion's parameters in the order, id; name; description and price in string array
	 * @return true or false based on success/error
	 */
	public boolean updatePromotion(List<String> promoParams) {
		try{
			int i;
			for(i = 0; i < promotions.size(); i++){
				if(promotions.get(i).getId() == Integer.parseInt(promoParams.get(0))){
					if(!promoParams.get(1).equals(ESCAPE_STRING_1)) promotions.get(i).setName(promoParams.get(1));
					if(!promoParams.get(2).equals(ESCAPE_STRING_1)) promotions.get(i).setDescription(promoParams.get(2));
					if(!promoParams.get(3).equals(ESCAPE_STRING_1)) promotions.get(i).setPrice(Double.parseDouble(promoParams.get(3)));
					this.updatePromotionFile();
					// doesn't change items
					return true;
				}
			}
			System.out.println("Promotion " + promoParams.get(0) + " does not exist!");
			return false;
		}
		catch(Exception error){
			System.out.println("Error Occured!\nPlease contact RRPCS Support Team for assistance.");
			System.out.println(error);
			return false;
		}
	}

}

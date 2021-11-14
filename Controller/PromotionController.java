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
public class PromotionController implements ISearch {

	private List<Promotion> promotions = new ArrayList<Promotion>();
	private FileController fileController = new FileController();
	private final static String PATH_TO_PROMOTIONS_FILE = Path.of("./Data/promotion.txt").toString();
	private final static String ESCAPE_STRING_1 = "\\";
	private final static String ESCAPE_STRING_2 = "-1";
	private final static String DELIMITER = ",";

	/**
	 * Constructor of the PromotionController Class
	 * It will read the previously created promotions which have been saved to promotion.txt
	 * then instantiate the promotions and their respective items ony by one
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
		}
	}

	/**
	* Convert all promotions into a string and overwrite promotion.txt
	* @return true or false based on success/error
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
	 * Adds a new promotion to promotions[]
	 * @param promoParams, the promotion id; name; description and price in string list
	 * @param items, the items' parameters in the order, id; name; description and price in string list
	 * @return true or false based on success/error
	 */
	public boolean addPromotion(List<String> promoParams, List<String> items) {
		if((Promotion) searchById(Integer.parseInt(promoParams.get(0))) != null){
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
		copy = new Promotion(promotions.get(i).getId(), promotions.get(i).getName(), promotions.get(i).getDescription(), promotions.get(i).getPrice(), items);
		return copy;
	}

	/**
	 * Returns the actual promotion (same hash identity)
	 * @param promoId, the promotion id which is used to search for a specific promotion
	 * @return the actual promotion that is being requested
	 */
	@Override
	public Object searchById(int promoId) {
		return promotions.stream()
			.filter(promo -> promo.getId() == promoId)
			.findFirst()
			.orElse(null);
	}

	/**
	* Prints the attributes of all the promotions and their respective items
	*/
	public void print() {
		System.out.print("\n");
		for(Promotion promo : promotions){
			promo.print();
		}
		System.out.print("\n");
	}

	/**
	 * Removes a specific promotion from promotions[]
	 * @param promoId, the promotion id which is used to search for a specific promotion
	 * @return true or false based on success/error
	 */
	public boolean removePromotion(int promoId) {
		Promotion toRemove = (Promotion) this.searchById(promoId);
		if(toRemove != null) {
			promotions.remove(toRemove);
			return this.updatePromotionFile();
		}
		System.out.println("Promotion " + promoId + " does not exist!");
		return false;
	}

	/**
	 * Adds an item to an existing promotion
	 * @param promoId, the promotion id which is used to search for a specific promotion
	 * @param itemParams, the item's parameters in the order, id; name; description and price in string array
	 * @return true or false based on success/error
	 */
	public boolean addItem(int promoId, List<String> itemParams) {
		((Promotion) searchById(promoId)).addItem(itemParams);
		this.updatePromotionFile();
		return true;
	}

	/**
	 * Removes an item from an existing promotion
	 * @param promoId, the promotion id which is used to search for a specific promotion
	 * @param itemId, the item id which is used to search for a specific item in the promotion
	 * @return true or false based on success/error
	 */
	public boolean removeItem(int promoId, int itemId) {
		Promotion toRemoveFrom = (Promotion) this.searchById(promoId);
		if(toRemoveFrom != null) {
			if(toRemoveFrom.removeItem(itemId))
				return this.updatePromotionFile();
		}
		System.out.println("Item " + itemId + " does not exist!");
		return false;
	}

	/**
	 * Updates the attributes of items in a promotion that have the same itemId
	 * @param itemParams, the item's parameters in the order, id; name; description and price in string array
	 * @return true or false based on success/error
	 */
	public boolean updateItem(int promoId, List<String> itemParams) {
		Promotion toUpdate = (Promotion) this.searchById(promoId);
		Item itemToUpdate = toUpdate.getItem(Integer.parseInt(itemParams.get(0)));
		if(itemToUpdate != null){
			if(!itemParams.get(1).equals(ESCAPE_STRING_1)) itemToUpdate.setName(itemParams.get(1));
			if(!itemParams.get(2).equals(ESCAPE_STRING_1)) itemToUpdate.setDescription(itemParams.get(2));
			if(!itemParams.get(3).equals(ESCAPE_STRING_2)) itemToUpdate.setPrice(Double.parseDouble(itemParams.get(3)));
			return this.updatePromotionFile();
		}
		System.out.println("Item " + itemParams.get(0) + " does not exist!");
		return false;
	}

	/**
	 * Updates the promotion's attributes, id; name; description and price
	 * @param promoParams, the promotion's parameters in the order, id; name; description and price in string array
	 * @return true or false based on success/error
	 */
	public boolean updatePromotion(List<String> promoParams) {
		Promotion toUpdate = (Promotion) this.searchById(Integer.parseInt(promoParams.get(0)));
		if(toUpdate != null) {
			if(!promoParams.get(1).equals(ESCAPE_STRING_1)) toUpdate.setName(promoParams.get(1));
			if(!promoParams.get(2).equals(ESCAPE_STRING_1)) toUpdate.setDescription(promoParams.get(2));
			if(!promoParams.get(3).equals(ESCAPE_STRING_2)) toUpdate.setPrice(Double.parseDouble(promoParams.get(3)));
			this.updatePromotionFile();
			// doesn't change items
			return true;
		}
		System.out.println("Promotion " + promoParams.get(0) + " does not exist!");
		return false;
	}

}

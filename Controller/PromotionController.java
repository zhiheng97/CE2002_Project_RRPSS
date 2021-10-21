package Controller;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import Models.Promotion;

public class PromotionController {

	private List<Promotion> promotions = new ArrayList<Promotion>();
	private FileController fileController = new FileController();
	private final static String PATH_TO_PROMOTIONS_FILE = Path.of("./promotion.txt").toString();

	public PromotionController() {
		// TODO - implement PromotionController.PromotionController
		// throw new UnsupportedOperationException();
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
			}while(!tokens.get(i).equals("ENDLINE"));
			itemParams.add(tokens.get(i));
			addPromotion(promoParams, itemParams);
			promoParams.clear();
			itemParams.clear();
			i++;
		}while(!tokens.get(i).equals("ENDFILE"));
	}

	/**
	 * Initializes a new promotion object and adds it to promotions[]
	 * @param promoParams
	 * @param items
	 */
	public boolean addPromotion(List<String> promoParams, List<String> items) {
		// TODO - implement PromotionController.addPromotion
		// throw new UnsupportedOperationException();
		promotions.add(new Promotion(Integer.parseInt(promoParams.get(0)), promoParams.get(1), promoParams.get(2), Double.parseDouble(promoParams.get(3)), items));
		return true;
	}

	/**
	 *
	 * @param promoId
	 */
	public Promotion findPromotionById(int promoId) {
		// TODO - implement PromotionController.findPromotionById
		// throw new UnsupportedOperationException();
		int i;
		for(i = 0; i < promotions.size(); i++){
			if(promotions.get(i).getId() == promoId) return promotions.get(i);
		}
		return null;
	}

	public void print() {
		// TODO - implement PromotionController.print
		// throw new UnsupportedOperationException();
		int i;
		for(i = 0; i < promotions.size(); i++){
			promotions.get(i).print();
		}
	}

	/**
	 * Calls lookUp and removes promotion from promotions[]
	 * @param promoId
	 */
	public boolean removePromotion(int promoId) {
		// TODO - implement PromotionController.removePromotion
		// throw new UnsupportedOperationException();
		int i;
		for(i = 0; i < promotions.size(); i++){
			if(promotions.get(i).getId() == promoId) promotions.remove(i);
		}
		return true;
	}

	/**
	 *
	 * @param itemParams
	 */
	public boolean addItem(String[] itemParams) {
		// TODO - implement PromotionController.addItem
		// throw new UnsupportedOperationException();

		return true;
	}

	/**
	 *
	 * @param id
	 * @param itemId
	 */
	public boolean removeItem(int id, int itemId) {
		// TODO - implement PromotionController.removeItem
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param itemParams
	 */
	public boolean updateItem(String[] itemParams) {
		// TODO - implement PromotionController.updateItem
	  throw new UnsupportedOperationException();
	}

	/**
	 * Calls lookUp to search for promotion, then uses setters to update promotion. If updating item, call getItem then proceed.
	 * @param promoParams
	 */
	public boolean updatePromotion(String[] promoParams) {
		// TODO - implement PromotionController.updatePromotion
		throw new UnsupportedOperationException();
	}

}

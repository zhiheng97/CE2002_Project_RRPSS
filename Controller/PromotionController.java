package Controller;

import.nio.file.Path;
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
		List<String> promotionList = fileController.readFile(PATH_TO_MENU_FILE);
		List<String> promoParams = new ArrayList<String>;
		String prevCat = "", curCat = "";
		int i = 0, j;
		do{
			promoParams[0] = promotionList.get(i); 		//id
			promoParams[1] = promotionList.get(i+1); //name
			promoParams[2] = promotionList.get(i+2); //description
			promoParams[3] = promotionList.get(i+3); //price
			j = 4;
			do{
				promoParams[j] = promotionList.get(i);
				i++;
				j++;
			}while(!promotionList[i].equals("ENDLINE"))
			this.addPromotion(promoParams);
			i++;
			k = 0;
		}while(!promotionList[i].equals("ENDFILE"))
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
	 * Initializes a new promotion object and adds it to promotions[]
	 * @param promoParams
	 * @param items
	 */
	public boolean addPromotion(String[] promoParams, String[] items) {
		// TODO - implement PromotionController.addPromotion
		// throw new UnsupportedOperationException();

	}

	/**
	 *
	 * @param promoId
	 */
	public Promotion findPromotionById(int promoId) {
		// TODO - implement PromotionController.findPromotionById
		throw new UnsupportedOperationException();
	}

	public void print() {
		// TODO - implement PromotionController.print
		throw new UnsupportedOperationException();
	}

	/**
	 * Calls lookUp and removes promotion from promotions[]
	 * @param promoId
	 */
	public boolean removePromotion(int promoId) {
		// TODO - implement PromotionController.removePromotion
		throw new UnsupportedOperationException();
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

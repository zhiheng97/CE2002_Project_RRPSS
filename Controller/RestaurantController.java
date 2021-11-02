package Controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import Models.Item;
import Models.Promotion;

public class RestaurantController {

	private TableController tableController;
	private ReportController reportController;
	private CategoryController categoryController;
	private PromotionController promotionController;

	/**
	 * Constructor for RestaurantController
	 */
	public RestaurantController() {
		this.tableController =  new TableController(12);
		// this.reportController = new ReportController();
		this.categoryController = new CategoryController();
		this.promotionController = new PromotionController();
	}

	/**
	 * Adds item to the menu
	 * @param itemParams Details of the item to be added
	 * @return Returns true if added successfully, otherwise false
	 */
	public boolean addItem(String[] itemParams) {
		return categoryController.addItem(itemParams);
	}

	/**
	 * Adds item to either the promotion
	 * @param promoId, the promotion id which is used to search for a specific promotion
	 * @param itemParams, the item's parameters in the order, id; name; description and price in string array
	 * @return Returns true if added successfully, otherwise false
	 */
	public boolean addItem(int promoId, List<String> itemParams) {
		return promotionController.addItem(promoId, itemParams);
	}

	/**
	 * Adds a new promotion
	 * @param promoParams, the promotion id; name; description and price in string list
	 * @param items, the items' parameters in the order, id; name; description and price in string list
	 * @return true or false based on success/error
	 */
	public boolean addPromotion(List<String> promoParams, List<String> items) {
		return promotionController.addPromotion(promoParams, items);
	}

	/**
	 *
	 * @param tableNo
	 * @param quantity
	 * @param itemId
	 * @param isPromo
	 */
	public void addToOrder(int tableNo, int itemId, int quantity) {
		Promotion promoToAdd = promotionController.findPromotionById(itemId);
		Item itemToAdd = categoryController.searchForItem(itemId);
		if(promoToAdd != null) {
			Promotion copied = promotionController.copyPromotion(itemId);
			this.tableController.addToOrder(tableNo, copied, quantity);
		} else if(itemToAdd != null) {
			Item copied = categoryController.copyItem(itemId); 
			this.tableController.addToOrder(tableNo, copied, quantity);
		}
	}

	public void expireReservations(Date date) {
		tableController.expireReservations(date);
	}

	public boolean removeFromOrder(int tableNo, int itemId) {
		Promotion promoToAdd = promotionController.findPromotionById(itemId);
		if(promoToAdd != null) {
			Promotion copied = promotionController.copyPromotion(itemId);
			return this.tableController.removeFromOrder(tableNo, copied);
		} else {
			Item copied = categoryController.copyItem(itemId); 
			return this.tableController.removeFromOrder(tableNo, copied);
		}
	}
	
	public void viewOrder(int tableNo) {
		this.tableController.viewOrder(tableNo);
	}


	/**
	 *
	 * @param tableNo
	 * @param details
	 */
	public boolean clearReservation(int tableNo) {
		return tableController.clearReservation(tableNo);
	}

	/**
	 *
	 */
	public void printAvailableTables() {
		tableController.printAvailableTables();
	}

	/**
	 *
	 * @param tableNo
	 */
	public void printInvoice(int tableNo) {
		// TODO - implement RestaurantController.printInvoice
		throw new UnsupportedOperationException();
	}

	/**
	 * Prints the menu
	 */
	public void printMenu() {
		categoryController.print();
	}

	/**
	 * prints the Promotions
	 */
	public void printPromotion(){
		promotionController.print();
	}

	public void printReservations(int tableNo) {
		tableController.printReservations(tableNo);
	}

	/**
	 *
	 * @param byMonth
	 */
	public void printSalesReport(boolean byMonth) {
		// TODO - implement RestaurantController.printSalesReport
		throw new UnsupportedOperationException();
	}

	/**
	 * Removes a specific promotion
	 * @param promoId, the promotion id which is used to search for a specific promotion
	 * @return true or false based on success/error
	 */
	public boolean removePromotion(int promoId) {
		return promotionController.removePromotion(promoId);
	}

	/**
	 * Removes item from either menu or promotion.
	 * @param itemId Id of item to be removed
	 * @param isPromo Flag to check if item to remove is from promotions
	 * @return True if item is removed successfully, otherwise false
	 */
	public boolean removeItem(int itemId) {
		return categoryController.removeItem(itemId);
	}

	/**
	 * Removes an item from an existing promotion
	 * @param promoId, the promotion id which is used to search for a specific promotion
	 * @param itemId, the item id which is used to search for a specific item in the promotion
	 * @return true or false based on success/error
	 */
	public boolean removeItem(int promoId, int itemId) {
		return promotionController.removeItem(promoId, itemId);
	}

	/**
	 *
	 * @param details
	 */
	public boolean reserveTable(String[] details) {
		return tableController.reserveTable(details);
	}

	/**
	 * Updates the promotion's attributes, id; name; description and price
	 * @param promoParams, the promotion's parameters in the order, id; name; description and price in string array
	 * @return true or false based on success/error
	 */
	public boolean updatePromotion(List<String> promoParams) {
		return promotionController.updatePromotion(promoParams);
	}

	/**
	 * Updates item in either menu
	 * @param itemParams Details of item to be updated
	 * @param isPromo Flag to check if item to be updated is from Promotion
	 */
	public boolean updateItem(String[] itemParams) {
		return categoryController.updateItem(itemParams);
	}

	/**
	 * Updates the attributes of items in a promotion that have the same itemId
	 * @param itemParams, the item's parameters in the order, id; name; description and price in string array
	 * @return true or false based on success/error
	 */
	public boolean updateItem(int promoId, List<String> itemParams) {
		return promotionController.updateItem(promoId, itemParams);
	}

}

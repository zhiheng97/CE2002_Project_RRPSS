package Controller;

import Models.Item;
import Models.Reservation;
import java.util.List;

public class RestaurantController {

	private TableController tableController;
	private ReportController reportController;
	private CategoryController categoryController = new CategoryController();
	private PromotionController promotionController = new PromotionController();

	/**
	 * Constructor for RestaurantController
	 */
	public RestaurantController() {}

	/**
	 * Adds item to the menu
	 * @param itemParams Details of the item to be added
	 * @return Returns true if added successfully, otherwise false
	 */
	public boolean addItem(String[] itemParams) {
		boolean res = false;
		res = categoryController.addItem(itemParams);
		return res;
	}

	/**
	 * Adds item to either the promotion
	 * @param promoId, the promotion id which is used to search for a specific promotion
	 * @param itemParams, the item's parameters in the order, id; name; description and price in string array
	 * @return Returns true if added successfully, otherwise false
	 */
	public boolean addItem(int promoId, String[] itemParams) {
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
	public boolean addToOrder(int tableNo, int quantity, int itemId, boolean isPromo) {
		// TODO - implement RestaurantController.addToOrder
		/**
		 * copied is a copy of the item from category to add into Order
		 */
		if(isPromo) {

		} else {
			Item copied = categoryController.copyItem(itemId);
		}
		return true;
	}

	/**
	 *
	 * @param tableNo
	 * @param details
	 */
	public boolean clearReservation(int tableNo, Reservation details) {
		// TODO - implement RestaurantController.clearReservation
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param details
	 */
	public boolean createReservation(Reservation details) {
		// TODO - implement RestaurantController.createReservation
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 */
	public void printAvailableTables() {
		// TODO - implement RestaurantController.printAvailableTables
		throw new UnsupportedOperationException();
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
		promotionController.print();
		categoryController.print();
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
	public boolean removeItem(int itemId, boolean isPromo) {
		boolean res = false;
		if(isPromo) {
			// TODO - implement RestaurantController.removeItem
		} else {
			categoryController.removeItem(itemId);
		}
		return res;
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
		// TODO - implement RestaurantController.reserveTable
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param tableNo
	 * @param quantity
	 * @param catId
	 * @param itemId
	 * @param isPromo
	 */
	public boolean removeFromOrder(int tableNo, int quantity, int itemId, boolean isPromo) {
		// TODO - implement RestaurantController.removeFromOrder
		throw new UnsupportedOperationException();
	}

	/**
	 * Updates the promotion's attributes, id; name; description and price
	 * @param promoParams, the promotion's parameters in the order, id; name; description and price in string array
	 * @return true or false based on success/error
	 */
	public boolean updatePromotion(String[] promoParams) {
		return promotionController.updatePromotion(promoParams);
	}

	/**
	 * Updates item in either menu
	 * @param itemParams Details of item to be updated
	 * @param isPromo Flag to check if item to be updated is from Promotion
	 */
	public boolean updateItem(String[] itemParams) {
		boolean res = false;
		categoryController.updateItem(itemParams);
		return res;
	}

	/**
	 * Updates the attributes of items in a promotion that have the same itemId
	 * @param itemParams, the item's parameters in the order, id; name; description and price in string array
	 * @return true or false based on success/error
	 */
	public boolean updateItem(int promoId, String[] itemParams) {
		return promotionController.updateItem(promoId, itemParams);
	}
}

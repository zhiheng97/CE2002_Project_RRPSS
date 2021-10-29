package Controller;

import Models.Item;
import Models.Reservation;

public class RestaurantController {

	private TableController tableController;
	private ReportController reportController;
	private CategoryController categoryController = new CategoryController();
	private PromotionController PromotionController;

	public RestaurantController() {}

	/**
	 * Adds item to either the menu or to a promotion
	 * @param itemParams Details of the item to be added
	 * @param isPromo Flag to check if item is to be added into promotion
	 * @return Returns true if added successfully, otherwise false
	 */
	public boolean addItem(String[] itemParams, boolean isPromo) {
		boolean res = false;
		if(isPromo){
			//TODO - implement RestaurantController.addItem
		} else {
			res = categoryController.addItem(itemParams);
		}
		return res;
	}

	/**
	 * 
	 * @param promoParams
	 * @param items
	 */
	public boolean addPromotion(String[] promoParams, String[] items) {
		// TODO - implement RestaurantController.addPromotion
		throw new UnsupportedOperationException();
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
		}
		Item copied = categoryController.copyItem(itemId); 
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
	 * 
	 * @param promoId
	 */
	public boolean removePromotion(int promoId) {
		// TODO - implement RestaurantController.removePromotion
		throw new UnsupportedOperationException();
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
	 * 
	 * @param promoId
	 * @param promoParams
	 */
	public boolean updatePromotion(int promoId, String[] promoParams) {
		// TODO - implement RestaurantController.updatePromotion
		throw new UnsupportedOperationException();
	}

	/**
	 * Updates item in either menu or promotion
	 * @param itemParams Details of item to be updated
	 * @param isPromo Flag to check if item to be updated is from Promotion
	 */
	public boolean updateItem(String[] itemParams, boolean isPromo) {
		boolean res = false;
		if(isPromo) {
			// TODO - implement RestaurantController.updateItem
		} else {
			categoryController.updateItem(itemParams);
		}
		return res;
	}

}
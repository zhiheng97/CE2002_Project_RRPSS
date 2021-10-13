package Controller;

import java.util.function.IntBinaryOperator;

import Models.*;

public class RestaurantController {

	private TableController tableController;
	private ReportController reportController;
	private CategoryController categoryController;
	private PromotionController promotionController;

	public RestaurantController() {
		// TODO - implement RestaurantController.RestaurantController
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param catId
	 */
	public boolean addCategory(int catId) {
		// TODO - implement RestaurantController.addCategory
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param itemParams
	 * @param id
	 * @param isPromo
	 */
	public boolean addItem(String[] itemParams, IntBinaryOperator id, boolean isPromo) {
		// TODO - implement RestaurantController.addItem
		throw new UnsupportedOperationException();
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
	 * @param catId
	 * @param itemId
	 * @param isPromo
	 */
	public boolean addToOrder(int tableNo, int quantity, int catId, int itemId, boolean isPromo) {
		// TODO - implement RestaurantController.addToOrder
		throw new UnsupportedOperationException();
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
	 * 
	 * @param itemId
	 * @param id
	 * @param isPromo
	 */
	public boolean removeItem(int itemId, int id, boolean isPromo) {
		// TODO - implement RestaurantController.removeItem
		throw new UnsupportedOperationException();
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
	public boolean removeFromOrder(int tableNo, int quantity, int catId, int itemId, boolean isPromo) {
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
	 * 
	 * @param itemParams
	 * @param id
	 * @param isPromo
	 */
	public boolean updateItem(String[] itemParams, int id, boolean isPromo) {
		// TODO - implement RestaurantController.updateItem
		throw new UnsupportedOperationException();
	}

}
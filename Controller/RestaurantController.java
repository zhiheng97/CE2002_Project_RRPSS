/**
 * An intermediatory controller that calls respective controllers to perform actions.
 * Acts as a bridge to other controllers, by passing messages to the appropriate controller.
 * Performs some data cleaning.
 * @author  @brianleect, @Henry-Hoang, @ghotinggoad, @zhiheng97
 * @since 10 October 2021
 */
package Controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import Models.Customer;
import Models.Item;
import Models.Order;
import Models.Promotion;
import Models.Reservation;
import Models.Staff;

public class RestaurantController {

	private TableController tableController;
	private ReportController reportController;
	private CategoryController categoryController;
	private PromotionController promotionController;
	private CustomerController customerController;
	private StaffController staffController;
	private static final Integer NO_OF_TABLES = 12; //No of tables in restaurant

	public RestaurantController() {
		this.tableController = new TableController(NO_OF_TABLES);
		this.reportController = new ReportController();
		this.categoryController = new CategoryController();
		this.promotionController = new PromotionController();
		this.customerController = new CustomerController();
		this.staffController = new StaffController();

		// initialize order for occupied tables
		Random rand = new Random();
		Date now = new Date();
		for (int id = 1; id <= 12; id++) {
			if (this.tableController.findTableById(id).getIsOccupied()) {
				List<Staff> staffList = staffController.getStaffList();
				List<Customer> customerList = customerController.getCustomers();
				this.tableController.findTableById(id)
						.setInvoice(new Order(staffList.get(rand.nextInt(staffList.size())),
								customerList.get(rand.nextInt(customerList.size())), now));
			}
		}
	}

	////////////////////// ITEM, PROMOTION FUNCTIONS ///////////////////

	/**
	 * Prints the menu
	 */
	public void printMenu() {
		categoryController.print();
	}
	/**
	 * Prints all the promotions
	 */
	public void printPromotion() {
		promotionController.print();
	}
	/**
	 * Adds item to the menu
	 *
	 * @param itemParams Details of the item to be added
	 * @return Returns true if added successfully, otherwise false
	 */
	public boolean addItem(String[] itemParams) {
		return categoryController.addItem(itemParams);
	}

	/**
	 * Adds item to either the promotion
	 *
	 * @param promoId,    the promotion id which is used to search for a specific
	 *                    promotion
	 * @param itemParams, the item's parameters in the order, id; name; description
	 *                    and price in string array
	 * @return Returns true if added successfully, otherwise false
	 */
	public boolean addItem(int promoId, List<String> itemParams) {
		return promotionController.addItem(promoId, itemParams);
	}

	/**
	 * Updates item in either menu
	 *
	 * @param itemParams Details of item to be updated
	 * @param isPromo    Flag to check if item to be updated is from Promotion
	 */
	public boolean updateItem(String[] itemParams) {
		return categoryController.updateItem(itemParams);
	}

	/**
	 * Updates the attributes of items in a promotion that have the same itemId
	 *
	 * @param itemParams, the item's parameters in the order, id; name; description
	 *                    and price in string array
	 * @return true or false based on success/error
	 */
	public boolean updateItem(int promoId, List<String> itemParams) {
		return promotionController.updateItem(promoId, itemParams);
	}

	/**
	 * Removes item from either menu or promotion.
	 *
	 * @param itemId  Id of item to be removed
	 * @param isPromo Flag to check if item to remove is from promotions
	 * @return True if item is removed successfully, otherwise false
	 */
	public boolean removeItem(int itemId) {
		return categoryController.removeItem(itemId);
	}

	/**
	 * Removes an item from an existing promotion
	 *
	 * @param promoId, the promotion id which is used to search for a specific
	 *                 promotion
	 * @param itemId,  the item id which is used to search for a specific item in
	 *                 the promotion
	 * @return true or false based on success/error
	 */
	public boolean removeItem(int promoId, int itemId) {
		return promotionController.removeItem(promoId, itemId);
	}

	/**
	 * Adds a new promotion
	 *
	 * @param promoParams, the promotion id; name; description and price in string
	 *                     list
	 * @param items,       the items' parameters in the order, id; name; description
	 *                     and price in string list
	 * @return true or false based on success/error
	 */
	public boolean addPromotion(List<String> promoParams, List<String> items) {
		return promotionController.addPromotion(promoParams, items);
	}

	/**
	 * Updates the promotion's attributes, id; name; description and price
	 *
	 * @param promoParams, the promotion's parameters in the order, id; name;
	 *                     description and price in string array
	 * @return true or false based on success/error
	 */
	public boolean updatePromotion(List<String> promoParams) {
		return promotionController.updatePromotion(promoParams);
	}

	/**
	 * Removes a specific promotion
	 *
	 * @param promoId, the promotion id which is used to search for a specific
	 *                 promotion
	 * @return true or false based on success/error
	 */
	public boolean removePromotion(int promoId) {
		return promotionController.removePromotion(promoId);
	}

	////////////////////// ORDER FUNCTIONS ///////////////////

	/**
	 * Checks if the customer Id provided is a valid registered customer
	 * @param cust_id Id to be checked
	 * @return True if customer is a registered customer, otherwise false
	 */
	public boolean isRegisteredCustomer(int cust_id) {
		if (cust_id < 0 || cust_id >= customerController.getCustomers().size())
			return false;
		return true;
	}

	/**
	 * Checks in a customer that made a reservation
	 * @param res_id Reservation id to check in
	 * @return the tableId and cust_id that for this reservation
	 */
	public int[] checkinReservation(String res_id) {
		if (!checkReservation(res_id)) return new int[] { -1, -1 };
		String[] res_id_params = res_id.split("-");
		System.out.println(res_id_params);
		int tableId = Integer.parseInt(res_id_params[0]);
		Reservation res = this.tableController.findReservation(res_id);
		int cust_id = res.getCustId();
		this.tableController.removeReservation(res_id);
		return new int[] { tableId, cust_id };
	}

	/**
	 * create new order for new customer
	 *
	 * @param tableId
	 * @param staffID
	 * @param date
	 */
	public void createOrder(int tableId, int cust_id, int staff_id, Date date) {
		Staff staff = staffController.getStaffList().get(staff_id);
		Customer cust = customerController.getCustomers().get(cust_id);
		this.tableController.findTableById(tableId).setIsOccupied(true);
		this.tableController.findTableById(tableId).setInvoice(new Order(staff, cust, date));
		System.out.printf("The new order is created for table %d. Enjoy!\n", tableId);
	}

	/**
	 * add a quantity of item/promotion to the order of table tableId
	 *
	 * @param tableId
	 * @param itemId
	 * @param quantity
	 * @return "promo" if successfully add a Promotion
	 * @return "item" if successfully add an Item
	 * @return "false" if invalid itemId
	 */
	public String addToOrder(int tableId, int itemId, int quantity) {
		Promotion promoToAdd = this.promotionController.findPromotionById(itemId);
		Item itemToAdd = this.categoryController.searchForItem(itemId);
		if (promoToAdd != null) {
			Promotion copied = this.promotionController.copyPromotion(itemId);
			this.tableController.addToOrder(tableId, copied, quantity);
			return "promo";
		} else if (itemToAdd != null) {
			Item copied = this.categoryController.copyItem(itemId);
			this.tableController.addToOrder(tableId, copied, quantity);
			return "item";
		}
		return "false";
	}

	/**
	 * remove Item/Promotion objects from the order of table tableId
	 *
	 * @param tableId  the tableId that has the order need to process
	 * @param itemId   id of the Item/Promotion to be removed
	 * @param quantity number of Promotion object to remove
	 * @return 2 if they are removed normally
	 * @return 1 if quantity >= current quantity in order (remove all anw)
	 * @return 0 if there is no Item/Promotion in this order
	 * @return -1 if invalid itemId
	 */
	public int removeFromOrder(int tableId, int itemId, int quantity) {
		Promotion promoToRemove = this.promotionController.findPromotionById(itemId);
		Item itemToRemove = this.categoryController.searchForItem(itemId);
		if (promoToRemove != null) {
			Promotion copied = this.promotionController.copyPromotion(itemId);
			return this.tableController.removeFromOrder(tableId, copied, quantity);
		} else if (itemToRemove != null) {
			Item copied = this.categoryController.copyItem(itemId);
			return this.tableController.removeFromOrder(tableId, copied, quantity);
		}
		return -1;
	}

	/**
	 * print the final invoice of table tableId add the invoice to the
	 * reportController and clear the table
	 *
	 * @param tableId
	 */
	public void printInvoice(int tableId) {
		Order invoice = this.tableController.findTableById(tableId).getInvoice();
		this.reportController.addInvoice(invoice); // Adds completed invoice to reportController to manage
		this.tableController.printInvoice(tableId);
	}

	/**
	 * view the current order of the table tableId
	 *
	 * @param tableId
	 */
	public void printOrder(int tableId, boolean withPrice) {
		this.tableController.printOrder(tableId, withPrice);
	}

	/**
	 * find first availabe table for noPax
	 *
	 * @param
	 * @return
	 */
	public int findValidTable(String[] details) throws ParseException {
		return this.tableController.findValidTable(details);
	}

	/**
	 * check whether table tableId is occupied or not
	 *
	 * @param tableId
	 * @return
	 */
	public boolean isTableOccupied(int tableId) {
		return this.tableController.findTableById(tableId).getIsOccupied();
	}

	/**
	 * print all occupied tables to let the staff choose which table to udpate order
	 *
	 * @return false if all tables are unoccupied
	 */
	public boolean printUnavailableTables() {
		return this.tableController.printUnavailableTables();
	}

	/**
	 * print all unoccupied tables
	 */
	public boolean printAvailableTables() {
		return this.tableController.printAvailableTables();
	}

	/**
	 * print all unoccupied tables that has number of seats >= noPax
	 *
	 * @param noPax
	 */
	public void printAvailableTables(int noPax) {
		this.tableController.printAvailableTables(noPax);
	}

	////////////////////// RESERVATION FUNCTIONS ///////////////////

	/**
	 * Checks if the reservation is valid
	 * @param res_id Reservation Id to check for
	 * @return true if the reservation is valid
	 */
	public boolean checkReservation(String res_id) {
		if (!res_id.contains("-")) return false;
		String[] res_id_params = res_id.split("-");
		if (res_id_params.length != 2) return false;
		int tableId = Integer.parseInt(res_id_params[0]);
		int id = Integer.parseInt(res_id_params[1]);
		if (tableId < 1 || tableId > 12 || id < 0 || id >= 15)
			return false;
		Reservation res = this.tableController.findReservation(res_id);
		if (res == null)
			return false;
		return true;
	}

	/**
	 * for debugging purpose only when add register new Customer show all customers
	 * in memory
	 */
	public void showCustomers() { // for debug
		for (Customer cust : customerController.getCustomers())
			System.out.printf("%d %s\n", cust.getId(), cust.getName());
	}

	/**
	 * @param cust_name
	 * @param contactNo
	 * @return cust_id for the new Customer
	 */
	public int registerCustomer(String cust_name, int contactNo) {
		return this.customerController.addCustomer(cust_name, contactNo);
	}

	/**
	 * @param details
	 * @return res_id if reserve successfully, "false" otherwise
	 */
	public String reserveTable(String[] details) {
		return this.tableController.reserveTable(details);
	}

	/**
	 * @param res_id return true/false
	 */
	public boolean removeReservation(String res_id) {
		return this.tableController.removeReservation(res_id);
	}

	/**
	 * @params res_id, datetime
	 * @return new_res_id or "false"
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	public String updateReservation(String res_id, String datetime) {
		return this.tableController.updateReservation(res_id, datetime);
	}

	/**
	 * @params res_id, noPax
	 * @return new_res_id or "false"
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	public String updateReservation(String res_id, int noPax) {
		return this.tableController.updateReservation(res_id, noPax);
	}

	/**
	 * Deletes reservations that have expired.
	 */
	public void deleteExpiredReservations() {
		this.tableController.deleteExpiredReservations();
	}

	/**
	 * print all reservations of a specified table
	 * @param tableId Table to print
	 */
	public void printReservations(int tableId) {
		this.tableController.printReservations(tableId);
	}

	/**
	 * print all reservations of the restaurant
	 */
	public void printReservations() {
		this.tableController.printReservations();
	}

	/**
	 * Calls the update method to update reservation.txt
	 */
	public void updateReservationFile(){
		this.tableController.updateReservationFile();
	}

	////////////////////// REPORT FUNCTIONS ///////////////////

	/**
	 *
	 * @param byMonth Determines whether print monthly or daily report
	 * @param timeNow Current time as a string
	 */
	public void printSalesReport(boolean byMonth, String timeNow) {
		this.reportController.print(byMonth, timeNow);
	}

}

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
	 * Returns true if this ID is a registered customer.
	 * 
	 * @param	cust_id		Customer ID to be checked.
	 * @return 	true if this is a registered customer, false otherwise.
	 */
	public boolean isRegisteredCustomer(int cust_id) {
		if (cust_id < 0 || cust_id >= customerController.getCustomers().size())
			return false;
		return true;
	}

	/**
	 * Returns the table ID and customer ID of this reservation when the customer wants to check in.
	 * 
	 * @param	res_id	Reservation ID to check in.
	 * @return	The table ID and customer ID of this reservation.
	 */
	public int[] checkinReservation(String res_id) {
		if (!checkReservation(res_id)) return new int[] { -1, -1 };
		String[] res_id_params = res_id.split("-");
		int tableId = Integer.parseInt(res_id_params[0]);
		Reservation res = this.tableController.findReservation(res_id);
		int cust_id = res.getCustId();
		this.tableController.removeReservation(res_id);
		return new int[] { tableId, cust_id };
	}

	/**
	 * Creates a new order for this table and customer.
	 *
	 * @param	tableId		The ID of the table that has the customer is allocated to.
	 * @param 	staffID		The ID of the staff who will be in charged for this order.
	 * @param 	date		The time date when this order is created.
	 */
	public void createOrder(int tableId, int cust_id, int staff_id, Date date) {
		Staff staff = staffController.getStaffList().get(staff_id);
		Customer cust = customerController.getCustomers().get(cust_id);
		this.tableController.findTableById(tableId).setIsOccupied(true);
		this.tableController.findTableById(tableId).setInvoice(new Order(staff, cust, date));
		System.out.printf("The new order is created for table %d. Enjoy!\n", tableId);
	}

	 /**
	 * Adds a quantity of Item objects to the order of the table tableId.
	 *
	 * @param	tableId		The ID of the table that has the order needs to be processed.
	 * @param	item		The Item object to be added.
	 * @param 	quantity	The number of Item objects to be added.
	 * @return	"promo" if a quantity of Promotion objects is added successfully,<br>
	 * 			or "item" if a quantity of Item objects is added successfully,<br>
	 * 			or "false" if this is not a valid ID for any Item or Promotion.
	 */
	public String addToOrder(int tableId, int itemId, int quantity) {
		Promotion promoToAdd = this.promotionController.findPromotionById(itemId);
		if (promoToAdd != null) {
			Promotion copied = this.promotionController.copyPromotion(itemId);
			this.tableController.addToOrder(tableId, copied, quantity);
			return "promo";
		}
		
		Item itemToAdd = this.categoryController.searchForItem(itemId);
		if (itemToAdd != null) {
			Item copied = this.categoryController.copyItem(itemId);
			this.tableController.addToOrder(tableId, copied, quantity);
			return "item";
		}
		return "false";
	}

	/**
	 * Removes a quantity of Item objects from the order of the table tableId.
	 *
	 * @param 	tableId 	The ID of the table that has the order needs to be processed.
	 * @param 	item 		The Item object to be removed.
	 * @param 	quantity 	The number of Item objects to be removed.
	 * @return 	2 if a quantity of item is removed from the order,<br>
	 * 			or 1 if all the occurrences of this item are removed from the order,<br>
	 * 			or 0 if cannot remove because there is no occurrence of this item in the order.
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
	 * Prints the current status of the order of table tableId.
	 * 
	 * @param 	tableId		The ID of the table that has the order needs to be printed.
	 * @param	withPrice	true to print the order's price when the customer checks out, false otherwise.
	 */
	public void printOrder(int tableId, boolean withPrice) {
		this.tableController.printOrder(tableId, withPrice);
	}

	/**
	 * Prints the final invoice of the table tableId when the customer wants to check out.<br>
	 * Clears the table tableId by setting it to unoccupied and removing the current order.
	 * 
	 * @param 	tableId		The ID of the table that has the order needs to be printed
	 */
	public void printInvoice(int tableId) {
		Order invoice = this.tableController.findTableById(tableId).getInvoice();
		this.reportController.addInvoice(invoice); // Adds completed invoice to reportController to manage
		this.tableController.printInvoice(tableId);
	}

	/**
	 * Returns a valid table id to walk in dining or make reservation (based on the restaurant's policy):<br>
	 * <ul>
	 * 		<li>For 1 or 2 pax: 	Only allocates tables of 2 or 4 seats.</li>
	 * 		<li>For 3 or 4 pax:		Only allocates tables of 4 or 6 seats.</li>
	 * 		<li>For 5 or 6 pax:		Only allocates tables of 6 or 8 seats.</li>
	 * 		<li>For 7 or 8 pax:		Only allocates tables of 8 or 10 seats.</li>
	 * 		<li>For 9 or 10 pax:	Only allocates tables of 10 seats.</li>
	 * </ul>
	 *
	 * @param	details	A string array (customer id, timestamp, number of pax).
	 * @return 	The tableId of a valid table, -1 if it cannot find any.
	 * @exception	ParseException
	 */
	public int findValidTable(String[] details) throws ParseException {
		return this.tableController.findValidTable(details);
	}

	/**
	 * Return true if this table is occupied.
	 *
	 * @param 	tableId		The ID of the table that is needed to be checked.
	 * @return	true if this table is occupied, false otherwise.
	 */
	public boolean isTableOccupied(int tableId) {
		return this.tableController.findTableById(tableId).getIsOccupied();
	}

	/**
	 * Prints all of the occupied tables.
	 * 
	 * @return 			true if there is at least 1 occupied table, false otherwise.
	 */
	public boolean printUnavailableTables() {
		return this.tableController.printUnavailableTables();
	}

	/**
	 * Prints all of the unoccupied tables.
	 * 
	 * @return			true if there is at least 1 unoccupied table, false otherwise.
	 */
	public boolean printAvailableTables() {
		return this.tableController.printAvailableTables();
	}

	/**
	 * Prints all of the unoccupied tables that has number of seats >= noPax.
	 * 
	 * @param 	noPax 	The number of pax.
	 */
	public void printAvailableTables(int noPax) {
		this.tableController.printAvailableTables(noPax);
	}

	////////////////////// RESERVATION FUNCTIONS ///////////////////

	/**
	 * Returns true if this reservation ID is valid.
	 * @param 	res_id	The reservation ID that is needed to be checked.
	 * @return 	true if this reservation ID valid
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
	 * Prints the information of all of the registered customers.
	 */
	public void printCustomers() { // for debug
		for (Customer cust : customerController.getCustomers())
			System.out.printf("%d %s\n", cust.getId(), cust.getName());
	}

	/**
	 * Returns the new customer ID when he/she register one.
	 * 
	 * @param	cust_name	The name of this customer who wishes to register.
	 * @param 	contactNo	The contact number of this customer who wishes to register.
	 * @return	The new customer ID for this customer who wishes to register.
	 */
	public int registerCustomer(String cust_name, int contactNo) {
		return this.customerController.addCustomer(cust_name, contactNo);
	}

	/**
	 * Returns the ID of the new reservation if it is made successfully.
	 *
	 * @param	details	A details string array to make new reservation (customer ID, timestamp, number of pax).
	 * @return	The ID of reservation if it is allocated successfully,<br>
	 * 			or "false 1" if there is no available table to reserve,<br>
	 * 			or "false 2" if the time date of this reservation is in the past.
	 */
	public String reserveTable(String[] details) {
		return this.tableController.reserveTable(details);
	}

	/**
	 * Return true if the reservation of this ID is removed successfully.
	 * 
	 * @param	res_id	The ID of the reservation that is needed to be removed.
	 * @return 	true if this reservation is removed succesfully, false otherwise.
	 */
	public boolean removeReservation(String res_id) {
		return this.tableController.removeReservation(res_id);
	}

	/**
	 * Updates a reservation with a new date time.
	 *
	 * @param	res_id		The ID of the reservation that is needed to be updated.
	 * @param	dateTime	The new date time to update.
	 * @return 	The new reservation id or "false" if the update cannot be made.
	 */
	public String updateReservation(String res_id, String datetime) {
		return this.tableController.updateReservation(res_id, datetime);
	}

	/**
	 * Updates a reservation with a new number of pax.
	 *
	 * @param	res_id		The ID of the reservation that is needed to be updated.
	 * @param	noPax		The new number of pax.
	 * @return	The new reservation id or "false" if the update cannot be made.
	 */
	public String updateReservation(String res_id, int noPax) {
		return this.tableController.updateReservation(res_id, noPax);
	}

	/**
	 * Prints all of the reservations that are reserved with table tableId.
	 *
	 * @param	tableId		The id of table that is needed to find the resevations.
	 */
	public void printReservations(int tableId) {
		this.tableController.printReservations(tableId);
	}

	/**
	 * Prints all of the reservations in this restaurant.
	 */
	public void printReservations() {
		this.tableController.printReservations();
	}

	/**
	 * Deletes all of the resevations that are expired after 1 minute not checking in.
	 */
	public void deleteExpiredReservations() {
		this.tableController.deleteExpiredReservations();
	}
	
	/**
	 * Updates the database files of the restaurant when the app is closed.
	 */
	public void updateRestaurantDatabase(){
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

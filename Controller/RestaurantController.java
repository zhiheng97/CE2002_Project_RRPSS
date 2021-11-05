package Controller;

import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;

import Models.*;

public class RestaurantController {

	private TableController tableController;
	private ReportController reportController;
	private CategoryController categoryController;
	private PromotionController promotionController;
	private FileController fileController;
	private static final String DATETIME_FORMAT_PATTERN = "EEE MMM yy HH:mm:ss z yyyy";
	
	private List<Staff> staffList;
	private static final String PATH_TO_STAFFS_FILE = Path.of("./Data/staff.txt").toString();

	private List<Customer> customerList;
	private static final String PATH_TO_CUSTOMERS_FILE = Path.of("./Data/customers.txt").toString();

	public RestaurantController() throws NumberFormatException, ParseException {
		this.tableController = new TableController(12);
		this.reportController = new ReportController();
		this.categoryController = new CategoryController();
		this.promotionController = new PromotionController();
		this.fileController = new FileController();

		this.customerList = new ArrayList<Customer>();
		List<String> custParams = fileController.readFile(PATH_TO_CUSTOMERS_FILE);
		for (int i = 4; i < custParams.size(); i += 4) {
			this.customerList.add(
				new Customer(
					Integer.parseInt(custParams.get(i)), 
					custParams.get(i + 1), 
					Boolean.parseBoolean(custParams.get(i + 2)),
					Integer.parseInt(custParams.get(i + 3))
				)
			);
		}

		this.staffList = new ArrayList<Staff>();
		List<String> staffParams = fileController.readFile(PATH_TO_STAFFS_FILE);
		for (int i = 3; i < staffParams.size(); i += 3) {
			this.staffList.add(
				new Staff(
					Integer.parseInt(staffParams.get(i)), 
					staffParams.get(i + 1), 
					staffParams.get(i + 2)
				)
			);
		}

		// initialize order for occupied tables
		Random rand = new Random();
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT_PATTERN);
		for (int id=1; id<=12; id++) {
			if (this.tableController.findTableByNo(id).getIsOccupied()){
				this.tableController.findTableByNo(id).setInvoice(
					new Order(
						this.staffList.get(rand.nextInt(this.staffList.size())),
						this.customerList.get(rand.nextInt(this.customerList.size())),
						sdf.format(now)
					)
				);
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

	public boolean removePromotion(int promoId) {
		return promotionController.removePromotion(promoId);
	}

	public void printPromotion() {
		promotionController.print();
	}
	
////////////////////// ORDER FUNCTIONS ///////////////////

	/**
	 * TODO: handle the mismatch input
	 *  
	 * @param res_id
	 * @return the tableNo and cust_id that for this reservation
	 */
	public int[] checkinReservation(String res_id) {
		String[] res_id_params = res_id.split("-");
		int tableNo = Integer.parseInt(res_id_params[0]);
		int id = Integer.parseInt(res_id_params[1]);
		if (tableNo < 1 || tableNo > 12 || id < 0 || id >= 15) return null;
		Reservation res = this.tableController.findReservation(res_id);
		int cust_id = res.getCustId();
		this.tableController.clearReservation(res_id);
		return new int[]{tableNo, cust_id};
	}

	/**
	 * create new order for new customer
	 * @param tableNo
	 * @param staffID
	 * @param date
	 */
	public void createOrder(int tableNo, int cust_id, int staffID, String date) {
		int i = 0;
		for(; i < staffList.size(); i++){
			if(staffList.get(i).getId() == staffID) break;
		}
		if(i == staffList.size()){
			System.out.println("There is no staff with the ID: " + staffID);
			return;
		}
		Staff staff = this.staffList.get(i);
		Customer cust = this.customerList.get(cust_id);
		this.tableController.findTableByNo(tableNo).setIsOccupied(true);
		this.tableController.findTableByNo(tableNo).setInvoice(new Order(staff, cust, date));
		System.out.printf("The new order is created for table %d. Enjoy!\n", tableNo);
	}

	/**
	 * add a quantity of item/promotion to the order of table tableNo
	 * @param tableNo
	 * @param itemId
	 * @param quantity
	 */
	public void addToOrder(int tableNo, int itemId, int quantity) {
		Promotion promoToAdd = this.promotionController.findPromotionById(itemId);
		Item itemToAdd = this.categoryController.searchForItem(itemId);
		if (promoToAdd != null) {
			Promotion copied = this.promotionController.copyPromotion(itemId);
			this.tableController.addToOrder(tableNo, copied, quantity);
		} else if (itemToAdd != null) {
			Item copied = this.categoryController.copyItem(itemId);
			this.tableController.addToOrder(tableNo, copied, quantity);
		}
	}

	/**
	 * remove item or promotion from the order of table tableNo
	 * @param tableNo
	 * @param itemId
	 * @return
	 */
	public boolean removeFromOrder(int tableNo, int itemId) {
		Promotion promoToRemove = this.promotionController.findPromotionById(itemId);
		if (promoToRemove != null) {
			Promotion copied = this.promotionController.copyPromotion(itemId);
			return this.tableController.removeFromOrder(tableNo, copied);
		} else {
			Item copied = this.categoryController.copyItem(itemId);
			return this.tableController.removeFromOrder(tableNo, copied);
		}
	}

	/**
	 * print the final invoice of table tableNo
	 * add the invoice to the reportController and clear the table
	 * @param tableNo
	 */
	public void printInvoice(int tableNo) {
		Order invoice = this.tableController.findTableByNo(tableNo).getInvoice();
		this.reportController.addInvoice(invoice); // Adds completed invoice to reportController to manage
		this.tableController.printInvoice(tableNo);
	}

	/**
	 * view the current order of the table tableNo
	 * @param tableNo
	 */
	public void viewOrder(int tableNo) {
		this.tableController.viewOrder(tableNo);
	}

	/**
	 * find first availabe table for noPax
	 * @param noPax
	 * @return
	 */
	public int findValidTable(int noPax) {
		return this.tableController.findValidTable(noPax);
	}

	/**
	 * check whether table tableNo is occupied or not
	 * @param tableNo
	 * @return
	 */
	public boolean isTableOccupied(int tableNo) {
		return this.tableController.findTableByNo(tableNo).getIsOccupied();
	}

	/**
	 * print all occupied tables to let the staff choose which table to udpate order
	 * @return false if all tables are unoccupied
	 */
	public boolean printUnavailableTables() {
		return this.tableController.printUnavailableTables();
	}

	/**
	 * print all unoccupied tables
	 */
	public void printAvailableTables() {
		this.tableController.printAvailableTables();
	}

	/**
	 * print all unoccupied tables that has number of seats >= noPax 
	 * @param noPax
	 */
	public void printAvailableTables(int noPax) {
		this.tableController.printAvailableTables(noPax);
	}

////////////////////// RESERVATION FUNCTIONS ///////////////////

	/**
	 * for debugging purpose only when add register new Customer
	 * show all customers in memory
	 */
	public void showCustomers() { 		// for debug
		for (Customer cust : this.customerList) 
			System.out.printf("%d %s\n", cust.getId(), cust.getName());
	}



	/**
	 * @param cust_name
	 * @param contactNo
	 * @return cust_id for the new Customer
	 */
	public int registerCustomer(String cust_name, int contactNo) {
		int new_id = this.customerList.size();
		this.customerList.add(new Customer(new_id, cust_name, false, contactNo));
		return new_id + 1;
	}

	/**
	 * @param details
	 * @return res_id if reserve successfully, "false" otherwise
	 */
	public String reserveTable(String[] details) throws NumberFormatException, ParseException {
		return this.tableController.reserveTable(details);
	}

	/**
	 * @param res_id
	 * return true/false
	 */
	public boolean clearReservation(String res_id) {
		return this.tableController.clearReservation(res_id);
	}

	/**
	 * @params res_id, datetime
	 * @return new_res_id or "false"
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	public String updateReservation(String res_id, String datetime) throws NumberFormatException, ParseException {
		return this.tableController.updateReservation(res_id, datetime);
	}

	/**
	 * @params res_id, noPax
	 * @return new_res_id or "false"
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	public String updateReservation(String res_id, int noPax) throws NumberFormatException, ParseException {
		return this.tableController.updateReservation(res_id, noPax);
	}

	public void deleteExpiredReservations() {
		this.tableController.deleteExpiredReservations();
	}

	/**
	 * @param tableNo
	 * print all reservations of a specified table 
	 */
	public void printReservations(int tableNo) {
		this.tableController.printReservations(tableNo);
	}

	/**
	 * print all reservations of the restaurant
	 */
	public void printReservations() {
		this.tableController.printReservations();
	}

	


////////////////////// REPORT FUNCTIONS ///////////////////

	/**
	 *
	 * @param byMonth
	 */
	public void printSalesReport(boolean byMonth) {
		this.reportController.print(byMonth);
	}

	/**
	 * Removes a specific promotion
	 *
	 * @param promoId, the promotion id which is used to search for a specific
	 *                 promotion
	 * @return true or false based on success/error
	 */

}

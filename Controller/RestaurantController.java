package Controller;

import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

	private List<Customer> memberList;
	private static final String PATH_TO_MEMBERS_FILE = Path.of("./Data/members.txt").toString();

	public RestaurantController() throws NumberFormatException, ParseException {
		this.tableController = new TableController(12);
		this.reportController = new ReportController();
		this.categoryController = new CategoryController();
		this.promotionController = new PromotionController();
		this.fileController = new FileController();

		this.memberList = new ArrayList<Customer>();
		List<String> memberParams = fileController.readFile(PATH_TO_MEMBERS_FILE);
		for (int i = 3; i < memberParams.size(); i += 3) {
			this.memberList.add(
				new Customer(
					Integer.parseInt(memberParams.get(i)), 
					memberParams.get(i + 1), 
					true,
					Integer.parseInt(memberParams.get(i + 2))
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

		Random rand = new Random();
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT_PATTERN);
		for (int id=1; id<=12; id++) {
			if (this.tableController.findTableByNo(id).getIsOccupied()){
				this.tableController.findTableByNo(id).setInvoice(
					new Order(
						this.staffList.get(rand.nextInt(this.staffList.size())),
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

	public void createOrder(int tableNo, int staffID, String date) {
		int i = 0;
		for(; i < staffList.size(); i++){
			if(staffList.get(i).getId() == staffID) break;
		}
		if(i == staffList.size()){
			System.out.println("There is no staff with the ID: " + staffID);
			return;
		}
		Staff staff = this.staffList.get(i);
		this.tableController.findTableByNo(tableNo).setIsOccupied(true);
		this.tableController.findTableByNo(tableNo).setInvoice(new Order(staff, date));
		System.out.printf("The new order is created for table %d. Enjoy!\n", tableNo);
	}

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

	public void printInvoice(int tableNo) {
		Order invoice = this.tableController.findTableByNo(tableNo).getInvoice();
		this.reportController.addInvoice(invoice); // Adds completed invoice to reportController to manage
		this.tableController.printInvoice(tableNo);
	}

	public void viewOrder(int tableNo) {
		this.tableController.viewOrder(tableNo);
	}

	// find first availabe table for noPax
	public int findValidTable(int noPax) {
		return this.tableController.findValidTable(noPax);
	}

	public boolean isTableOccupied(int tableNo) {
		return this.tableController.findTableByNo(tableNo).getIsOccupied();
	}

	public boolean printUnavailableTables() {
		return this.tableController.printUnavailableTables();
	}

	public void printAvailableTables() {
		this.tableController.printAvailableTables();
	}

	public void printAvailableTables(int noPax) {
		this.tableController.printAvailableTables(noPax);
	}

////////////////////// RESERVATION FUNCTIONS ///////////////////

	public void showCustomers() { 		// for debug
		for (Customer cust : this.memberList) 
			System.out.printf("%d %s\n", cust.getId(), cust.getName());
	}

	public int registerCustomer(String cust_name, int contactNo) {
		int new_id = this.memberList.size();
		this.memberList.add(new Customer(new_id, cust_name, false, contactNo));
		return new_id + 1;
	}

	public boolean reserveTable(String[] details) throws NumberFormatException, ParseException {
		return tableController.reserveTable(details);
	}

	// public void expireReservations(Date date) {
	// 	this.tableController.expireReservations(date);
	// }

	// public void printReservations(int tableNo) {
	// 	tableController.printReservations(tableNo);
	// }

	public void printReservations() {
		tableController.printReservations();
	}

	// /**
	//  *
	//  * @param tableNo
	//  * @param details
	//  */
	// public boolean clearReservation(int tableNo) {
	// 	return tableController.clearReservation(tableNo);
	// }


////////////////////// REPORT FUNCTIONS ///////////////////

	/**
	 *
	 * @param byMonth
	 */
	public void printSalesReport(boolean byMonth) {
		reportController.print(byMonth);
	}

	/**
	 * Removes a specific promotion
	 *
	 * @param promoId, the promotion id which is used to search for a specific
	 *                 promotion
	 * @return true or false based on success/error
	 */

}

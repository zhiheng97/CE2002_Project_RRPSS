/**
 * A controller that is responsible for managing tables and their reservations.
 * @author  @Henry-Hoang
 * @since 10 October 2021
 */
package Controller;

import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Models.Item;
import Models.Order;
import Models.Promotion;
import Models.Reservation;
import Models.Table;

public class TableController {

	private int noOfTables;
	private List<Table> tables = new ArrayList<Table>();
	private static final String PATH_TO_TABLES_FILE = Path.of("./Data/table.txt").toString();
	private static final String PATH_TO_RESERVATIONS_FILE = Path.of("./Data/reservation.txt").toString();
	private static final String DATETIME_FORMAT_PATTERN = "EEE MMM dd HH:mm:ss z yyyy";
	private static final String DELIMITER = ",";
	private FileController fileController = new FileController();

	public TableController(int noOfTables) {
		this.noOfTables = noOfTables;
		this.tables = new ArrayList<Table>(noOfTables);
		String[] params;
		// this.initializeTables();
		List<String> tableParams = fileController.readFile(PATH_TO_TABLES_FILE);
		for (int i = 1; i < tableParams.size(); i++) {
			params = tableParams.get(i).split(DELIMITER);
			tables.add(new Table(Integer.parseInt(params[0]), Boolean.parseBoolean(params[1]),
					Integer.parseInt(params[2])));
		}

		List<String> reserveParams = fileController.readFile(PATH_TO_RESERVATIONS_FILE);
		for (int i = 1; i < reserveParams.size(); i++) {
			params = reserveParams.get(i).split(DELIMITER);
			this.reserveTable(params);
		}
	}

////////////////////// BASIC METHODS ///////////////////

	/**
	 * @param tableNo
	 * @return Table object
	 */
	public Table findTableByNo(int tableNo) {
		return tables.stream().filter(t -> t.getTableNo() == tableNo).findFirst().orElse(null);
	}

	/**
	 * @return number of tables
	 */
	public int getNoOfTables() {
		return this.noOfTables;
	}

	/**
	 * print all occupied tables
	 * @return false if all the tables are unoccupied
	 */
	public boolean printUnavailableTables() {
		int num_occupied = 0;
		for (Table table : tables) {
			if (table.getIsOccupied()) {
				System.out.printf("Table %d is occupied, staff: %s\n", table.getTableNo(), table.getInvoice().getPlacedBy());
				num_occupied++;
			}  
		}
		if (num_occupied == 0) {
			System.out.println("There is no occupied table at the moment, please checkin to create a new order."); 
			return false;
		}
		return true;
	}

	/**
	 * print all unoccupied tables
	 * @return false if all tables are occupied
	 */
	public boolean printAvailableTables() {
		int num_avail = 0;
		for (Table table : tables) {
			if (!table.getIsOccupied()) {
				System.out.printf("Table %d (max %d pax)\n", table.getTableNo(), table.getSeats());
				num_avail++;
			}
		}
		if (num_avail == 0) {
			System.out.println("There is no available table at the moment, please comeback later or make reservation!");
			return false;
		}
		return true;
	}

	/**
	 * print all unoccupied tables that has number of seats >= noPax 
	 * @param noPax
	 */
	public void printAvailableTables(int noPax) {
		int num_occupied = 0;
		for (Table table : tables) {
			if (!table.getIsOccupied() && table.getSeats() >= noPax) {
				System.out.printf("Table %d (max %d pax)\n", table.getTableNo(), table.getSeats());
			} else num_occupied++;
		}
		if (num_occupied == this.noOfTables) 
			System.out.println("All the tables are occupied!");
	}

////////////////////// ORDER FUNCTIONS ///////////////////

	/**
	 * find the first valid table for noPax
	 * the tables are sorted by noPax already so just loop until got one
	 * @param noPax
	 * @return tableNo, -1 if there is no available tables
	 */
	public int findValidTableToCheckin(int noPax) {
		for (Table table : this.tables) {
			if (!table.getIsOccupied() && noPax <= table.getSeats() &&
				!table.isReserved())
				return table.getTableNo();
		}
		return -1;
	}

	/**
	 * add a quantity of Item objects to the order of table tableNo
	 * @param tableNo
	 * @param item
	 * @param quantity
	 */
	public void addToOrder(int tableNo, Item item, int quantity) {
		this.findTableByNo(tableNo).setIsOccupied(true);
		this.findTableByNo(tableNo).addToOrder(item, quantity);
	}

	/**
	 * add a quantity of Promotion objects to the order of table tableNo
	 * @param tableNo
	 * @param promotion
	 * @param quantity
	 */
	public void addToOrder(int tableNo, Promotion promotion, int quantity) {
		this.findTableByNo(tableNo).setIsOccupied(true);
		this.findTableByNo(tableNo).addToOrder(promotion, quantity);
	}

	/**
	 * remove Item objects from the order of table tableNo
	 * 
	 * @param tableNo 		the tableNo that has the order need to process
	 * @param item 			Item to be removed
	 * @param quantity 		number of Item object to remove
	 * @return 2 if they are removed normally
	 * @return 1 if quantity >= current quantity in order (remove all anw)
	 * @return 0 if there is no Item in this order
	 */
	public int removeFromOrder(int tableNo, Item item, int quantity) {
		return this.findTableByNo(tableNo).removeFromOrder(item, quantity);
	}

	/**
	 * remove Promotion objects from the order of table tableNo
	 * 
	 * @param tableNo 		the tableNo that has the order need to process
	 * @param promotion 	Promotion to be removed
	 * @param quantity 		number of Promotion object to remove
	 * @return 2 if they are removed normally
	 * @return 1 if quantity >= current quantity in order (remove all anw)
	 * @return 0 if there is no Promotion in this order
	 */
	public int removeFromOrder(int tableNo, Promotion promotion, int quantity) {
		return this.findTableByNo(tableNo).removeFromOrder(promotion, quantity);
	}

	/**
	 * view the current order of the table tableNo
	 * @param tableNo
	 */
	public void printOrder(int tableNo, boolean withPrice) {
		this.findTableByNo(tableNo).printOrder(withPrice);
	}

	/**
	 * print the final invoice of table tableNo
	 * clear the table (set to unoccupied + remove order)
	 * @param tableNo
	 */
	public void printInvoice(int tableNo) {
		Table table = this.findTableByNo(tableNo);
		table.print();
		table.setIsOccupied(false);
		table.setInvoice(new Order(null, null, null));
	}

////////////////////// RESERVATION FUNCTIONS ///////////////////

	/**
	 * input from RestaurantController
	 * @param details[3]: cust_id, res_datetime, pax
	 * @return tableNo that is allocated for reservation, -1 if not found
	 */
	public int findValidTableToReserve(String[] details) throws ParseException {
		int noPax = Integer.parseInt(details[2]);
		SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT_PATTERN);
		Date res_date = sdf.parse(details[1]);
		
		int tableNo = -1;
		switch (noPax) { 
			case 1: 	// search table 1->2 (2 pax)
				for (int id=1; id<=2; id++) {
					boolean isValid = true;
					Table table = this.findTableByNo(id);
					if (table.getSeats() < noPax) continue;
					for (Reservation res : table.getReservations()) {
						Date temp_date = res.getDate();
						long time_diff = res_date.getTime() - temp_date.getTime();
						if (time_diff >= 8.64e7) continue; 				// 1 day
						if (time_diff <= 60000) isValid = false;		// 1 min
					}
					if (isValid) {
						tableNo = id;
						break;
					}
				}
				break; 
			case 2, 3:				// search table 1->2 (2 pax), 3->5 (4 pax)
				for (int id=1; id<=5; id++) {
					boolean isValid = true;
					Table table = this.findTableByNo(id);
					if (table.getSeats() < noPax) continue;
					for (Reservation res : table.getReservations()) {
						Date temp_date = res.getDate();
						long time_diff = res_date.getTime() - temp_date.getTime();
						if (time_diff >= 8.64e7) continue; 				// 1 day
						if (time_diff <= 60000) isValid = false;		// 1 min
					}
					if (isValid) {
						tableNo = id;
						break;
					}
				}
				break;
			case 4, 5, 6:	// search table 3->5 (4 pax), 6->8 (6 pax)
				for (int id=3; id<=8; id++) {
					boolean isValid = true;
					Table table = this.findTableByNo(id);
					if (table.getSeats() < noPax) continue;
					for (Reservation res : table.getReservations()) {
						Date temp_date = res.getDate();
						long time_diff = res_date.getTime() - temp_date.getTime();
						if (time_diff >= 8.64e7) continue; 				// 1 day
						if (time_diff <= 120000) isValid = false;		// 2 min
					}
					if (isValid) {
						tableNo = id;
						break;
					}
				}
				break;
			case 7:		// search table 6->8 (6 pax), 9->10 (8 pax)
				for (int id=6; id<=10; id++) {
					boolean isValid = true;
					Table table = this.findTableByNo(id);
					if (table.getSeats() < noPax) continue;
					for (Reservation res : table.getReservations()) {
						Date temp_date = res.getDate();
						long time_diff = res_date.getTime() - temp_date.getTime();
						if (time_diff >= 8.64e7) continue; 				// 1 day
						if (time_diff <= 120000) isValid = false;		// 2 min
					}
					if (isValid) {
						tableNo = id;
						break;
					}
				}
				break;
			case 8, 9, 10:	// search table 9->10 (8 pax), 11->12 (10 pax)
				for (int id=9; id<=12; id++) {
					boolean isValid = true;
					Table table = this.findTableByNo(id);
					if (table.getSeats() < noPax) continue;
					for (Reservation res : table.getReservations()) {
						Date temp_date = res.getDate();
						long time_diff = res_date.getTime() - temp_date.getTime();
						if (time_diff >= 8.64e7) continue; 				// 1 day
						if (time_diff <= 120000) isValid = false;		// 2 min
					}
					if (isValid) {
						tableNo = id;
						break;
					}
				}
				break;
		}
		return tableNo;
	}

	/**
	 * 1) input from RestaurantController when add new reservation
	 * @param details[3]: cust_id, res_datetime, pax
	 * @return res_id if allocated succesfully, "false" other
	 * 2) input from TableController's constructor for initialize the memory
	 * @param details[5]: cust_id, res_datetime, pax, table_id, res_id
	 * @return nothing, does not matter
	 */
	public String reserveTable(String[] details) {
		int tableNo = -1;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT_PATTERN);
		try {
			if(sdf.parse(details[1]).getTime() - date.getTime() >= (60000 * 5)) {
				if (details.length == 5) {
					tableNo = Integer.parseInt(details[3]);
					this.findTableByNo(tableNo).addReservation(
						new Reservation (
							details[4],						// res_id
							Integer.parseInt(details[0]), 	// cust_id
							sdf.parse(details[1]), 			// date
							Integer.parseInt(details[2]) 	// pax
						)
					);
				} else {
					tableNo = this.findValidTableToReserve(details);
					if (tableNo == -1) return "false 1";
					return this.findTableByNo(tableNo).addReservation(
						Integer.parseInt(details[0]), 		// cust_id
						sdf.parse(details[1]), 				// date
						Integer.parseInt(details[2]) 		// pax
					);
				}
			} else return "false 2";
		} catch (ParseException e) {
			System.out.println("Error Occured! \nPlease contact RRPSS Support Team for assistance.");
			System.out.println("");
		}
		return "false 1";
	}

	/**
	 * @param res_id String
	 * @return the corresponding Reservation object
	 */
	public Reservation findReservation(String res_id) {
		String[] res_id_params = res_id.split("-");
		return this.findTableByNo(Integer.parseInt(res_id_params[0])).findReservation(res_id);
	}

	/**
	 * @param res_id (e.g. 5-6 -> table 5, id 6)
	 * @return true/false 
	 * idk when it should return false
	 */
	public boolean clearReservation(String res_id) {
		String[] res_id_params = res_id.split("-");
		return this.findTableByNo(Integer.parseInt(res_id_params[0])).removeReservation(res_id);
	}

	/**
	 * @param res_id
	 * @param datetime
	 * @return String new_res_id or "false"
	 */
	public String updateReservation(String res_id, String dateTime) {
		String[] res_id_params = res_id.split("-");
		
		Reservation copied = this.findTableByNo(Integer.parseInt(res_id_params[0]))
								.getReservations()
								.get(Integer.parseInt(res_id_params[1]));

		String[] new_res_params = new String[3];
		new_res_params[0] = String.valueOf(copied.getCustId());
		new_res_params[1] = dateTime;
		new_res_params[2] = String.valueOf(copied.getNoPax());
		String new_res_id = this.reserveTable(new_res_params);
		if(!new_res_id.equals("false 1") && !new_res_id.equals("false 2")) {
			this.clearReservation(res_id);
			if (new_res_id == "false") this.findTableByNo(Integer.parseInt(res_id_params[0])).addReservation(copied);
			return new_res_id;
		}
		else
			return res_id;

		
	}

	/**
	 * @param res_id
	 * @param noPax
	 * @return
	 */
	public String updateReservation(String res_id, int noPax) {
		String[] res_id_params = res_id.split("-");
		
		Reservation copied = this.findTableByNo(Integer.parseInt(res_id_params[0]))
								.getReservations()
								.get(Integer.parseInt(res_id_params[1]));
		this.clearReservation(res_id);

		String[] new_res_params = new String[3];
		new_res_params[0] = String.valueOf(copied.getCustId());
		new_res_params[1] = copied.getTime();
		new_res_params[2] = String.valueOf(noPax);
		String new_res_id = this.reserveTable(new_res_params);
		
		if (new_res_id == "false") this.findTableByNo(Integer.parseInt(res_id_params[0])).addReservation(copied);
		return new_res_id;
	}

	/**
	 * @param tableNo
	 * print all reservation of 1 table tableNo
	 */
	public void printReservations(int tableNo) {
		System.out.println("Reservations for table " + this.findTableByNo(tableNo));
		for (Reservation reservation : this.findTableByNo(tableNo).getReservations()) {
			reservation.print();
			System.out.println();
		}
	}

	/**
	 * @param tableNo
	 * print all reservation of all tables
	 */
	public void printReservations() {
		for(Table table : this.tables){
			System.out.printf("- Table %d: %d reservation(s).\n", table.getTableNo(), table.getNoOfReseravtions());
			for (Reservation reservation : table.getReservations())
				reservation.print();
		}
	}

	/**
	 * pass the Date object to the table and clear all reservations 
	 * after 1 minute not cheking in
	 */
	public void deleteExpiredReservations() {
		Date date = new Date();
		for (Table table : this.tables) {
			table.deleteExpiredReservations(date);
		}
	}
/*
	private boolean updateReservationFile() {
		List<String> updatedRes = new ArrayList<String>();
		for (Table t : tables) {
			for (Reservation r : t.getReservations()) {
				updatedRes.add(String.valueOf(r.getId()));
				updatedRes.add(String.valueOf(t.getTableNo()));
				updatedRes.add(r.getCustomer().getName());
				updatedRes.add(String.valueOf(r.getCustomer().getMobileNo()));
				updatedRes.add(r.getDate().toString());
				updatedRes.add(String.valueOf(r.getNoPax()));
			}
		}
		return fileController.writeFile(updatedRes.toArray(new String[updatedRes.size()]), PATH_TO_RESERVATIONS_FILE);
	}
	
	public void expireReservations(Date date) {
		do {
			Reservation expired = tables.stream().flatMap(t -> t.getReservations().stream())
					.filter(r -> (r.getDate().getTime() + EXPIRE_BUFFER_MILLISECOND) - date.getTime() <= 0).findFirst()
					.orElse(null);
			if (expired == null)
				return;
			Table table = tables.stream().filter(t -> t.getReservations().contains(expired)).findFirst().orElse(null);
			if (table.getReservations().remove(expired))
				updateReservationFile();
		} while (true);
	}
*/

	

}

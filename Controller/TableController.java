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

/**
 * The TableController class is a controller class to handle queries that are related to:<br>
 * <ol>
 * 		<li>Order: let the staff update the status of any current order.</li>
 * 		<li>Reservation: let the staff add/remove/update any reservation.</li>
 * </ol>
 *
 */
public class TableController {

	private int noOfTables;
	private List<Table> tables = new ArrayList<Table>();
	private static final String PATH_TO_TABLES_FILE = Path.of("./Data/table.txt").toString();
	private static final String PATH_TO_RESERVATIONS_FILE = Path.of("./Data/reservation.txt").toString();
	private static final String DATETIME_FORMAT_PATTERN = "dd-MMM-yy HH:mm";
	private static final String DELIMITER = ",";
	private FileController fileController = new FileController();

	/**
	 * Constructs the TableController and initializes the tables and reservations from txt files.
	 * @param	noOfTables	Number of tables in this restaurant (12 by default).
	 */
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
	 * Finds the table by its id.
	 * @param	tableId	The id of the table that is needed to be search.
	 * @return 			The corresponding Table object.
	 */
	public Table findTableById(int tableId) {
		return tables.stream().filter(t -> t.getTableId() == tableId).findFirst().orElse(null);
	}

	/**
	 * Returns the number of tables in this restaurant.
	 * @return 			Number of tables in this restaurant.
	 */
	public int getNoOfTables() {
		return this.noOfTables;
	}

	/**
	 * Prints all occupied tables.
	 * @return 			true if there is at least 1 occupied table, false otherwise.
	 */
	public boolean printUnavailableTables() {
		int num_occupied = 0;
		for (Table table : tables) {
			if (table.getIsOccupied()) {
				System.out.printf("Table %d is occupied, staff: %s\n", table.getTableId(), table.getInvoice().getPlacedBy());
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
	 * Prints all unoccupied tables.
	 * @return			true if there is at least 1 unoccupied table, false otherwise.
	 */
	public boolean printAvailableTables() {
		int num_avail = 0;
		for (Table table : tables) {
			if (!table.getIsOccupied()) {
				System.out.printf("Table %d (max %d pax)\n", table.getTableId(), table.getSeats());
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
	 * Prints all unoccupied tables that has number of seats >= noPax.
	 * @param 	noPax 	The number of pax.
	 */
	public void printAvailableTables(int noPax) {
		int num_occupied = 0;
		for (Table table : tables) {
			if (!table.getIsOccupied() && table.getSeats() >= noPax) {
				System.out.printf("Table %d (max %d pax)\n", table.getTableId(), table.getSeats());
			} else num_occupied++;
		}
		if (num_occupied == this.noOfTables)
			System.out.println("All the tables are occupied!");
	}

////////////////////// ORDER FUNCTIONS ///////////////////

	/**
	 * Adds a quantity of Item objects to the order of the table tableId.
	 *
	 * @param	tableId		The id of the table that has the order needs to be processed.
	 * @param	item		The Item object to be added.
	 * @param 	quantity	The number of Item objects to be added.
	 */
	public void addToOrder(int tableId, Item item, int quantity) {
		this.findTableById(tableId).setIsOccupied(true);
		this.findTableById(tableId).addToOrder(item, quantity);
	}

	/**
	 * Adds a quantity of Promotion objects to the order of the table tableId.
	 *
	 * @param 	tableId		The id of the table that has the order needs to be processed.
	 * @param 	promotion	The Promotion object to be added.
	 * @param 	quantity	The number of Promotion objects to be added.
	 */
	public void addToOrder(int tableId, Promotion promotion, int quantity) {
		this.findTableById(tableId).setIsOccupied(true);
		this.findTableById(tableId).addToOrder(promotion, quantity);
	}

	/**
	 * Removes a quantity of Item objects from the order of the table tableId.
	 *
	 * @param 	tableId 	The id of the table that has the order needs to be processed.
	 * @param 	item 		The Item object to be removed.
	 * @param 	quantity 	The number of Item objects to be removed.
	 * @return 	2 if a quantity of item is removed from the order,<br>
	 * 			or 1 if all the occurrences of this item are removed from the order,<br>
	 * 			or 0 if cannot remove because there is no occurrence of this item in the order.
	 */
	public int removeFromOrder(int tableId, Item item, int quantity) {
		return this.findTableById(tableId).removeFromOrder(item, quantity);
	}

	/**
	 * Removes a quantity of Promotion objects from the order of the table tableId.
	 *
	 * @param 	tableId 	The id of the table that has the order needs to be processed.
	 * @param	promotion 	The Promotion object to be removed.
	 * @param 	quantity 	The number of Promotion objects to be removed.
	 * @return 	2 if a quantity of promotion is removed from the order,<br>
	 * 			or 1 if all the occurrences of this promotion are removed from the order,<br>
	 * 	  		or 0 if cannot remove because there is no occurrence of this promotion in the order.
	 */
	public int removeFromOrder(int tableId, Promotion promotion, int quantity) {
		return this.findTableById(tableId).removeFromOrder(promotion, quantity);
	}

	/**
	 * Prints the current status of the order of table tableId.
	 * @param 	tableId		The id of the table that has the order needs to be printed.
	 * @param	withPrice	true to print the order's price when the customer checks out, false otherwise.
	 */
	public void printOrder(int tableId, boolean withPrice) {
		this.findTableById(tableId).printOrder(withPrice);
	}

	/**
	 * Prints the final invoice of the table tableId when the customer wants to check out.<br>
	 * Clears the table tableId by setting it to unoccupied and removing the current order.
	 * @param 	tableId		The id of the table that has the order needs to be printed
	 */
	public void printInvoice(int tableId) {
		Table table = this.findTableById(tableId);
		table.print();
		table.setIsOccupied(false);
		table.setInvoice(new Order(null, null, null));
	}

////////////////////// RESERVATION FUNCTIONS ///////////////////

	/**
	 * Returns a valid table id to check in or make reservation (based on the restaurant's policy):<br>
	 * <ul>
	 * 		<li>For 1 or 2 pax: 	Only allocates tables of 2 or 4 pax.</li>
	 * 		<li>For 3 or 4 pax:	Only allocates tables of 4 or 6 pax.</li>
	 * 		<li>For 5 or 6 pax:	Only allocates tables of 6 or 8 pax.</li>
	 * 		<li>For 9 or 10 pax:	Only allocates tables of 10 pax.</li>
	 * </ul>
	 *
	 * @param	details	A string array (customer id, timestamp, number of pax).
	 * @return 	The tableId of a valid table, -1 if it cannot find any.
	 * @exception	ParseException
	 */
	public int findValidTable(String[] details) throws ParseException {
		int noPax = Integer.parseInt(details[2]);
		SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT_PATTERN);
		Date res_date = sdf.parse(details[1]);

		int tableId = -1;
		switch (noPax) {
			case 1, 2: 				// search tables 1->2 (2 pax), 3->5 (4 pax)
				for (int id=1; id<=5; id++) {
					boolean isValid = true;
					Table table = this.findTableById(id);
					if (table.getSeats() < noPax) continue;
					for (Reservation res : table.getReservations()) {
						Date temp_date = res.getDate();
						long time_diff = res_date.getTime() - temp_date.getTime();
						if (time_diff >= 8.64e7) continue; 				// 1 day
						if (time_diff <= 60000) isValid = false;		// 1 min
					}
					if (table.getIsOccupied()) isValid = false;
					if (isValid) {
						tableId = id;
						break;
					}
				}
				break;
			case 3, 4:				// search tables 3->5 (4 pax), 6->8 (6 pax)
				for (int id=3; id<=8; id++) {
					boolean isValid = true;
					Table table = this.findTableById(id);
					if (table.getSeats() < noPax) continue;
					for (Reservation res : table.getReservations()) {
						Date temp_date = res.getDate();
						long time_diff = res_date.getTime() - temp_date.getTime();
						if (time_diff >= 8.64e7) continue; 				// 1 day
						if (time_diff <= 60000) isValid = false;		// 1 min
					}
					if (table.getIsOccupied()) isValid = false;
					if (isValid) {
						tableId = id;
						break;
					}
				}
				break;
			case 5, 6:				// search tables 6->8 (6 pax), 9->10 (8 pax)
				for (int id=6; id<=10; id++) {
					boolean isValid = true;
					Table table = this.findTableById(id);
					if (table.getSeats() < noPax) continue;
					for (Reservation res : table.getReservations()) {
						Date temp_date = res.getDate();
						long time_diff = res_date.getTime() - temp_date.getTime();
						if (time_diff >= 8.64e7) continue; 				// 1 day
						if (time_diff <= 120000) isValid = false;		// 2 min
					}
					if (table.getIsOccupied()) isValid = false;
					if (isValid) {
						tableId = id;
						break;
					}
				}
				break;
			case 7, 8:				// search tables 9->10 (8 pax), 11->12 (10 pax)
				for (int id=9; id<=12; id++) {
					boolean isValid = true;
					Table table = this.findTableById(id);
					if (table.getSeats() < noPax) continue;
					for (Reservation res : table.getReservations()) {
						Date temp_date = res.getDate();
						long time_diff = res_date.getTime() - temp_date.getTime();
						if (time_diff >= 8.64e7) continue; 				// 1 day
						if (time_diff <= 120000) isValid = false;		// 2 min
					}
					if (table.getIsOccupied()) isValid = false;
					if (isValid) {
						tableId = id;
						break;
					}
				}
				break;
			case 9, 10:				// search tables 11->12 (10 pax)
				for (int id=11; id<=12; id++) {
					boolean isValid = true;
					Table table = this.findTableById(id);
					if (table.getSeats() < noPax) continue;
					for (Reservation res : table.getReservations()) {
						Date temp_date = res.getDate();
						long time_diff = res_date.getTime() - temp_date.getTime();
						if (time_diff >= 8.64e7) continue; 				// 1 day
						if (time_diff <= 120000) isValid = false;		// 2 min
					}
					if (table.getIsOccupied()) isValid = false;
					if (isValid) {
						tableId = id;
						break;
					}
				}
				break;
		}
		return tableId;
	}

	/**
	 * Returns the id of the new reservation if it is made successfully.
	 *
	 * @param	details	A string array to make new reservation (customer id, timestamp, number of pax),<br>
	 *                 	or a string array from the constructor (customer id, timestamp, number of pax, table id, reservation id).
	 * @return	The id of reservation if it is allocated successfully,<br>
	 * 			or "false 1" if there is no available table to reserve,<br>
	 * 			or "false 2" if the time date of this reservation is in the past.
	 */
	public String reserveTable(String[] details) {
		int tableId = -1;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT_PATTERN);
		try {
			if(sdf.parse(details[1]).getTime() - date.getTime() < (60000 * 5)) return "false 2"; 	// 5 mins
			if (details.length == 5) {
				tableId = Integer.parseInt(details[3]);
				this.findTableById(tableId).addReservation(
					new Reservation (
						details[4],						// res_id
						Integer.parseInt(details[0]), 	// cust_id
						sdf.parse(details[1]), 			// date
						Integer.parseInt(details[2]) 	// pax
					)
				);
			} else {
				tableId = this.findValidTable(details);
				if (tableId == -1) return "false 1";
				return this.findTableById(tableId).addReservation(
					Integer.parseInt(details[0]), 		// cust_id
					sdf.parse(details[1]), 				// date
					Integer.parseInt(details[2]) 		// pax
				);
			}
		} catch (ParseException e) {
			System.out.println("Error Occured! \nPlease contact RRPSS Support Team for assistance.");
			System.out.println("");
		}
		return "false 1";
	}

	/**
	 * Removes a reservation by its id.
	 *
	 * @param	res_id	The id of the reservation that is needed to be removed (e.g. 5-6 -> table 5, id 6).
	 *
	 * @return	true it is removed successfully, false if it cannot find the resevation id.
	 */
	public boolean removeReservation(String res_id) {
		String[] res_id_params = res_id.split("-");
		return this.findTableById(Integer.parseInt(res_id_params[0])).removeReservation(res_id);
	}

	/**
	 * Finds the reservation by its id.
	 *
	 * @param	res_id	The id of reservation that is needed to be found.
	 *
	 * @return			The corresponding Reservation object of this id, or null if the system cannot find this id.
	 */
	public Reservation findReservation(String res_id) {
		String[] res_id_params = res_id.split("-");
		return this.findTableById(Integer.parseInt(res_id_params[0])).findReservation(res_id);
	}

	/**
	 * Updates a reservation with a new date time.
	 *
	 * @param	res_id		The id of the reservation that is needed to be updated.
	 * @param	dateTime	The new date time to update.
	 * @return 	The new reservation id or "false" if the update cannot be made.
	 */
	public String updateReservation(String res_id, String dateTime) {
		String[] res_id_params = res_id.split("-");

		Reservation copied = this.findTableById(Integer.parseInt(res_id_params[0]))
								.getReservations()
								.get(Integer.parseInt(res_id_params[1]));
		this.removeReservation(res_id);

		String[] new_res_params = new String[3];
		new_res_params[0] = String.valueOf(copied.getCustId());
		new_res_params[1] = dateTime;
		new_res_params[2] = String.valueOf(copied.getNoPax());
		String new_res_id = this.reserveTable(new_res_params);

		if(new_res_id.equals("false 1") || new_res_id.equals("false 2")) 
			this.findTableById(Integer.parseInt(res_id_params[0])).addReservation(copied);
		return new_res_id;
	}

	/**
	 * Updates a reservation with a new number of pax.
	 *
	 * @param	res_id		The id of the reservation that is needed to be updated.
	 * @param	noPax		The new number of pax.
	 * @return	The new reservation id or "false" if the update cannot be made.
	 */
	public String updateReservation(String res_id, int noPax) {
		String[] res_id_params = res_id.split("-");

		Reservation copied = this.findTableById(Integer.parseInt(res_id_params[0]))
								.getReservations()
								.get(Integer.parseInt(res_id_params[1]));
		this.removeReservation(res_id);

		String[] new_res_params = new String[3];
		new_res_params[0] = String.valueOf(copied.getCustId());
		new_res_params[1] = copied.getTime();
		new_res_params[2] = String.valueOf(noPax);
		String new_res_id = this.reserveTable(new_res_params);

		if(new_res_id.equals("false 1") || new_res_id.equals("false 2")) 
			this.findTableById(Integer.parseInt(res_id_params[0])).addReservation(copied);
		return new_res_id;
	}

	/**
	 * Prints all of the reservations that are reserved with table tableId.
	 *
	 * @param	tableId		The id of table that is needed to find the resevations.
	 */
	public void printReservations(int tableId) {
		System.out.println("Reservations for table " + this.findTableById(tableId));
		for (Reservation reservation : this.findTableById(tableId).getReservations()) {
			reservation.print();
			System.out.println();
		}
	}

	/**
	 * Prints all of the reservations in this restaurant.
	 */
	public void printReservations() {
		for(Table table : this.tables){
			System.out.printf("- Table %d: %d reservation(s).\n", table.getTableId(), table.getNoOfReseravtions());
			for (Reservation reservation : table.getReservations())
				reservation.print();
		}
	}

	/**
	 * Deletes all of the resevations that are expired after 1 minute not checking in.
	 */
	public void deleteExpiredReservations() {
		Date date = new Date();
		for (Table table : this.tables) {
			table.deleteExpiredReservations(date);
		}
	}

	public boolean updateReservationFile() {
		List<String> updatedRes = new ArrayList<String>();
		for (Table t : tables) {
			for (Reservation r : t.getReservations()) {
				updatedRes.add(String.valueOf(r.getResId()).concat(DELIMITER));
				updatedRes.add(String.valueOf(t.getTableId()).concat(DELIMITER));
				updatedRes.add(r.getTime().toString().concat(DELIMITER));
				updatedRes.add(String.valueOf(r.getNoPax()));
				updatedRes.add(System.getProperty("line.separator"));
			}
		}
		return fileController.writeFile(updatedRes.toArray(new String[updatedRes.size()]), PATH_TO_RESERVATIONS_FILE);
	}



}

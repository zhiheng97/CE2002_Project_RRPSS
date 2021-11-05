package Controller;

import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import Models.*;

public class TableController {

	private int noOfTables;
	private List<Table> tables = new ArrayList<Table>();
	private static final String PATH_TO_ORDERS_FILE = Path.of("./Data/orders.txt").toString();
	private static final String PATH_TO_TABLES_FILE = Path.of("./Data/table.txt").toString();
	private static final String PATH_TO_RESERVATIONS_FILE = Path.of("./Data/reservation.txt").toString();
	private static final String DATETIME_FORMAT_PATTERN = "EEE MMM yy HH:mm:ss z yyyy";
	private FileController fileController = new FileController();
	private static final int EXPIRE_BUFFER_MILLISECOND = 300000;

	public TableController(int noOfTables) throws NumberFormatException, ParseException {
		this.noOfTables = noOfTables;
		this.tables = new ArrayList<Table>(noOfTables);
		// this.initializeTables();
		List<String> tableParams = fileController.readFile(PATH_TO_TABLES_FILE);
		for (int i = 3; i < tableParams.size(); i += 3) {
			tables.add(new Table(Integer.parseInt(tableParams.get(i)), Boolean.parseBoolean(tableParams.get(i + 1)),
					Integer.parseInt(tableParams.get(i + 2))));
		}

		List<String> reserveParams = fileController.readFile(PATH_TO_RESERVATIONS_FILE);
		for (int i = 5; i < reserveParams.size(); i += 5) {
			this.reserveTable(reserveParams.subList(i, i + 5).toArray(new String[5]));
		}
	}

////////////////////// BASIC METHODS ///////////////////

	// private void initializeTables() {
	// 	Date now = new Date();
	// 	SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT_PATTERN);
	// 	List<String> tableParams = fileController.readFile(PATH_TO_TABLES_FILE);
	// 	for (int i = 3; i < tableParams.size(); i += 3) {
	// 		this.tables.add(new Table(Integer.parseInt(tableParams.get(i)), Boolean.parseBoolean(tableParams.get(i + 1)),
	// 				Integer.parseInt(tableParams.get(i + 2))));

	// 		if (Boolean.parseBoolean(tableParams.get(i + 1))) {
	// 			this.findTableByNo(Integer.parseInt(i)).setInvoice()
	// 		}
	// 	}
	// }

	public Table findTableByNo(int tableNo) {
		return tables.stream().filter(t -> t.getTableNo() == tableNo).findFirst().orElse(null);
	}

	public int getNoOfTables() {
		return this.noOfTables;
	}

	public boolean printUnavailableTables() {
		int num_occupied = 0;
		for (Table table : tables) {
			if (table.getIsOccupied()) {
				System.out.printf("Table %d is occupied, staff: %s\n", table.getTableNo(), table.getInvoice().getPlacedBy());
			} else num_occupied++;
		}
		if (num_occupied == 0) return false;
		return true;
	}

	public void printAvailableTables() {
		int num_occupied = 0;
		for (Table table : tables) {
			if (!table.getIsOccupied()) {
				System.out.printf("Table %d (max %d paxes)\n", table.getTableNo(), table.getSeats());
			} else num_occupied++;
		}
		if (num_occupied == this.noOfTables) 
			System.out.println("All the tables are occupied!");
	}

	public void printAvailableTables(int noPax) {
		int num_occupied = 0;
		for (Table table : tables) {
			if (!table.getIsOccupied() && table.getSeats() >= noPax) {
				System.out.printf("Table %d (max %d paxes)\n", table.getTableNo(), table.getSeats());
			} else num_occupied++;
		}
		if (num_occupied == this.noOfTables) 
			System.out.println("All the tables are occupied!");
	}

////////////////////// ORDER FUNCTIONS ///////////////////

	public int findValidTable(int noPax) {
		// the tables are sorted by noPax already so just loop until got one, return -1 if none availabe
		for (Table table : this.tables) {
			if (!table.getIsOccupied() && noPax <= table.getSeats())
				return table.getTableNo();
		}
		return -1;
	}

	public void addToOrder(int tableNo, Item item, int quantity) {
		this.findTableByNo(tableNo).setIsOccupied(true);
		this.findTableByNo(tableNo).addToOrder(item, quantity);
	}

	public void addToOrder(int tableNo, Promotion promotion, int quantity) {
		this.findTableByNo(tableNo).setIsOccupied(true);
		this.findTableByNo(tableNo).addToOrder(promotion, quantity);
	}

	public boolean removeFromOrder(int tableNo, Item item) {
		return this.findTableByNo(tableNo).removeFromOrder(item);
	}

	public boolean removeFromOrder(int tableNo, Promotion promotion) {
		return this.findTableByNo(tableNo).removeFromOrder(promotion);
	}

	public void viewOrder(int tableNo) {
		Order invoice = this.findTableByNo(tableNo).getInvoice();
		List<Item> items = invoice.getItems();
		List<Promotion> promotions = invoice.getPromo();
		Map<Integer, Integer> item2quant = invoice.getOrderItems();
		Map<Integer, Integer> promo2quant = invoice.getOrderPromos();

		System.out.println("Your current order is:");
		for (Promotion promotion : promotions)
			System.out.println(promo2quant.get(promotion.getId()) + " x " + promotion.getName() + "[PROMO]");
		for (Item item : items)
			System.out.println(item2quant.get(item.getId()) + " x " + item.getName() + "[ITEM]");
		System.out.printf("-> The current price for this order is: %.2f\n\n", invoice.getTotal());
	}

	public void printInvoice(int tableNo) {
		Table table = this.findTableByNo(tableNo);
		table.print();
		table.setIsOccupied(false);
		table.setInvoice(new Order(null, null));
	}

////////////////////// RESERVATION FUNCTIONS ///////////////////

	/**
	 * input from RestaurantController
	 * @param details[3]: cust_id, res_datetime, pax
	 * @return tableNo that is allocated for reservation, -1 if not found
	 */
	public int findValidTable(String[] details) throws ParseException {
		int noPax = Integer.parseInt(details[2]);
		SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT_PATTERN);
		Date res_date = sdf.parse(details[1]);
		
		int tableNo = -1;
		switch (noPax) {
			case 1, 2, 3:	// search table 1->2 (2 paxes), 3->5 (4 paxes)
				for (int id=1; id<=5; id++) {
					boolean isValid = true;
					Table table = this.findTableByNo(id);
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
			case 4, 5:		// search table 3->5 (4 paxes), 6->8 (6 paxes)
				for (int id=3; id<=8; id++) {
					boolean isValid = true;
					Table table = this.findTableByNo(id);
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
			case 6, 7:		// search table 6->8 (6 paxes), 9->10 (8 paxes)
				for (int id=6; id<=10; id++) {
					boolean isValid = true;
					Table table = this.findTableByNo(id);
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
			case 8, 9, 10:	// search table 9->10 (8 paxes), 11->12 (10 paxes)
				for (int id=9; id<=12; id++) {
					boolean isValid = true;
					Table table = this.findTableByNo(id);
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
	 * 1) input from RestaurantController
	 * @param details[3]: cust_id, res_datetime, pax
	 * 2) input from TableController's constructor for initialize the memory
	 * @param details[5]: cust_id, res_datetime, pax, table_id, res_id
	 * @return res_id if allocated succesfully, "false" other
	 */
	public String reserveTable(String[] details) throws NumberFormatException, ParseException {
		int tableNo = -1;
		SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT_PATTERN);
		try {
			if (details.length == 5) {
				tableNo = Integer.parseInt(details[3]);
				this.findTableByNo(tableNo).addReservation(
					Integer.parseInt(details[0]), // cust_id
					sdf.parse(details[1]), // date
					Integer.parseInt(details[2]), // pax
					details[4]
				);
			} else {
				tableNo = this.findValidTable(details);
				if (tableNo == -1) return "false";
				return this.findTableByNo(tableNo).addReservation(
					Integer.parseInt(details[0]), 
					sdf.parse(details[1]), // date
					Integer.parseInt(details[2]) // pax
				);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "false";
	}

	public Reservation findReservation(String res_id) {
		String[] res_id_params = res_id.split("-");
		return this.findTableByNo(Integer.parseInt(res_id_params[0]))
					.getReservations()
					.get(Integer.parseInt(res_id_params[1]));
	}

	/**
	 * @param res_id e.g. 5-6 -> table 5, id 6
	 * @return true/false 
	 * idk when it should return false
	 */
	public boolean clearReservation(String res_id) {
		String[] res_id_params = res_id.split("-");
		return this.findTableByNo(Integer.parseInt(res_id_params[0])).removeReservation(res_id);
	}

	public String updateReservation(String res_id, String datetime) throws NumberFormatException, ParseException {
		String[] res_id_params = res_id.split("-");
		
		Reservation copied = this.findTableByNo(Integer.parseInt(res_id_params[0]))
								.getReservations()
								.get(Integer.parseInt(res_id_params[1]));
		this.clearReservation(res_id);

		SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT_PATTERN);
		String[] new_res_params = new String[3];
		new_res_params[0] = String.valueOf(copied.getCustId());
		new_res_params[1] = datetime;
		new_res_params[2] = String.valueOf(copied.getNoPax());
		String new_res_id = this.reserveTable(new_res_params);

		if (new_res_id == "false") this.findTableByNo(Integer.parseInt(res_id_params[0])).addReservation(copied);
		return new_res_id;
	}

	public String updateReservation(String res_id, int noPax) throws NumberFormatException, ParseException {
		String[] res_id_params = res_id.split("-");
		
		Reservation copied = this.findTableByNo(Integer.parseInt(res_id_params[0]))
								.getReservations()
								.get(Integer.parseInt(res_id_params[1]));
		this.clearReservation(res_id);

		SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT_PATTERN);
		String[] new_res_params = new String[3];
		new_res_params[0] = String.valueOf(copied.getCustId());
		new_res_params[1] = copied.getTime();
		new_res_params[2] = String.valueOf(noPax);
		String new_res_id = this.reserveTable(new_res_params);

		if (new_res_id == "false") this.findTableByNo(Integer.parseInt(res_id_params[0])).addReservation(copied);
		return new_res_id;
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

	public boolean clearReservation(int resId) {
		Reservation toRemove = findReservation(resId);
		Table table = tables.stream().filter(t -> t.getReservations().contains(toRemove)).findFirst().orElse(null);
		if (table.removeReservation(toRemove))
			return updateReservationFile();
		return false;
	}

	public Reservation findReservation(int resId) {
		return tables.stream().flatMap(t -> t.getReservations().stream()).filter(r -> r.getId() == resId).findFirst()
				.orElse(null);
	}
*/
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
		for(Table table : tables){
			System.out.printf("- Table %d: %d resevation.\n", table.getTableNo(),table.getNoOfReseravtions());
			for (Reservation reservation : table.getReservations())
				reservation.print();
		}
	}

}

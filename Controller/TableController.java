package Controller;

import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import Models.*;

public class TableController {

	private List<Table> tables = new ArrayList<Table>();
	private static final String PATH_TO_TABLES_FILE = Path.of("./Data/table.txt").toString();
	private static final String PATH_TO_RESERVATIONS_FILE = Path.of("./Data/reservation.txt").toString();
	private static final String DATETIME_FORMAT_PATTERN = "EEE MMM yy HH:mm:ss z yyyy";
	private FileController fileController = new FileController();
	private static final int EXPIRE_BUFFER_MILLISECOND = 300000;

	private int noOfTables;

	public TableController(int noOfTables) {
		this.noOfTables = noOfTables;
		this.tables = new ArrayList<Table>(noOfTables);
		List<String> tableParams = fileController.readFile(PATH_TO_TABLES_FILE);
		for(int i = 0; i < tableParams.size(); i+=3) {
			tables.add(
				new Table(
					Integer.parseInt(tableParams.get(i)),
					Boolean.parseBoolean(tableParams.get(i + 1)),
					Integer.parseInt(tableParams.get(i + 2))
				)
			);
		}
		List<String> reserveParams = fileController.readFile(PATH_TO_RESERVATIONS_FILE);
		if(reserveParams.size() > 0){
			for(int i = 0; i < reserveParams.size(); i+=6) {
				this.reserveTable(reserveParams.subList(i, i+6).toArray(new String[6]));
			}
		}
	}

	public void addToOrder(int tableNo, Item item, int quantity) {
		this.findTableByNo(tableNo).setIsOccupied(true);
		this.findTableByNo(tableNo).addToOrder(item, quantity);
	}

	public void addToOrder(int tableNo, Promotion promotion, int quantity) {
		this.findTableByNo(tableNo).setIsOccupied(true);
		this.findTableByNo(tableNo).addToOrder(promotion, quantity);
	}

	public void expireReservations(Date date) {
		do{
			Reservation expired = tables.stream()
				.flatMap(t -> t.getReservations().stream())
				.filter(r -> (r.getDate().getTime() + EXPIRE_BUFFER_MILLISECOND) - date.getTime() <= 0)
				.findFirst()
				.orElse(null);
			if(expired == null)
				return;
			Table table = tables.stream().filter(t -> t.getReservations().contains(expired)).findFirst().orElse(null);
			if(table.getReservations().remove(expired))
				updateReservationFile();
		} while (true);
	}

	/**
	 *
	 * @param resId
	 */
	public boolean clearReservation(int resId) {
		Reservation toRemove = findReservation(resId);
		Table table = tables.stream().filter(t -> t.getReservations().contains(toRemove)).findFirst().orElse(null);
		if(table.getReservations().remove(toRemove))
			return updateReservationFile();
		return false;
	}

	public Reservation findReservation(int resId){
		return tables.stream()
			.flatMap(t -> t.getReservations().stream())
			.filter(r -> r.getId() == resId)
			.findFirst()
			.orElse(null);
	}

	/**
	 *
	 * @param tableNo
	 */
	public Table findTableByNo(int tableNo) {
		return tables.stream().filter(t -> t.getTableNo() == tableNo).findFirst().orElse(null);
	}

	public int getNoOfTables() {
		return this.noOfTables;
	}

	public void printAvailableTables() {
		for(Table t : tables){
			if(!t.getIsOccupied()){
				System.out.println(t.getTableNo());
			}
		}
	}

	public void printInvoice(int tableNo) {
		Table table = this.findTableByNo(tableNo);
		table.print();
		table.setIsOccupied(false);
		table.setInvoice(new Order(null, null, 0.0));
	}

	public void printReservations(int tableNo){
		for(Reservation reservation : this.findTableByNo(tableNo).getReservations()) {
			reservation.print();
		}
	}

	public boolean removeFromOrder(int tableNo, Item item) {
		return this.findTableByNo(tableNo).removeFromOrder(item);
	}

	public boolean removeFromOrder(int tableNo, Promotion promotion) {
		return this.findTableByNo(tableNo).removeFromOrder(promotion);
	}

	/**
	 *
	 * @param details
	 */
	public boolean reserveTable(String[] details) {
		Table table = tables.stream()
			.filter(t ->  ((t.getReservations().stream().count() < 15) && t.getSeats() >= Integer.parseInt(details[5])) ||
				t.getTableNo() == Integer.parseInt(details[1])
			)
			.findAny()
			.orElse(null);
		SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT_PATTERN);
		Reservation reservation;
		try {
			reservation = new Reservation(
				details[0].hashCode(), //id
				details[2], //name
				sdf.parse(details[4]), //date
				Integer.parseInt(details[3]), //contact
				Integer.parseInt(details[5]) //pax
			);
			if(table.addReservation(reservation))
				return updateReservationFile();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean updateReservationFile(){
		List<String> updatedRes = new ArrayList<String>();
			for(Table t : tables) {
				for(Reservation r : t.getReservations()){
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

	public void viewOrder(int tableNo) {
		Order invoice = this.findTableByNo(tableNo).getInvoice();
		List<Item> items = invoice.getItems();
		List<Promotion> promotions = invoice.getPromo();
		Map<Integer, Integer> item2quant = invoice.getOrderItems();
		Map<Integer, Integer> promo2quant = invoice.getOrderPromos();

		System.out.println("Your current order is:");
		for(Promotion promotion : promotions) System.out.println(promo2quant.get(promotion.getId()) + " x " + promotion.getName() + "[PROMO]");
		for (Item item : items) System.out.println(item2quant.get(item.getId()) + " x " + item.getName() + "[ITEM]");
		System.out.printf("> The current price for this order is: %.2f\n\n", invoice.getTotal());
	}

}

/**
 * A console app that performs basic restaurant operations, such as updating/removing menu/promotions,
 * creating/updating/removing reservations, placing/updating/removing orders from tables, and printing of sales reports.
 * The application uses the Model-View-Controller (MVC) system.
 * @author  @brianleect, @Henry-Hoang, @ghotinggoad, @zhiheng97
 * @since 10 October 2021
 */
package App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;

import Controller.RestaurantController;

public class RRPSSApp {

	private static final String ESCAPE_STRING = "-9"; //Added to maintain consistency of exit/escape codes
	private static final String DATETIME_FORMAT_PATTERN = "dd-MMM-yy HH:mm"; //Pattern used to parse date/time values

	/**
	 * main
	 * @param args
	 */
	public static void main(String[] args) {

		RestaurantController restaurantController = new RestaurantController();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT_PATTERN);
		Calendar c = Calendar.getInstance();
		String option_main = "", option_sub = "", res_id = "";
		int choice, tableId = -1, cust_id = -1, staff_id = -1, noPax = -1;
		try { //Exceptions that are not caught in the do-while loop will be caught by this try/catch block.
			do { //Main menu loop
				try{ //Specfic exceptions that have been identified will be caught by this try/catch block.
					System.out.println("\nRestaurant Reservation and Point of Sale System");
					System.out.print("1. Menu\n2. Promotion\n3. Order\n4. Reservation\n"
							+ "5. Print sales report\n6. Modify time\n-9. Exit\nEnter your choice: ");
					option_main = reader.readLine();
					switch (option_main) {
					/////////////////// MENU ///////////////////
						case "1":
								String[] itemParams = new String[5];
								do {
									System.out.print("\nMenu's Sub-menu\n1. Add item to menu\n2. Update item in menu\n"
											+ "3. Remove item from menu\n4. View Menu\n-9. Return\nEnter your choice: ");
									option_sub = reader.readLine();
									switch (option_sub) {
										/////////////////// MENU'S SUB-MENU ///////////////////
										case "1":
											/////////////////// ADD ITEM TO MENU ///////////////////
											restaurantController.printMenu();
											System.out.println("(type -9 to return to previous menu)");
											System.out
													.print("Enter the category id to add to [0 - Mains, 1 - Sides, 2 - Drinks]: ");
											option_sub = reader.readLine();
											if (option_sub.equals(ESCAPE_STRING))
												break;
											itemParams[4] = option_sub;
											System.out.print("Enter the item id: ");
											itemParams[0] = reader.readLine();
											System.out.print("Enter the item name: ");
											itemParams[1] = reader.readLine();
											System.out.print("Enter the item description: ");
											itemParams[2] = reader.readLine();
											System.out.print("Enter the price of the item: ");
											itemParams[3] = reader.readLine();
											if (restaurantController.addItem(itemParams))
												System.out.println("Item added successfully!");
											else
												System.out.println("Item not added successfully! Possible duplicate item Id!");
											System.out.println();
											break;
										case "2":
											/////////////////// UPDATE ITEM IN MENU ///////////////////
											restaurantController.printMenu();
											System.out.println("(type -9 to return to previous menu)");
											System.out.print("Enter the item id: ");
											option_sub = reader.readLine();
											if (option_sub.equals(ESCAPE_STRING))
												break;
											itemParams[1] = option_sub;
											System.out.print("Enter the item name [Enter \\ if you do not intend to modify]: ");
											itemParams[0] = reader.readLine();
											System.out.print(
													"Enter the item description [Enter \\ if you do not intend to modify]: ");
											itemParams[2] = reader.readLine();
											System.out.print(
													"Enter the price of the item [Enter -1 if you do not intend to modify]: ");
											itemParams[3] = reader.readLine();
											if (restaurantController.updateItem(itemParams))
												System.out.println("Item updated successfully!");
											else
												System.out.println("Item wasn't updated! Check if you entered a valid item Id!");
											System.out.println();
											break;
										case "3":
											/////////////////// REMOVE ITEM FROM MENU ///////////////////
											restaurantController.printMenu();
											System.out.println("(type -9 to return to previous menu)");
											System.out.print("Enter the item id that you wish to remove: ");
											int itemToRemove = Integer.parseInt(reader.readLine());
											if (itemToRemove == Integer.parseInt(ESCAPE_STRING))
												break;
											else if (restaurantController.removeItem(itemToRemove))
												System.out.println("Item removed successfully!");
											else
												System.out.println("Item wasn't removed! Check if you entered a valid item Id!");
											System.out.println();
											break;
										case "4":
											/////////////////// PRINT MENU ///////////////////
											System.out.println();
											restaurantController.printMenu();
											break;
										case ESCAPE_STRING:
											System.out.println("Returning....");
											System.out.println();
											break;
										default:
											System.out.println("Invalid option");
											System.out.println();
											break;
									}
								} while (!option_sub.equals(ESCAPE_STRING));
							
							break;

						/////////////////// PROMOTIONS ///////////////////
						case "2":
							List<String> promoParams = new ArrayList<String>();
							List<String> itemsParams = new ArrayList<String>();
							do {
								promoParams.clear();
								itemsParams.clear();
								System.out.println("\nPromotions Sub-menu");
								System.out.println(
										"1. Add a new promotion\n2. Update promotion\n3. Remove promotion\n4. Add item to promotion");
								System.out.print(
										"5. Update item in promotion\n6. Remove item from promotion \n7. View promotions\n-9. Return\nEnter your choice: ");
								option_sub = reader.readLine();
								switch (option_sub) {
									/////////////////// PROMOTION'S SUB-MENU ///////////////////
									case "1":
										/////////////////// ADD A NEW PROMOTION ///////////////////
										restaurantController.printPromotion();
										System.out.println("(type -9 to return to previous menu)");
										System.out.print("Enter the promotion id: ");
										promoParams.add(reader.readLine());
										System.out.print("Enter the promotion name: ");
										promoParams.add(reader.readLine());
										System.out.print("Enter the promotion description: ");
										promoParams.add(reader.readLine());
										System.out.print("Enter the promotion price: ");
										promoParams.add(reader.readLine());
										if (promoParams.contains(ESCAPE_STRING))
											break;
										System.out.print("Enter the number of items in the promotions: ");
										choice = Integer.parseInt(reader.readLine());
										while (choice < 0) {
											System.out.print("Please enter a valid number (more than -1): ");
											choice = Integer.parseInt(reader.readLine());
										}
										for (int i = 0; i < choice; i++) {
											System.out.print("Enter the item id: ");
											itemsParams.add(reader.readLine());
											System.out.print("Enter the item name: ");
											itemsParams.add(reader.readLine());
											System.out.print("Enter the item description: ");
											itemsParams.add(reader.readLine());
											System.out.print("Enter the item price: ");
											itemsParams.add(reader.readLine());
										}
										restaurantController.addPromotion(promoParams, itemsParams);
										System.out.println();
										break;
									case "2":
										/////////////////// UPDATE PROMOTION ///////////////////
										restaurantController.printPromotion();
										System.out.println("(type -9 to return to previous menu)");
										System.out.print("Enter the promotion id: ");
										promoParams.add(reader.readLine());
										System.out
												.print("Enter the new promotion name [Enter \\ if you do not intend to modify]: ");
										promoParams.add(reader.readLine());
										System.out.print(
												"Enter the new promotion description [Enter \\ if you do not intend to modify]: ");
										promoParams.add(reader.readLine());
										System.out.print(
												"Enter the new price of the promotion [Enter -1 if you do not intend to modify]: ");
										promoParams.add(reader.readLine());
										if (promoParams.contains(ESCAPE_STRING))
											break;
										restaurantController.updatePromotion(promoParams);
										System.out.println();
										break;
									case "3":
										/////////////////// REMOVE PROMOTION ///////////////////
										restaurantController.printPromotion();
										System.out.println("(type -9 to return to previous menu)");
										System.out.print("Enter the promotion id that you wish to remove: ");
										option_sub = reader.readLine();
										restaurantController.removePromotion(Integer.parseInt(option_sub));
										System.out.println();
										break;
									case "4":
										/////////////////// ADD A NEW ITEM TO A PROMOTION ///////////////////
										System.out.println();
										restaurantController.printPromotion();
										System.out.println("(type -9 to return to previous menu)");
										System.out.print("Enter the promotion id that you wish to add an item to: ");
										option_sub = reader.readLine();
										if (option_sub.equals(ESCAPE_STRING))
											break;
										System.out.print("Enter the item id: ");
										itemsParams.add(reader.readLine());
										System.out.print("Enter the item name: ");
										itemsParams.add(reader.readLine());
										System.out.print("Enter the item description: ");
										itemsParams.add(reader.readLine());
										System.out.print("Enter the item price: ");
										itemsParams.add(reader.readLine());
										restaurantController.addItem(Integer.parseInt(option_sub), itemsParams);
										System.out.println();
										break;
									case "5":
										/////////////////// UPDATE AN ITEM IN A PROMOTION ///////////////////
										restaurantController.printPromotion();
										System.out.println("(type -9 to return to previous menu)");
										System.out.print("Enter the promotion id that you wish to update the item in: ");
										option_sub = reader.readLine();
										if (option_sub.equals(ESCAPE_STRING))
											break;
										System.out.print("Enter the item id: ");
										itemsParams.add(reader.readLine());
										System.out.print("Enter the item name [Enter \\ if you do not intend to modify]: ");
										itemsParams.add(reader.readLine());
										System.out.print("Enter the item description [Enter \\ if you do not intend to modify]: ");
										itemsParams.add(reader.readLine());
										System.out.print("Enter the price of the item [Enter -1 if you do not intend to modify]: ");
										itemsParams.add(reader.readLine());
										restaurantController.updateItem(Integer.parseInt(option_sub), itemsParams);
										System.out.println();
										break;
									case "6":
										/////////////////// REMOVE AN ITEM FROM A PROMOTION ///////////////////
										restaurantController.printPromotion();
										System.out.println("(type -9 to return to previous menu)");
										System.out.print("Enter the promotion id that you wish to remove the item from: ");
										option_sub = reader.readLine();
										if (option_sub.equals(ESCAPE_STRING))
											break;
										System.out.print("Enter the item id that you wish to remove: ");
										restaurantController.removeItem(Integer.parseInt(option_sub), Integer.parseInt(reader.readLine()));
										System.out.println();
										break;
									case "7":
										/////////////////// PRINT PROMOTION ///////////////////
										restaurantController.printPromotion();
										System.out.println();
										break;
									case ESCAPE_STRING:
										System.out.println("Returning....");
										System.out.println();
										break;
									default:
										System.out.println("Invalid option!");
										System.out.println();
										break;
								}
							} while (!option_sub.equals(ESCAPE_STRING));
							break;

						/////////////////// ORDER ///////////////////
						case "3":
							boolean backToMenu = false;
							restaurantController.deleteExpiredReservations();
							System.out.println("\n(type -9 to return to previous menu)");
							System.out.println("Do you want to checkin a new table or update a current order?");
							System.out.print("Enter 1 to checkin or 2 to update, your choice is: ");
							option_sub = reader.readLine();
							if (option_sub.equals(ESCAPE_STRING))
								break;
							System.out.println();
							do {
								switch (option_sub) {
									case "1":
										/////////////////// CHECK IN CUSTOMER ///////////////////
										restaurantController.deleteExpiredReservations();
										System.out.print("Enter [Y] if this customer made a reservation, enter anything otherwise: ");
										String isReserved = reader.readLine();

										/////////////////// CHECK IN WITH RESERVATION ///////////////////
										if (isReserved.toLowerCase().equals("y")) {
											int[] res_info = { -1, -1 };
											while (true) {
												restaurantController.printReservations();
												System.out.println("\n(type -9 to return to previous menu)");
												System.out.print("Enter the reservation ID: ");
												res_id = reader.readLine();
												if (res_id.equals(ESCAPE_STRING)) {
													backToMenu = true;
													break;
												}

												// TODO: checkin at the correct datetime, to avoid the case many reservations
												// same table but different timeslots check in at the same time
												res_info = restaurantController.checkinReservation(res_id);
												if (res_info[0] == -1 || res_info[1] == -1) {
													System.out.printf("Cannot find any reservation with ID %s, enter [Y] if you want to retry: ", res_id);
													String temp = reader.readLine();
													if (!temp.toLowerCase().equals("y")) {
														backToMenu = true;
														break;
													}
												} else
													break;
											}
											if (backToMenu)
												break;
											tableId = res_info[0];
											cust_id = res_info[1];
											System.out.printf("Customer ID %d is allocated with table %d.\n", cust_id, tableId);
										} else {
											/////////////////// CHECK IN WITHOUT RESERVATION ///////////////////
											boolean isAnyAvail = restaurantController.printAvailableTables();
											if (!isAnyAvail) {
												backToMenu = true;
												break;
											}
											while (true) {
												System.out.print("Enter the number of pax: ");
												noPax = Integer.parseInt(reader.readLine());
												if (noPax < 1 || noPax > 10)
													System.out.println("Invalid! The number of pax must be between 1 and 10, please try again.");
												// if > 10 then try again or ask comeback later or split into 2 tables or
												// anything idk
												// this would be new feature (not for now).
												else break;
											}

											/////////////////// FOR REGISTERED CUSTOMER ///////////////////
											System.out.print("Enter [Y] if this is a registered customer, [N] otherwise: ");
											String isRegistered = reader.readLine();
											if (isRegistered.toLowerCase().equals("y")) {
												System.out.print("Enter the customer ID: ");
												cust_id = Integer.parseInt(reader.readLine());
												if (!restaurantController.isRegisteredCustomer(cust_id)) {
													System.out.printf("Cannot find any customer with ID %d, enter [Y] if you want to register new one: ", cust_id);
													String opt = reader.readLine();
													if (!opt.toLowerCase().equals("y")) {
														backToMenu = true;
														break;
													} else {
														System.out.print("Enter the customer name: ");
														String cust_name = reader.readLine();
														System.out.print("Enter the customer's contact number: ");
														String contactNo = reader.readLine();
														cust_id = restaurantController.registerCustomer(cust_name, Integer.parseInt(contactNo));
													}
												}
											} else {
												/////////////////// FOR UNREGISTERED CUSTOMER ///////////////////
												System.out.print("Enter the customer name: ");
												String cust_name = reader.readLine();
												System.out.print("Enter the customer's contact number: ");
												String contactNo = reader.readLine();
												cust_id = restaurantController.registerCustomer(cust_name, Integer.parseInt(contactNo));
											}

											String[] checkInParams = {
													String.valueOf(cust_id),
													sdf.format(c.getTime()).toString(),
													String.valueOf(noPax)
											};

											tableId = restaurantController.findValidTable(checkInParams);
											if (tableId == -1) {
												System.out.printf("There are no available tables for %d!\n", tableId);
												backToMenu = true;
												break; // no available table for noPax, maybe ask to reserve for future meal(?)
											}
										}

										while (true) {
											System.out.print("Enter your staff ID: ");
											staff_id = Integer.parseInt(reader.readLine());
											if (0 <= staff_id && staff_id <= 3)
												break;
											else
												System.out.println("Unknown staff ID!");
										}
										restaurantController.createOrder(tableId, cust_id, staff_id, c.getTime());
										break;
									case "2":
										/////////////////// UPDATE TABLE ///////////////////
										boolean isAnyOccupied = restaurantController.printUnavailableTables();
										if (!isAnyOccupied) {
											backToMenu = true;
											break;
										}
										while (true) {
											System.out.println("\n(type -9 to return to previous menu)");
											System.out.print("Enter the table number you want to update: ");
											tableId = Integer.parseInt(reader.readLine());
											if (tableId == Integer.parseInt(ESCAPE_STRING))
												break;
											if (tableId < 1 || tableId > 12)
												System.out.println("Invalid table!");
											else if (!restaurantController.isTableOccupied(tableId))
												System.out.println("This table is not occupied!");
											else
												break;
										}
										break;
									default:
										System.out.println("Invalid option!");
										System.out.print("Enter 1 to update and 2 to checkin, your choice is: ");
										option_sub = reader.readLine();
										break;
								}
							} while (!(option_sub.equals("1") || option_sub.equals("2")));

							if (backToMenu || tableId == Integer.parseInt(ESCAPE_STRING)) break;

							do {
								/////////////////// ORDER'S SUB-MENU ///////////////////
								int itemId, quantity;
								System.out.println();
								System.out.println("1. Add item to your order");
								System.out.println("2. Add promotion to your order");
								System.out.println("3. Remove item from your order");
								System.out.println("4. View your current order");
								System.out.println("5. Checkout and/or return");
								System.out.println("-9. Return to main menu");
								System.out.print("Enter your choice: ");
								option_sub = reader.readLine();
								System.out.println();

								switch (option_sub) {
									case "1":
										/////////////////// ADD ITEM TO TABLE'S ORDER ///////////////////
										restaurantController.printMenu();
										System.out.println("(type -9 to return to previous menu)");
										System.out.print("Enter the item ID: ");
										itemId = Integer.parseInt(reader.readLine());
										if (itemId == Integer.parseInt(ESCAPE_STRING))
											break;
										System.out.print("Enter the quantity you want: ");
										quantity = Integer.parseInt(reader.readLine());
										if (!restaurantController.addToOrder(tableId, itemId, quantity).equals("item"))
											System.out.println("Invalid item ID!");
										System.out.println();
										break;
									case "2":
										/////////////////// ADD PROMOTION TO TABLE'S ORDER ///////////////////
										restaurantController.printPromotion();
										System.out.println("(type -9 to return to previous menu)");
										System.out.print("Enter the promotion ID: ");
										itemId = Integer.parseInt(reader.readLine());
										if (itemId == Integer.parseInt(ESCAPE_STRING))
											break;
										System.out.print("Enter the quantity you want: ");
										quantity = Integer.parseInt(reader.readLine());
										if (!restaurantController.addToOrder(tableId, itemId, quantity).equals("promo"))
											System.out.println("Invalid promotion ID!");
										System.out.println();
										break;
									case "3":
										/////////////////// REMOVE ITEM/PROMOTION FROM TABLE'S ORDER ///////////////////
										restaurantController.printOrder(tableId, false);
										System.out.println("(type -9 to return to previous menu)");
										System.out.print("Enter the item/promotion ID to remove: ");
										itemId = Integer.parseInt(reader.readLine());
										if (itemId == Integer.parseInt(ESCAPE_STRING))
											break;
										System.out.print("Enter the quantity you want to remove: ");
										quantity = Integer.parseInt(reader.readLine());
										int temp = restaurantController.removeFromOrder(tableId, itemId, quantity);
										if (temp == 2)
											System.out.printf("Successfully removed %d items/promotions with ID %d!%n", quantity, itemId);
										else if (temp == 1)
											System.out.printf("All items/promotions with ID %d are removed from this order!%n", itemId);
										else if (temp == 0)
											System.out.printf("Cannot find any item/promotion with ID %d in this order!%n", itemId);
										else 
											System.out.println("Invalid item/promotion ID!");
										System.out.println();
										break;
									case "4":
										/////////////////// PRINT TABLE'S ORDER ///////////////////
										System.out.println();
										restaurantController.printOrder(tableId, true);
										System.out.println();
										break;
									case "5":
										/////////////////// CHECK OUT TABLE ///////////////////
										restaurantController.printInvoice(tableId);
										System.out.println("Returning....\n");
										System.out.println();
										break;
									case ESCAPE_STRING:
										System.out.println("Returning....\n");
										System.out.println();
										break;
									default:
										System.out.println("option not found");
										System.out.println();
										break;
								}
							} while (!option_sub.equals("5") && !option_sub.equals(ESCAPE_STRING));
							break;
						/////////////////// RESERVATIONS ///////////////////
						case "4":
							restaurantController.deleteExpiredReservations();
							do {
								/////////////////// RESERVATION'S SUB-MENU ///////////////////
								backToMenu = false;
								System.out.println();
								System.out.println("1. Add reservation");
								System.out.println("2. Remove reservation");
								System.out.println("3. Update reservation");
								System.out.println("4. View all reservations");
								System.out.println("-9. Return");
								System.out.print("Enter your choice: ");
								option_sub = reader.readLine();
								System.out.println();
		
								switch (option_sub) {
									case "1":
										/////////////////// ADD RESERVATION ///////////////////
										restaurantController.deleteExpiredReservations();
										String[] resParams = new String[3];
										System.out.println("(type -9 to return to previous menu)");
										System.out.print("Enter [Y] if this is a registered customer, enter anything otherwise: ");
										String isRegistered = reader.readLine();
										if (isRegistered.equals(ESCAPE_STRING))
											break;
										if (isRegistered.toLowerCase().equals("y")) {
											System.out.print("Enter the customer ID: ");
											cust_id = Integer.parseInt(reader.readLine());
											if (!restaurantController.isRegisteredCustomer(cust_id)) {
												System.out.printf("Canont find any customer with ID %d, enter [Y] if you want to register a new one: ", cust_id);
												String opt = reader.readLine();
												if (!opt.toLowerCase().equals("y")) {
													backToMenu = true;
													break;
												} else {
													System.out.print("Enter the customer name: ");
													String cust_name = reader.readLine();
													System.out.print("Enter the customer's contact number: ");
													String contactNo = reader.readLine();
													cust_id = restaurantController.registerCustomer(cust_name, Integer.parseInt(contactNo));
												}
											}
										} else {
											System.out.print("Enter [Y] if you want to register new customer: ");
											String temp = reader.readLine();
											if (temp.toLowerCase().equals("y")) {
												System.out.print("Enter the customer name: ");
												String cust_name = reader.readLine();
												System.out.print("Enter the customer's contact number: ");
												String contactNo = reader.readLine();
												cust_id = restaurantController.registerCustomer(cust_name, Integer.parseInt(contactNo));
											}
											else break;
										}
										if (backToMenu)
											continue;
		
										resParams[0] = String.valueOf(cust_id);
										System.out.print("Enter the date of reservation [dd-MMM-yy]: ");
										String time = reader.readLine();
										System.out.print("Enter the time of reservation [HH:mm]: ");
										time = time.concat(" " + reader.readLine());
										resParams[1] = sdf.parse(time).toString();
										System.out.println(resParams[1]);
										System.out.print("Enter the number of pax: ");
										resParams[2] = reader.readLine();
			
										res_id = restaurantController.reserveTable(resParams);
										if (!res_id.equals("false 1") && !res_id.equals("false 2"))
											System.out.println("Reservation has been made successfully with reservation ID: " + res_id);
										else if (res_id.equals("false 1"))
											System.out.println("There is no available table for your time date and number of pax!");
										else
											System.out.println("Reservation to be made is in the past.");
										System.out.println();
										break;
									case "2":
										/////////////////// REMOVE RESERVATION ///////////////////
										restaurantController.deleteExpiredReservations();
										restaurantController.printReservations();
										System.out.println("(type -9 to return to previous menu)");
										System.out.print("Enter the reservation ID that you wish to remove: ");
										res_id = reader.readLine();
										if (res_id.equals(ESCAPE_STRING))
											break;
										else if (restaurantController.clearReservation(res_id))
											System.out.println("Reservation has been removed successfully!");
										else
											System.out.printf("Cannot find any reservation with ID %s to remove!\n", res_id);
										System.out.println();
										break;
									case "3":
										/////////////////// UPDATE RESERVATION ///////////////////
										backToMenu = false;
										restaurantController.deleteExpiredReservations();
										restaurantController.printReservations();
										while (true) {
											System.out.println("(type -9 to return to previous menu)");
											System.out.print("Enter the reservation ID you want to update: ");
											res_id = reader.readLine();
											if (res_id.equals(ESCAPE_STRING))
												break;
											if (!restaurantController.checkReservation(res_id)) {
												System.out.printf("Cannot find any reservation with ID %s, enter [Y] if you want to try again: ", res_id);
												String temp = reader.readLine();
												if (!temp.toLowerCase().equals("y")) {
													backToMenu = true;
													break;
												}
											} else
												break;
										}
										if (backToMenu || res_id.equals(ESCAPE_STRING))
											break;
			
										System.out.println("(type anything else to return)");
										System.out.println("1. Date and time of the reservation");
										System.out.println("2. Number of pax");
										System.out.print("Enter your choice: ");
										option_sub = reader.readLine();
										switch (option_sub) {
											/////////////////// UPDATE RESERVATION'S SUB-MENU ///////////////////
											case "1":
												/////////////////// UPDATE DATETIME OF RESERVATION ///////////////////
												System.out.println("(type -9 to return to previous menu)");
												System.out.print("Enter the date you want to update [dd-MMM-yy]: ");
												time = reader.readLine();
												if (time.equals(ESCAPE_STRING))
													break;
												System.out.print("Enter the time of reservation [HH:mm]: ");
												time = time.concat(" " + reader.readLine());
												time = sdf.parse(time).toString();
												String new_res_id = restaurantController.updateReservation(res_id, time);
												if (!new_res_id.equals("false 1") && !new_res_id.equals("false 2") && !new_res_id.equals(res_id))
													System.out.println("Reservation has been made successfully with reservation ID: " + new_res_id);
												else if(res_id.equals("false 1"))
													System.out.println("There is no available table for your time date and number of pax!");
												else
													System.out.println("Reservation to be made is in the past. Reservation is not updated.");
												break;
											case "2":
												/////////////////// UPDATE NO OF PAX IN RESERVATION ///////////////////
												noPax = -1;
												while (true) {
													System.out.println("(type -9 to return to previous menu)");
													System.out.print("Enter the number of pax you want to update: ");
													noPax = Integer.parseInt(reader.readLine());
													if (noPax < 1 || noPax > 10)
														System.out.println("Invalid! The number of pax must be between 1 and 10, please try again.");
													else
														break;
												}
												if (noPax == Integer.parseInt(ESCAPE_STRING))
													break;
												res_id = restaurantController.updateReservation(res_id, noPax);
												if (!res_id.equals("false 1") && !res_id.equals("false 2"))
													System.out.println("Reservation has been made successfully with reservation ID: " + res_id);
												else
													System.out.println("There is no available table for your number of pax!");
												break;
											default:
												break;
										}
										System.out.println();
										break;
									case "4":
										/////////////////// PRINT RESERVATIONS ///////////////////
										restaurantController.deleteExpiredReservations();
										restaurantController.printReservations();
										System.out.println();
										break;
									case ESCAPE_STRING:
										System.out.println("Returning....\n");
										System.out.println();
										break;
									default:
										System.out.println("Option not found");
										System.out.println();
										break;
								}
							} while (!option_sub.equals(ESCAPE_STRING));
							break;
						/////////////////// REPORT ///////////////////
						case "5":
							do {
								/////////////////// REPORT'S SUB-MENU ///////////////////
								System.out.println();
								System.out.println("1. Print Monthly Report");
								System.out.println("2. Print Daily Report");
								System.out.println("-9. Return");
								System.out.print("Enter your choice: ");
								option_sub = reader.readLine();
								System.out.println();
								switch (option_sub) {
									case "1":
										/////////////////// PRINT MONTHLY REPORT ///////////////////
									case "2":
										/////////////////// PRINT DAILY REPORT ///////////////////
										restaurantController.printSalesReport(Integer.parseInt(option_sub) == 1,
												c.getTime().toString()); // 1 is for
										// monthly, 0 is
										// for daily
										break;
									case ESCAPE_STRING:
										System.out.println("Returning....\n");
										System.out.println();
										break;
									default:
										System.out.println("Option not found");
										System.out.println();
										break;
								}
							} while (!option_sub.equals(ESCAPE_STRING));
							break;
						/////////////////// MODIFY TIME ///////////////////
						case "6":
							do {
								/////////////////// MODIFY TIME'S SUB-MENU ///////////////////
								System.out.println();
								System.out.println("1. Add 10 minutes");
								System.out.println("2. Add 1 hour");
								System.out.println("3. Add 1 day");
								System.out.println("4. Add 1 month");
								System.out.println("-9. Return");
								System.out.print("Enter your choice: ");
								option_sub = reader.readLine();
								System.out.println();
								Date oldDate = c.getTime();
								switch (option_sub) {
								case "1":
									/////////////////// ADD 10 MINUTES ///////////////////
									c.add(Calendar.MINUTE, 10);
									break;
								case "2":
									/////////////////// ADD 1 HOUR ///////////////////
									c.add(Calendar.HOUR, 1);
									break;
								case "3":
									/////////////////// ADD 1 DAY ///////////////////
									c.add(Calendar.DAY_OF_MONTH, 1);
									break;
								case "4":
									/////////////////// ADD 1 MONTH ///////////////////
									c.add(Calendar.MONTH, 1);
									break;
								case ESCAPE_STRING:
									System.out.println("Returning....\n");
									System.out.println();
									break;
								default:
									System.out.println("Option not found");
									System.out.println();
									break;
								}
								System.out.println("Old Date: " + oldDate + "\nNew Date: " + c.getTime());
							} while (!option_sub.equals(ESCAPE_STRING));
							System.out.println("Exit time: " + c.getTime());
							break;
						case ESCAPE_STRING:
							System.out.println("Exiting....");
							break;
						default:
							System.out.println("Option not found");
							break;
					}
				//Any exceptions that are thrown will caught here if not at the following try/catch block
				} catch (InputMismatchException e) {
					System.out.println("Invalid input, returning to previous menu.");
					System.out.println();
				} catch (NumberFormatException e) {
					System.out.println("Unable to parse input, please check your input. Returning to previous menu.");
					System.out.println();
				} catch (IOException e) {
					System.out.println("Error Occured! \nPlease contact RRPSS Support Team for assistance.");
					e.printStackTrace();
				} catch (ParseException e) {
					System.out.println("Unable to parse date / time input. Returning to previous menu.");
					System.out.println();
				} catch (IllegalArgumentException e) {
					System.out.println("Price of item / promotion cannot be negative, returning to previous menu.");
					System.out.println();
				}
			} while (!option_main.equals(ESCAPE_STRING));
		} catch (Exception e) { //Any exceptions not caught by the exceptions listed in the above try/catch block will be caught here
			System.out.println("Error Occured! \nPlease contact RRPSS Support Team for assistance.");
			e.printStackTrace();
		}
	}
}
package App;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Controller.RestaurantController;

public class RRPSSApp {

	private static final String ESCAPE_STRING = "-9"; // use an integer, if we use a string, we cannot use try catch
	private static final String DATETIME_FORMAT_PATTERN = "EEE MMM dd HH:mm:ss z yyyy";
	private static final String DATETIME_FORMAT_PATTERN_2 = "dd-MMM-yy HH:mm";

	public static void main(String[] args) throws NumberFormatException, ParseException {

		RestaurantController restaurantController = new RestaurantController();
		Scanner sc = new Scanner(System.in);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		SimpleDateFormat sdf;
		Calendar c = Calendar.getInstance();
		String option_main = "", option_sub = "";
		int tableNo = 0, choice;
		try {
			do {
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
						case "1":
							restaurantController.printMenu();
							try {
								System.out.println("(type -9 to return to previous menu)");
								System.out
										.print("Enter the category id to add to [0 - Mains, 1 - Sides, 2 - Drinks]: ");
								option_sub = reader.readLine();
								if (option_sub.equals(ESCAPE_STRING))
									break;
								itemParams[4] = String.valueOf(option_sub);
								System.out.print("Enter the item id: ");
								itemParams[1] = String.valueOf(sc.nextInt());
								System.out.print("Enter the item name: ");
								itemParams[0] = reader.readLine();
								System.out.print("Enter the item description: ");
								itemParams[2] = reader.readLine();
								System.out.print("Enter the price of the item: ");
								itemParams[3] = String.valueOf(sc.nextDouble());
							} catch (InputMismatchException e) {
								System.out.println("Invalid input, returning to previous menu.");
								System.out.println();
								break;
							}
							if (restaurantController.addItem(itemParams))
								System.out.println("Item added successfully!");
							else
								System.out.println("Item not added successfully! Possible duplicate item Id!");
							System.out.println();
							break;
						case "2":
							restaurantController.printMenu();
							try {
								System.out.println("(type -9 to return to previous menu)");
								System.out.print("Enter the item id: ");
								option_sub = reader.readLine();
								if (option_sub.equals(ESCAPE_STRING))
									break;
								itemParams[1] = String.valueOf(option_sub);
								System.out.print("Enter the item name [Enter \\ if you do not intend to modify]: ");
								itemParams[0] = reader.readLine();
								System.out.print(
										"Enter the item description [Enter \\ if you do not intend to modify]: ");
								itemParams[2] = reader.readLine();
								System.out.print(
										"Enter the price of the item [Enter -1 if you do not intend to modify]: ");
								itemParams[3] = String.valueOf(sc.nextDouble());
							} catch (InputMismatchException e) {
								System.out.println("Invalid input, returning to previous menu.");
								System.out.println();
								break;
							}
							if (restaurantController.updateItem(itemParams))
								System.out.println("Item updated successfully!");
							else
								System.out.println("Item wasn't updated! Check if you entered a valid item Id!");
							System.out.println();
							break;
						case "3":
							restaurantController.printMenu();
							try {
								System.out.println("(type -9 to return to previous menu)");
								System.out.print("Enter the item id that you wish to remove: ");
								int itemToRemove = sc.nextInt();
								if (itemToRemove == Integer.parseInt(ESCAPE_STRING))
									break;
								else if (restaurantController.removeItem(itemToRemove))
									System.out.println("Item removed successfully!");
								else
									System.out.println("Item wasn't removed! Check if you entered a valid item Id!");
								System.out.println();
							} catch (InputMismatchException e) {
								System.out.println("Invalid input, returning to previous menu.");
								System.out.println();
								break;
							}
							break;
						case "4":
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
						case "1":
							restaurantController.printPromotion();
							try {
								System.out.println("(type -9 to return to previous menu)");
								System.out.print("Enter the promotion id: ");
								String escape_check = reader.readLine();
								if (escape_check.equals(ESCAPE_STRING))
									break;
								promoParams.add(escape_check);
								System.out.print("Enter the promotion name: ");
								promoParams.add(reader.readLine());
								System.out.print("Enter the promotion description: ");
								promoParams.add(reader.readLine());
								System.out.print("Enter the promotion price: ");
								promoParams.add(reader.readLine());
								if (promoParams.contains(ESCAPE_STRING))
									break;
								System.out.print("Enter the number of items in the promotions: ");
								choice = sc.nextInt();
								while (choice < 0) {
									System.out.print("Please enter a valid number (more than 0): ");
									choice = sc.nextInt();
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
							} catch (InputMismatchException e) {
								System.out.println("Invalid input, returning to previous menu.");
								System.out.println();
								break;
							}
							restaurantController.addPromotion(promoParams, itemsParams);
							System.out.println();
							break;
						case "2":
							restaurantController.printPromotion();
							System.out.println("(type -9 to return to previous menu)");
							System.out.print("Enter the promotion id: ");
							String escape_check = reader.readLine();
							if (escape_check.equals(ESCAPE_STRING))
								break;
							promoParams.add(escape_check);
							System.out
									.print("Enter the new promotion name [Enter \\ if you do not intend to modify]: ");
							promoParams.add(reader.readLine());
							System.out.print(
									"Enter the new promotion description [Enter \\ if you do not intend to modify]: ");
							promoParams.add(reader.readLine());
							System.out.print(
									"Enter the new price of the promotion [Enter -1 if you do not intend to modify]: ");
							promoParams.add(reader.readLine());
							restaurantController.updatePromotion(promoParams);
							System.out.println();
							break;
						case "3":
							restaurantController.printPromotion();
							try {
								System.out.println("(type -9 to return to previous menu)");
								System.out.print("Enter the promotion id that you wish to remove: ");
								option_sub = reader.readLine();
							} catch (InputMismatchException e) {
								System.out.println("Invalid input, returning to previous menu.");
								System.out.println();
								break;
							}
							restaurantController.removePromotion(Integer.parseInt(option_sub));
							System.out.println();
							break;
						case "4":
							System.out.println();
							restaurantController.printPromotion();
							try {
								System.out.println("(type -9 to return to previous menu)");
								System.out.print("Enter the promotion id that you wish to add an item to: ");
								option_sub = reader.readLine();
								if (option_sub.equals(ESCAPE_STRING))
									break;
							} catch (InputMismatchException e) {
								System.out.println("Invalid input, returning to previous menu.");
								System.out.println();
								break;
							}
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
							restaurantController.printPromotion();
							try {
								System.out.println("(type -9 to return to previous menu)");
								System.out.print("Enter the promotion id that you wish to update the item in: ");
								option_sub = reader.readLine();
								if (option_sub.equals(ESCAPE_STRING))
									break;
							} catch (InputMismatchException e) {
								System.out.println("Invalid input, returning to previous menu.");
								System.out.println();
								break;
							}
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
							restaurantController.printPromotion();
							try {
								System.out.println("(type -9 to return to previous menu)");
								System.out.print("Enter the promotion id that you wish to remove the item from: ");
								option_sub = reader.readLine();
								if (option_sub.equals(ESCAPE_STRING))
									break;
								System.out.print("Enter the item id that you wish to remove: ");
								restaurantController.removeItem(Integer.parseInt(option_sub), sc.nextInt());
								System.out.println();
							} catch (InputMismatchException e) {
								System.out.println("Invalid input, returning to previous menu.");
								System.out.println();
								break;
							}
							break;
						case "7":
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
					restaurantController.deleteExpiredReservations();
					System.out.println("\n(type -9 to return to previous menu)");
					System.out.println("Do you want to update a current order or checkin new table?");
					System.out.print("Enter 1 to update and 2 to checkin, your choice is: ");
					choice = sc.nextInt();
					if (choice == Integer.parseInt(ESCAPE_STRING))
						break;
					System.out.println();

					boolean back = false;
					do {
						switch (choice) {
						case 1:
							boolean isAnyOccupied = restaurantController.printUnavailableTables();
							if (!isAnyOccupied) {
								back = true;
								break;
							}
							while (true) {
								System.out.println("\n(type -9 to return to previous menu)");
								System.out.print("Enter the table number you want to update: ");
								tableNo = sc.nextInt();
								if (tableNo == Integer.parseInt(ESCAPE_STRING))
									break;
								if (tableNo < 1 || tableNo > 12)
									System.out.println("Invalid table!");
								else if (!restaurantController.isTableOccupied(tableNo))
									System.out.println("This table is not occupied!");
								else
									break;
							}
							break;
						case 2:
							restaurantController.deleteExpiredReservations();
							int noPax, cust_id, staff_id;
							System.out
									.print("Enter [Y] if this customer made a reservation, enter anything otherwise: ");
							String isReserved = reader.readLine();

							if (isReserved.toLowerCase().equals("y")) {
								// TODO: handle the mismatch input
								System.out.print("Enter the reservation ID: ");
								String res_id = reader.readLine();
								int[] res_info = restaurantController.checkinReservation(res_id);
								tableNo = res_info[0];
								cust_id = res_info[1];
								System.out.printf("Customer ID %d is allocated with table %d.\n", cust_id, tableNo);
							} else {
								boolean isAnyAvail = restaurantController.printAvailableTables();
								if (!isAnyAvail) {
									back = true;
									break;
								}
								while (true) {
									System.out.print("Enter the number of pax: ");
									noPax = sc.nextInt();
									if (noPax < 1 || noPax > 10)
										System.out.println(
												"Invalid! The number of pax must be between 1 and 10, please try again.");
									// if > 10 then try again or ask comeback later or split into 2 tables or
									// anything idk
									// this would be new feature (not for now).
									else
										break;
								}

								System.out.print("Enter [Y] if this is a registerd customer, [N] otherwise: ");
								String isRegistered = reader.readLine();
								if (isRegistered.toLowerCase().equals("y")) {
									System.out.print("Enter the customer ID: ");
									cust_id = sc.nextInt();
									if (!restaurantController.isRegisteredCustomer(cust_id)) {
										System.out.printf(
												"Canont find any customer with ID %d, do you want to register [Y/N]? ",
												cust_id);
										String opt = reader.readLine();
										if (!opt.toLowerCase().equals("y")) {
											back = true;
											break;
										} else
											cust_id = restaurantController.registerCustomer();
									}
								} else {
									cust_id = restaurantController.registerCustomer();
								}
								tableNo = restaurantController.findValidTable(noPax);
								if (tableNo == -1) {
									System.out.printf("There are no available tables for %d!\n", tableNo);
									back = true;
									break; // no available table for noPax, maybe ask to reserve for future meal(?)
								}
							}

							while (true) {
								System.out.print("Enter your staff ID: ");
								staff_id = sc.nextInt();
								if (0 <= staff_id && staff_id <= 3)
									break;
								else
									System.out.println("Unknown staff ID!");
							}
							Date now = c.getTime();
							sdf = new SimpleDateFormat(DATETIME_FORMAT_PATTERN);
							restaurantController.createOrder(tableNo, cust_id, staff_id, sdf.format(now));
							break;
						default:
							System.out.println("Invalid option!");
							System.out.print("Enter 1 to update and 2 to checkin, your choice is: ");
							choice = sc.nextInt();
							break;
						}
					} while (!(choice == 1 || choice == 2));
					if (back)
						break;
					if (tableNo == Integer.parseInt(ESCAPE_STRING))
						break;

					do {
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
							restaurantController.printMenu();
							System.out.println("(type -9 to return to previous menu)");
							System.out.print("Enter the item id: ");
							itemId = sc.nextInt();
							if (itemId == Integer.parseInt(ESCAPE_STRING))
								break;
							System.out.print("Enter the quantity you want: ");
							quantity = sc.nextInt();
							if (!restaurantController.addToOrder(tableNo, itemId, quantity).equals("item")) 
								System.out.println("Invalid item id!");
							System.out.println();
							break;
						case "2":
							restaurantController.printPromotion();
							System.out.println("(type -9 to return to previous menu)");
							System.out.print("Enter the promotion id: ");
							itemId = sc.nextInt();
							if (itemId == Integer.parseInt(ESCAPE_STRING))
								break;
							System.out.print("Enter the quantity you want: ");
							quantity = sc.nextInt();
							if (!restaurantController.addToOrder(tableNo, itemId, quantity).equals("promo"))
								System.out.println("Invalid promotion id!");
							System.out.println();
							break;
						case "3":
							restaurantController.printOrder(tableNo, false);
							System.out.println("(type -9 to return to previous menu)");
							System.out.print("Enter the item/promotion id to remove: ");
							itemId = sc.nextInt();
							if (itemId == Integer.parseInt(ESCAPE_STRING))
								break;
							System.out.print("Enter the quantity you want to remove: ");
							quantity = sc.nextInt();
							int temp = restaurantController.removeFromOrder(tableNo, itemId, quantity);
							if (temp == 2)
								System.out.printf("Successfully removed %d items/promotions with id %d!%n", quantity,
										itemId);
							else if (temp == 1)
								System.out.printf("All items/promotions with id %d are removed from this order!%n", itemId);
							else if (temp == 0)
								System.out.printf("Cannot find any item/promotion with id %d in this order!%n", itemId);
							else 
								System.out.println("Invalid item/promotion id!");
							System.out.println();
							break;
						case "4":
							System.out.println();
							restaurantController.printOrder(tableNo, true);
							System.out.println();
							break;
						case "5":
							restaurantController.printInvoice(tableNo);
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
							restaurantController.deleteExpiredReservations();
							String[] resParams = new String[3];
							try {
								String ans;
								int cust_id = -1;
								System.out.println("(type -9 to return to previous menu)");
								System.out.print("Is this a registered member [y/N]? ");
								ans = reader.readLine();
								if (ans.equals(ESCAPE_STRING))
									break;
								if (ans.toLowerCase().equals("y")) {
									System.out.print("Enter the customer id: ");
									cust_id = sc.nextInt();
								} else {
									cust_id = restaurantController.registerCustomer();
								}

								resParams[0] = String.valueOf(cust_id);
								System.out.print("Enter the date of reservation [dd-MMM-yy]: ");
								String time = reader.readLine();
								System.out.print("Enter the time of reservation [HH:mm]: ");
								time = time.concat(" " + reader.readLine());
								sdf = new SimpleDateFormat(DATETIME_FORMAT_PATTERN_2);
								resParams[1] = sdf.parse(time).toString();
								System.out.print("Enter the number of guest: ");
								resParams[2] = String.valueOf(sc.nextInt());
							} catch (InputMismatchException e) {
								System.out.println("Invalid input, returning to previous menu.");
								System.out.println();
								break;
							} catch (ParseException e) {
								System.out.println(
										"Error occured while trying to parse input for date and time of reservation!");
								System.out.println();
							}

							String res_id = restaurantController.reserveTable(resParams);
							if (!res_id.equals("false"))
								System.out.println(
										"Reservation has been made successfully with reservation ID: " + res_id);
							else
								System.out
										.println("There is no available table for your time date and number of paxes!");
							System.out.println();
							break;
						case "2":
							restaurantController.deleteExpiredReservations();
							restaurantController.printReservations();
							System.out.println("(type -9 to return to previous menu)");
							System.out.print("Enter the reservation ID that you wish to remove: ");
							String resIdToRemove = reader.readLine();
							if (resIdToRemove.equals(ESCAPE_STRING))
								break;
							else if (restaurantController.clearReservation(resIdToRemove))
								System.out.println("Reservation has been removed successfully!");
							else
								System.out.println("Reservation was not removed!");
							System.out.println();
							break;
						case "3":
							restaurantController.deleteExpiredReservations();
							restaurantController.printReservations();
							System.out.println("(type -9 to return to previous menu)");
							System.out.print("Enter the reservation ID you want to update: ");
							String resIdToUpdate = reader.readLine();
							if (resIdToUpdate.equals(ESCAPE_STRING))
								break;
							System.out.println("(type anything to return)");
							System.out.println("1. Date and time of the reservation");
							System.out.println("2. Number of paxes");
							System.out.print("Enter your choice: ");
							option_sub = reader.readLine();
							switch (option_sub) {
							case "1":
								System.out.println("(type -9 to return to previous menu)");
								System.out.print("Enter the date you want to update [dd-MMM-yy]: ");
								String time = reader.readLine();
								if (time.equals(ESCAPE_STRING))
									break;
								System.out.print("Enter the time of reservation [HH:mm]: ");
								time = time.concat(" " + reader.readLine());
								sdf = new SimpleDateFormat(DATETIME_FORMAT_PATTERN_2);
								time = sdf.parse(time).toString();
								res_id = restaurantController.updateReservation(resIdToUpdate, time);
								if (!res_id.equals("false"))
									System.out.println(
											"Reservation has been made successfully with reservation ID: " + res_id);
								else
									System.out.println(
											"There is no available table for your time date and number of paxes!");
								break;
							case "2":
								int noPax = -1;
								while (true) {
									System.out.println("(type -9 to return to previous menu)");
									System.out.print("Enter the number of pax you want to update: ");
									noPax = sc.nextInt();
									if (noPax < 2 || noPax > 10)
										System.out.println(
												"Invalid! The number of pax must be between 2 and 10, please try again.");
									else
										break;
								}
								if (noPax == Integer.parseInt(ESCAPE_STRING))
									break;
								res_id = restaurantController.updateReservation(resIdToUpdate, noPax);
								if (!res_id.equals("false"))
									System.out.println(
											"Reservation has been made successfully with reservation ID: " + res_id);
								else
									System.out.println(
											"There is no available table for your time date and number of paxes!");
								break;
							default:
								break;
							}
							System.out.println();
							break;
						case "4":
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
						System.out.println();
						System.out.println("1. Print Monthly Report");
						System.out.println("2. Print Daily Report");
						System.out.println("-9. Return");
						System.out.print("Enter your choice: ");
						option_sub = reader.readLine();
						System.out.println();
						switch (option_sub) {
						case "1":
						case "2":
							restaurantController.printSalesReport(Integer.parseInt(option_sub) == 1); // 1 is for
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
				/////////////////// Modify Time ///////////////////
				case "6":
					do {
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
							c.add(Calendar.MINUTE, 10);
							break;
						case "2":
							c.add(Calendar.HOUR, 1);
							break;
						case "3":
							c.add(Calendar.DAY_OF_MONTH, 1);
							break;
						case "4":
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
			} while (!option_main.equals(ESCAPE_STRING));
		} catch (Exception e) {
			System.out.println("Error Occured! \nPlease contact RRPSS Support Team for assistance.");
			e.printStackTrace();
		}
		sc.close();
	}

}
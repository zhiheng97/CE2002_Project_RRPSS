package App;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Controller.RestaurantController;

public class RRPSSApp {
	
	private static final String ESCAPE_STRING = "-9"; // use an integer, if we use a string, we cannot use try catch
	private static final String DATETIME_FORMAT_PATTERN = "EEE MMM yy HH:mm:ss z yyyy";
	private static final String DATETIME_FORMAT_PATTERN_2 = "dd-MMM-yy HH:mm";
	public static void main(String[] args) {

		RestaurantController restaurantController = new RestaurantController();
		Scanner sc = new Scanner(System.in);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		SimpleDateFormat sdf;
		String option_main = "", option_sub = "";
		try{
			do {
				System.out.println("\nRestaurant Reservation and Point of Sale System");
				System.out.print("1. Menu\n2. Promotion\n3. Order\n4. Reservation\n"
						+ "5. Print sales report\n-9. Exit\nEnter your choice: ");
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
							try{
								System.out.println("(type -9 to return to previous menu)");
								System.out.print("Enter the category id to add to [0 - Mains, 1 - Sides, 2 - Drinks]: ");
								option_sub = reader.readLine();
								if(option_sub.equals(ESCAPE_STRING)) break;
								itemParams[4] = String.valueOf(option_sub);
								System.out.print("Enter the item id: ");
								itemParams[1] = String.valueOf(sc.nextInt());
								System.out.print("Enter the item name: ");
								itemParams[0] = reader.readLine();
								System.out.print("Enter the item description: ");
								itemParams[2] = reader.readLine();
								System.out.print("Enter the price of the item: ");
								itemParams[3] = String.valueOf(sc.nextDouble());
							} catch(InputMismatchException e){
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
							try{
								System.out.println("(type -9 to return to previous menu)");
								System.out.print("Enter the item id: ");
								option_sub = reader.readLine();
								if(option_sub.equals(ESCAPE_STRING)) break;
								itemParams[4] = String.valueOf(option_sub);
								System.out.print("Enter the item name [Enter \\ if you do not intend to modify]: ");
								itemParams[0] = reader.readLine();
								System.out.print("Enter the item description [Enter \\ if you do not intend to modify]: ");
								itemParams[2] = reader.readLine();
								System.out.print("Enter the price of the item [Enter -1 if you do not intend to modify]: ");
								itemParams[3] = String.valueOf(sc.nextDouble());
							}
							catch(InputMismatchException e){
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
							try{
								System.out.println("(type -9 to return to previous menu)");
								System.out.print("Enter the item id that you wish to remove: ");
								int itemToRemove = sc.nextInt();
								if(itemToRemove == Integer.parseInt(ESCAPE_STRING)) break;
								else if (restaurantController.removeItem(itemToRemove))
									System.out.println("Item removed successfully!");
								else
									System.out.println("Item wasn't removed! Check if you entered a valid item Id!");
								System.out.println();
							}
							catch(InputMismatchException e){
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
						System.out.println("1. Add a new promotion\n2. Update promotion\n3. Remove promotion\n4. Add item to promotion");
						System.out.print("5. Update item in promotion\n6. Remove item from promotion \n7. View promotions\n-9. Return\nEnter your choice: ");
						option_sub = reader.readLine();
						switch (option_sub) {
						case "1":
							restaurantController.printPromotion();
							try{
								System.out.println("(type -9 to return to previous menu)");
								System.out.print("Enter the promotion id: ");
								String escape_check = reader.readLine();
								if(escape_check.equals(ESCAPE_STRING)) break;
								promoParams.add(escape_check);
								System.out.print("Enter the promotion name: ");
								promoParams.add(reader.readLine());
								System.out.print("Enter the promotion description: ");
								promoParams.add(reader.readLine());
								System.out.print("Enter the promotion price: ");
								promoParams.add(reader.readLine());
								if(promoParams.contains(ESCAPE_STRING)) break;
								System.out.print("Enter the number of items in the promotions: ");
								int choice = sc.nextInt();
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
							}
							catch(InputMismatchException e){
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
							if(escape_check.equals(ESCAPE_STRING)) break;
							promoParams.add(escape_check);
							System.out.print("Enter the new promotion name [Enter \\ if you do not intend to modify]: ");
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
							try{
								System.out.println("(type -9 to return to previous menu)");
								System.out.print("Enter the promotion id that you wish to remove: ");
								option_sub = reader.readLine();
							}
							catch(InputMismatchException e){
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
							try{
								System.out.println("(type -9 to return to previous menu)");
								System.out.print("Enter the promotion id that you wish to add an item to: ");
								option_sub = reader.readLine();
								if(option_sub.equals(ESCAPE_STRING)) break;
							}
							catch(InputMismatchException e){
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
							try{
								System.out.println("(type -9 to return to previous menu)");
								System.out.print("Enter the promotion id that you wish to update the item in: ");
								option_sub = reader.readLine();
								if(option_sub.equals(ESCAPE_STRING)) break;
							}
							catch(InputMismatchException e){
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
							try{
								System.out.println("(type -9 to return to previous menu)");
								System.out.print("Enter the promotion id that you wish to remove the item from: ");
								option_sub = reader.readLine();
								if(option_sub.equals(ESCAPE_STRING)) break;
								System.out.print("Enter the item id that you wish to remove: ");
								restaurantController.removeItem(Integer.parseInt(option_sub), sc.nextInt());
								System.out.println();
							}
							catch(InputMismatchException e){
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
							System.out.println("Invalid option");
							System.out.println();
							break;
						}
					} while (!option_sub.equals(ESCAPE_STRING));
					break;

				/////////////////// ORDER ///////////////////
				case "3":
					System.out.println("\n(type -9 to return to previous menu)");
					System.out.print("Enter the table number: ");
					int tableNo = sc.nextInt();
					if(tableNo == Integer.parseInt(ESCAPE_STRING)) break;
					// function check table
					System.out.print("Enter your staff ID: ");
					int staffID = sc.nextInt();
					Date now = new Date();
					sdf = new SimpleDateFormat(DATETIME_FORMAT_PATTERN);
					restaurantController.createOrder(tableNo, staffID, sdf.format(now));
					do {
						System.out.println();
						System.out.println("1. Add item to your order");
						System.out.println("2. Remove item from your order");
						System.out.println("3. View your current order");
						System.out.println("4. Checkout and/or return");
						System.out.println("-9. Return to main menu");
						System.out.print("Enter your choice: ");
						option_sub = reader.readLine();
						System.out.println();

						switch (option_sub) {
						case "1":
							System.out.println("(type -9 to return to previous menu)");
							System.out.print("Enter the item id: ");
							int itemIdToAdd = sc.nextInt();
							if(itemIdToAdd == Integer.parseInt(ESCAPE_STRING)) break;
							System.out.print("Enter the quantity you want: ");
							int quantityToAdd = sc.nextInt();
							restaurantController.addToOrder(tableNo, itemIdToAdd, quantityToAdd);
							System.out.println();
							break;
						case "2":
							System.out.println("(type -9 to return to previous menu)");
							System.out.print("Enter the item id: ");
							int itemIdToRemove = sc.nextInt();
							if(itemIdToRemove == Integer.parseInt(ESCAPE_STRING)) break;
							boolean isValid = restaurantController.removeFromOrder(tableNo, itemIdToRemove);
							if (isValid)
								System.out.println("Successfully removed the item");
							else
								System.out.println("Unsuccessfully removed the item because it is not in your order\n");
							System.out.println();
							break;
						case "3":
							System.out.println();
							restaurantController.viewOrder(tableNo);
							System.out.println();
							break;
						case "4":
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
					} while (!option_sub.equals("4") && !option_sub.equals(ESCAPE_STRING));
					break;
				/////////////////// RESERVATIONS ///////////////////
				case "4":
					restaurantController.expireReservations(new Date());
					do {
						System.out.println();
						System.out.println("1. Add reservation");
						System.out.println("2. Remove reservation");
						System.out.println("3. View reservations");
						System.out.println("4. View available tables");
						System.out.println("-9. Return");
						System.out.print("Enter your choice: ");
						option_sub = reader.readLine();
						System.out.println();

						switch (option_sub) {
						case "1":
							String[] resParams = new String[6];
							try{
								System.out.println("(type -9 to return to previous menu)");
								System.out.print("Enter the customer id: ");
								int custId = sc.nextInt();
								if(custId == Integer.parseInt(ESCAPE_STRING)) break;
								resParams[0] = String.valueOf(custId);
								System.out.print("Enter the customer name: ");
								resParams[2] = reader.readLine();
								System.out.print("Enter the date of reservation [dd-MMM-yy]: ");
								resParams[4] = reader.readLine();
								System.out.print("Enter the time of reservation [HH:mm]: ");
								String time = resParams[4].concat(" " + reader.readLine());
								sdf = new SimpleDateFormat(DATETIME_FORMAT_PATTERN_2);
								resParams[4] = sdf.parse(time).toString();
								System.out.print("Enter the contact number: ");
								resParams[3] = String.valueOf(sc.nextInt());
								System.out.print("Enter the number of guest: ");
								resParams[5] = String.valueOf(sc.nextInt());
							}
							catch(InputMismatchException e){
								System.out.println("Invalid input, returning to previous menu.");
								System.out.println();
								break;
							} catch(ParseException e) {
								System.out.println("Error occured while trying to parse input for date and time of reservation!");
								System.out.println();
							}
							if (restaurantController.reserveTable(resParams))
								System.out.println("Reservation has been made successfully!");
							else
								System.out.println("Reservation was not made!");
							System.out.println();
							break;
						case "2":
							restaurantController.printReservations();
							System.out.println("(type -9 to return to previous menu)");
							System.out.print("Enter the reservation Id that you wish to remove: ");
							int resIdToRemove = sc.nextInt();
							if(resIdToRemove == Integer.parseInt(ESCAPE_STRING)) break;
							else if (restaurantController.clearReservation(resIdToRemove))
								System.out.println("Reservation has been removed successfully!");
							else
								System.out.println("Reservation was not removed!");
							System.out.println();
							break;
						case "3":
							System.out.println();
							System.out.println("(type -9 to return to previous menu)");
							System.out.print("Enter the table no that you wish to view the current reservations: ");
							int tableNum = sc.nextInt();
							if(tableNum == Integer.parseInt(ESCAPE_STRING)) break;
							restaurantController.printReservations(tableNum);
							System.out.println();
							break;
						case "4":
							restaurantController.printAvailableTables();
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
							restaurantController.printSalesReport(Integer.parseInt(option_sub) == 1); // 1 is for monthly, 0 is for daily
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

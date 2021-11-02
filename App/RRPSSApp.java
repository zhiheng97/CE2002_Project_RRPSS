package App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import Controller.RestaurantController;

public class RRPSSApp {

	public static void main(String[] args) throws IOException {

		RestaurantController restaurantController = new RestaurantController();
		Scanner sc = new Scanner(System.in);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int option = 0;
		do{
			restaurantController.expireReservations(new Date());
			System.out.println("Restaurant Reservation and Point of Sale System");
			System.out.print("1. Menu\n2. Promotion\n3. Order\n4. Reservation\n" +
				"5. Print sales report\n6. Exit\nEnter your choice: ");
			option = sc.nextInt();
			switch (option) {
				/////////////////// MENU ///////////////////
				case 1:
					String[] itemParams = new String[5];
					do {
						System.out.print("1. Add item to menu\n2. Update item in menu\n" +
						"3. Remove item from menu\n4. View Menu\n5. Return\nEnter your choice: ");
						option = sc.nextInt();
						switch (option) {
							case 1:
								System.out.print("Enter the category id to add to [0 - Mains, 1 - Sides, 2 - Drinks]: ");
								itemParams[4] = String.valueOf(sc.nextInt());
								System.out.print("Enter the item id: ");
								itemParams[1] = String.valueOf(sc.nextInt());
								System.out.print("Enter the item name: ");
								itemParams[0] = reader.readLine();
								System.out.print("Enter the item description: ");
								itemParams[2] = reader.readLine();
								System.out.print("Enter the price of the item: ");
								itemParams[3] = String.valueOf(sc.nextDouble());
								if(restaurantController.addItem(itemParams))
									System.out.println("Item added successfully!");
								else
									System.out.println("Item not added successfully! Possible duplicate item Id!");
								System.out.println();
								break;
							case 2:
								System.out.print("Enter the item id: ");
								itemParams[1] = String.valueOf(sc.nextInt());
								System.out.print("Enter the item name [Enter \\ if you do not intend to modify]: ");
								itemParams[0] = reader.readLine();
								System.out.print("Enter the item description [Enter \\ if you do not intend to modify]: ");
								itemParams[2] = reader.readLine();
								System.out.print("Enter the price of the item [Enter -1 if you do not intend to modify]: ");
								itemParams[3] = String.valueOf(sc.nextDouble());
								if(restaurantController.updateItem(itemParams))
									System.out.println("Item updated successfully!");
								else
									System.out.println("Item wasn't updated! Check if you entered a valid item Id!");
								System.out.println();
								break;
							case 3:
								System.out.print("Enter the item id that you wish to remove: ");
								if(restaurantController.removeItem(sc.nextInt()))
									System.out.println("Item removed successfully!");
								else
									System.out.println("Item wasn't removed! Check if you entered a valid item Id!");
								System.out.println();
								break;
							case 4:
								restaurantController.printMenu();
								System.out.println();
								break;
							case 5:
								System.out.println("Returning....");
								System.out.println();
								break;
							default:
								System.out.println("Invalid option");
								System.out.println();
								break;
						}
					} while (option != 5);
					break;
				
				/////////////////// PROMOTIONS ///////////////////
				case 2:
					//TODO - Write Options for Promotions
					List<String> promoParams = new ArrayList<String>();
					List<String> itemsParams = new ArrayList<String>();
					do{
						System.out.println("Promotions Sub-menu");
						System.out.println("1. View promotions\n2. Add a new promotion\n3. Update promotion\n4. Remove promotion\n5. Add item to promotion");
						System.out.print("6. Update item in promotion\n7. Remove item from promotion\n8. Return\nEnter your choice: ");
						option = sc.nextInt();
						switch(option){
							case 1:
								restaurantController.printPromotion();
								System.out.println();
								break;
							case 2:
								System.out.println("Enter the promotion id: ");
								promoParams.add(reader.readLine());
								System.out.println("Enter the promotion name: ");
								promoParams.add(reader.readLine());
								System.out.println("Enter the promotion description: ");
								promoParams.add(reader.readLine());
								System.out.println("Enter the promotion price: ");
								promoParams.add(reader.readLine());
								System.out.println("Enter the number of items in the promotions: ");
								option = sc.nextInt();
								while(option < 0){
									System.out.println("Please enter a valid number (more than 0): ");
									option = sc.nextInt();
								}
								for(int i = 0; i < option; i++){
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
								promoParams.clear();
								itemsParams.clear();
								System.out.println();
								break;
							case 3:
								System.out.print("Enter the promotion id: ");
								promoParams.add(reader.readLine());
								System.out.print("Enter the new promotion name [Enter \\ if you do not intend to modify]: ");
								promoParams.add(reader.readLine());
								System.out.print("Enter the new promotion description [Enter \\ if you do not intend to modify]: ");
								promoParams.add(reader.readLine());
								System.out.print("Enter the new price of the promotion [Enter -1 if you do not intend to modify]: ");
								promoParams.add(reader.readLine());
								restaurantController.updatePromotion(promoParams);
								promoParams.clear();
								System.out.println();
								break;
							case 4:
								System.out.print("Enter the promotion id that you wish to remove: ");
								option = sc.nextInt();
								restaurantController.removePromotion(option);
								System.out.println();
								break;
							case 5:
								System.out.print("Enter the promotion id that you wish to add an item to: ");
								option = sc.nextInt();
								System.out.print("Enter the item id: ");
								itemsParams.add(reader.readLine());
								System.out.print("Enter the item name: ");
								itemsParams.add(reader.readLine());
								System.out.print("Enter the item description: ");
								itemsParams.add(reader.readLine());
								System.out.print("Enter the item price: ");
								itemsParams.add(reader.readLine());
								restaurantController.addItem(option, itemsParams);
								itemsParams.clear();
								System.out.println();
								break;
							case 6:
								System.out.print("Enter the promotion id that you wish to update the item in: ");
								option = sc.nextInt();
								System.out.print("Enter the item id: ");
								itemsParams.add(reader.readLine());
								System.out.print("Enter the item name [Enter \\ if you do not intend to modify]: ");
								itemsParams.add(reader.readLine());
								System.out.print("Enter the item description [Enter \\ if you do not intend to modify]: ");
								itemsParams.add(reader.readLine());
								System.out.print("Enter the price of the item [Enter -1 if you do not intend to modify]: ");
								itemsParams.add(reader.readLine());
								restaurantController.updateItem(option, itemsParams);
								itemsParams.clear();
								System.out.println();
								break;
							case 7:
								System.out.print("Enter the promotion id that you wish to remove the item from: ");
								option = sc.nextInt();
								System.out.print("Enter the item id that you wish to remove: ");
								restaurantController.removeItem(option, sc.nextInt());
								System.out.println();
								break;
							case 8:
							  	System.out.println("Returning....");
								System.out.println();
								break;
							default:
								System.out.println("Invalid option");
								System.out.println();
								break;
							}
						}while(option != 8);
						break;

					/////////////////// ORDER ///////////////////
					case 3:
					System.out.print("\nEnter your table number: ");
					int tableNo = sc.nextInt();
					do {
						System.out.println("1. Add item to your order");
						System.out.println("2. Remove item from your order");
						System.out.println("3. View your order");
						System.out.println("4. Return");
						System.out.print("Enter your choice: ");
						option = sc.nextInt();

						switch (option) {
							case 1:
								System.out.print("Enter the item id: ");
								int itemIdToAdd = sc.nextInt();
								System.out.print("Enter the quantity you want: ");
								int quantityToAdd = sc.nextInt();
								// boolean isPromo = 
								restaurantController.addToOrder(tableNo, itemIdToAdd, quantityToAdd);
								// System.out.printf("Successfully added %d numbers of item")
								System.out.println();
								break;
							case 2:
								System.out.print("Enter the item id: ");
								int itemIdToRemove = sc.nextInt();
								// boolean isPromo = 
								boolean isValid = restaurantController.removeFromOrder(tableNo, itemIdToRemove);
								if (isValid) System.out.println("Successfully removed the item");
								else System.out.println("Unsuccessfully removed the item because it is not in your order\n");
								System.out.println();
								break;
							case 3:
								System.out.println();
								restaurantController.viewOrder(tableNo);
								System.out.println();
								break;	
							case 4:
								System.out.println("Returning....\n");
								System.out.println();
								break;
							default:
								System.out.println("Option not found");
								System.out.println();
								break;
						}
					} while (option != 4);
					break;
				/////////////////// RESERVATIONS ///////////////////
				case 4:
					do {
						System.out.println("1. Add reservation");
						System.out.println("2. Remove reservation");
						System.out.println("3. View reservations");
						System.out.println("4. View available tables");
						System.out.println("5. Return");
						System.out.print("Enter your choice: ");
						option = sc.nextInt();

						switch (option) {
							case 1:
								String[] resParams = new String[8];
								System.out.print("Enter the customer id: ");
								resParams[0] = String.valueOf(sc.nextInt());
								System.out.print("Enter the customer name: ");
								resParams[2] = reader.readLine();
								System.out.print("Enter the date of reservation [dd-MMM-yy]: ");
								resParams[4] = reader.readLine();
								System.out.print("Enter the time of reservation: ");
								resParams[5] = reader.readLine();
								System.out.print("Enter the contact number: ");
								resParams[3] = String.valueOf(sc.nextInt());
								System.out.print("Enter the number of guest: ");
								resParams[6] = String.valueOf(sc.nextInt());
								if(restaurantController.reserveTable(resParams))
									System.out.println("Reservation has been made successfully!");
								else
									System.out.println("Reservation was not made!");
								System.out.println();
								break;
							case 2:
								System.out.print("Enter the reservation Id that you wish to remove: ");
								int resIdToRemove = sc.nextInt();
								if(restaurantController.clearReservation(resIdToRemove))
									System.out.println("Reservation has been removed successfully!");
								else
									System.out.println("Reservation was not removed!");
								System.out.println();
								break;
							case 3:
								System.out.println();
								System.out.println("Enter the table no that you wish to view the current reservations: ");
								int tableNum = sc.nextInt();
								restaurantController.printReservations(tableNum);
								System.out.println();
								break;	
							case 4:
								restaurantController.printAvailableTables();
								System.out.println();
								break;
							case 5:
								System.out.println("Returning....\n");
								System.out.println();
								break;
							default:
								System.out.println("Option not found");
								System.out.println();
								break;
						}
					} while (option != 5);
					break;
				case 5:
					//TODO - Write Options for printing Report
					break;
				case 6:
					System.out.println("Exiting....");
					break;
				default:
					System.out.println("Option not found");
					break;
			}
		} while(option != 6);
		sc.close();
	}

}

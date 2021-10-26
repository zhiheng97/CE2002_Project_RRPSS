package App;

import java.util.Scanner;

import Controller.RestaurantController;

public class RRPSSApp {

	public static void main(String[] args) {
		
		RestaurantController restaurantController = new RestaurantController();
		Scanner sc = new Scanner(System.in);
		int option = 0;
		do{
			System.out.println("Restaurant Reservation and Point of Sale System");
			System.out.print("1. Menu\n2. Promotion\n3. Order\n4. Reservation\n" +
				"5. Print sales report\n6. Exit\nEnter your choice: ");
			option = sc.nextInt();
			switch (option) {
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
								itemParams[0] = sc.next();
								System.out.print("Enter the item description: ");
								itemParams[2] = sc.next();
								System.out.print("Enter the price of the item: ");
								itemParams[3] = String.valueOf(sc.nextDouble());
								restaurantController.addItem(itemParams, false);
								break;
							case 2:
								System.out.print("Enter the item id: ");
								itemParams[0] = String.valueOf(sc.nextInt());
								System.out.print("Enter the item name [Press enter if you do not intend to modify]: ");
								itemParams[1] = sc.next();
								System.out.print("Enter the item description [Press enter if you do not intend to modify]: ");
								itemParams[2] = sc.next();
								System.out.print("Enter the price of the item [Press enter if you do not intend to modify]: ");
								itemParams[3] = String.valueOf(sc.nextDouble());
								restaurantController.updateItem(itemParams, false);
								break;
							case 3:
								System.out.print("Enter the item id that you wish to remove: ");
								restaurantController.removeItem(sc.nextInt(), false);
								break;
							case 4:
								restaurantController.printMenu();
								break;
							case 5:
								System.out.println("Returning....");
								break;
							default:
								System.out.println("Option not found");
								break;
						}
					} while (option != 5);
					break;
				case 2:
					//TODO - Write Options for Promotions
					break;
				case 3:
					//TODO - Write Options for Order
					break;
				case 4:
					//TODO - Write Options for Reservations
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
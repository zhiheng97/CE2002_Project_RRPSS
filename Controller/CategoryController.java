package Controller;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Enumerations.Categories;
import Models.Category;
import Models.Item;

/**
 * A controller that is responsible for managing the menu of the restaurant.
 * 
 * @author @zhiheng97
 * @since 10 October 2021
 */
public class CategoryController implements ISearch {

	private List<Category> categories = new ArrayList<Category>();
	private FileController fileController = new FileController();
	private final static String PATH_TO_MENU_FILE = Path.of("./Data/menu.txt").toString(); //Hold resolved path to menu.txt
	private final static String ESCAPE_STRING_1 = "\\"; //Checked against parameter to see if user would like to update the following attribute
	private final static String ESCAPE_STRING_2 = "-1.0"; //Checked against parameter to see if user would like to update the following attribute
	private final static String DELIMITER = ",";

	/**
	 * Constructs the CategoryController object.
	 */
	public CategoryController() {
		initializeMenuItems();
	}

	/**
	 * Adds a new category to the list of categories, it returns false if the category is duplicated.
	 * 
	 * @param 	categoryType	The Category type that is needed to be added to the list.
	 * @return 	true the category type is added, false otherwise.
	 */
	public boolean addCategory(Categories categoryType) {
		if(findCatByType(categoryType.toString()) == null) { //Searches for category type
			return categories.add(new Category(categoryType)); //New category created and added to the list of categories
		}
		return false;
	}

	/**
	 * Adds an item to the category specified in the parameter[0].
	 * 
	 * @param 	itemParams	The parameter of the item to be added.
	 * @return	true if the item is added, false otherwise.
	 */
	public boolean addItem(String[] itemParams) {
		Category toAddto;
		//Perform check of itemParams[4] which contains respective information of category
		if(itemParams[4].equals("0"))
			toAddto = findCatByType("MAINS");
		else if(itemParams[4].equals("1"))
			toAddto = findCatByType("SIDES");
		else
			toAddto = findCatByType("DRINKS");
		if(toAddto != null) {
			if(toAddto.addItem(Arrays.copyOfRange(itemParams, 0, itemParams.length-1))) { //Adds item to the category
				return updateMenuFile(); //Calls method to update menu.txt
			}
		}
		return false;
	}

	/**
	 * Searches List<Item> which is nested in List<Category> for the item with the specified itemId.
	 * 
	 * @param id Is the itemId to be copied
	 * @return Copy of the item that has been searched for
	 */
	public Item copyItem(int id) {
		Item toCopy = (Item) searchById(id); //Search for item based on id
		return toCopy.copyOf();
	}

	/**
	 * Searches the list of categories for the provided categoryType
	 * @param categoryType Category to be searched for
	 * @return Category that matches the provided parameter
	 */
	public Category findCatByType(String categoryType) {
		return categories.stream() //Searches the list of categories for a matching categoryType
			.filter(category -> category.getCategory().toString().equals(categoryType))
			.findFirst() //Returns first match
			.orElse(null); //Else return null
	}

	/**
	 * Method is called upon initialization of CategoryController.
	 */
	private void initializeMenuItems() {
		List<String> menuList = fileController.readFile(PATH_TO_MENU_FILE); //Calls fileController to read entire file into a List<String>
		//String[] itemParams = new String[4];
		String prevCat = "", curCat = "";
		for(int i = 0; i < menuList.size(); i++){ //Iterate through menuList
			String[] itemParams= menuList.get(i).split(DELIMITER);
			curCat = itemParams[4];
			//Performs check to see if a new category is to be created
			if(curCat.equals(Categories.MAINS.toString()) && !prevCat.equals(curCat)) 
				addCategory(Categories.MAINS);
			else if(curCat.equals(Categories.SIDES.toString()) && !prevCat.equals(curCat))
				addCategory(Categories.SIDES);
			else if(curCat.equals(Categories.DRINKS.toString()) && !prevCat.equals(curCat))
				addCategory(Categories.DRINKS);
			Category category = findCatByType(curCat); //Search for category to add item to
			category.addItem(itemParams); //Adds item to the category
			prevCat = curCat; //Updates parameter for next iteration
		}
	}

	/**
	 * Prints all categories in the list and all the items in the category
	 */
	public void print() {
		for(Category category : categories){ //Iterates through list of categories and calls the print method.
			category.print();
		}
		System.out.println();
	}

	/**
	 * Removes an item from the category, based on the parameter catType given
	 * @param itemId Id of item to be removed
	 * @param catType Category type for item to be removed from
	 * @return True if item was removed, false otherwise
	 */
	public boolean removeItem(int itemId) {
		Item toRemove = (Item) searchById(itemId); //Search for item based on id
		Category toRemoveFrom = categories.stream() //Searches categories for a match of the item to remove
			.filter(category -> category.getItems().contains(toRemove))
			.findFirst() //Returns first
			.orElse(null); //Else return null
		if(toRemoveFrom != null) { //If category is found
			toRemoveFrom.removeItem(itemId); //Remove item from category
			return updateMenuFile(); //Calls the method to update menu.txt
		}
		return false;
	}

	/**
	 * Searches for the item specified in the menu
	 * @param itemId ItemId to be searched for
	 * @return Item object that matches the specified itemId, otherwise null
	 */
	@Override
	public Object searchById(int itemId) {
		return categories.stream() //For each category in categories, get the items and find a match in item Id
		.flatMap(category -> category.getItems().stream())
		.filter(item -> item.getId() == itemId)
		.findFirst() //Returns first match
		.orElse(null); //Else return null
	}

	/**
	 * A function called upon modifying an item in the menu.
	 * @return True if file was modified, false otherwise
	 */
	private boolean updateMenuFile() {
		boolean res = false;
		List<String> records = new ArrayList<String>(); //Dynamic String array
		for(Category category : categories) //Iterates through list of categories
			for(Item item : category.getItems()){ //Iterates through the items of the category
				//Adds each record to records
				records.add(String.valueOf(item.getId()).concat(DELIMITER));
				records.add(item.getName().concat(DELIMITER));
				records.add(item.getDescription().concat(DELIMITER));
				records.add(String.valueOf(item.getPrice()).concat(DELIMITER));
				records.add(category.getCategory().toString());
				records.add(System.getProperty("line.separator")); //Adds a line break in txt file
			}
		if(fileController.writeFile(records.toArray(new String[records.size()]), PATH_TO_MENU_FILE)) //Calls method to update menu.txt
			res = true;
		return res;
	}

	/**
	 * Updates an item from the category, based on the parameter catType given
	 * @param itemParams New parameters for the item
	 * @param catType Category type for item to be updated
	 * @return True if item was updated, false otherwise
	 */
	public boolean updateItem(String[] itemParams) {
		Item toUpdate = (Item) searchById(Integer.parseInt(itemParams[1])); //Searches for item based on itemParams[1]
		if(toUpdate != null) { //If item found
			//Performs check on each itemParam[index] for the ESCAPE_STRING defined, if match skip the update, else update the attribute
			if(!itemParams[0].equals(ESCAPE_STRING_1))
				toUpdate.setName(itemParams[0]);
			if(!itemParams[2].equals(ESCAPE_STRING_1))
				toUpdate.setDescription(itemParams[2]);
			if(!itemParams[3].equals(ESCAPE_STRING_2))
				toUpdate.setPrice(Double.parseDouble(itemParams[3]));
			return updateMenuFile(); //Calls method to update menu.txt
		}
		return false;
	}

}

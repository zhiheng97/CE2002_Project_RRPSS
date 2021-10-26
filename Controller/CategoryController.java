package Controller;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Enumerations.Categories;
import Models.Category;
import Models.Item;

public class CategoryController {

	private List<Category> categories = new ArrayList<Category>();
	private FileController fileController = new FileController();
	private final static String PATH_TO_MENU_FILE = Path.of("./menu.txt").toString();

	/**
	 * Constructor of the CategoryController Class
	 */
	public CategoryController() {
		initializeMenuItems();
	}

	/**
	 * Adds a new category to the list of categories, does a search for any duplicate categories
	 * @param categoryType Category type to be added to the list.
	 * @return True the category type is added, false otherwise.
	 */
	public boolean addCategory(Categories categoryType) {
		if(findCatByType(categoryType.toString()) == null) {
			categories.add(new Category(categoryType));
			return true;
		}
		return false;
	}

	/**
	 * Adds an item to the category specified in the parameter[0]
	 * @param itemParams Parameter of the item to be added
	 * @return True if item is added, false otherwise
	 */
	public boolean addItem(String[] itemParams) {
		Category toAddto;
		if(itemParams[4].equals("0"))
			toAddto = findCatByType("MAINS");
		else if(itemParams[4].equals("1"))
			toAddto = findCatByType("SIDES");
		else
			toAddto = findCatByType("DRINKS");
		if(toAddto != null) {
			if(toAddto.addItem(Arrays.copyOfRange(itemParams, 0, itemParams.length-1))) {
				updateMenuFile();
				return true;
			}
		}
		return false;
	}

	/**
	 * Searches List<Item> which is nested in List<Category> for the item with the specified itemId.
	 * @param id Is the itemId to be copied
	 * @return Copy of the item that has been searched for
	 */
	public Item copyItem(int id) {
		Item toCopy = searchForItem(id);
		return toCopy.copyOf();
	}
	
	/**
	 * Searches the list of categories for the provided categoryType
	 * @param categoryType Category to be searched for
	 * @return Category that matches the provided parameter
	 */
	public Category findCatByType(String categoryType) {
		return categories.stream()
			.filter(category -> category.getCategory().toString().equals(categoryType))
			.findFirst()
			.orElse(null);
	}

	/**
	 * Method is called upon initialization of CategoryController.
	 */
	private void initializeMenuItems() {
		List<String> menuList = fileController.readFile(PATH_TO_MENU_FILE);
		String[] itemParams = new String[4];
		String prevCat = "", curCat = "";
		for(int i = 0; i < menuList.size(); i += 5){
			curCat = menuList.get(i + 4);
			if(curCat.equals(Categories.MAINS.toString()) && !prevCat.equals(curCat))
				addCategory(Categories.MAINS);
			else if(curCat.equals(Categories.SIDES.toString()) && !prevCat.equals(curCat))
				addCategory(Categories.SIDES);
			else if(curCat.equals(Categories.DRINKS.toString()) && !prevCat.equals(curCat))
				addCategory(Categories.DRINKS);
			itemParams[1] = menuList.get(i); //id
			itemParams[0] = menuList.get(i + 1); //name
			itemParams[2] = menuList.get(i + 2); //description
			itemParams[3] = menuList.get(i + 3); //price
			Category category = findCatByType(curCat);
			category.addItem(itemParams);
			prevCat = curCat;
		}
	}

	/**
	 * Prints all categories in the list and all the items in the category
	 */
	public void print() {
		for(Category category : categories){
			category.print();
		}
	}

	/**
	 * Removes an item from the category, based on the parameter catType given
	 * @param itemId Id of item to be removed
	 * @param catType Category type for item to be removed from
	 * @return True if item was removed, false otherwise
	 */
	public boolean removeItem(int itemId) {
		Item toRemove = searchForItem(itemId);
		Category toRemoveFrom = categories.stream()
			.filter(category -> category.getItems().contains(toRemove)).findFirst().orElse(null);
		if(toRemoveFrom != null) {
			toRemoveFrom.removeItem(itemId);
			return true;
		}
		return false;
	}

	/**
	 * Searches for the item specified in the menu
	 * @param itemId ItemId to be searched for
	 * @return Item object that matches the specified itemId, otherwise null
	 */
	private Item searchForItem(int itemId) {
		return categories.stream()
		.flatMap(category -> category.getItems().stream())
		.filter(item -> item.getId() == itemId)
		.findFirst()
		.orElse(null);
	}

	/**
	 * A function called upon modifying an item in the menu.
	 * @return True if file was modified, false otherwise
	 */
	private boolean updateMenuFile() {
		boolean res = false;
		List<String> records = new ArrayList<String>();
		for(Category category : categories)
			for(Item item : category.getItems()){
				records.add(String.valueOf(item.getId()));
				records.add(item.getName());
				records.add(item.getDescription());
				records.add(String.valueOf(item.getPrice()));
				records.add(category.getCategory().toString());
			}
		if(fileController.writeFile(records.toArray(new String[records.size()]), PATH_TO_MENU_FILE))
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
		Item toUpdate = searchForItem(Integer.parseInt(itemParams[0]));
		if(toUpdate != null) {
			if(toUpdate != null) {
				if(!itemParams[1].equals(null) || !itemParams[1].equals(""))
					toUpdate.setName(itemParams[1]);
				if(!itemParams[2].equals(null) || !itemParams[2].equals(""))
					toUpdate.setDescription(itemParams[2]);
				if(!itemParams[3].equals(null) || !itemParams[3].equals(""))
					toUpdate.setPrice(Double.parseDouble(itemParams[3]));
				return true;
			}
		}
		return false;
	}

}
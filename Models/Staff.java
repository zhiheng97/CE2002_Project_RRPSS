package Models;

/**
 * A model that represents the staff of the restaurant.
 * @author	@Henry-Hoang
 * @since 	10 October 2021
 */
public class Staff {

	private int id;
	private String name;
	private String position;

	/**
	 * Constructs the Staff object by the ID, name, and position of the staff. 
	 * 
	 * @param id       	The ID of this staff.
	 * @param name     	The name of this staff.
	 * @param position 	The position held by this staff.
	 */
	public Staff(int id, String name, String position) {
		this.id = id;
		this.name = name;
		this.position = position;
	}

	/**
	 * Gets the ID of this staff.
	 * 
	 * @return	The ID of this staff.
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Gets the name of this staff.
	 * 
	 * @return	The name of this staff.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the position of this staff.
	 * 
	 * @return	The position of this staff.
	 */
	public String getPosition() {
		return this.position;
	}

}
package Models;

public class Staff {

	private int id;
	private String name;
	private String position;

	/**
	 * Constructor for Staff Object
	 * 
	 * @param id       	Id of the staff
	 * @param name     	Name of the staff
	 * @param position 	Position held by staff
	 */
	public Staff(int id, String name, String position) {
		this.id = id;
		this.name = name;
		this.position = position;
	}

	/**
	 * Id getter
	 * 
	 * @return id of staff
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Name getter
	 * 
	 * @return name of staff
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Position getter
	 * 
	 * @return position of staff
	 */
	public String getPosition() {
		return this.position;
	}

}
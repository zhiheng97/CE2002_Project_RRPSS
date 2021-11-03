package Models;

public class Staff {

	private int id;
	private String name;
	private String position;

	/**
	 * Constructor for Staff Object
	 * 
	 * @param staffId       Id of the staff
	 * @param staffName     Name of the staff
	 * @param staffPosition Position held by staff
	 */
	public Staff(int staffId, String staffName, String staffPosition) {
		this.id = staffId;
		this.name = staffName;
		this.position = staffPosition;
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
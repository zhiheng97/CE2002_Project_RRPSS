package Models;

public class Staff {

	private int id;
	private String name;
	private String position;

	/**
	 * 
	 * @param staffId
	 * @param staffName
	 * @param staffPosition
	 */
	public Staff(int staffId, String staffName, String staffPosition) {
		this.id = staffId;
		this.name = staffName;
		this.position = staffPosition;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getPosition() {
		return this.position;
	}

}
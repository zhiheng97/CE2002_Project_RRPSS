package Controller;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import Models.Staff;

/**
 * A controller that is responsible for managing the staffs of the restaurant.
 * @author  @Henry-Hoang
 * @since   10 October 2021
 */
public class StaffController {
    
	private List<Staff> staffList = new ArrayList<Staff>();
	private static final String PATH_TO_STAFFS_FILE = Path.of("./Data/staff.txt").toString(); //Holds resolved path to staff.txt
    private static final String DELIMITER = ",";
    private FileController fileController = new FileController();

    /**
     * Constructs the StaffController object
     */
    public StaffController() {
		List<String> staffParams = fileController.readFile(PATH_TO_STAFFS_FILE);
		for (int i = 1; i < staffParams.size(); i++) {
            String[] params = staffParams.get(i).split(DELIMITER);
			this.staffList.add(
					new Staff(Integer.parseInt(params[0]), params[1], params[2]));
		}
    }
    
    /**
     * Gets the list of the staff in this restaurant.
     * 
     * @return  The list of the staff in this restaurant.
     */
    public List<Staff> getStaffList() {
        return this.staffList;
    }
}

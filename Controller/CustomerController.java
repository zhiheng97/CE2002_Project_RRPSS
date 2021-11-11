/**
 * A controller that is responsible for managing the restaurant's registered customers
 * @author  @Henry-Hoang
 * @since 10 October 2021
 */
package Controller;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import Models.Customer;

public class CustomerController {

    private List<Customer> customers = new ArrayList<Customer>();
	private static final String PATH_TO_CUSTOMERS_FILE = Path.of("./Data/customers.txt").toString(); //Holds resolved path to customers.txt
    private static final String DELIMITER = ",";
    private FileController fileController = new FileController();

    /**
     * Constructor for CustomerController object
     */
    public CustomerController() {
        //Reading in of registered customers
        List<String> custParams = fileController.readFile(PATH_TO_CUSTOMERS_FILE);
        for (int i = 1; i < custParams.size(); i++) {
            String[] params = custParams.get(i).split(DELIMITER);
            this.customers.add(new Customer(Integer.parseInt(params[0]), params[1],
                    Boolean.parseBoolean(params[2]), Integer.parseInt(params[3])));
        }
    }

    /**
     * Get all registered customers
     * @return List of customers
     */
    public List<Customer> getCustomers() {
        return this.customers;
    }

    /**
     * Registers a new customer
     * @param cust_name Name of the customer
     * @param contactNo Contact number of the customer
     * @return new customer id, or -1 if duplicate registration
     */
    public int addCustomer(String cust_name, int contactNo){
        if(searchByCustName(cust_name) == null && searchByContact(contactNo) == null){
            int new_id = this.customers.size();
            this.customers.add(new Customer(new_id, cust_name, false, contactNo));
            return new_id;
        }
        return -1;
    }

    /**
     * Searches for a customer by their name
     * @param cust_name Name of customer
     * @return Customer object if found, null otherwise
     */
    private Customer searchByCustName(String cust_name) {
        return customers.stream()
            .filter(c -> c.getName().equals(cust_name))
            .findFirst()
            .orElse(null);
    }

    /**
     * Searches for a customer by their contactNo
     * @param contactNo Contact number of customer
     * @return Customer object if found, null otherwise
     */
    private Customer searchByContact(int contactNo) {
        return customers.stream()
            .filter(c -> c.getMobileNo() == contactNo)
            .findFirst()
            .orElse(null);
    }
    
}

/**
 * A controller that is responsible for performing read/write operations to the "database".
 * @author  @brianleect, @Henry-Hoang, @ghotinggoad, @zhiheng97
 * @since 10 October 2021
 */
package Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileController {

    /**
     * Constructor for the FileController Class
     */
    public FileController() {}
    
    /**
     * CSV File reader. Reads the contents of a file and returns the contents as a List<String> object
     * @param path Path to file
     * @return List<String> if the file is valid and has contents, null otherwise
     */
    public List<String> readFile(String path) {
        try{
            List<String> list = new ArrayList<String>(); //Dynamic String array to hold entire file
            String row = "";
            BufferedReader csvReader = new BufferedReader(new FileReader(path));
            while((row = csvReader.readLine()) != null) { //Reads line while not null
                list.add(row); //Adds each line into the list
            }
            csvReader.close();
            return list;
        } catch (IOException e) {
            System.out.println("Error Occured!\nPlease contact RRPSS Support Team for assistance.");
            e.printStackTrace();
        }
        return null;
    }
  
    /**
     * CSV file writer. Writes to the file path specified.
     * @param params Content to be written to the file
     * @param path Path to file
     * @return True if modification to the file was made, false otherwise
     */
    public boolean writeFile(String[] params, String path) {
        boolean res = false;
        try{
            FileWriter fw = new FileWriter(path, false); //Initialize FileWriter object with path to file and boolean flag to overwrite file
            for(String item : params) {
                fw.write(item);
            }
            fw.flush();
            fw.close();
            res = true;
        }catch (IOException e) {
            System.out.println("Error Occured!\nPlease contact RRPSS Support Team for assistance.");
            e.printStackTrace();
        }
        return res;
    }
}

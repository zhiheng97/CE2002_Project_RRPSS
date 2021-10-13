package Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileController {

    /**
     * Constructor for the FileController Class
     */
    public FileController() {}
    
    public List<String> readFile(String path) {
        try{
            List<String> list = new ArrayList<String>();
            String row = "";
            BufferedReader csvReader = new BufferedReader(new FileReader(path));
            while((row = csvReader.readLine()) != null) {
                for(String s : row.split(",")){
                    list.add(s);
                }
            }
            csvReader.close();
            return list;
        } catch (IOException e) {
            System.out.println("ERROR: Unable to access file");
            System.out.println(e.getStackTrace());
        }
        return null;
    }
    
}

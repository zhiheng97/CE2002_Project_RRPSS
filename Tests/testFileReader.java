package Tests;
import java.nio.file.Path;
import java.util.List;

import Controller.FileController;

public class testFileReader {
    public static void main(String[] args) {
        FileController fileController = new FileController();
        String PATH_TO_RESERVATIONS_FILE = Path.of("./Data/reservation.txt").toString();

        List<String> reserveParams = fileController.readFile(PATH_TO_RESERVATIONS_FILE);
		for (String s : reserveParams)
            System.out.println(s);
    }
}

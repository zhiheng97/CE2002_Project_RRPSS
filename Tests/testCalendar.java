package Tests;

import java.util.Calendar;
import java.util.Date;

public class testCalendar {
    public static void main(String[] args) {
        Calendar c = Calendar.getInstance();
        Date dt = c.getTime();
        System.out.println("The Current Date is: " + dt);
    }

}

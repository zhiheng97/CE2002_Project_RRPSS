package test_function;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import Models.Report;
import Models.Order;
import Models.Promotion;
import Models.Item;
import Models.Staff;

import Controller.*;

public class testReport {

    public static void main(String[] args) {
        List<Report> reports = new ArrayList<Report>();
        FileController fileController = new FileController();
        String PATH_TO_ORDERS_FILE = Path.of("./orders.txt").toString();

        List<String> tokens = fileController.readFile(PATH_TO_ORDERS_FILE);
        String prevCat = "", curCat = "";
        int i = 0;
        Report currentReport = new Report("NIL");

        do {
            double orderTotal = 0;

            Staff tmpStaff = new Staff(Integer.parseInt(tokens.get(i)), tokens.get(i + 1), tokens.get(i + 2));
            String dateTime = tokens.get(i + 3);
            String dateOnly = dateTime.substring(0, dateTime.length() - 6);

            // Checks if order date same as report, create new report if its different
            if (!dateOnly.equals(currentReport.getDate())) {
                System.out.println("New Date: " + dateTime + " Old Date: " + currentReport.getDate());

                if (!currentReport.getDate().equals("NIL"))
                    reports.add(currentReport); // Adds previous report to list only if its not NIL which is start
                                                // report

                System.out.println("\nNew report created with date: " + dateOnly + "\n");
                currentReport = new Report(dateOnly); // Create new report for new date
            }

            System.out.println("Id: " + tmpStaff.getId() + " / Name: " + tmpStaff.getName() + " / Position: "
                    + tmpStaff.getPosition());

            System.out.println("Date Time: " + dateTime);
            System.out.println("Date Only: " + dateOnly);

            List<Item> items = new ArrayList<Item>();

            i += 4; // Increments past staff and order info

            // We immediately check if ENDITEM, for case where NO ITEM but PROMO ORDER
            while (i < tokens.size() && !tokens.get(i).equals("ENDITEM")) {
                Item tmpItem = new Item(Integer.parseInt(tokens.get(i)), tokens.get(i + 1), tokens.get(i + 2),
                        Double.parseDouble(tokens.get(i + 3)));

                System.out.println("Item Id: " + tmpItem.getId() + " / Description: " + tmpItem.getDescription()
                        + " / Price: " + tmpItem.getPrice());

                orderTotal += tmpItem.getPrice();
                items.add(tmpItem);
                i += 4;
            }
            i++; // Increment past ENDITEM

            // Check if any promo exists
            if (tokens.get(i).equals("ENDPROMO"))
                i++; // We continue if no promos
            else {
                List<Promotion> tmpPromoList = new ArrayList<Promotion>();
                do {
                    int promo_id = Integer.parseInt(tokens.get(i));
                    String promo_name = tokens.get(i + 1);
                    String promo_desc = tokens.get(i + 2);
                    Double promo_cost = Double.parseDouble(tokens.get(i + 3));
                    orderTotal += promo_cost;
                    System.out.println("\nPromo Id: " + promo_id + " / Promo Name: " + promo_name + " / Description: "
                            + promo_desc + " / Price: " + promo_cost);

                    List<Item> promo_items = new ArrayList<Item>();
                    List<String> promo_items_str = new ArrayList<String>();

                    i += 4; // Move to the start of items in promo

                    // String item getting method
                    int j = i;
                    while (!tokens.get(j).equals("ENDPROMO"))
                        promo_items_str.add(tokens.get(j++));

                    System.out.println(promo_items_str);

                    // Conventional item getting method
                    while (i < tokens.size() && !tokens.get(i).equals("ENDPROMO")) {
                        Item tmpItem = new Item(Integer.parseInt(tokens.get(i)), tokens.get(i + 1), tokens.get(i + 2),
                                Double.parseDouble(tokens.get(i + 3)));
                        System.out.println("Item Id: " + tmpItem.getId() + " / Name: " + tmpItem.getName()
                                + " Description: " + tmpItem.getDescription() + " / Price: " + tmpItem.getPrice());
                        promo_items.add(tmpItem);
                        i += 4;
                    }

                    Promotion tmpPromo = new Promotion(promo_id, promo_name, promo_desc, promo_cost, promo_items_str);
                    tmpPromoList.add(tmpPromo);
                    // promo_index++;
                    i++; // Exits current promo to check for next promo
                } while (i < tokens.size() && !tokens.get(i).equals("ENDORDER"));

                System.out.println("Total value of order being added: " + orderTotal);
                Order tmpOrder = new Order(tmpStaff, dateOnly, items, tmpPromoList, orderTotal);
                currentReport.addInvoice(tmpOrder);
                System.out.println("No more promo found, checking for next order next\n");
                i++; // Increment past ENDORDER
            }
        } while (i < tokens.size() && !tokens.get(i).equals("ENDFILE"));
        System.out.println("All orders scanned, exiting file");
    }
}

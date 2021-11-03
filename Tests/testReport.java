package Tests;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import Controller.FileController;
import Models.Item;
import Models.Promotion;
import Models.Report;
import Models.Staff;

public class testReport {

    public static void main(String[] args) {
        List<Report> reports = new ArrayList<Report>();
        FileController fileController = new FileController();
        String PATH_TO_ORDERS_FILE = Path.of("./orders.txt").toString();

        List<String> tokens = fileController.readFile(PATH_TO_ORDERS_FILE);
        int i = 0;
        int ordersScanned = 0;
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

                // System.out.println("Printing old report information");
                // currentReport.print();
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
                // Order tmpOrder = new Order(tmpStaff, dateOnly, items, tmpPromoList, orderTotal);
                // ordersScanned++;
                // currentReport.addInvoice(tmpOrder);
                // System.out.println("No more promo found, checking for next order next\n");
                i++; // Increment past ENDORDER
            }
        } while (i < tokens.size() && !tokens.get(i).equals("ENDFILE"));
        System.out.println("All orders scanned, exiting file");
        System.out.println("Orders scanned: " + ordersScanned + " / Reports generated: " + reports.size());

        for (int j = 0; j < reports.size(); j++)
            reports.get(j).print();

        LinkedHashMap<String, Integer> item_map = new LinkedHashMap<String, Integer>();
        LinkedHashMap<String, Integer> promo_map = new LinkedHashMap<String, Integer>();
        // Get last 30 reports
        int reports_size = reports.size();
        Report tmpReport;

        double salesRevenue = 0;
        if (reports_size < 30) {
            String start_date = reports.get(0).getDate();
            String end_date = reports.get(reports_size - 1).getDate();

            for (int j = 0; j < reports_size; j++) {
                tmpReport = reports.get(j);
                salesRevenue += tmpReport.getSalesRevenue(); // Update sales revenue
                int count;
                String item_name;
                LinkedHashMap<String, Integer> tmpReportItems = tmpReport.getItemMap();
                for (Map.Entry<String, Integer> e : tmpReportItems.entrySet()) {
                    item_name = e.getKey();
                    count = item_map.containsKey(item_name) ? item_map.get(item_name) : 0;
                    item_map.put(item_name, count + e.getValue());
                }

                String promo_name;
                LinkedHashMap<String, Integer> tmpReportPromos = tmpReport.getPromoMap();
                for (Map.Entry<String, Integer> e : tmpReportPromos.entrySet()) {
                    promo_name = e.getKey();
                    count = promo_map.containsKey(promo_name) ? promo_map.get(promo_name) : 0;
                    promo_map.put(promo_name, count + e.getValue());
                }

            }
            System.out.println("\n\n(Monthly Report) Start: " + start_date + " / End: " + end_date);
            System.out.println("Monthly Sales: " + salesRevenue);

            System.out.println("\nItems:");
            for (Map.Entry<String, Integer> e : item_map.entrySet())
                System.out.println("Item: " + e.getKey() + " Quantity: " + e.getValue());

            System.out.println("\nPromos:");
            for (Map.Entry<String, Integer> e : promo_map.entrySet())
                System.out.println("Promo: " + e.getKey() + " Quantity: " + e.getValue());
        }
    }
}

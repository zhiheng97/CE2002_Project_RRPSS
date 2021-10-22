import java.util.*;
import Controller.PromotionController;
import Models.Promotion;

public class TestPromotionController{
  public static void main(String[] args){
    System.out.println("Promotion Test");
    PromotionController promotionController = new PromotionController();
    promotionController.print();

    String[] input = new String[4];
    input[0] = "800";
    input[1] = "Festive Cake";
    input[2] = "Christmas Only!";
    input[3] = "26.00";

    List<String> input1 = new ArrayList<String>();
    input1.add("800");
    input1.add("Festive Cake");
    input1.add("Christmas Only!");
    input1.add("26.00");

    List<String> input2 = new ArrayList<String>();
    input2.add("404");
    input2.add("Festive Cake Promo");
    input2.add("After Christmas Only!");
    input2.add("13.00");
    input2.add("404");
    input2.add("Festive Cake Promo");
    input2.add("After Christmas Only!");
    input2.add("13.00");

    promotionController.addItem(400, input);
    System.out.println();
    System.out.println();
    promotionController.print();
    System.out.println();
    System.out.println();
    promotionController.removeItem(400, 800);
    System.out.println();
    System.out.println();
    promotionController.print();
    System.out.println();
    System.out.println();
    promotionController.removePromotion(400);
    System.out.println();
    System.out.println();
    promotionController.print();
    System.out.println();
    System.out.println();
    promotionController.addPromotion(input2, input1);
    System.out.println();
    System.out.println();
    promotionController.print();
  }
}

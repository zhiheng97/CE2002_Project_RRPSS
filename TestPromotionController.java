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
  }
}

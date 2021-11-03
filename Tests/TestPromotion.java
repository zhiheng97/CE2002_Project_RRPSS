import java.util.*;
import Controller.PromotionController;
import Models.Promotion;

public class TestPromotionController{
  public static void main(String[] args){
    System.out.println("Promotion Test");
    PromotionController promotionController = new PromotionController();
    promotionController.print();

    System.out.println("\n\n\n");

    Promotion copy = promotionController.copyPromotion(400);
    copy.print();

    System.out.println("\n");

    // this prints the object hash identifier to make sure they're not the same object
    System.out.println(System.identityHashCode(promotionController.findPromotionById(400)));
    System.out.println(System.identityHashCode(copy));
  }
}

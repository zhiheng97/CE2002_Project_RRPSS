package Models;

/**
 * An interface class for the model Item.
 * @author  @ghotinggoad
 * @since   10 October 2021
 */
interface IItem{
  public int getId();
  public String getName();
  public String getDescription();
  public double getPrice();
  public void setName(String name);
  public void setDescription(String description);
  public void setPrice(double price);
  public void print();
}

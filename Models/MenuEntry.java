package Models;

interface MenuEntry{
  public int getId();
  public String getName();
  public String getDescription();
  public double getPrice();
  public void setName(String name);
  public void setDescription(String description);
  public void setPrice(double price);
  public void print();
}

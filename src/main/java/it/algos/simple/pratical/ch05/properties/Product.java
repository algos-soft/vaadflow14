package it.algos.simple.pratical.ch05.properties;

public class Product {

  private String name;

  private boolean available = true;

  private Manufacturer manufacturer = new Manufacturer("", "", "");

  public Product() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  public Manufacturer getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(Manufacturer manufacturer) {
    this.manufacturer = manufacturer;
  }

}

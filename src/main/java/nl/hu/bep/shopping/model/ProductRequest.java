package nl.hu.bep.shopping.model;

public class ProductRequest {
    public String name;
    public int amount;
    public String listname;

    public String getName() { return name; }

    public String getListName() { return listname; }

    public int getAmount() { return amount; }
}

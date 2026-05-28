package dk.ek.entities;

public class BOMLine {
    private String name;
    private int amount;
    private String unit;
    private int length;
    private String description;

    public BOMLine(String name, int amount, String unit, int length, String description) {
        this.name        = name;
        this.amount      = amount;
        this.unit        = unit;
        this.length      = length;
        this.description = description;
    }

    public String getName()        { return name; }
    public int getAmount()         { return amount; }
    public String getUnit()        { return unit; }
    public int getLength()         { return length; }
    public String getDescription() { return description; }
}
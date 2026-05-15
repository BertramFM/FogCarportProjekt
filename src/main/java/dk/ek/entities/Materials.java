package dk.ek.entities;

public class Materials {

    private int id;
    private String name;
    private String unit;
    private double pricePerUnit;
    private String category;

    public Materials(int id, String name, String unit, double pricePerUnit, String category) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.pricePerUnit = pricePerUnit;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public String getCategory() {
        return category;
    }
}
package dk.ek.entities;

public class Material {

    private int id;
    private String name;
    private String unit;
    private double pricePerUnit;

    public Material(int id, String name, String unit, double pricePerUnit) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.pricePerUnit = pricePerUnit;
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
}
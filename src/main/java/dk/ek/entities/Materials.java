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

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
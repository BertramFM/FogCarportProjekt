package dk.ek.entities;

public class Materials {

    private int id;
    private String name;
    private String unitOfMeasurement;
    private double pricePerUnit;
    private String category;
    private final double materialWidth;
    private final double materialHeight;

    public Materials(int id, String name,String unitOfMeasurement, double pricePerUnit, String category, double materialWidth, double materialHeight) {
        this.id = id;
        this.name = name;
        this.unitOfMeasurement = unitOfMeasurement;
        this.pricePerUnit = pricePerUnit;
        this.category = category;
        this.materialWidth = materialWidth;
        this.materialHeight = materialHeight;
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

    public double getMaterialWidth() {
        return materialWidth;
    }

    public double getMaterialHeight() {
        return materialHeight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
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
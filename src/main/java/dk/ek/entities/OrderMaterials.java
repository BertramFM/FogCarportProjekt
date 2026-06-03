package dk.ek.entities;

public class OrderMaterials {
    private String name;
    private String materialDescription;
    private int lengthMeasurement;
    private int amount;
    private String unitOfAmount;
    private String usageDescription;
    private double pricePerUnit;
    private int materialId;

    public OrderMaterials(String name, String materialDescription, int lengthMeasurement, int amount, String unitOfAmount, String description, int materialId) {
        this.name = name;
        this.materialDescription = materialDescription;
        this.lengthMeasurement = lengthMeasurement;
        this.amount = amount;
        this.unitOfAmount = unitOfAmount;
        this.usageDescription = description;
        this.materialId = materialId;
    }



    public OrderMaterials(String name, String materialDescription, int lengthMeasurement, int amount, String unitOfAmount, String usageDescription, double pricePerUnit) {
        this.name = name;
        this.materialDescription = materialDescription;
        this.lengthMeasurement = lengthMeasurement;
        this.amount = amount;
        this.unitOfAmount = unitOfAmount;
        this.usageDescription = usageDescription;
        this.pricePerUnit = pricePerUnit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public int getLengthMeasurement() {
        return lengthMeasurement;
    }

    public void setLengthMeasurement(int lengthMeasurement) {
        this.lengthMeasurement = lengthMeasurement;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUnitOfAmount() {
        return unitOfAmount;
    }

    public void setUnitOfAmount(String unitOfAmount) {
        this.unitOfAmount = unitOfAmount;
    }

    public String getUsageDescription() {
        return usageDescription;
    }

    public void setDescription(String description) {
        this.usageDescription = description;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }
}



package dk.ek.entities;

public class OrderMaterials {
    private String name;
    private String materialDescription;
    private int lengthMeasurement;
    private int amount;
    private String unitOfAmount;
    private String usageDescription;

    public OrderMaterials(String name, String materialDescription, int lengthMeasurement, int amount, String unitOfAmount, String description) {
        this.name = name;
        this.materialDescription = materialDescription;
        this.lengthMeasurement = lengthMeasurement;
        this.amount = amount;
        this.unitOfAmount = unitOfAmount;
        this.usageDescription = description;
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
}



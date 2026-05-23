package dk.ek.entities;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private int customerId;
    private int employeeId;
    private String roofMaterial;
    private int roofAngle;
    private int carportWidth;
    private int carportLength;
    private boolean hasToolShed;
    private int shedWidth;
    private int shedLength;
    private String note;
    private String status;
    private LocalDateTime createdAt;

    public Order(int id, int customerId, int employeeId, String roofMaterial, int carportWidth, int carportLength, boolean hasToolShed, int shedWidth, int shedLength, String note, String status, LocalDateTime createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.roofMaterial = roofMaterial;
        this.carportWidth = carportWidth;
        this.carportLength = carportLength;
        this.hasToolShed = hasToolShed;
        this.shedWidth = shedWidth;
        this.shedLength = shedLength;
        this.note = note;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Order(int id, int customerId, int employeeId, String roofMaterial, int roofAngle, int carportWidth, int carportLength, boolean hasToolShed, int shedWidth, int shedLength, String note, String status, LocalDateTime createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.roofMaterial = roofMaterial;
        this.roofAngle = roofAngle;
        this.carportWidth = carportWidth;
        this.carportLength = carportLength;
        this.hasToolShed = hasToolShed;
        this.shedWidth = shedWidth;
        this.shedLength = shedLength;
        this.note = note;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getRoofMaterial() {
        return roofMaterial;
    }

    public void setRoofMaterial(String roofMaterial) {
        this.roofMaterial = roofMaterial;
    }

    public int getRoofAngle() {
        return roofAngle;
    }

    public void setRoofAngle(int roofAngle) {
        this.roofAngle = roofAngle;
    }

    public int getCarportWidth() {
        return carportWidth;
    }

    public void setCarportWidth(int carportWidth) {
        this.carportWidth = carportWidth;
    }

    public int getCarportLength() {
        return carportLength;
    }

    public void setCarportLength(int carportLength) {
        this.carportLength = carportLength;
    }

    public boolean getHasToolShed() {
        return hasToolShed;
    }

    public void setHasToolShed(boolean toolShed) {
        this.hasToolShed = toolShed;
    }

    public int getShedWidth() {
        return shedWidth;
    }

    public void setShedWidth(int shedWidth) {
        this.shedWidth = shedWidth;
    }

    public int getShedLength() {
        return shedLength;
    }

    public void setShedLength(int shedLength) {
        this.shedLength = shedLength;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
package dk.ek.entities;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private int customerId;
    private int employeeId;
    private String roofType;
    private int width;
    private int length;
    private Boolean isToolShed;
    private int shedWidth;
    private int shedLength;
    private String note;
    private String status;
    private int roofSlope;
    private LocalDateTime createdAt;

    public Order(int id, int customerId, int employeeId, String roofType, int width, int length, Boolean isToolShed, int shedWidth, int shedLength, String note, String status, LocalDateTime createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.roofType = roofType;
        this.width = width;
        this.length = length;
        this.isToolShed = isToolShed;
        this.shedWidth = shedWidth;
        this.shedLength = shedLength;
        this.note = note;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Order(int id, int customerId, String roofType, int width, int length, Boolean isToolShed, int shedWidth, int shedLength, String note, String status, LocalDateTime createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.roofType = roofType;
        this.width = width;
        this.length = length;
        this.isToolShed = isToolShed;
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

    public String getRoofType() {
        return roofType;
    }

    public void setRoofType(String roofType) {
        this.roofType = roofType;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Boolean getIsToolShed() {
        return isToolShed;
    }

    public void setIsToolShed(Boolean toolShed) {
        this.isToolShed = toolShed;
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
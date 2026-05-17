package dk.ek.entities;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private String customerName;
    private String employeeName;
    private String roofType;
    private int width;
    private int length;
    private boolean toolShed;
    private Integer shedWidth;
    private Integer shedLength;
    private String note;
    private String status;
    private LocalDateTime createdAt;

    public Order(int id, String customerName, String employeeName, String roofType,
                 int width, int length, boolean toolShed, Integer shedWidth,
                 Integer shedLength, String note, String status, LocalDateTime createdAt) {
        this.id = id;
        this.customerName = customerName;
        this.employeeName = employeeName;
        this.roofType = roofType;
        this.width = width;
        this.length = length;
        this.toolShed = toolShed;
        this.shedWidth = shedWidth;
        this.shedLength = shedLength;
        this.note = note;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getEmployeeName() { return employeeName; }
    public String getRoofType() { return roofType; }
    public int getWidth() { return width; }
    public int getLength() { return length; }
    public boolean isToolShed() { return toolShed; }
    public Integer getShedWidth() { return shedWidth; }
    public Integer getShedLength() { return shedLength; }
    public String getNote() { return note; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
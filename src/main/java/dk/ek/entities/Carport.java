package dk.ek.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Carport {

    private RoofType RoofType;
    private int id;

    private int customerId;
    private int employeeId;
    private String status;
    private Date createdAt;

    private RoofType roofType;
    private int roofAngle;
    private int length;
    private int width;

    private List<Material> carportMaterial = new ArrayList<>();
    private List<Material> roofMaterial = new ArrayList<>();

    private boolean toolShed;
    private Integer shedWidth;
    private Integer shedLength;

    private String note;

    private String firstname;
    private String lastname;

    private String address;
    private String zipcode;
    private String city;

    private String email;
    private String phone;

    public Carport() {}

    public Carport(
            int id,
            int customerId,
            int employeeId,
            String roofType,
            int width,
            int length,
            boolean toolShed,
            int shedWidth,
            int shedLength,
            String note,
            String status,
            Date createdAt
    ) {
        this.id = id;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.width = width;
        this.length = length;
        this.toolShed = toolShed;
        this.shedWidth = shedWidth;
        this.shedLength = shedLength;
        this.note = note;
        this.status = status;
        this.createdAt = createdAt;
        this.roofType = RoofType;
    }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoofType getRoofType() {
        return roofType;
    }

    public void setRoofType(RoofType roofType) {
        this.roofType = roofType;
    }

    public int getRoofAngle() {
        return roofAngle;
    }

    public void setRoofAngle(int roofAngle) {
        this.roofAngle = roofAngle;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public List<Material> getCarportMaterial() {
        return carportMaterial;
    }

    public void setCarportMaterial(List<Material> carportMaterial) {
        this.carportMaterial = carportMaterial;
    }

    public List<Material> getRoofMaterial() {
        return roofMaterial;
    }

    public void setRoofMaterial(List<Material> roofMaterial) {
        this.roofMaterial = roofMaterial;
    }

    public boolean isToolShed() {
        return toolShed;
    }

    public void setToolShed(boolean toolShed) {
        this.toolShed = toolShed;
    }

    public Integer getShedWidth() {
        return shedWidth;
    }

    public void setShedWidth(Integer shedWidth) {
        this.shedWidth = shedWidth;
    }

    public Integer getShedLength() {
        return shedLength;
    }

    public void setShedLength(Integer shedLength) {
        this.shedLength = shedLength;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
package dk.ek.entities;

public class Customer {

    private int id;
    private String firstname;
    private String lastname;
    private String address;
    private String email;
    private String phone;
    private int zipcodeId;

    public Customer(String firstname, String lastname, String address,
                    String email, String phone, int zipcodeId) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.zipcodeId = zipcodeId;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setZipcodeId(int zipcodeId) {
        this.zipcodeId = zipcodeId;
    }

    public Customer() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }

    public String getPhone() { return phone; }

    public int getZipcodeId() { return zipcodeId; }
}
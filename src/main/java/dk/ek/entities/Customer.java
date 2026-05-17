package dk.ek.entities;

public class Customer {
    private int id;
    private String firstname;
    private String lastname;
    private String address;
    private String email;
    private String phone;
    private int zipcode;
    private String city;

    public Customer(int id, String firstname, String lastname, String address,
                    String email, String phone, int zipcode, String city) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.zipcode = zipcode;
        this.city = city;
    }

    public int getId() { return id; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public int getZipcode() { return zipcode; }
    public String getCity() { return city; }
}
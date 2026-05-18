package dk.ek.entities;

public class Employee {

    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String role;

    public Employee(String firstname, String lastname, String email, String role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
    }

    public Employee() {}

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }

    public String getEmail() { return email; }

    public String getRole() { return role; }
}
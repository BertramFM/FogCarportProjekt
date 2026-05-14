package dk.ek.entities;


public class User {
    private int id;
    private String email;

    private String password;

    private String role;

    public User( String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getRole() {
        return role;
    }

}

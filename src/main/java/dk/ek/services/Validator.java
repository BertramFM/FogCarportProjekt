package app.services;

public class Validator {
    public static String validateUser(String email, String password) {
        if(email == null || email.isBlank()) {
            return "Email skal udfyldes";
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return "Email er ikke gyldig";
        } else if (password == null || password.isBlank()) {
            return "Password skal udfyldes";
        } else if (password.length() < 8) {
            return "Password skal være mindst 8 tegn";
        } else if (!password.matches(".*[0-9].*")) {
            return "Password skal indeholde mindst ét tal";
        } else if (!password.matches(".*[^a-zA-Z0-9].*")) {
            return "Password skal indeholde mindst ét specialtegn";
        }
        return null;
    }
}

package dk.ek.services;

public class Validator {
    public static String validatePassword(String password) {
        if (password == null || password.isBlank()) {
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

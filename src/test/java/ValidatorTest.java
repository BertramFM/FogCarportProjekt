import dk.ek.services.Validator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ValidatorTest {
    @Test
    void shouldRejectBlankPassword() {
        // Arrange

        // Act
        String error = Validator.validatePassword( "");

        // Assert
        assertEquals("Password skal udfyldes", error);
    }

    @Test
    void shouldRejectShortPassword() {
        // Arrange

        // Act
        String error = Validator.validatePassword("1234");

        // Assert
        assertEquals("Password skal være mindst 8 tegn", error);
    }

    @Test
    void shouldRejectPasswordWithoutNumbers() {
        // Arrange

        // Act
        String error = Validator.validatePassword("password");

        // Assert
        assertEquals("Password skal indeholde mindst ét tal", error);
    }

    @Test
    void shouldRejectPasswordWithoutSpecialCharacters() {
        // Arrange

        // Act
        String error = Validator.validatePassword("password123");

        // Assert
        assertEquals("Password skal indeholde mindst ét specialtegn", error);
    }

    @Test
    void shouldAcceptValidPassword() {
        // Arrange

        // Act
        String error = Validator.validatePassword( "Password123@");

        // Assert
        assertNull(error);
    }
}

import dk.ek.entities.Materials;
import dk.ek.persistence.MaterialMapper;
import dk.ek.services.CalculatorService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorServiceTest {
    @Test
    void calculateCladdingLengthwise() {
        // Arrange
        int carportLength = 540;

        // Act
        int claddingLength = CalculatorService.calculateLengthwiseCladding(carportLength);

        // Assert
        assertEquals(540, claddingLength);
    }

    @Test
    void calculateCladdingWidthwise() {
        // Arrange
        int carportWidth = 300;
        Materials cladding = new Materials(1, "Beklædning", "Stk", 10.95, "trykimp. Brædt", 1.5, 40.0);

        // Act
        int claddingLength = CalculatorService.calculateWidthwiseCladding(carportWidth, cladding);

        // Assert
        assertEquals(297, claddingLength);
    }

    @Test
    void calculateBeamLength() {
        // Arrange
        int carportLength = 540;
        Materials cladding = new Materials(1, "Beklædning", "Stk", 10.95, "trykimp. Brædt", 1.5, 40.0);

        // Act
        int beamLength = CalculatorService.calculateLengthwiseBeam(carportLength, cladding);

        // Assert
        assertEquals(537, beamLength);
    }

    @Test
    void calculatePostAmount() {
        // Arrange
        int carportLength = 540;
        boolean hasToolShed = false;

        // Act
        int postAmount = CalculatorService.calculateAmountOfPosts(carportLength, hasToolShed);

        // Assert
        assertEquals(6, postAmount);
    }

    @Test
    void calculateRaftersAmount() {
        // Arrange
        int carportLength = 540;
        Materials rafter = new Materials(3, "Spær", "Stk", 8.45, "spærtræ ubh.", 5.0, 20.0);

        // Act
        int rafterAmount = CalculatorService.calculateAmountOfRafters(carportLength, rafter);

        // Assert
        assertEquals(9, rafterAmount);
    }

    @Test
    void calculateRafterLength() {
        // Arrange
        int carportWidth = 300;
        Materials cladding = new Materials(1, "Beklædning", "Stk", 10.95, "trykimp. Brædt", 1.5, 40.0);

        // Act
        int rafterLength = CalculatorService.calculateRafterLength(carportWidth, cladding);

        // Assert
        assertEquals(297, rafterLength);
    }

    @Test
    void calculateRoofPlateLength() {
        // Arrange
        int carportLength = 540;
        Materials cladding = new Materials(1, "Beklædning", "Stk", 10.95, "trykimp. Brædt", 1.5, 40.0);

        // Act
        int roofPlateLength = CalculatorService.calculateRoofPlateLength(carportLength, cladding);

        // Assert
        assertEquals(537, roofPlateLength);
    }

    @Test
    void calculateRoofPlateAmount() {
        // Arrange
        int carportWidth = 300;
        Materials roofPlate = new Materials(5, "Plasttrapezplader", "Stk", 5.45, "Plastmo Ecolite Blåtonet", 120.0, 5.0);

        // Act
        int roofPlateAmount = CalculatorService.calculateAmountOfRoofPlates(carportWidth, roofPlate);

        // Assert
        assertEquals(3, roofPlateAmount);
    }

    @Test
    void calculateBoltAmount() {
        // Arrange
        int carportLength = 540;
        boolean hasToolShed = false;
        int postAmount = CalculatorService.calculateAmountOfPosts(carportLength, hasToolShed);

        // Act
        int boltAmount = CalculatorService.calculateAmountOfBolts(postAmount);

        // Assert
        assertEquals(24, boltAmount);
    }

    @Test
    void calculateBracketAmount() {
        // Arrange
        int carportLength = 540;
        boolean hasToolShed = false;
        int postAmount = CalculatorService.calculateAmountOfPosts(carportLength, hasToolShed);

        Materials rafter = new Materials(3, "Spær", "Stk", 8.45, "spærtræ ubh.", 5.0, 20.0);
        int rafterAmount = CalculatorService.calculateAmountOfRafters(carportLength, rafter);

        // Act
        int bracketAmount = CalculatorService.calculateAmountOfBrackets(postAmount, rafterAmount);

        // Assert
        assertEquals(48, bracketAmount);

    }
}

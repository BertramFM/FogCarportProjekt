package dk.ek.services;

import dk.ek.entities.Materials;
import dk.ek.entities.Order;
import dk.ek.entities.OrderMaterials;
import dk.ek.exceptions.DatabaseException;
import dk.ek.persistence.ConnectionPool;
import dk.ek.persistence.MaterialMapper;

import java.util.List;
import java.util.Objects;

public class MaterialCalculatorService {
    public static void calculateFlatCarportMaterialList(Order order, ConnectionPool connectionPool) throws DatabaseException {
        List<OrderMaterials> billOfMaterials = new java.util.ArrayList<>(List.of());

        List<Materials> materialsList = MaterialMapper.getAllMaterials(connectionPool);

        // Calculate cladding (beklædning)
        Materials cladding = materialsList.stream()
            .filter(material -> material.getName().equalsIgnoreCase("Beklædning"))
            .findFirst()
            .orElseThrow();

        int claddingLength = calculateLengthwiseCladding(order.getCarportLength());
        billOfMaterials.add(new OrderMaterials("For- og bag-beklædning", cladding.getMaterialWidth() + "x" + cladding.getMaterialHeight() + "cm " + cladding.getCategory(), claddingLength, 2, cladding.getUnitOfMeasurement(), "Yderbeklædning til længden af carporten"));

        int claddingWidth = calculateWidthwiseCladding(order.getCarportWidth(), cladding);
        billOfMaterials.add(new OrderMaterials("Side-beklædning", cladding.getMaterialWidth() + "x" + cladding.getMaterialHeight() + "cm " + cladding.getCategory(), claddingWidth, 2, cladding.getUnitOfMeasurement(), "Yderbeklædning til bredden af carporten"));

        // Calculate beams (rem)
        Materials beam = materialsList.stream()
            .filter(material -> material.getName().equalsIgnoreCase("Rem"))
            .findFirst()
            .orElseThrow();

        int beamLength = calculateLengthwiseBeam(order.getCarportLength(), cladding);
        billOfMaterials.add(new OrderMaterials("Rem", beam.getMaterialWidth() + "x" + beam.getMaterialHeight() + "cm " + beam.getCategory(), beamLength, 2, beam.getUnitOfMeasurement(), "Remme til længden af carporten"));

        // Calculate posts (stolper)
        Materials post = materialsList.stream()
            .filter(material -> material.getName().equalsIgnoreCase("Stolpe"))
            .findFirst()
            .orElseThrow();

        int postAmount = calculateAmountOfPosts(order.getCarportLength(), order.getHasToolShed());

        billOfMaterials.add(new OrderMaterials("Stolper", post.getMaterialWidth() + "x" + post.getMaterialHeight() + "cm " + post.getCategory(), 300, postAmount, post.getUnitOfMeasurement(), "Stolper til fundamentet af carport"));

        // Calculate rafters (spær)
        Materials rafter = materialsList.stream()
            .filter(material -> material.getName().equalsIgnoreCase("Spær"))
            .findFirst()
            .orElseThrow();

        int rafterAmount = calculateAmountOfRafters(order.getCarportLength(), rafter);
        int rafterLength = calculateRafterLength(order.getCarportWidth(), cladding);
        billOfMaterials.add(new OrderMaterials("Spær", rafter.getMaterialWidth() + "x" + rafter.getMaterialHeight() + "cm " + rafter.getCategory(), rafterLength, rafterAmount, rafter.getUnitOfMeasurement(), "Spær til montering på rem"));

        // Calculate roof plates (Plasttrapezplader)
        if (Objects.equals(order.getRoofMaterial(), "Plasttrapezplader")) {
            Materials roofPlate = materialsList.stream()
                .filter(material -> material.getName().equalsIgnoreCase("Plasttrapezplader"))
                .findFirst()
                .orElseThrow();

            int roofPlateAmount = calculateAmountOfRoofPlates(order.getCarportWidth(), roofPlate);
            int roofPlateLength = calculateRoofPlateLength(order.getCarportLength(), roofPlate);
            billOfMaterials.add(new OrderMaterials("Plasttrapezplader", roofPlate.getMaterialWidth() + "x" + roofPlate.getMaterialHeight() + "cm " + roofPlate.getCategory(), roofPlateLength, roofPlateAmount, roofPlate.getUnitOfMeasurement(), "Tagplader til montering på spær"));
        }

        // Static materials needed
        Materials roofScrews = materialsList.stream()
            .filter(material -> material.getName().equalsIgnoreCase("PlastmoBundskruer"))
            .findFirst()
            .orElseThrow();
        billOfMaterials.add(new OrderMaterials("Plastmo bundskruer", roofScrews.getCategory(), 0, 3, roofScrews.getUnitOfMeasurement(), "Skruer til tagplader"));

        Materials screws = materialsList.stream()
            .filter(material -> material.getName().equalsIgnoreCase("Skruer"))
            .findFirst()
            .orElseThrow();
        billOfMaterials.add(new OrderMaterials("Skruer", screws.getMaterialWidth() + "x" + screws.getMaterialHeight() + "mm. " + screws.getCategory(), 0, 4, screws.getUnitOfMeasurement(), "Skruer til beslag og spær"));

        Materials bolts = materialsList.stream()
            .filter(material -> material.getName().equalsIgnoreCase("Bræddebolt"))
            .findFirst()
            .orElseThrow();
        int amountOfBolts = calculateAmountOfBolts(postAmount);
        billOfMaterials.add(new OrderMaterials("Bræddebolt", bolts.getMaterialWidth() + "x" + bolts.getMaterialHeight() + "mm. " + bolts.getCategory(), 0, amountOfBolts, bolts.getUnitOfMeasurement(), "Bolte til montering af rem på stolper"));

        Materials angleBracket = materialsList.stream()
            .filter(material -> material.getName().equalsIgnoreCase("VinkelBeslag"))
            .findFirst()
            .orElseThrow();
        int amountOfBrackets = calculateAmountOfBrackets(postAmount, rafterAmount);
        billOfMaterials.add(new OrderMaterials("Vinkelbeslag", angleBracket.getCategory(), 0, amountOfBrackets, angleBracket.getUnitOfMeasurement(), "Til montering af rem på stolper og spær på rem"));


        for (OrderMaterials orderMaterial : billOfMaterials) {
            System.out.println(orderMaterial.getName() + " | " + orderMaterial.getMaterialDescription() + " | "+ orderMaterial.getLengthMeasurement() + " | " + orderMaterial.getAmount() + " | " + orderMaterial.getUnitOfAmount() + " | " + orderMaterial.getDescription());
        }
    }


    // === CALCULATIONS ===
    private static int calculateLengthwiseCladding(int carportLength) {
        return carportLength;
    }

    private static int calculateWidthwiseCladding(int carportWidth, Materials cladding) {
        return (int) (carportWidth - ((cladding.getMaterialWidth()) * 2));
    }

    private static int calculateLengthwiseBeam(int carportLength, Materials cladding) {
        return (int) (carportLength - ((cladding.getMaterialWidth()) * 2));
    }

    private static int calculateAmountOfPosts(int carportLength, boolean hasToolShed) {
        int totalAmountOfPosts;
        int postAmountLength;
        int postAmountWidth = 2;

        if (carportLength >= 390) {
            postAmountLength = 3;
        } else {
            postAmountLength = 2;
        }

        totalAmountOfPosts = postAmountLength * postAmountWidth;

        if (hasToolShed) {
            totalAmountOfPosts += postAmountWidth + 1;
        }

        return totalAmountOfPosts;
    }

    private static int calculateAmountOfRafters(int carportLength, Materials rafter) {
        int spaceBetweenRafters = 55;
        return (int) (carportLength / (spaceBetweenRafters + rafter.getMaterialWidth()));
    }

    private static int calculateRafterLength(int carportWidth, Materials cladding) {
        return (int) (carportWidth - ((cladding.getMaterialWidth()) * 2));
    }

    private static int calculateRoofPlateLength(int carportLength, Materials cladding) {
        return (int) (carportLength - ((cladding.getMaterialWidth()) * 2));
    }

    private static int calculateAmountOfRoofPlates(int carportWidth, Materials roofPlate) {
        return (int) Math.ceil((double) carportWidth / roofPlate.getMaterialWidth());
    }

    private static int calculateAmountOfBolts(int amountOfPosts) {
        return amountOfPosts * 4;
    }

    private static int calculateAmountOfBrackets(int amountOfPosts, int amountOfRafters) {
        return (amountOfPosts * 2) + (amountOfRafters * 4);
    }

}

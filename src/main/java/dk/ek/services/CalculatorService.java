package dk.ek.services;

import dk.ek.entities.Materials;

public class CalculatorService {
    public static int calculateLengthwiseCladding(int carportLength) {
        return carportLength;
    }

    public static int calculateWidthwiseCladding(int carportWidth, Materials cladding) {
        return (int) (carportWidth - ((cladding.getMaterialWidth()) * 2));
    }

    public static int calculateLengthwiseBeam(int carportLength, Materials cladding) {
        return (int) (carportLength - ((cladding.getMaterialWidth()) * 2));
    }

    public static int calculateAmountOfPosts(int carportLength, boolean hasToolShed) {
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

    public static int calculateAmountOfRafters(int carportLength, Materials rafter) {
        int spaceBetweenRafters = 55;
        return (int) (carportLength / (spaceBetweenRafters + rafter.getMaterialWidth()));
    }

    public static int calculateRafterLength(int carportWidth, Materials cladding) {
        return (int) (carportWidth - ((cladding.getMaterialWidth()) * 2));
    }

    public static int calculateRoofPlateLength(int carportLength, Materials cladding) {
        return (int) (carportLength - ((cladding.getMaterialWidth()) * 2));
    }

    public static int calculateAmountOfRoofPlates(int carportWidth, Materials roofPlate) {
        return (int) Math.ceil((double) carportWidth / roofPlate.getMaterialWidth());
    }

    public static int calculateAmountOfBolts(int amountOfPosts) {
        return amountOfPosts * 4;
    }

    public static int calculateAmountOfBrackets(int amountOfPosts, int amountOfRafters) {
        return (amountOfPosts * 2) + (amountOfRafters * 4);
    }
}

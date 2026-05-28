package dk.ek.services;

public class CarportSvg {

    // Mellemrum mellem pile og tegning
    private static final int MARGIN = 35;

    public static String generateCarportSvg(int width, int length) {

        int offSetY = 35;

        int spaerAntal = (int) Math.ceil((double) length / 55);
        double spaerMellemrum = (double) length / (spaerAntal);

        // Outer SVG — length er x-aksen, width er y-aksen
        int outerWidth  = length + MARGIN * 2 + 50;
        int outerHeight = width + MARGIN + 50;

        Svg outerSvg = new Svg(0, 0,
                String.format("0 0 %d %d", outerWidth, outerHeight),
                "700px", "700px", true
        );

        // Længde pil (x-akse)
        outerSvg.addArrow(
                MARGIN, outerHeight - MARGIN,
                MARGIN + length, outerHeight - MARGIN,
                "stroke:#000000"
        );
        outerSvg.addText(MARGIN + length / 2, outerHeight - 5, 0, length + " cm");

        // Bredde pil (lodret — y-akse)
        outerSvg.addArrow(
                20, MARGIN,
                20, MARGIN + width,
                "stroke:#000000"
        );
        outerSvg.addText(15, MARGIN + width / 2, -90, width + " cm");

        // Inner SVG
        Svg innerSvg = new Svg(
                MARGIN, MARGIN,
                String.format("0 0 %d %d", length, width),
                length, width, false
        );

        // Ramme / Beklædning
        innerSvg.addRectangle(0, 0, length, width,
                "stroke:#000000; fill:#ffffff; stroke-width:1");

        // Rem — langs top og bund langs x-akse, 40 cm fra fra top og bund
        innerSvg.addRectangle(0, offSetY, length, 4.5,
                "stroke:#000000; fill:#ffffff; stroke-width:1");
        // width - MARGIN - 4, y-akse kan ikke være en double burde have været -4.5
        innerSvg.addRectangle(0, width - offSetY - 4, length, 4.5,
                "stroke:#000000; fill:#ffffff; stroke-width:1");

        // Spær — lodrette linjer langs bredden (y-aksen)
        for (int i = 1; i < spaerAntal; i++) {
            int x = (int) Math.round(i  * spaerMellemrum);
            innerSvg.addRectangle(x, 0, 4.5, width,
                    "stroke:#000000; fill:#ffffff; stroke-width:1");
        }

        // Stolper
        int postSize = 10;
        // Bottom left
        innerSvg.addRectangle((int) spaerMellemrum *2 - 2, width - offSetY - 6, postSize, postSize, "stroke:#000000; fill:#000000; stroke-width:1");
        // Top left
        innerSvg.addRectangle((int) spaerMellemrum *2 - 2, offSetY - 2, postSize, postSize, "stroke:#000000; fill:#000000; stroke-width:1");
        // Bottom right
        innerSvg.addRectangle( length - (int) spaerMellemrum - 4, width - offSetY - 6, postSize, postSize, "stroke:#000000; fill:#000000; stroke-width:1");
        // Top right
        innerSvg.addRectangle(length - (int) spaerMellemrum - 4,offSetY - 2, postSize, postSize, "stroke:#000000; fill:#000000; stroke-width:1");

        innerSvg.close();
        outerSvg.addSvg(innerSvg);
        outerSvg.close();

        return outerSvg.toString();
    }
}
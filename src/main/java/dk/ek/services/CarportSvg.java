package dk.ek.services;

import dk.ek.entities.Order;
import dk.ek.exceptions.DatabaseException;
import dk.ek.persistence.ConnectionPool;

public class CarportSvg {


    public static String generateCarportSvg(Order order, ConnectionPool connectionPool) throws DatabaseException {
        int width = order.getCarportWidth();
        int length = order.getCarportLength();
        double rafterWidth = 4.5;
        double rafterSpace = 55.0;

        // Mellemrum mellem pile og tegning
        int spacing = 35;


        int offSetY = 35;
        int arrowOffsetY = 30;

        int rafterAmount = MaterialCalculatorService.getRafterAmount(order, connectionPool);
        int amountOfGaps = rafterAmount + 1;
        double gap = (length - rafterAmount * rafterWidth) / amountOfGaps;

        // Outer SVG — length er x-aksen, width er y-aksen
        int outerWidth = length + spacing * 2 + 50;
        int outerHeight = width + spacing + 50 + 30;

        Svg outerSvg = new Svg(0, 0,
                String.format("0 0 %d %d", outerWidth, outerHeight),
                "700px", "700px", true
        );

        // length qrrow (x-akse)
        outerSvg.addArrow(
                spacing, outerHeight - spacing,
                spacing + length, outerHeight - spacing,
                "stroke:#000000"
        );
        outerSvg.addText(spacing + length / 2, outerHeight - 5, 0, length + " cm");

        // Width arrow (y-akse)
        outerSvg.addArrow(
                20, spacing + arrowOffsetY,
                20, spacing + width + arrowOffsetY,
                "stroke:#000000"
        );
        outerSvg.addText(15, outerHeight /2, 270, length + " cm");

        // top arrow (spacing)
        int arrowY = 30;
        outerSvg.addArrow(spacing, arrowY, (int) Math.round(gap) + spacing, arrowY, "stroke:#000000");
        outerSvg.addText((int) Math.round(gap / 2) + spacing, arrowY - 5, 0, (int) gap + " cm");

        for (int i = 0; i < rafterAmount - 1; i++) {
            int x1 = (int) Math.round((i + 1) * gap + i * rafterWidth + rafterWidth) + spacing;
            int x2 = (int) Math.round((i + 2) * gap + (i + 1) * rafterWidth) + spacing;
            outerSvg.addArrow(x1, arrowY, x2, arrowY, "stroke:#000000");
            outerSvg.addText((x1 + x2) / 2, arrowY - 5, 0, (int) gap + " cm");
        }

        int lastX1 = (int) Math.round(rafterAmount * gap + (rafterAmount - 1) * rafterWidth + rafterWidth) + spacing;
        int lastX2 = length + spacing;

        outerSvg.addArrow(lastX1, arrowY, lastX2, arrowY, "stroke:#000000");
        outerSvg.addText((lastX1 + lastX2) / 2, arrowY - 5, 0, (int) gap + " cm");

        // Inner SVG
        Svg innerSvg = new Svg(spacing, spacing + 30, String.format("0 0 %d %d", length, width),
                length, width, false
        );

        // Beklædning
        innerSvg.addRectangle(0, 0, length, width,
                "stroke:#000000; fill:#ffffff; stroke-width:1");

        // Rem — langs top og bund langs x-akse, 40 cm fra fra top og bund
        innerSvg.addRectangle(0, offSetY, length, 4.5,
                "stroke:#000000; fill:#ffffff; stroke-width:1");

        // width - spacing - 4, y-akse kan ikke være en double burde have været -4.5
        innerSvg.addRectangle(0, width - offSetY - 4, length, 4.5,
                "stroke:#000000; fill:#ffffff; stroke-width:1");

        // Spær — lodrette linjer langs bredden (y-aksen)
        for (int i = 0; i < rafterAmount; i++) {
            int x = (int) Math.round((i + 1) * gap + i * rafterWidth);
            innerSvg.addRectangle(x, 0, rafterWidth, width,
                    "stroke:#000000; fill:#ffffff; stroke-width:1");
        }

        // Stolper
        int postSize = 10;
        // Bottom left
        innerSvg.addRectangle((int) (gap * 1.5), width - offSetY - 6, postSize, postSize, "stroke:#000000; fill:#000000; stroke-width:1");
        // Top left
        innerSvg.addRectangle((int) (gap * 1.5), offSetY - 2, postSize, postSize, "stroke:#000000; fill:#000000; stroke-width:1");
        // Bottom right
        innerSvg.addRectangle(length - (int) gap - 7, width - offSetY - 6, postSize, postSize, "stroke:#000000; fill:#000000; stroke-width:1");
        // Top right
        innerSvg.addRectangle(length - (int) gap - 7, offSetY - 2, postSize, postSize, "stroke:#000000; fill:#000000; stroke-width:1");

        innerSvg.close();
        outerSvg.addSvg(innerSvg);
        outerSvg.close();

        return outerSvg.toString();
    }
}
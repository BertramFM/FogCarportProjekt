package dk.ek.services;

import dk.ek.entities.Order;
import dk.ek.exceptions.DatabaseException;
import dk.ek.persistence.ConnectionPool;

public class CarportSvg {


    public static String generateCarportSvg(Order order, ConnectionPool connectionPool) throws DatabaseException {
        int margin = 35;
        int leftMargin = 60;
        int width = order.getCarportWidth();
        int length = order.getCarportLength();
        double rafterWidth = 4.5;
        double rafterSpace = 55.0;

        // Mellemrum mellem pile og tegning


        int offSetY = 35;
        int arrowOffsetY = 30;

        int rafterAmount = MaterialCalculatorService.getRafterAmount(order, connectionPool);
        int amountOfGaps = rafterAmount + 1;
        double gap = (length - rafterAmount * rafterWidth) / amountOfGaps;

        // Outer SVG — length er x-aksen, width er y-aksen
        int outerWidth = length + leftMargin + margin * 2 + 50;
        int outerHeight = width + margin + 50 + 30;

        Svg arrowSvg = new Svg(0, 0,
                String.format("0 0 %d %d", outerWidth, outerHeight),
                "700px", "700px", true
        );

        // length qrrow (x-akse)
        arrowSvg.addArrow(leftMargin, outerHeight - margin, leftMargin + length, outerHeight - margin, "stroke:#000000");
        arrowSvg.addText(leftMargin + length / 2, outerHeight - 5, 0, length + " cm");

        // Width arrow (y-akse)
        arrowSvg.addArrow(25, margin + arrowOffsetY, 25, margin + width + arrowOffsetY, "stroke:#000000");
        arrowSvg.addText(20, outerHeight /2, 270, width + " cm");

        // rem spacing
        int innerWidth = width - (offSetY*2);
        int remArrowX = leftMargin - 10;
        arrowSvg.addArrow(remArrowX, margin + arrowOffsetY + offSetY, remArrowX, margin + arrowOffsetY + offSetY + innerWidth, "stroke:#000000");
        arrowSvg.addText(remArrowX - 5, margin + arrowOffsetY + offSetY + innerWidth / 2, -90, innerWidth + " cm");

        // top arrow (spacing)
        int arrowY = 30;
        arrowSvg.addArrow(leftMargin, arrowY, (int) Math.round(gap) + leftMargin, arrowY, "stroke:#000000");
        arrowSvg.addText((int) Math.round(gap / 2) + leftMargin, arrowY - 5, 0, (int) gap + " cm");
        for (int i = 0; i < rafterAmount; i++) {
            int x1 = (int) Math.round((i + 1) * gap + i * rafterWidth + rafterWidth) + leftMargin;
            int x2 = (int) Math.round((i + 2) * gap + (i + 1) * rafterWidth) + leftMargin;
            arrowSvg.addArrow(x1, arrowY, x2, arrowY, "stroke:#000000");
            arrowSvg.addText((x1 + x2) / 2, arrowY - 5, 0, (int) gap + " cm");
        }

        // Inner SVG
        Svg mainSvg = new Svg(leftMargin, margin + 30, String.format("0 0 %d %d", length, width),
                length, width, false
        );

        // Beklædning
        mainSvg.addRectangle(0, 0, length, width,
                "stroke:#000000; fill:#ffffff; stroke-width:1");

        // Rem — langs top og bund langs x-akse, 40 cm fra fra top og bund
        mainSvg.addRectangle(0, offSetY, length, 4.5,
                "stroke:#000000; fill:#ffffff; stroke-width:1");

        // width - spacing - 4, y-akse kan ikke være en double burde have været -4.5
        mainSvg.addRectangle(0, width - offSetY - 4, length, 4.5,
                "stroke:#000000; fill:#ffffff; stroke-width:1");

        // Spær — lodrette linjer langs bredden (y-aksen)
        for (int i = 0; i < rafterAmount; i++) {
            int x = (int) Math.round((i + 1) * gap + i * rafterWidth);
            mainSvg.addRectangle(x, 0, rafterWidth, width,
                    "stroke:#000000; fill:#ffffff; stroke-width:1");
        }

        // Stolper
        int postSize = 10;
        // Bottom left
        mainSvg.addRectangle((int) (gap * 1.5), width - offSetY - 6, postSize, postSize, "stroke:#000000; fill:#000000; stroke-width:1");
        // Top left
        mainSvg.addRectangle((int) (gap * 1.5), offSetY - 2, postSize, postSize, "stroke:#000000; fill:#000000; stroke-width:1");
        // Bottom right
        mainSvg.addRectangle(length - (int) gap - 7, width - offSetY - 6, postSize, postSize, "stroke:#000000; fill:#000000; stroke-width:1");
        // Top right
        mainSvg.addRectangle(length - (int) gap - 7, offSetY - 2, postSize, postSize, "stroke:#000000; fill:#000000; stroke-width:1");

        mainSvg.close();
        arrowSvg.addSvg(mainSvg);
        arrowSvg.close();

        return arrowSvg.toString();
    }
}
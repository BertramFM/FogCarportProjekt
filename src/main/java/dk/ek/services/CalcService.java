package dk.ek.services;

import dk.ek.entities.BOMLine;

import java.util.ArrayList;
import java.util.List;

public class CalcService {

    // Hvor mange cm per pixel i SVG
    private static final double SCALE = 0.1;

//    public static List<BOMLine> calcMaterials(int width, int length) {
//        List<BOMLine> bom = new ArrayList<>();
//
//        // 4 stolper altid
//        // Stolperne er 97x97 mm og nedgraves 90 cm
//        int stolperAntal = 4;
//        bom.add(new BOMLine("97x97 mm. trykimp. Stolpe", stolperAntal, "stk", 300, "Stolper nedgraves 90 cm i jord"));
//
//        // --- REMME ---
//        // 2 remme, én per side, løber langs hele længden
//        bom.add(new BOMLine("45x195 mm. spærtræ ubh. (rem)", 2, "stk", length, "Remme monteres på stolper"));
//
//        // --- SPÆR ---
//        // Max 55 cm mellem hvert spær
//        // Overhæng: 90% foran (bil siden), 10% bagved
//        int spaerMellemrum = 55;
//        int spaerAntal = (int) Math.ceil((double) length / spaerMellemrum) + 1;
//        int spaerLaengde = width + 10; // +10 cm til udhæng på siderne
//        bom.add(new BOMLine("45x195 mm. spærtræ ubh. (spær)", spaerAntal, "stk", spaerLaengde, "Spær monteres på rem, max 55 cm mellemrum"));
//
//        // --- UNDERSTERN ---
//        bom.add(new BOMLine("25x200 mm. trykimp. Brædt (understern forende)", 2, "stk", width, "Understern forende"));
//        bom.add(new BOMLine("25x200 mm. trykimp. Brædt (understern bagende)", 2, "stk", width, "Understern bagende"));
//        bom.add(new BOMLine("25x200 mm. trykimp. Brædt (understern sider)", 2, "stk", length, "Understern sider"));
//
//        // --- OVERSTERN ---
//        bom.add(new BOMLine("25x125 mm. trykimp. Brædt (overstern forende)", 2, "stk", width, "Overstern forende"));
//        bom.add(new BOMLine("25x125 mm. trykimp. Brædt (overstern sider)", 2, "stk", length, "Overstern sider"));
//
//        // --- VANDBRÆDT ---
//        bom.add(new BOMLine("19x100 mm. trykimp. Brædt (vandbrædt sider)", 2, "stk", length, "Vandbrædt på stern i sider"));
//        bom.add(new BOMLine("19x100 mm. trykimp. Brædt (vandbrædt forende)", 1, "stk", width, "Vandbrædt på stern i forende"));
//
//        // --- TAGPLADER ---
//        // Plastmo Ecolite dækker 109 cm med overlap på 20 cm = 89 cm nettodækning
//        int tagpladerAntal = (int) Math.ceil((double) width / 89) + 1;
//        bom.add(new BOMLine("Plastmo Ecolite blåtonet", tagpladerAntal, "stk", length + 50, "Tagplader monteres på spær"));
//
//        // --- BESLAG ---
//        bom.add(new BOMLine("Universal 190 mm højre", spaerAntal, "stk", 0, "Til montering af spær på rem"));
//        bom.add(new BOMLine("Universal 190 mm venstre", spaerAntal, "stk", 0, "Til montering af spær på rem"));
//        bom.add(new BOMLine("Hulbånd 1x20 mm. 10 mtr.", 2, "rulle", 0, "Til vindkryds på spær"));
//        bom.add(new BOMLine("Bræddebolt 10x120 mm.", stolperAntal * 2, "stk", 0, "Til montering af rem på stolper"));
//        bom.add(new BOMLine("Firkantskiver 40x40x11 mm.", stolperAntal, "stk", 0, "Til montering af rem på stolper"));
//
//        // --- SKRUER ---
//        bom.add(new BOMLine("Plastmo bundskruer 200 stk.", (int) Math.ceil(tagpladerAntal / 2.0), "pakke", 0, "Skruer til tagplader"));
//        bom.add(new BOMLine("4,5x60 mm. skruer 200 stk.", 1, "pakke", 0, "Til montering af stern og vandbrædt"));
//        bom.add(new BOMLine("4,0x50 mm. beslagskruer 250 stk.", (int) Math.ceil(spaerAntal / 4.0), "pakke", 0, "Til montering af universalbeslag og hulbånd"));
//
//        return bom;
//    }

    public static String generateSVG(int width, int length) {
        int svgWidth  = (int) (width  * SCALE * 10) + 100;
        int svgHeight = (int) (length * SCALE * 10) + 100;
        int marginX   = 50;
        int marginY   = 50;

        int w = (int) (width  * SCALE * 10);
        int l = (int) (length * SCALE * 10);

        // mulig legacy kode
        int offsetX = (int) (30 * SCALE * 10);
        int offsetY = (int) (55 * SCALE * 10);

        int spaerMellemrum = 55;
        int spaerAntal     = (int) Math.ceil((double) length / spaerMellemrum) + 1;

        StringBuilder svg = new StringBuilder();
        svg.append(String.format("<svg width=\"%d\" height=\"%d\" xmlns=\"http://www.w3.org/2000/svg\">\n", svgWidth + 50, svgHeight + 50));

        // Spær langs y-akse
        svg.append("  <!-- Spær -->\n");
        for (int i = 0; i < spaerAntal; i++) {
            int y = marginY + (int) ((double) i / (spaerAntal - 1) * l);
            svg.append(String.format(
                    "  <line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"stroke:#8B4513; stroke-width:3\"/>\n",
                    marginX, y, marginX + w, y
            ));
        }

        // Remme - langs x-akse
        svg.append("  <!-- Remme -->\n");
        svg.append(String.format(
                "  <line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"stroke:#8B4513; stroke-width:5\"/>\n",
                marginX + offsetX, marginY, marginX + offsetX, marginY + l  // venstre rem
        ));
        svg.append(String.format(
                "  <line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"stroke:#8B4513; stroke-width:5\"/>\n",
                marginX + w - offsetX, marginY, marginX + w - offsetX, marginY + l  // højre rem
        ));

        // Stolper
        svg.append("  <!-- Stolper -->\n");
        int stolpeSize = 8;
        int[][] stolper = {
                {marginX + offsetX,     marginY + offsetY},          // venstre forende
                {marginX + w - offsetX, marginY + offsetY},          // højre forende
                {marginX + offsetX,     marginY + l - offsetY},      // venstre bagende
                {marginX + w - offsetX, marginY + l - offsetY}       // højre bagende
        };

        for (int[] s : stolper) {
            svg.append(String.format(
                    "  <rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" style=\"fill:#4a4a4a; stroke:#000; stroke-width:1\"/>\n",
                    s[0] - stolpeSize / 2, s[1] - stolpeSize / 2, stolpeSize, stolpeSize
            ));
        }

        // Mål / pil tekst (f.eks 480 cm)
        svg.append(String.format(
                "  <text x=\"%d\" y=\"%d\" text-anchor=\"middle\" font-size=\"12\" fill=\"#000\">%d cm</text>\n",
                marginX + w / 2, marginY - 10, width
        ));
        svg.append(String.format(
                "  <text x=\"%d\" y=\"%d\" text-anchor=\"middle\" font-size=\"12\" fill=\"#000\" transform=\"rotate(-90, %d, %d)\">%d cm</text>\n",
                marginX - 20, marginY + l / 2, marginX - 20, marginY + l / 2, length
        ));

        svg.append("</svg>");
        return svg.toString();
    }
}
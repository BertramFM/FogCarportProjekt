package dk.ek.services;

public class Svg {

    private static final String ARROW_DEFS = """
        <defs>
            <marker id="beginArrow" markerWidth="12" markerHeight="12" refX="0" refY="6" orient="auto">
                <path d="M0,6 L12,0 L12,12 L0,6" style="fill: #000000;" />
            </marker>
            <marker id="endArrow" markerWidth="12" markerHeight="12" refX="12" refY="6" orient="auto">
                <path d="M0,0 L12,6 L0,12 L0,0" style="fill: #000000;" />
            </marker>
        </defs>
        """;

    private StringBuilder svg = new StringBuilder();

    public Svg(int x, int y, String viewBox, String width, String height, boolean arrowDefs) {
        svg.append(String.format(
                "<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" " +
                        "x=\"%d\" y=\"%d\" width=\"%s\" height=\"%s\" viewBox=\"%s\" preserveAspectRatio=\"xMinYMin meet\">\n",
                x, y, width, height, viewBox
        ));
        if (arrowDefs) {
            svg.append(ARROW_DEFS);
        }
    }

    public Svg(int x, int y, String viewBox, int width, int height, boolean arrowDefs) {
        svg.append(String.format(
                "<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" " +
                        "x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" viewBox=\"%s\" preserveAspectRatio=\"xMinYMin meet\">\n",
                x, y, width, height, viewBox
        ));
        if (arrowDefs) {
            svg.append(ARROW_DEFS);
        }
    }

    public void addRectangle(int x, int y, double width, double height, String style) {
        svg.append(String.format(
                "  <rect x=\"%d\" y=\"%d\" width=\"%.1f\" height=\"%.1f\" style=\"%s\"/>\n",
                x, y, width, height, style
        ));
    }

    public void addLine(int x1, int y1, int x2, int y2, String style) {
        svg.append(String.format(
                "  <line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"%s\"/>\n",
                x1, y1, x2, y2, style
        ));
    }

    public void addArrow(int x1, int y1, int x2, int y2, String style) {
        svg.append(String.format(
                "  <line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"%s; " +
                        "marker-start: url(#beginArrow); marker-end: url(#endArrow);\"/>\n",
                x1, y1, x2, y2, style
        ));
    }

    public void addText(int x, int y, int rotation, String text) {
        if (rotation != 0) {
            svg.append(String.format(
                    "  <text style=\"text-anchor: middle\" transform=\"translate(%d,%d) rotate(%d)\">%s</text>\n",
                    x, y, rotation, text
            ));
        } else {
            svg.append(String.format(
                    "  <text style=\"text-anchor: middle\" x=\"%d\" y=\"%d\">%s</text>\n",
                    x, y, text
            ));
        }
    }

    public void addSvg(Svg innerSvg) {
        svg.append(innerSvg.toString());
    }

    public void close() {
        svg.append("</svg>\n");
    }

    @Override
    public String toString() {
        return svg.toString();
    }
}
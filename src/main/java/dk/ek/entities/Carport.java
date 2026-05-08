package dk.ek.entities;

public class Carport {
    private int id;
    private RoofType roofType;
    private int length;
    private int width;

    public Carport(int id, RoofType roofType, int length, int width) {
        this.id = id;
        this.roofType = roofType;
        this.length = length;
        this.width = width;
    }

    public Carport(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoofType getRoofType() {
        return roofType;
    }

    public void setRoofType(RoofType roofType) {
        this.roofType = roofType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}

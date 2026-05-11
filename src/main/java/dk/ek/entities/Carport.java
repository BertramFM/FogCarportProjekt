package dk.ek.entities;

import java.util.ArrayList;
import java.util.List;

public class Carport {
    private int id;
    private RoofType roofType;
    private int length;
    private int width;
    private List<Material> carportMaterial = new ArrayList<>();
    private List<Material> roofMaterial = new ArrayList<>();

    public Carport(int id, RoofType roofType, int length, int width) {
        this.id = id;
        this.roofType = roofType;
        this.length = length;
        this.width = width;
    }

    public Carport(){}

    public List<Material> getCarportMaterial() {
        return carportMaterial;
    }

    public void setCarportMaterial(List<Material> carportMaterial) {
        this.carportMaterial = carportMaterial;
    }

    public List<Material> getRoofMaterial() {
        return roofMaterial;
    }

    public void setRoofMaterial(List<Material> roofMaterial) {
        this.roofMaterial = roofMaterial;
    }

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

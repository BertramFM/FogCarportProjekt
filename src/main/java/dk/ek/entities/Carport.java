package dk.ek.entities;

import java.util.ArrayList;
import java.util.List;

public class Carport {
    private int id;
    private RoofType roofType;
    private int length;
    private int width;
    private int roofAngle;
    private List<Materials> carportMaterials = new ArrayList<>();
    private List<Materials> roofMaterials = new ArrayList<>();

    public Carport(int id, RoofType roofType, int length, int width) {
        this.id = id;
        this.roofType = roofType;
        this.length = length;
        this.width = width;
    }

    public Carport(){}

    public List<Materials> getCarportMaterial() {
        return carportMaterials;
    }

    public void setCarportMaterial(List<Materials> carportMaterials) {
        this.carportMaterials = carportMaterials;
    }

    public List<Materials> getRoofMaterial() {
        return roofMaterials;
    }

    public void setRoofMaterial(List<Materials> roofMaterials) {
        this.roofMaterials = roofMaterials;
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

    public int getRoofAngle() {
        return roofAngle;
    }

    public void setRoofAngle(int roofAngle) {
        this.roofAngle = roofAngle;
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

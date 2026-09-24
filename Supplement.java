
public class Supplement extends OTCMedicine {

    private String supplementType;

    public Supplement(String medicineId, String name, String manufacturer,
                      double price, int stockQuantity, String expiryDate, String supplementType) {
        super(medicineId, name, manufacturer, price, stockQuantity, expiryDate);
        this.supplementType = supplementType;
    }

    public String getSupplementType() { return supplementType; }

    public void displayInfo() {
        System.out.println(this + " | Prescription Required: No | Type: " + supplementType);
    }
}


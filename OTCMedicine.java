public class OTCMedicine extends Medicine {
    private final boolean requiresPrescription=false;

    public  OTCMedicine(String medicineId, String name, String manufacturer,
                      double price, int stockQuantity, String expiryDate) {
        super(medicineId, name, manufacturer, price, stockQuantity, expiryDate);
    }

    // Overloaded constructor #2: with minimumAge
    public OTCMedicine(String medicineId, String name, String manufacturer,
                       double price, int stockQuantity, String expiryDate, int minimumAge) {
        super(medicineId, name, manufacturer, price, stockQuantity, expiryDate, minimumAge);
    }
    public boolean isRequiresPrescription() { return requiresPrescription; }

    public void displayInfo() {
        System.out.println(this + " | Prescription Required: No");
    }
}


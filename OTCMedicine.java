public class OTCMedicine extends Medicine {

    private final boolean requiresPrescription = false;

    // Overloaded constructor #1: no age restriction
    public OTCMedicine(String medicineId, String name, String manufacturer,
                       double price, int stockQuantity, String expiryDate) {
        this(medicineId, name, manufacturer, price, stockQuantity, expiryDate, 0);
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

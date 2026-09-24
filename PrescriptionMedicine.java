public class PrescriptionMedicine extends Medicine implements Trackable {

    private final boolean requiresPrescription = true;
    private String dosageInstruction;

    public PrescriptionMedicine(String medicineId, String name, String manufacturer,
                                double price, int stockQuantity, String expiryDate,
                                int minimumAge, String dosageInstruction) {
        super(medicineId, name, manufacturer, price, stockQuantity, expiryDate, minimumAge);
        this.dosageInstruction = dosageInstruction;
    }

    public boolean isRequiresPrescription() { return requiresPrescription; }
    public String getDosageInstruction() { return dosageInstruction; }

    public boolean checkPrescriptionRequirement() {
        return true;
    }
    public void displayInfo() {
        System.out.println(this + " | Prescription Required: Yes | Dosage: " + dosageInstruction);
    }
    public String getTrackingId() {
        return "RX-" + getMedicineId();
    }

    public String getTrackingId() {
        return "RX-" + getMedicineId();
    }

    public void sendNotification(Customer customer, String message) {
        customer.receiveNotification(message);
    }
    public Customer(String customerId, String name, String phone) {
        this(customerId, name, phone, 18);
    }

}

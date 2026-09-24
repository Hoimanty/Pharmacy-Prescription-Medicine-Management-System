public class ColdMedicine extends OTCMedicine {

    private boolean isDrowsy;

    public ColdMedicine(String medicineId, String name, String manufacturer,
                        double price, int stockQuantity, String expiryDate,
                        int minimumAge, boolean isDrowsy) {
        super(medicineId, name, manufacturer, price, stockQuantity, expiryDate, minimumAge);
        this.isDrowsy = isDrowsy;
    }

    public boolean isDrowsy() { return isDrowsy; }

    public void displayInfo() {
        System.out.println(this + " | Prescription Required: No"
                + (isDrowsy ? " | WARNING: May cause drowsiness" : " | Non-drowsy formula")
                + " | Minimum Age: " + getMinimumAge());
    }
}


public class ControlledMedicine extends PrescriptionMedicine{
    private String controlledSubstanceSchedule;
    private int maxRefillsAllowed;

    public ControlledMedicine(String medicineId, String name, String manufacturer,
                              double price, int stockQuantity, String expiryDate,
                              int minimumAge, String dosageInstruction,
                              String controlledSubstanceSchedule, int maxRefillsAllowed) {
        super(medicineId, name, manufacturer, price, stockQuantity, expiryDate, minimumAge, dosageInstruction);
        this.controlledSubstanceSchedule = controlledSubstanceSchedule;
        this.maxRefillsAllowed = maxRefillsAllowed;
    }
    public String getControlledSubstanceSchedule() { return controlledSubstanceSchedule; }
    public int getMaxRefillsAllowed() { return maxRefillsAllowed; }

    public boolean isRefillAllowed(int refillsUsed) {
        return refillsUsed < maxRefillsAllowed;
    }
    public void displayInfo() {
        System.out.println(this + " | Prescription Required: Yes | Dosage: " + getDosageInstruction()
                + " | Schedule: " + controlledSubstanceSchedule + " | Max Refills: " + maxRefillsAllowed);
    }

}

public class OralAntibiotic extends Antibiotic {

    private String form;

    public OralAntibiotic(String medicineId, String name, String manufacturer,
                          double price, int stockQuantity, String expiryDate,
                          int minimumAge, String dosageInstruction,
                          String antibioticClass, int courseDurationDays, String form) {
        super(medicineId, name, manufacturer, price, stockQuantity, expiryDate, minimumAge,
                dosageInstruction, antibioticClass, courseDurationDays);
        this.form = form;
    }

    public String getForm() { return form; }


    public void displayInfo() {
        System.out.println(this + " | Prescription Required: Yes | Dosage: " + getDosageInstruction()
                + " | Class: " + getAntibioticClass() + " | Course: " + getCourseDurationDays()
                + " days | Form: " + form + " (take with food)");
    }
}

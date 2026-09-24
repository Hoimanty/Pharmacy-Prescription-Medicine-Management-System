public class Antibiotic extends PrescriptionMedicine{
    private String antibioticClass;
    private int courseDurationDays;
    public Antibiotic(String medicineId, String name, String manufacturer,
                      double price, int stockQuantity, String expiryDate,
                      int minimumAge, String dosageInstruction,
                      String antibioticClass, int courseDurationDays) {
        super(medicineId, name, manufacturer, price, stockQuantity, expiryDate, minimumAge, dosageInstruction);
        this.antibioticClass = antibioticClass;
        this.courseDurationDays = courseDurationDays;
    }
    public String getAntibioticClass() { return antibioticClass; }
    public int getCourseDurationDays() { return courseDurationDays; }
    public void checkCourseCompletion(int daysTaken) {
        if (daysTaken < courseDurationDays) {
            System.out.println("Warning: " + getName() + " course stopped early (" + daysTaken
                    + "/" + courseDurationDays + " days). Stopping antibiotics early can cause resistance.");
        } else {
            System.out.println(getName() + " course completed (" + daysTaken + "/" + courseDurationDays + " days).");
        }
    }
    public void displayInfo() {
        System.out.println(this + " | Prescription Required: Yes | Dosage: " + getDosageInstruction()
                + " | Class: " + antibioticClass + " | Course: " + courseDurationDays + " days");
    }


}

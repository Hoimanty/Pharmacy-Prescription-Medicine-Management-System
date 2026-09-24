public class InjectableAntibiotic extends Antibiotic{
    private String injectionRoute;
    private boolean requiresRefrigeration;

    public InjectableAntibiotic(String medicineId, String name, String manufacturer,
                                double price, int stockQuantity, String expiryDate,
                                int minimumAge, String dosageInstruction,
                                String antibioticClass, int courseDurationDays,
                                String injectionRoute, boolean requiresRefrigeration) {
        super(medicineId, name, manufacturer, price, stockQuantity, expiryDate, minimumAge,
                dosageInstruction, antibioticClass, courseDurationDays);
        this.injectionRoute = injectionRoute;
        this.requiresRefrigeration = requiresRefrigeration;
    }

    public String getInjectionRoute() { return injectionRoute; }
    public boolean isRequiresRefrigeration() { return requiresRefrigeration; }
    public void displayInfo() {
        System.out.println(this + " | Prescription Required: Yes | Dosage: " + getDosageInstruction()
                + " | Class: " + getAntibioticClass() + " | Course: " + getCourseDurationDays()
                + " days | Route: " + injectionRoute
                + " | Refrigeration required: " + (requiresRefrigeration ? "Yes" : "No"));
    }



}

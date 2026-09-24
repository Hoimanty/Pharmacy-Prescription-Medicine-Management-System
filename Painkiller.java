public class Painkiller extends OTCMedicine{
    private String activeIngredient; // Paracetamol / Ibuprofen
    private int maxDailyDosageMg;

    public Painkiller(String medicineId, String name, String manufacturer,
                      double price, int stockQuantity, String expiryDate,
                      String activeIngredient, int maxDailyDosageMg) {
        super(medicineId, name, manufacturer, price, stockQuantity, expiryDate);
        this.activeIngredient = activeIngredient;
        this.maxDailyDosageMg = maxDailyDosageMg;
    }
    public String getActiveIngredient() { return activeIngredient; }
    public int getMaxDailyDosageMg() { return maxDailyDosageMg; }
    public void displayInfo() {
        System.out.println(this + " | Prescription Required: No | Ingredient: " + activeIngredient
                + " | Max Daily Dosage: " + maxDailyDosageMg + " mg");
    }
}

public abstract class Medicine {

    private String medicineId;
    private String name;
    private String manufacturer;
    private double price;
    private int stockQuantity;
    private String expiryDate;
    private int minimumAge;
    public Medicine(String medicineId, String name, String manufacturer,
                    double price, int stockQuantity, String expiryDate) {
        this(medicineId, name, manufacturer, price, stockQuantity, expiryDate, 0);
    }
    public Medicine(String medicineId, String name, String manufacturer,
                    double price, int stockQuantity, String expiryDate, int minimumAge) {
        this.medicineId = medicineId;
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.expiryDate = expiryDate;
        this.minimumAge = minimumAge;
    }
    public abstract void displayInfo();

    public boolean isAvailable(int qty) {
        return stockQuantity >= qty;
    }

    public void updateStock(int qty) {
        stockQuantity -= qty;
    }

    public boolean isAgeEligible(int customerAge) {
        return customerAge >= minimumAge;
    }
    public String getMedicineId() { return medicineId; }
    public String getName() { return name; }
    public String getManufacturer() { return manufacturer; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
    public String getExpiryDate() { return expiryDate; }
    public int getMinimumAge() { return minimumAge; }

    public void setPrice(double price) { this.price = price; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }
    public void setMinimumAge(int minimumAge) { this.minimumAge = minimumAge; }

    public String toString() {
        return String.format("%s (%s) - %s | Stock: %d | Price: %.2f | Expiry: %s",
                name, medicineId, manufacturer, stockQuantity, price, expiryDate);
    }
}
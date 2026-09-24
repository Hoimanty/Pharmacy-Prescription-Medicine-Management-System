public abstract class Medicine {
    private String medicineId;
    private String name;
    private String manufacturer;
    private double price;
    private int stockQuantity;
    private String expiryDate;
    private int minimumAge;
    public Medicine(String med, String n, String man,
                    double p, int s, String exp) {
        medicineId=med;
        name=n;
        manufacturer=man;
        price=p;
        stockQuantity=s;
        expiryDate=exp;
    }
    public Medicine(String med, String n, String man,
                    double p, int s, String exp,int age) {
        medicineId=med;
        name=n;
        manufacturer=man;
        price=p;
        stockQuantity=s;
        expiryDate=exp;
        minimumAge=age;
    }
    public abstract void display();

    public boolean isAvailable (int qty){
        return stockQuantity >=qty;
    }
    public void updateStock(int qty){
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


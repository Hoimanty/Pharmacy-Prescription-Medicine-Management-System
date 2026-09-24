import java.util.HashMap;
public class Pharmacist {

    private String pharmacistId;
    private String name;
    private String licenseNumber;

    Pharmacist(String pharmacistId, String name, String licenseNumber)
    {
        this.pharmacistId = pharmacistId;
        this.name = name;
        this.licenseNumber = licenseNumber;
    }

    public Medicine searchMedicine(String id, HashMap<String, Medicine> inventory)
            throws MedicineNotFoundException {
        Medicine m = inventory.get(id);
        if (m == null) {
            throw new MedicineNotFoundException("No medicine found with ID: " + id);
        }
        return m;
    }


    public void verifyPrescription(Prescription p)
            throws InvalidPrescriptionException {
        if (p == null || !p.isValid()) {
            throw new InvalidPrescriptionException("Prescription is missing or marked invalid.");
        }
        if (p.getDoctorName() == null || p.getDoctorName().trim().isEmpty()) {
            throw new InvalidPrescriptionException("Prescription " + p.getPrescriptionId() + " has no doctor on file.");
        }
    }

    public boolean checkQuantity(Medicine m, int qty) {
        return m.isAvailable(qty);
    }

    public void dispenseMedicine(Medicine m, int qty)
            throws InsufficientStockException {
        try {
            dispenseMedicine(m, qty, null);
        }
        catch (AgeRestrictionException impossible) {

        }
    }

    public void dispenseMedicine(Medicine m, int qty, Customer customer)
            throws InsufficientStockException, AgeRestrictionException {
        if (customer != null && !m.isAgeEligible(customer.getAge())) {
            throw new AgeRestrictionException(customer.getName() + " (age " + customer.getAge()
                    + ") does not meet the minimum age of " + m.getMinimumAge() + " required for " + m.getName() + ".");
        }
        if (!checkQuantity(m, qty)) {
            throw new InsufficientStockException("Cannot dispense " + qty + " units of " + m.getName()
                    + ": only " + m.getStockQuantity() + " in stock.");
        }
        m.updateStock(qty);
        System.out.println("Dispensed " + qty + " units of " + m.getName());
    }

    public String getPharmacistId() { return pharmacistId; }
    public String getName() { return name; }
    public String getLicenseNumber() { return licenseNumber; }

    public String toString() {
        return name + " (ID: " + pharmacistId + ", License: " + licenseNumber + ")";
    }

}
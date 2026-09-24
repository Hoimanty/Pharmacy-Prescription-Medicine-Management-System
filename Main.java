import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.HashMap;
public class Main {
    public static void main(String[] args) {
        HashMap<String, Medicine> medicineInventory = new HashMap<>();
        HashMap<String, Customer> customers = new HashMap<>();
        ArrayList<Prescription> activePrescriptions = new ArrayList<>();
        Pharmacist pharmacist = new Pharmacist("PH001", "Dr. Rahim Uddin", "LIC-4521");

        medicineInventory.put("MED001", new OralAntibiotic(
                "MED001", "Amoxicillin", "Square Pharma", 2.50, 100, "2027-06-01",
                0, "1 capsule every 8 hours", "Penicillin", 7, "Capsule"));

        medicineInventory.put("MED002", new InjectableAntibiotic(
                "MED002", "Ceftriaxone", "Beximco", 15.00, 30, "2027-03-15",
                0, "1 vial every 24 hours", "Cephalosporin", 5, "IV", true));

        medicineInventory.put("MED003", new ControlledMedicine(
                "MED003", "Tramadol", "Incepta", 5.00, 50, "2027-01-20",
                18, "1 tablet every 12 hours as needed", "Schedule II", 2));

        medicineInventory.put("MED004", new Painkiller(
                "MED004", "Napa Extra", "Beximco", 1.20, 200, "2028-01-01",
                "Paracetamol", 4000));

        medicineInventory.put("MED005", new Supplement(
                "MED005", "Seven Seas Cod Liver Oil", "Reckitt", 8.50, 80, "2027-09-01", "Vitamin"));

        medicineInventory.put("MED006", new ColdMedicine(
                "MED006", "Comex-D", "ACI", 2.00, 60, "2027-05-01", 12, true));


        customers.put("CUST001", new Customer("CUST001", "Tanvir Ahmed", "01711000000", 25));
        customers.put("CUST002", new Customer("CUST002", "Mina Chowdhury", "01822000000", 15));

        System.out.println("--- Full Inventory (dynamic binding via Medicine-typed references) ---");
        for (Medicine m : medicineInventory.values()) {
            m.displayInfo();
        }

        try {
            pharmacist.searchMedicine("MED999", medicineInventory);
        }
        catch (MedicineNotFoundException e) {
            System.out.println("\nCaught MedicineNotFoundException: " + e.getMessage());
        }

        try {
            Medicine tramadol = medicineInventory.get("MED003");
            pharmacist.dispenseMedicine(tramadol, 1, customers.get("CUST002"));
        }
        catch (InsufficientStockException | AgeRestrictionException e) {
            System.out.println("Caught " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        try {
            Medicine supplement = medicineInventory.get("MED005");
            pharmacist.dispenseMedicine(supplement, 9999);
        }
        catch (InsufficientStockException e) {
            System.out.println("Caught InsufficientStockException: " + e.getMessage());
        }

        try {
            int bad = Integer.parseInt("not-a-number");
        }
        catch (NumberFormatException e) {
            System.out.println("Caught NumberFormatException: " + e.getMessage());
        }

        Prescription rx1 = new Prescription("RX001", customers.get("CUST001"), medicineInventory.get("MED001"),
                "Dr. Karim", 20, 1, 20, java.time.LocalDate.now().minusDays(15));
        activePrescriptions.add(rx1);
        rx1.checkAndNotify();
        System.out.println("\n--- Refill check ---");
        System.out.println(rx1);
        if (!customers.get("CUST001").getNotifications().isEmpty()) {
            System.out.println("Notification queued: " + customers.get("CUST001").getNotifications().get(0));
        }

        System.out.println("\nLaunching GUI...");
        SwingUtilities.invokeLater(() -> {
            PharmacyGUI gui = new PharmacyGUI(medicineInventory, customers, activePrescriptions, pharmacist);
            gui.setVisible(true);
        });
    }
}

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
        } catch (MedicineNotFoundException e) {
            System.out.println("\nCaught MedicineNotFoundException: " + e.getMessage());
        }

        
        try {
            Medicine tramadol = medicineInventory.get("MED003");
            pharmacist.dispenseMedicine(tramadol, 1, customers.get("CUST002"));
        } catch (InsufficientStockException | AgeRestrictionException e) {
            System.out.println("Caught " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        try {
            Medicine supplement = medicineInventory.get("MED005");
            pharmacist.dispenseMedicine(supplement, 9999);
        } catch (InsufficientStockException e) {
            System.out.println("Caught InsufficientStockException: " + e.getMessage());
        }

       
        try {
            int bad = Integer.parseInt("not-a-number");
        } catch (NumberFormatException e) {
            System.out.println("Caught NumberFormatException: " + e.getMessage());
        }
        
        Customer demoCustomer = new Customer("DEMO", "Demo Patient", "000", 30);
        Medicine demoMedicine = new OralAntibiotic("DEMO-MED", "Demo Antibiotic", "Demo Pharma",
                1.0, 1000, "2099-01-01", 0, "as directed", "Demo Class", 10, "Tablet");
        Prescription demoRx = new Prescription("DEMO-RX", demoCustomer, demoMedicine, "Dr. Demo", 20, 2, 10);

        System.out.println("\n--- Refill tracking demo (throwaway data, not part of the live GUI state) ---");
        System.out.println("Before dispensing: " + demoRx);
        try {
            pharmacist.dispenseMedicine(demoMedicine, 10, demoCustomer, demoRx);
        } catch (InsufficientStockException | AgeRestrictionException e) {
            System.out.println("Unexpected exception: " + e.getMessage());
        }
        System.out.println("After dispensing 10: " + demoRx);
        demoRx.checkAndNotify();
        if (!demoCustomer.getNotifications().isEmpty()) {
            System.out.println("Notification queued: " + demoCustomer.getNotifications().get(0));
        }

        System.out.println("\nLaunching GUI...");
        SwingUtilities.invokeLater(() -> {
            PharmacyGUI gui = new PharmacyGUI(medicineInventory, customers, activePrescriptions, pharmacist);
            gui.setVisible(true);
        });
    }
}

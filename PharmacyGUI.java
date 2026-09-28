import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
public class PharmacyGUI extends JFrame {
    private final HashMap<String, Medicine> inventory;
    private final HashMap<String, Customer> customers;
    private final ArrayList<Prescription> activePrescriptions;
    private final Pharmacist pharmacist;
    private DefaultTableModel inventoryTableModel;
    private DefaultTableModel customerTableModel;
    private DefaultTableModel prescriptionStatusModel;
    private JTextArea addMedicineLog;
    private JTextArea addCustomerLog;
    private JTextArea dispenseLog;
    private JTextArea prescriptionLog;
    private JTextArea notificationsArea;
    private JComboBox<String> categoryCombo;
    private JTextField idField, nameField, manufacturerField, priceField, stockField, expiryField, minAgeField;
    private CardLayout categoryCardLayout;
    private JPanel categoryCardPanel;
    private JTextField dosageField1, antibioticClassField1, courseDaysField1;
    private JComboBox<String> formCombo;
    private JComboBox<String> routeCombo;
    private JCheckBox refrigerationCheck;
    private JTextField scheduleField, maxRefillsField;
    private JTextField ingredientField, maxDosageField;
    private JTextField supplementTypeField;
    private JCheckBox drowsyCheck;
    public PharmacyGUI(HashMap<String, Medicine> inventory, HashMap<String, Customer> customers,
                        ArrayList<Prescription> activePrescriptions, Pharmacist pharmacist) {
        super("MediTrack - Pharmacy Management System");
        this.inventory = inventory;
        this.customers = customers;
        this.activePrescriptions = activePrescriptions;
        this.pharmacist = pharmacist;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 640);
        setLocationRelativeTo(null);
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Inventory", buildInventoryPanel());
        tabs.addTab("Add Medicine", buildAddMedicinePanel());
        tabs.addTab("Customers", buildCustomersPanel());
        tabs.addTab("Dispense", buildDispensePanel());
        tabs.addTab("Prescription Entry", buildPrescriptionPanel());
        tabs.addTab("Notifications", buildNotificationsPanel());
        add(tabs);
    }
    private JPanel buildInventoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        String[] cols = {"ID", "Name", "Category", "Manufacturer", "Stock", "Price", "Expiry", "Min Age"};
        inventoryTableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable table = new JTable(inventoryTableModel);
        refreshInventoryTable();
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> refreshInventoryTable());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(refreshBtn, BorderLayout.SOUTH);
        return panel;
    }
    private void refreshInventoryTable() {
        inventoryTableModel.setRowCount(0);
        for (Medicine m : inventory.values()) {
            inventoryTableModel.addRow(new Object[]{
                    m.getMedicineId(), m.getName(), categoryOf(m), m.getManufacturer(),
                    m.getStockQuantity(), String.format("%.2f", m.getPrice()),
                    m.getExpiryDate(), m.getMinimumAge()
            });
        }
    }
    private String categoryOf(Medicine m) {
        if (m instanceof OralAntibiotic) return "Oral Antibiotic";
        if (m instanceof InjectableAntibiotic) return "Injectable Antibiotic";
        if (m instanceof Antibiotic) return "Antibiotic";
        if (m instanceof ControlledMedicine) return "Controlled Medicine";
        if (m instanceof Painkiller) return "Painkiller";
        if (m instanceof Supplement) return "Supplement";
        if (m instanceof ColdMedicine) return "Cold Medicine";
        if (m instanceof PrescriptionMedicine) return "Prescription Medicine";
        if (m instanceof OTCMedicine) return "OTC Medicine";
        return "Medicine";
    }
    private JPanel buildAddMedicinePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        JPanel commonFields = new JPanel(new GridLayout(0, 2, 5, 5));
        commonFields.setBorder(BorderFactory.createTitledBorder("Common Details"));
        idField = new JTextField();
        nameField = new JTextField();
        manufacturerField = new JTextField();
        priceField = new JTextField();
        stockField = new JTextField();
        expiryField = new JTextField();
        expiryField.setToolTipText("e.g. 2027-06-01");
        minAgeField = new JTextField("0");
        commonFields.add(new JLabel("Medicine ID:"));
        commonFields.add(idField);
        commonFields.add(new JLabel("Name:"));
        commonFields.add(nameField);
        commonFields.add(new JLabel("Manufacturer:"));
        commonFields.add(manufacturerField);
        commonFields.add(new JLabel("Price:"));
        commonFields.add(priceField);
        commonFields.add(new JLabel("Stock Quantity:"));
        commonFields.add(stockField);
        commonFields.add(new JLabel("Expiry Date:"));
        commonFields.add(expiryField);
        commonFields.add(new JLabel("Minimum Age (0 = none):"));
        commonFields.add(minAgeField);
        JPanel categoryRow = new JPanel(new BorderLayout(5, 5));
        categoryRow.setBorder(BorderFactory.createTitledBorder("Category"));
        categoryCombo = new JComboBox<>(new String[]{
                "Oral Antibiotic", "Injectable Antibiotic", "Controlled Medicine",
                "Painkiller", "Supplement", "Cold Medicine"
        });
        categoryRow.add(categoryCombo, BorderLayout.NORTH);
        categoryCardLayout = new CardLayout();
        categoryCardPanel = new JPanel(categoryCardLayout);
        categoryCardPanel.add(buildOralAntibioticCard(), "Oral Antibiotic");
        categoryCardPanel.add(buildInjectableAntibioticCard(), "Injectable Antibiotic");
        categoryCardPanel.add(buildControlledMedicineCard(), "Controlled Medicine");
        categoryCardPanel.add(buildPainkillerCard(), "Painkiller");
        categoryCardPanel.add(buildSupplementCard(), "Supplement");
        categoryCardPanel.add(buildColdMedicineCard(), "Cold Medicine");
        categoryCombo.addActionListener(e ->
                categoryCardLayout.show(categoryCardPanel, (String) categoryCombo.getSelectedItem()));
        top.add(commonFields);
        top.add(categoryRow);
        top.add(categoryCardPanel);
        JButton addBtn = new JButton("Add to Inventory");
        addMedicineLog = new JTextArea();
        addMedicineLog.setEditable(false);
        JScrollPane logScroll = new JScrollPane(addMedicineLog);
        logScroll.setBorder(BorderFactory.createTitledBorder("Log"));
        panel.add(new JScrollPane(top), BorderLayout.CENTER);
        panel.add(logScroll, BorderLayout.EAST);
        panel.add(addBtn, BorderLayout.SOUTH);
        addBtn.addActionListener(e -> handleAddMedicine());
        return panel;
    }
    private JPanel buildOralAntibioticCard() {
        JPanel p = new JPanel(new GridLayout(0, 2, 5, 5));
        p.setBorder(BorderFactory.createTitledBorder("Oral Antibiotic Details"));
        dosageField1 = new JTextField();
        antibioticClassField1 = new JTextField();
        courseDaysField1 = new JTextField();
        formCombo = new JComboBox<>(new String[]{"Tablet", "Capsule", "Syrup"});
        p.add(new JLabel("Dosage Instruction:")); p.add(dosageField1);
        p.add(new JLabel("Antibiotic Class:")); p.add(antibioticClassField1);
        p.add(new JLabel("Course Duration (days):")); p.add(courseDaysField1);
        p.add(new JLabel("Form:")); p.add(formCombo);
        return p;
    }
    private JPanel buildInjectableAntibioticCard() {
        JPanel p = new JPanel(new GridLayout(0, 2, 5, 5));
        p.setBorder(BorderFactory.createTitledBorder("Injectable Antibiotic Details"));
        JTextField dosage = new JTextField();
        JTextField antClass = new JTextField();
        JTextField courseDays = new JTextField();
        routeCombo = new JComboBox<>(new String[]{"IV", "IM"});
        refrigerationCheck = new JCheckBox("Requires refrigeration");
        p.add(new JLabel("Dosage Instruction:")); p.add(dosage);
        p.add(new JLabel("Antibiotic Class:")); p.add(antClass);
        p.add(new JLabel("Course Duration (days):")); p.add(courseDays);
        p.add(new JLabel("Injection Route:")); p.add(routeCombo);
        p.add(new JLabel("")); p.add(refrigerationCheck);
        p.putClientProperty("dosage", dosage);
        p.putClientProperty("antClass", antClass);
        p.putClientProperty("courseDays", courseDays);
        return p;
    }
    private JPanel buildControlledMedicineCard() {
        JPanel p = new JPanel(new GridLayout(0, 2, 5, 5));
        p.setBorder(BorderFactory.createTitledBorder("Controlled Medicine Details"));
        JTextField dosage = new JTextField();
        scheduleField = new JTextField();
        maxRefillsField = new JTextField();
        p.add(new JLabel("Dosage Instruction:")); p.add(dosage);
        p.add(new JLabel("Controlled Substance Schedule:")); p.add(scheduleField);
        p.add(new JLabel("Max Refills Allowed:")); p.add(maxRefillsField);
        p.putClientProperty("dosage", dosage);
        return p;
    }
    private JPanel buildPainkillerCard() {
        JPanel p = new JPanel(new GridLayout(0, 2, 5, 5));
        p.setBorder(BorderFactory.createTitledBorder("Painkiller Details"));
        ingredientField = new JTextField();
        maxDosageField = new JTextField();
        p.add(new JLabel("Active Ingredient:")); p.add(ingredientField);
        p.add(new JLabel("Max Daily Dosage (mg):")); p.add(maxDosageField);
        return p;
    }
    private JPanel buildSupplementCard() {
        JPanel p = new JPanel(new GridLayout(0, 2, 5, 5));
        p.setBorder(BorderFactory.createTitledBorder("Supplement Details"));
        supplementTypeField = new JTextField();
        p.add(new JLabel("Supplement Type (Vitamin/Mineral/Protein):")); p.add(supplementTypeField);
        return p;
    }
    private JPanel buildColdMedicineCard() {
        JPanel p = new JPanel(new GridLayout(0, 2, 5, 5));
        p.setBorder(BorderFactory.createTitledBorder("Cold Medicine Details"));
        drowsyCheck = new JCheckBox("Causes drowsiness");
        p.add(new JLabel("")); p.add(drowsyCheck);
        return p;
    }
    private void handleAddMedicine() {
        String id = idField.getText().trim();
        if (id.isEmpty()) {
            addMedicineLog.append("Error: Medicine ID cannot be empty.\n\n");
            return;
        }
        if (inventory.containsKey(id)) {
            addMedicineLog.append("Error: Medicine ID " + id + " already exists.\n\n");
            return;
        }
        String name = nameField.getText().trim();
        String manufacturer = manufacturerField.getText().trim();
        String expiry = expiryField.getText().trim();
        String category = (String) categoryCombo.getSelectedItem();
        double price;
        int stock, minAge;
        try {
            price = Double.parseDouble(priceField.getText().trim());
            stock = Integer.parseInt(stockField.getText().trim());
            minAge = Integer.parseInt(minAgeField.getText().trim());
        } catch (NumberFormatException nfe) {
            addMedicineLog.append("Error: Price / Stock / Minimum Age must be valid numbers.\n\n");
            return;
        }
        try {
            Medicine medicine;
            switch (category) {
                case "Oral Antibiotic":
                    medicine = new OralAntibiotic(id, name, manufacturer, price, stock, expiry, minAge,
                            dosageField1.getText().trim(), antibioticClassField1.getText().trim(),
                            Integer.parseInt(courseDaysField1.getText().trim()),
                            (String) formCombo.getSelectedItem());
                    break;
                case "Injectable Antibiotic": {
                    JPanel card = (JPanel) findCard("Injectable Antibiotic");
                    JTextField dosage = (JTextField) card.getClientProperty("dosage");
                    JTextField antClass = (JTextField) card.getClientProperty("antClass");
                    JTextField courseDays = (JTextField) card.getClientProperty("courseDays");
                    medicine = new InjectableAntibiotic(id, name, manufacturer, price, stock, expiry, minAge,
                            dosage.getText().trim(), antClass.getText().trim(),
                            Integer.parseInt(courseDays.getText().trim()),
                            (String) routeCombo.getSelectedItem(), refrigerationCheck.isSelected());
                    break;
                }
                case "Controlled Medicine": {
                    JPanel card = (JPanel) findCard("Controlled Medicine");
                    JTextField dosage = (JTextField) card.getClientProperty("dosage");
                    medicine = new ControlledMedicine(id, name, manufacturer, price, stock, expiry, minAge,
                            dosage.getText().trim(), scheduleField.getText().trim(),
                            Integer.parseInt(maxRefillsField.getText().trim()));
                    break;
                }
                case "Painkiller":
                    medicine = new Painkiller(id, name, manufacturer, price, stock, expiry,
                            ingredientField.getText().trim(), Integer.parseInt(maxDosageField.getText().trim()));
                    break;
                case "Supplement":
                    medicine = new Supplement(id, name, manufacturer, price, stock, expiry,
                            supplementTypeField.getText().trim());
                    break;
                case "Cold Medicine":
                    medicine = new ColdMedicine(id, name, manufacturer, price, stock, expiry, minAge,
                            drowsyCheck.isSelected());
                    break;
                default:
                    addMedicineLog.append("Error: Unknown category.\n\n");
                    return;
            }
            inventory.put(id, medicine);
            addMedicineLog.append("Added: " + medicine.toString() + "\n\n");
            refreshInventoryTable();
        } catch (NumberFormatException nfe) {
            addMedicineLog.append("Error: One of the category-specific numeric fields is invalid.\n\n");
        }
    }
    private Component findCard(String name) {
        String[] order = {"Oral Antibiotic", "Injectable Antibiotic", "Controlled Medicine",
                "Painkiller", "Supplement", "Cold Medicine"};
        Component[] comps = categoryCardPanel.getComponents();
        for (int i = 0; i < order.length && i < comps.length; i++) {
            if (order[i].equals(name)) return comps[i];
        }
        return null;
    }
    private JPanel buildCustomersPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        String[] cols = {"Customer ID", "Name", "Phone", "Age"};
        customerTableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable table = new JTable(customerTableModel);
        refreshCustomerTable();
        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Register Customer"));
        JTextField custIdField = new JTextField();
        JTextField custNameField = new JTextField();
        JTextField custPhoneField = new JTextField();
        JTextField custAgeField = new JTextField();
        form.add(new JLabel("Customer ID:")); form.add(custIdField);
        form.add(new JLabel("Name:")); form.add(custNameField);
        form.add(new JLabel("Phone:")); form.add(custPhoneField);
        form.add(new JLabel("Age:")); form.add(custAgeField);
        JButton registerBtn = new JButton("Register Customer");
        addCustomerLog = new JTextArea(4, 20);
        addCustomerLog.setEditable(false);
        JPanel formAndLog = new JPanel(new BorderLayout(5, 5));
        formAndLog.add(form, BorderLayout.NORTH);
        formAndLog.add(new JScrollPane(addCustomerLog), BorderLayout.CENTER);
        formAndLog.add(registerBtn, BorderLayout.SOUTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(formAndLog, BorderLayout.SOUTH);
        registerBtn.addActionListener(e -> {
            String id = custIdField.getText().trim();
            String name = custNameField.getText().trim();
            String phone = custPhoneField.getText().trim();
            if (id.isEmpty() || name.isEmpty()) {
                addCustomerLog.append("Error: Customer ID and Name are required.\n");
                return;
            }
            if (customers.containsKey(id)) {
                addCustomerLog.append("Error: Customer ID " + id + " already exists.\n");
                return;
            }
            int age;
            try {
                age = Integer.parseInt(custAgeField.getText().trim());
            } catch (NumberFormatException nfe) {
                addCustomerLog.append("Error: Age must be a valid number.\n");
                return;
            }
            customers.put(id, new Customer(id, name, phone, age));
            addCustomerLog.append("Registered: " + name + " (" + id + ")\n");
            refreshCustomerTable();
        });
        return panel;
    }
    private void refreshCustomerTable() {
        customerTableModel.setRowCount(0);
        for (Customer c : customers.values()) {
            customerTableModel.addRow(new Object[]{c.getCustomerId(), c.getName(), c.getPhone(), c.getAge()});
        }
    }
    private JPanel buildDispensePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Dispense Medicine"));
        JTextField medicineIdField = new JTextField();
        JTextField qtyField = new JTextField();
        JTextField customerIdField = new JTextField();
        JTextField prescriptionIdField = new JTextField();
        form.add(new JLabel("Medicine ID:"));
        form.add(medicineIdField);
        form.add(new JLabel("Quantity:"));
        form.add(qtyField);
        form.add(new JLabel("Customer ID (optional, enables age check):"));
        form.add(customerIdField);
        form.add(new JLabel("Prescription ID (required for Rx medicine):"));
        form.add(prescriptionIdField);
        JButton dispenseBtn = new JButton("Dispense");
        dispenseLog = new JTextArea();
        dispenseLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(dispenseLog);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Activity Log"));
        panel.add(form, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(dispenseBtn, BorderLayout.SOUTH);
        dispenseBtn.addActionListener(e -> handleDispense(
                medicineIdField.getText().trim(), qtyField.getText().trim(),
                customerIdField.getText().trim(), prescriptionIdField.getText().trim()));
        return panel;
    }
    private void handleDispense(String medicineId, String qtyText, String customerId, String prescriptionId) {
        int qty;
        try {
            qty = Integer.parseInt(qtyText);
        } catch (NumberFormatException nfe) {
            dispenseLog.append("Error: Quantity must be a valid whole number.\n\n");
            return;
        }
        try {
            Medicine medicine = pharmacist.searchMedicine(medicineId, inventory);
            Prescription rx = null;
            if (medicine instanceof PrescriptionMedicine) {
                if (prescriptionId.isEmpty()) {
                    throw new InvalidPrescriptionException(medicine.getName()
                            + " requires a prescription, but no Prescription ID was entered.");
                }
                rx = findPrescription(prescriptionId);
                pharmacist.verifyPrescription(rx);
            }
            Customer customer = customerId.isEmpty() ? null : customers.get(customerId);
            if (!customerId.isEmpty() && customer == null) {
                dispenseLog.append("Warning: Customer ID " + customerId
                        + " not found — dispensing without an age check.\n");
            }
            pharmacist.dispenseMedicine(medicine, qty, customer, rx);
            dispenseLog.append("Dispensed " + qty + " unit(s) of " + medicine.getName() + ".\n");
            dispenseLog.append(medicine.toString() + "\n");
            if (rx != null) {
                rx.checkAndNotify();
                dispenseLog.append(rx.getPillsRemaining() + " pills remaining on " + rx.getPrescriptionId() + ".\n");
                if (rx.isReminderSent()) {
                    dispenseLog.append("Refill reminder sent to " + rx.getCustomer().getName() + ".\n");
                }
                refreshPrescriptionStatus();
            }
            dispenseLog.append("\n");
            refreshInventoryTable();
        } catch (MedicineNotFoundException | InvalidPrescriptionException
                 | InsufficientStockException | AgeRestrictionException ex) {
            dispenseLog.append("Dispense failed: " + ex.getMessage() + "\n\n");
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Dispense Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    private Prescription findPrescription(String prescriptionId) {
        for (Prescription p : activePrescriptions) {
            if (p.getPrescriptionId().equals(prescriptionId)) return p;
        }
        return null;
    }
    private JPanel buildPrescriptionPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("New Prescription"));
        JTextField rxIdField = new JTextField();
        JTextField customerIdField = new JTextField();
        JTextField medicineIdField = new JTextField();
        JTextField doctorField = new JTextField();
        JTextField totalPillsField = new JTextField();
        JTextField dailyDosageField = new JTextField();
        JTextField courseDaysField = new JTextField();
        form.add(new JLabel("Prescription ID:"));
        form.add(rxIdField);
        form.add(new JLabel("Customer ID:"));
        form.add(customerIdField);
        form.add(new JLabel("Medicine ID:"));
        form.add(medicineIdField);
        form.add(new JLabel("Doctor Name:"));
        form.add(doctorField);
        form.add(new JLabel("Total Pills Prescribed:"));
        form.add(totalPillsField);
        form.add(new JLabel("Daily Dosage:"));
        form.add(dailyDosageField);
        form.add(new JLabel("Course Duration (days):"));
        form.add(courseDaysField);
        JButton createBtn = new JButton("Create Prescription");
        prescriptionLog = new JTextArea();
        prescriptionLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(prescriptionLog);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Prescriptions Created"));
        panel.add(form, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(createBtn, BorderLayout.SOUTH);
        createBtn.addActionListener(e -> handleCreatePrescription(
                rxIdField.getText().trim(), customerIdField.getText().trim(),
                medicineIdField.getText().trim(), doctorField.getText().trim(),
                totalPillsField.getText().trim(), dailyDosageField.getText().trim(),
                courseDaysField.getText().trim()));
        return panel;
    }
    private void handleCreatePrescription(String rxId, String customerId, String medicineId, String doctor,
                                           String totalPillsText, String dailyDosageText, String courseDaysText) {
        int totalPills, dailyDosage, courseDays;
        try {
            totalPills = Integer.parseInt(totalPillsText);
            dailyDosage = Integer.parseInt(dailyDosageText);
            courseDays = Integer.parseInt(courseDaysText);
        } catch (NumberFormatException nfe) {
            prescriptionLog.append("Error: Pills / dosage / course fields must be whole numbers.\n\n");
            return;
        }
        Customer customer = customers.get(customerId);
        if (customer == null) {
            prescriptionLog.append("Error: No customer found with ID " + customerId
                    + ". Register them in the Customers tab first.\n\n");
            return;
        }
        Medicine medicine;
        try {
            medicine = pharmacist.searchMedicine(medicineId, inventory);
        } catch (MedicineNotFoundException ex) {
            prescriptionLog.append("Error: " + ex.getMessage() + "\n\n");
            return;
        }
        Prescription rx = new Prescription(rxId, customer, medicine, doctor, totalPills, dailyDosage, courseDays);
        activePrescriptions.add(rx);
        prescriptionLog.append("Created: " + rx + "\n\n");
    }
    private JPanel buildNotificationsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        String[] cols = {"Prescription ID", "Customer", "Medicine", "Total", "Dispensed", "Remaining", "Reminder Sent?"};
        prescriptionStatusModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable statusTable = new JTable(prescriptionStatusModel);
        JScrollPane statusScroll = new JScrollPane(statusTable);
        statusScroll.setBorder(BorderFactory.createTitledBorder("All Active Prescriptions (live)"));
        notificationsArea = new JTextArea();
        notificationsArea.setEditable(false);
        JScrollPane msgScroll = new JScrollPane(notificationsArea);
        msgScroll.setBorder(BorderFactory.createTitledBorder("Reminder Messages"));
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, statusScroll, msgScroll);
        split.setResizeWeight(0.55);
        JButton checkBtn = new JButton("Check Reminders / Refresh");
        checkBtn.addActionListener(e -> handleCheckReminders());
        panel.add(split, BorderLayout.CENTER);
        panel.add(checkBtn, BorderLayout.SOUTH);
        handleCheckReminders(); // populate immediately so the tab isn't empty on first open
        return panel;
    }
    private void handleCheckReminders() {
        for (Prescription p : activePrescriptions) {
            p.checkAndNotify();
        }
        refreshPrescriptionStatus();
        notificationsArea.setText("");
        for (Customer c : customers.values()) {
            for (String msg : c.getNotifications()) {
                notificationsArea.append("[" + c.getName() + "] " + msg + "\n");
            }
        }
        if (notificationsArea.getText().isEmpty()) {
            notificationsArea.setText("No reminder messages yet.");
        }
    }
    private void refreshPrescriptionStatus() {
        if (prescriptionStatusModel == null) return; // Notifications tab not built yet
        prescriptionStatusModel.setRowCount(0);
        for (Prescription p : activePrescriptions) {
            prescriptionStatusModel.addRow(new Object[]{
                    p.getPrescriptionId(), p.getCustomer().getName(), p.getMedicine().getName(),
                    p.getTotalPillsPrescribed(), p.getPillsDispensed(), p.getPillsRemaining(),
                    p.isReminderSent() ? "Yes" : "No"
            });
        }
    }
}

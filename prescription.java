import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Prescription {

    private static final int REFILL_THRESHOLD = 10;

    private String prescriptionId;
    private Customer customer;
    private Medicine medicine;
    private String doctorName;
    private int totalPillsPrescribed;
    private int dailyDosage;
    private int courseDurationDays;
    private LocalDate startDate;
    private boolean isValid;
    private boolean reminderSent;
    private int refillsUsed;

    
    public Prescription(String prescriptionId, Customer customer, Medicine medicine, String doctorName, int totalPillsPrescribed, int dailyDosage, int courseDurationDays) {

        this.prescriptionId = prescriptionId;
        this.customer = customer;
        this.medicine = medicine;
        this.doctorName = doctorName;
        this.totalPillsPrescribed = totalPillsPrescribed;
        this.dailyDosage = dailyDosage;
        this.courseDurationDays = courseDurationDays;
        this.startDate = LocalDate.now();

        this.isValid = true;
        this.reminderSent = false;
        this.refillsUsed = 0;
    }

    public Prescription(String prescriptionId, Customer customer, Medicine medicine, String doctorName, int totalPillsPrescribed, int dailyDosage, int courseDurationDays, LocalDate startDate) {

        this.prescriptionId = prescriptionId;
        this.customer = customer;
        this.medicine = medicine;
        this.doctorName = doctorName;
        this.totalPillsPrescribed = totalPillsPrescribed;
        this.dailyDosage = dailyDosage;
        this.courseDurationDays = courseDurationDays;
        this.startDate = startDate;

        this.isValid = true;
        this.reminderSent = false;
        this.refillsUsed = 0;
    }


    public int getDaysElapsed() {
        return (int) ChronoUnit.DAYS.between(startDate, LocalDate.now());
    }


    public int getPillsRemaining() {
        return totalPillsPrescribed - (dailyDosage * getDaysElapsed());
    }


    public boolean needsRefillReminder(int threshold) {
        return getPillsRemaining() <= threshold && !reminderSent;
    }


    public void checkAndNotify() {

        if (needsRefillReminder(REFILL_THRESHOLD)) {

            String message = "Refill reminder: your prescription " + prescriptionId + " for " + medicine.getName() + " has about " + getPillsRemaining() + " pills left. Please arrange a refill soon.";


            if (medicine instanceof Trackable) {

                Trackable trackableMedicine = (Trackable) medicine;

                trackableMedicine.sendNotification(customer, message);
            }

            reminderSent = true;
        }
    }

    public void incrementRefillsUsed() {
        refillsUsed++;
    }


    public String getPrescriptionId() {
        return prescriptionId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public int getTotalPillsPrescribed() {
        return totalPillsPrescribed;
    }

    public int getDailyDosage() {
        return dailyDosage;
    }

    public int getCourseDurationDays() {
        return courseDurationDays;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public boolean isReminderSent() {
        return reminderSent;
    }

    public int getRefillsUsed() {
        return refillsUsed;
    }
    
    public String toString() {
        return "Rx " + prescriptionId
                + " | " + customer.getName()
                + " for " + medicine.getName()
                + " | " + totalPillsPrescribed + " pills"
                + ", " + dailyDosage + "/day"
                + " | Day " + getDaysElapsed()
                + " | " + getPillsRemaining() + " remaining";
    }

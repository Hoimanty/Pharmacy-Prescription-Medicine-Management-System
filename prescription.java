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
    private int pillsDispensed;
    private int lastNotifiedRemaining; 
    public Prescription(String prescriptionId, Customer customer, Medicine medicine, String doctorName,
                         int totalPillsPrescribed, int dailyDosage, int courseDurationDays) {
        this(prescriptionId, customer, medicine, doctorName, totalPillsPrescribed,
                dailyDosage, courseDurationDays, LocalDate.now());
    }

    
    public Prescription(String prescriptionId, Customer customer, Medicine medicine, String doctorName,
                         int totalPillsPrescribed, int dailyDosage, int courseDurationDays, LocalDate startDate) {
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
        this.pillsDispensed = 0;
        this.lastNotifiedRemaining = -1;
    }

    public int getDaysElapsed() {
        return (int) ChronoUnit.DAYS.between(startDate, LocalDate.now());
    }

    public void recordDispense(int qty) {
        pillsDispensed += qty;
    }

    public int getPillsDispensed() { 
        return pillsDispensed; 
    }

    public int getPillsRemaining() {
        
        return totalPillsPrescribed - pillsDispensed;
    }
    public boolean needsRefillReminder(int threshold) {
        return getPillsRemaining() <= threshold;
    }

    public void checkAndNotify() {
        int remaining = getPillsRemaining();
        if (remaining == lastNotifiedRemaining) {
            return; 
        }

        if (remaining <= 0) {
            customer.sendNotification("Prescription " + prescriptionId + " for " + medicine.getName()
                    + " has been fully dispensed (" + totalPillsPrescribed + "/" + totalPillsPrescribed
                    + " pills). No refill needed.");
        }
        else if (needsRefillReminder(REFILL_THRESHOLD)) {
            customer.sendNotification("Prescription " + prescriptionId + " for " + medicine.getName()
                    + " now has " + remaining + " of " + totalPillsPrescribed
                    + " pills remaining. Please arrange a refill soon.");
        } 
        else {
            customer.sendNotification("Prescription " + prescriptionId + " for " + medicine.getName()
                    + " now has " + remaining + " of " + totalPillsPrescribed + " pills remaining.");
        }

        reminderSent = remaining > 0 ;
        lastNotifiedRemaining = remaining;
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
        return String.format("Rx %s | %s for %s | %d pills, %d/day | %d dispensed | %d remaining",
                prescriptionId, customer.getName(), medicine.getName(),
                totalPillsPrescribed, dailyDosage, pillsDispensed, getPillsRemaining());
    }
}

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private String customerId;
    private String name;
    private String phone;
    private int age;
    private List<String> notifications;

    public Customer(String customerId, String name, String phone) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.notifications = new ArrayList<>();
    }



    public Customer(String customerId, String name, String phone, int age) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.age = age;
        this.notifications = new ArrayList<>();
    }

    public void receiveNotification(String msg) {

        notifications.add(msg);
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public String getCustomerId() {
        return customerId;
    }
    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }
    public int getAge() {
        return age;
    }

    public String toString() {

        return name + " (" + customerId + "), age " + age + ", " + phone;
    }
}

public interface Trackable {
    String getTrackingId();
    default void sendNotification(String message) {
        System.out.println("[NOTIFY -> " + getTrackingId() + "] " + message);
    }
}

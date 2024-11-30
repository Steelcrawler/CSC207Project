package use_case.logout;

/**
 * The Input Data for the Logout Use Case.
 */
public class LogoutInputData {

    private final String currentUsername;

    public LogoutInputData(String username) {
        this.currentUsername = username;
    }

    /**
     * Gets the current username.
     * @return the current username as a string.
     */
    public String getCurrentUsername() {
        return currentUsername;
    }
}

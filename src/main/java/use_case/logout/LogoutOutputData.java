package use_case.logout;

/**
 * Output Data for the Logout Use Case.
 */
public class LogoutOutputData {

    private String username;
    private boolean useCaseFailed;

    public LogoutOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    /**
     * Returns the username.
     * @return the current username as a String.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns if the use case failed.
     * @return if the use case failed as a boolean.
     */
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}

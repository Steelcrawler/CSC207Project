package use_case.change_password;

/**
 * Output Data for the Change Password Use Case.
 */
public class ChangePasswordOutputData {

    private final String username;

    private final boolean useCaseFailed;

    public ChangePasswordOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    /**
     * Returns the username.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns whether the use case failed.
     * @return whether the use case failed
     */
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}

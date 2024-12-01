package use_case.change_password;

/**
 * The input data for the Change Password Use Case.
 */
public class ChangePasswordInputData {

    private final String password;
    private final String username;

    public ChangePasswordInputData(String password, String username) {
        this.password = password;
        this.username = username;
    }
    
    /**
     * Returns the new password.
     * @return the new password.
     */
    String getPassword() {
        return password;
    }

    /**
     * Returns the username.
     * @return the username.
     */
    String getUsername() {
        return username;
    }

}

package use_case.login;

/**
 * The Input Data for the Login Use Case.
 */
public class LoginInputData {

    private final String username;
    private final String password;

    public LoginInputData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the username.
     * @return gets the username as a String.
     */
    String getUsername() {
        return username;
    }

    /**
     * Gets the password.
     * @return gets the password as a String.
     */
    String getPassword() {
        return password;
    }

}

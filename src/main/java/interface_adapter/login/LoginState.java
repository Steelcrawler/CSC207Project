package interface_adapter.login;

/**
 * The state for the Login View Model.
 */
public class LoginState {
    private String username = "";
    private String loginError;
    private String password = "";

    /**
     * Returns the username.
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the login error.
     * @return the login error.
     */
    public String getLoginError() {
        return loginError;
    }

    /**
     * Returns the password.
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the username.
     * @param username the username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the login error.
     * @param usernameError the login error.
     */
    public void setLoginError(String usernameError) {
        this.loginError = usernameError;
    }

    /**
     * Sets the password.
     * @param password the password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

}

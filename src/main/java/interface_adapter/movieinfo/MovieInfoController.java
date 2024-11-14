package interface_adapter.movieinfo;

import use_case.movieinfo.MovieInfoInputData;

/**
 * The controller for the Movie Info Use Case.
 */
public class MovieInfoController {
    private final MovieInfoInputData movieInfoUseCaseInteractor;

    public MovieInfoController(MovieInfoInputData movieInfoUseCaseInteractor) {
        this.movieInfoUseCaseInteractor = movieInfoUseCaseInteractor;
    }

    /**
     * Executes the Login Use Case.
     * @param username the username of the user logging in
     * @param password the password of the user logging in
     */
    public void execute(String username, String password) {
        final LoginInputData loginInputData = new LoginInputData(
                username, password);

        loginUseCaseInteractor.execute(loginInputData);
    }
}

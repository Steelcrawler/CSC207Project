package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.movie_search.MovieSearchState;
import interface_adapter.movie_search.MovieSearchViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final MovieSearchViewModel movieSearchViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          MovieSearchViewModel movieSearchViewModel,
                          LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.movieSearchViewModel = movieSearchViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        // On success, switch to the movie search view.

        final MovieSearchState movieSearchState =  movieSearchViewModel.getState();
        this.movieSearchViewModel.setState(movieSearchState);
        this.movieSearchViewModel.firePropertyChanged();

        this.viewManagerModel.setState(movieSearchViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.firePropertyChanged();
    }

    @Override
    public void switchToSignUpView() {
        viewManagerModel.setState("sign up");
        viewManagerModel.firePropertyChanged();
    }
}

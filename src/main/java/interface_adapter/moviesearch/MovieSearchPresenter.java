package interface_adapter.moviesearch;

import interface_adapter.ViewManagerModel;
import interface_adapter.moviesearch.MovieSearchState;
import interface_adapter.moviesearch.MovieSearchViewModel;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;
import use_case.movie_search.MovieSearchOutputBoundary;
import use_case.movie_search.MovieSearchOutputData;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupOutputData;

import javax.swing.*;

/**
 * The Presenter for the Signup Use Case.
 */
public class MovieSearchPresenter implements MovieSearchOutputBoundary {

    private final MovieSearchViewModel movieSearchViewModel;
    private final ViewManagerModel viewManagerModel;

    public MovieSearchPresenter(ViewManagerModel viewManagerModel,
                           MovieSearchViewModel movieSearchViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.movieSearchViewModel = movieSearchViewModel;
    }

    @Override
    public void prepareSuccessView(MovieSearchOutputData outputData) {
        final MovieSearchState movieSearchState = movieSearchViewModel.getState();
        movieSearchState.setErrorMessage(errorMessage);
        movieSearchViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final MovieSearchState movieSearchState = movieSearchViewModel.getState();
        movieSearchState.setErrorMessage(errorMessage);
        movieSearchViewModel.firePropertyChanged();
    }
}


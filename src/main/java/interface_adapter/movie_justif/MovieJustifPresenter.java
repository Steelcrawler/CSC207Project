package interface_adapter.movie_justif;

import entity.Movie;
import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginState;
import interface_adapter.movie_justif.MovieJustifState;
import interface_adapter.movie_justif.MovieJustifViewModel;
import interface_adapter.movie_search.MovieSearchState;
import use_case.login.LoginOutputData;
import use_case.movie_justif.MovieJustifOutputBoundary;
import use_case.movie_justif.MovieJustifOutputData;

import java.util.ArrayList;

/**
 * The Presenter for the Movie Justif Use Case.
 */
public class MovieJustifPresenter implements MovieJustifOutputBoundary {

    private final MovieJustifViewModel movieJustifViewModel;
    private final ViewManagerModel viewManagerModel;

    public MovieJustifPresenter(ViewManagerModel viewManagerModel,
                              MovieJustifViewModel movieJustifViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.movieJustifViewModel = movieJustifViewModel;
    }

    @Override
    public void prepareSuccessView(MovieJustifOutputData outputData) {
        final MovieJustifState movieJustifState =  movieJustifViewModel.getState();
        movieJustifState.setMovieTitle(outputData.getMovieTitle());
        movieJustifState.setRatingInfo(outputData.getMovieRating());
        movieJustifState.setJustifInfo(outputData.getJustification());
        movieJustifState.setTrailerLink(outputData.getMovieTrailer());
        movieJustifState.setPosterPath(outputData.getMoviePosterPath());
        movieJustifState.setUserReviews(outputData.getMovieReviews());
        movieJustifViewModel.firePropertyChanged();

        viewManagerModel.setState(movieJustifViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final MovieJustifState movieJustifState = movieJustifViewModel.getState();
        movieJustifViewModel.firePropertyChanged();
    }

    @Override
    public void switchToRecView() {
        this.viewManagerModel.setState("Recommendation"); // change the state to recommendation
        this.viewManagerModel.firePropertyChanged();
    }
}

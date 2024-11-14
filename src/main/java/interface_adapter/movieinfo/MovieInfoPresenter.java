package interface_adapter.movieinfo;

import entity.Movie;
import interface_adapter.ViewManagerModel;
import interface_adapter.movieinfo.MovieInfoState;
import interface_adapter.movieinfo.MovieInfoViewModel;
import use_case.movieinfo.MovieInfoOutputBoundary;
import use_case.movieinfo.MovieInfoOutputData;

import java.util.ArrayList;

/**
 * The Presenter for the Movie Info Use Case.
 */
public class MovieInfoPresenter implements MovieInfoOutputBoundary {

    private final MovieInfoViewModel movieInfoViewModel;
    private final ViewManagerModel viewManagerModel;

    public MovieInfoPresenter(ViewManagerModel viewManagerModel,
                              MovieInfoViewModel movieInfoViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.movieInfoViewModel = movieInfoViewModel;
    }

    @Override
    public void prepareSuccessView(MovieInfoOutputData outputData) {
        final MovieInfoState movieInfoState = movieInfoViewModel.getState();

        movieInfoState.setMovieTitle(outputData.getMovieTitle());
        movieInfoState.setRatingInfo(outputData.getMovieRating());
        movieInfoState.setPlotInfo(outputData.getMoviePlot());

        movieInfoViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final MovieSearchState movieSearchState = movieSearchViewModel.getState();
        movieSearchState.setSearchFound(false);
        movieSearchState.setErrorMessage(errorMessage);
        movieSearchViewModel.firePropertyChanged();
    }
}


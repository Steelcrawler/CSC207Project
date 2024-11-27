package interface_adapter.movieinfo;

import entity.Movie;
import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginState;
import interface_adapter.movieinfo.MovieInfoState;
import interface_adapter.movieinfo.MovieInfoViewModel;
import interface_adapter.moviesearch.MovieSearchState;
import use_case.login.LoginOutputData;
import use_case.movieinfo.MovieInfoOutputBoundary;
import use_case.movieinfo.MovieInfoOutputData;

import java.util.ArrayList;

/**
 * The Presenter for the Movie Info Use Case.
 */
public class MovieInfoPresenter implements MovieInfoOutputBoundary {

    private final MovieInfoViewModel movieInfoViewModel;
    private final ViewManagerModel viewManagerModel;
    // private final WatchlistViewModel watchlistViewModel;

    public MovieInfoPresenter(ViewManagerModel viewManagerModel,
                              MovieInfoViewModel movieInfoViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.movieInfoViewModel = movieInfoViewModel;
        // need this.watchlistViewModel = watchlistViewModel;
    }

    @Override
    public void prepareSuccessView(MovieInfoOutputData outputData) {
        final MovieInfoState movieInfoState =  movieInfoViewModel.getState();
        movieInfoState.setMovieTitle(outputData.getMovieTitle());
        movieInfoState.setRatingInfo(outputData.getMovieRating());
        movieInfoState.setPlotInfo(outputData.getMoviePlot());
        movieInfoState.setTrailerLink(outputData.getMovieTrailer());
        movieInfoState.setPosterPath(outputData.getMoviePosterPath());
        movieInfoState.setUserReviews(outputData.getMovieReviews());
        this.movieInfoViewModel.setState(movieInfoState);
        this.movieInfoViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final MovieInfoState movieInfoState = movieInfoViewModel.getState();
        movieInfoViewModel.firePropertyChanged();
    }

    @Override
    public void switchToWatchlistView() {
        this.viewManagerModel.setState("Watchlist");
        this.viewManagerModel.firePropertyChanged();
    }

//    @Override
//    public void prepareSuccessView(MovieInfoOutputData outputData) {
//        final MovieInfoState movieInfoState = movieInfoViewModel.getState();
//        movieInfoState.setMovieTitle(outputData.getMovieTitle());
//        movieInfoState.setRatingInfo(outputData.getMovieRating());
//        movieInfoState.setPlotInfo(outputData.getMoviePlot());
//        movieInfoState.setTrailerLink(outputData.getMovieTrailer());
//        movieInfoState.setPosterPath(outputData.getMoviePosterPath());
//        movieInfoState.setUserReviews(outputData.getMovieReviews());
//
//        movieInfoViewModel.firePropertyChanged();
//    }
//
//    @Override
//    public void prepareFailView(String errorMessage) {
//    }
}


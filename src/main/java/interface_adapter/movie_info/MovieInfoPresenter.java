package interface_adapter.movie_info;

import interface_adapter.ViewManagerModel;
import use_case.movie_info.MovieInfoOutputBoundary;
import use_case.movie_info.MovieInfoOutputData;

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
        this.viewManagerModel.setState("movie info");
        final MovieInfoState movieInfoState = movieInfoViewModel.getState();
        movieInfoState.setMovieTitle(outputData.getMovieTitle());
        movieInfoState.setRatingInfo(outputData.getMovieRating());
        movieInfoState.setPlotInfo(outputData.getMoviePlot());
        movieInfoState.setTrailerLink(outputData.getMovieTrailer());
        movieInfoState.setPosterPath(outputData.getMoviePosterPath());
        movieInfoState.setUserReviews(outputData.getMovieReviews());
        movieInfoViewModel.firePropertyChanged();

        viewManagerModel.setState(movieInfoViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        movieInfoViewModel.firePropertyChanged();
    }

    @Override
    public void switchToWatchlistView() {
        this.viewManagerModel.setState("Watchlist");
        this.viewManagerModel.firePropertyChanged();
    }
}


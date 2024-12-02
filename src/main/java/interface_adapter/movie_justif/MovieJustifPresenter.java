package interface_adapter.movie_justif;

import interface_adapter.ViewManagerModel;
import use_case.movie_justif.MovieJustifOutputBoundary;
import use_case.movie_justif.MovieJustifOutputData;

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
        final MovieJustifState movieJustifState = movieJustifViewModel.getState();
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
        // change the state to recommendation
        this.viewManagerModel.setState("Recommendation");
        this.viewManagerModel.firePropertyChanged();
    }
}

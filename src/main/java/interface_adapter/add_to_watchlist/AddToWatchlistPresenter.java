package interface_adapter.add_to_watchlist;

import interface_adapter.ViewManagerModel;
import interface_adapter.movie_search.MovieSearchState;
import interface_adapter.movie_search.MovieSearchViewModel;
import use_case.add_to_watchlist.AddToWatchlistOutputBoundary;
import use_case.add_to_watchlist.AddToWatchlistOutputData;

/**
 * The Presenter for the Add To Watchlist Use Case.
 */
public class AddToWatchlistPresenter implements AddToWatchlistOutputBoundary {

    private final MovieSearchViewModel movieSearchViewModel;
    private final ViewManagerModel viewManagerModel;

    public AddToWatchlistPresenter(ViewManagerModel viewManagerModel,
                                   MovieSearchViewModel movieSearchViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.movieSearchViewModel = movieSearchViewModel;
    }

    @Override
    public void prepareSuccessView(AddToWatchlistOutputData outputData) {
        final MovieSearchState movieSearchState = movieSearchViewModel.getState();
        // update the state with the message to be shown once this use case is executed
        movieSearchState.setMovieAddedToWatchlist(outputData.getAddedMessage());
        movieSearchViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final MovieSearchState movieSearchState = movieSearchViewModel.getState();
        // update the state with the message to be shown once this use case is executed
        movieSearchState.setMovieAddedToWatchlist(errorMessage);
        movieSearchViewModel.firePropertyChanged();
    }
}


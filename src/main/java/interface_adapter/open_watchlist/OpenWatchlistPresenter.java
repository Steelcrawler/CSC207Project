package interface_adapter.open_watchlist;

import interface_adapter.ViewManagerModel;
import interface_adapter.movieinfo.MovieInfoState;
import interface_adapter.movieinfo.MovieInfoViewModel;
import interface_adapter.moviesearch.MovieSearchViewModel;
import interface_adapter.watchlist.WatchlistState;
import interface_adapter.watchlist.WatchlistViewModel;
import use_case.open_watchlist.OpenWatchlistOutputBoundary;
import use_case.open_watchlist.OpenWatchlistOutputData;

/**
 * The Presenter for the Open Watchlist Use Case.
 */
public class OpenWatchlistPresenter implements OpenWatchlistOutputBoundary {

    private final WatchlistViewModel watchlistViewModel;
    private final MovieSearchViewModel movieSearchViewModel;
    private final ViewManagerModel viewManagerModel;
    private final MovieInfoViewModel movieInfoViewModel;

    public OpenWatchlistPresenter(ViewManagerModel viewManagerModel,
                                  WatchlistViewModel openWatchlistViewModel, MovieSearchViewModel movieSearchViewModel, MovieInfoViewModel movieInfoViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.watchlistViewModel = openWatchlistViewModel;
        this.movieSearchViewModel = movieSearchViewModel;
        this.movieInfoViewModel = movieInfoViewModel;
    }

    @Override
    public void prepareSuccessView(OpenWatchlistOutputData outputData) {
        final WatchlistState watchlistState = watchlistViewModel.getState();
        watchlistState.setWatchlist(outputData.getMovieIDsList());
        watchlistState.setMovieTitles(outputData.getMovieTitlesList());
        watchlistState.setPosterPaths(outputData.getPosterPathsList());
        watchlistViewModel.firePropertyChanged();

        viewManagerModel.setState(watchlistViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final WatchlistState watchlistState = watchlistViewModel.getState();
        watchlistState.setEmptyWatchlist(true);
        watchlistState.setErrorMessage(errorMessage);
        watchlistViewModel.firePropertyChanged();
    }

    @Override
    public void switchToMovieSearchView() {
        viewManagerModel.setState(movieSearchViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
    @Override
    public void switchToMovieInfoView(int movieID) {
        MovieInfoState movieInfoState = movieInfoViewModel.getState();
        movieInfoState.setMovieID(movieID);
        viewManagerModel.setState(movieInfoViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}


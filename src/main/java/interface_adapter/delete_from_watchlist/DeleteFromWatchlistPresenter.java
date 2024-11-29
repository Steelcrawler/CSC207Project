package interface_adapter.delete_from_watchlist;

import interface_adapter.Select.SelectState;
import interface_adapter.Select.SelectViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.watchlist.WatchlistState;
import interface_adapter.watchlist.WatchlistViewModel;
import use_case.delete_from_watchlist.DeleteFromWatchlistOutputBoundary;
import use_case.delete_from_watchlist.DeleteFromWatchlistOutputData;

import java.util.ArrayList;

/**
 * The Presenter for the Delete From Watchlist Use Case.
 */
public class DeleteFromWatchlistPresenter implements DeleteFromWatchlistOutputBoundary {

    private final WatchlistViewModel watchlistViewModel;
    private final SelectViewModel selectViewModel;
    private final ViewManagerModel viewManagerModel;

    public DeleteFromWatchlistPresenter(ViewManagerModel viewManagerModel,
                                        WatchlistViewModel openWatchlistViewModel, SelectViewModel selectViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.watchlistViewModel = openWatchlistViewModel;
        this.selectViewModel = selectViewModel;
    }

    @Override
    public void prepareSuccessView(DeleteFromWatchlistOutputData outputData) {
        final WatchlistState watchlistState = watchlistViewModel.getState();
        watchlistState.setWatchlist(outputData.getMovieIDsList());
        watchlistState.setMovieTitles(outputData.getMovieTitlesList());
        watchlistState.setPosterPaths(outputData.getPosterPathsList());
        watchlistViewModel.firePropertyChanged();

        final SelectState selectState = selectViewModel.getState();
        selectState.setSelectedMovies(new ArrayList<>());
        selectState.setWatchlist(outputData.getMovieIDsList());
        selectState.setMovieTitles(outputData.getMovieTitlesList());
        selectState.setPosterPaths(outputData.getPosterPathsList());
        selectViewModel.firePropertyChanged();

        viewManagerModel.setState(watchlistViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final WatchlistState watchlistState = watchlistViewModel.getState();
        watchlistState.setNoSelectedMoviesToDelete(errorMessage);
        watchlistViewModel.firePropertyChanged();
        viewManagerModel.setState(watchlistViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}


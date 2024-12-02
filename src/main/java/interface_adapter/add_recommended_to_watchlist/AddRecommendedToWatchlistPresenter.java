package interface_adapter.add_recommended_to_watchlist;

import interface_adapter.ViewManagerModel;
import interface_adapter.recommendation.RecommendationState;
import interface_adapter.recommendation.RecommendationViewModel;
import interface_adapter.select.SelectState;
import interface_adapter.select.SelectViewModel;
import interface_adapter.watchlist.WatchlistState;
import interface_adapter.watchlist.WatchlistViewModel;
import use_case.add_recommended_to_watchlist.AddRecommendedToWatchlistOutputBoundary;
import use_case.add_recommended_to_watchlist.AddRecommendedToWatchlistOutputData;

/**
 * The Presenter for the Add Recommended To Watchlist Use Case.
 */
public class AddRecommendedToWatchlistPresenter implements AddRecommendedToWatchlistOutputBoundary {

    private final RecommendationViewModel recommendationViewModel;
    private final ViewManagerModel viewManagerModel;
    private final WatchlistViewModel watchlistViewModel;
    private final SelectViewModel selectViewModel;

    public AddRecommendedToWatchlistPresenter(ViewManagerModel viewManagerModel,
                                              RecommendationViewModel recommendationViewModel,
                                              WatchlistViewModel watchlistViewModel, SelectViewModel selectViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.recommendationViewModel = recommendationViewModel;
        this.watchlistViewModel = watchlistViewModel;
        this.selectViewModel = selectViewModel;
    }

    @Override
    public void prepareSuccessView(AddRecommendedToWatchlistOutputData outputData) {
        final RecommendationState recommendationState = recommendationViewModel.getState();
        // update the state with the message to be shown once this use case is executed
        recommendationState.setMovieAddedToWatchlist(outputData.getAddedMessage());
        recommendationViewModel.firePropertyChanged();

        final WatchlistState watchlistState = watchlistViewModel.getState();
        if (!watchlistState.getWatchlist().contains(outputData.getMovieID())) {
            watchlistState.getWatchlist().add(outputData.getMovieID());
        }

        if (!watchlistState.getMovieTitles().contains(outputData.getMovieTitle())) {
            watchlistState.getMovieTitles().add(outputData.getMovieTitle());
        }

        if (!watchlistState.getPosterPaths().contains(outputData.getPosterPath())) {
            watchlistState.getPosterPaths().add(outputData.getPosterPath());
        }
        watchlistViewModel.firePropertyChanged();

        final SelectState selectState = selectViewModel.getState();
        if (!selectState.getWatchlist().contains(outputData.getMovieID())) {
            selectState.getWatchlist().add(outputData.getMovieID());
        }

        if (!selectState.getMovieTitles().contains(outputData.getMovieTitle())) {
            selectState.getMovieTitles().add(outputData.getMovieTitle());
        }

        if (!selectState.getPosterPaths().contains(outputData.getPosterPath())) {
            selectState.getPosterPaths().add(outputData.getPosterPath());
        }
        watchlistViewModel.firePropertyChanged();
        selectViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final RecommendationState recommendationState = recommendationViewModel.getState();
        // update the state with the message to be shown once this use case is executed
        recommendationState.setMovieAddedToWatchlist(errorMessage);
        recommendationViewModel.firePropertyChanged();
    }
}


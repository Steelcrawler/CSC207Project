package interface_adapter.add_recommended_to_watchlist;

import interface_adapter.ViewManagerModel;
import interface_adapter.recommendation.RecommendationState;
import interface_adapter.recommendation.RecommendationViewModel;
import use_case.add_recommended_to_watchlist.AddRecommendedToWatchlistOutputBoundary;
import use_case.add_recommended_to_watchlist.AddRecommendedToWatchlistOutputData;

/**
 * The Presenter for the Add Recommended To Watchlist Use Case.
 */
public class AddRecommendedToWatchlistPresenter implements AddRecommendedToWatchlistOutputBoundary {

    private final RecommendationViewModel recommendationViewModel;
    private final ViewManagerModel viewManagerModel;

    public AddRecommendedToWatchlistPresenter(ViewManagerModel viewManagerModel,
                                              RecommendationViewModel recommendationViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.recommendationViewModel = recommendationViewModel;
    }

    @Override
    public void prepareSuccessView(AddRecommendedToWatchlistOutputData outputData) {
        final RecommendationState recommendationState = recommendationViewModel.getState();
        // update the state with the message to be shown once this use case is executed
        recommendationState.setMovieAddedToWatchlist(outputData.getAddedMessage());
        recommendationViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final RecommendationState recommendationState = recommendationViewModel.getState();
        // update the state with the message to be shown once this use case is executed
        recommendationState.setMovieAddedToWatchlist(errorMessage);
        recommendationViewModel.firePropertyChanged();
    }
}


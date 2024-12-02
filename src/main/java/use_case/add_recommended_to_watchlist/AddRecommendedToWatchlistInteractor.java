package use_case.add_recommended_to_watchlist;

/**
 * The Add Recommended To Watchlist Interactor.
 */
public class AddRecommendedToWatchlistInteractor implements AddRecommendedToWatchlistInputBoundary {
    private final AddRecommendedToWatchlistDataAccessInterface mongoDBDataAccessObject;
    private final AddRecommendedToWatchlistOutputBoundary addRecommendedToWatchlistPresenter;

    public AddRecommendedToWatchlistInteractor(AddRecommendedToWatchlistDataAccessInterface mongoDBDataAccessObject,
                                               AddRecommendedToWatchlistOutputBoundary addRecommendedToWatchlistOutputBoundary) {
        this.mongoDBDataAccessObject = mongoDBDataAccessObject;
        this.addRecommendedToWatchlistPresenter = addRecommendedToWatchlistOutputBoundary;
    }

    @Override
    public void execute(AddRecommendedToWatchlistInputData addRecommendedToWatchlistInputData) {
        if (!mongoDBDataAccessObject.existsInWatchlist(mongoDBDataAccessObject.getCurrentUsername(),
                addRecommendedToWatchlistInputData.getMovieID())) {
            mongoDBDataAccessObject.addToWatchlist(mongoDBDataAccessObject.getCurrentUsername(),
                    addRecommendedToWatchlistInputData.getMovieID());

            final AddRecommendedToWatchlistOutputData addRecommendedToWatchlistOutputData = new AddRecommendedToWatchlistOutputData(
                    addRecommendedToWatchlistInputData.getMovieTitle(), addRecommendedToWatchlistInputData.getMovieID(),
                    addRecommendedToWatchlistInputData.getPosterPath(), false);
            this.addRecommendedToWatchlistPresenter.prepareSuccessView(addRecommendedToWatchlistOutputData);
        }
        else {
            this.addRecommendedToWatchlistPresenter.prepareFailView("This movie is already in your watchlist.");
        }
    }
}


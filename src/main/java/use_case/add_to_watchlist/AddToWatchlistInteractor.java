package use_case.add_to_watchlist;

/**
 * The Add To Watchlist Interactor.
 */
public class AddToWatchlistInteractor implements AddToWatchlistInputBoundary {
    private final AddToWatchlistDataAccessInterface mongoDBDataAccessObject;
    private final AddToWatchlistOutputBoundary addToWatchlistPresenter;

    public AddToWatchlistInteractor(AddToWatchlistDataAccessInterface mongoDBDataAccessObject,
                                    AddToWatchlistOutputBoundary addToWatchlistOutputBoundary) {
        this.mongoDBDataAccessObject = mongoDBDataAccessObject;
        this.addToWatchlistPresenter = addToWatchlistOutputBoundary;
    }

    @Override
    public void execute(AddToWatchlistInputData addToWatchlistInputData) {
        if (!mongoDBDataAccessObject.existsInWatchlist(mongoDBDataAccessObject.getCurrentUsername(),
                addToWatchlistInputData.getMovieID())) {
            mongoDBDataAccessObject.addToWatchlist(mongoDBDataAccessObject.getCurrentUsername(), addToWatchlistInputData
                    .getMovieID());

            final AddToWatchlistOutputData addToWatchlistOutputData = new AddToWatchlistOutputData(
                    addToWatchlistInputData.getMovieTitle(), false);
            this.addToWatchlistPresenter.prepareSuccessView(addToWatchlistOutputData);
        }
        else {
            this.addToWatchlistPresenter.prepareFailView("This movie is already in your watchlist.");
        }
    }
}


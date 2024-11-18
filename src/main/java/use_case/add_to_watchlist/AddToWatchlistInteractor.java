package use_case.add_to_watchlist;

/**
 * The Add To Watchlist Interactor.
 */
public class AddToWatchlistInteractor implements AddToWatchlistInputBoundary {
    private final AddToWatchlistDataAccessInterface MongoDBDataAccessObject;
    private final AddToWatchlistOutputBoundary addToWatchlistPresenter;

    public AddToWatchlistInteractor(AddToWatchlistDataAccessInterface MongoDBDataAccessObject,
                                    AddToWatchlistOutputBoundary addToWatchlistOutputBoundary) {
        this.MongoDBDataAccessObject = MongoDBDataAccessObject;
        this.addToWatchlistPresenter = addToWatchlistOutputBoundary;
    }

    @Override
    public void execute(AddToWatchlistInputData addToWatchlistInputData) {
        if (MongoDBDataAccessObject != null) {
            System.out.println("Not null");
            if (!MongoDBDataAccessObject.existsInWatchlist(MongoDBDataAccessObject.getCurrentUsername(), addToWatchlistInputData.getMovieID())) {
                System.out.println("movie isn't in watchlist yet");
                this.MongoDBDataAccessObject.addToWatchlist(MongoDBDataAccessObject.getCurrentUsername(), addToWatchlistInputData.getMovieID());

                final AddToWatchlistOutputData addToWatchlistOutputData = new AddToWatchlistOutputData(addToWatchlistInputData.getMovieTitle(), false);
                this.addToWatchlistPresenter.prepareSuccessView(addToWatchlistOutputData);
            }
            else {
                this.addToWatchlistPresenter.prepareFailView("This movie is already in your watchlist.");
            }
        }
        else {
            System.out.println("Its null");
            this.addToWatchlistPresenter.prepareFailView("No movies with that title.");
        }

    }
}


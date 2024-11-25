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
        if (!MongoDBDataAccessObject.existsInWatchlist(MongoDBDataAccessObject.getCurrentUsername(), addToWatchlistInputData.getMovieID())) {
            System.out.println("Movie isn't in watchlist yet");
            MongoDBDataAccessObject.addToWatchlist(MongoDBDataAccessObject.getCurrentUsername(), addToWatchlistInputData.getMovieID());

            final AddToWatchlistOutputData addToWatchlistOutputData = new AddToWatchlistOutputData(addToWatchlistInputData.getMovieTitle(), false);
            this.addToWatchlistPresenter.prepareSuccessView(addToWatchlistOutputData);
            }
        else {
            this.addToWatchlistPresenter.prepareFailView("This movie is already in your watchlist.");
            }
        }
    }


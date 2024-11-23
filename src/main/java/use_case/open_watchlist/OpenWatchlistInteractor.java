package use_case.open_watchlist;

import data_access.TMDBDataAccessObject;
import java.util.List;

/**
 * The Open Watchlist Interactor.
 */
public class OpenWatchlistInteractor implements OpenWatchlistInputBoundary {
    private final OpenWatchlistDataAccessInterface MongoDBDataAccessObject;
    private final TMDBDataAccessObject tmdbDataAccessObject;
    private final OpenWatchlistOutputBoundary openWatchlistPresenter;

    public OpenWatchlistInteractor(OpenWatchlistDataAccessInterface MongoDBDataAccessObject, TMDBDataAccessObject tmdbDataAccessObject,
                                   OpenWatchlistOutputBoundary openWatchlistOutputBoundary) {
        this.MongoDBDataAccessObject = MongoDBDataAccessObject;
        this.tmdbDataAccessObject = tmdbDataAccessObject;
        this.openWatchlistPresenter = openWatchlistOutputBoundary;
    }

    @Override
    public void execute() {
        String currentUsername = MongoDBDataAccessObject.getCurrentUsername();
        List<Integer> movieIDsList = MongoDBDataAccessObject.getWatchlist(currentUsername);
        if (movieIDsList != null) {
            List<String> titlesList;
            for (Integer movieID : movieIDsList) {
                String movieTitle = tmdbDataAccessObject.getTitle(movieID);
                titlesList.add(movieTitle);
            }

            final OpenWatchlistOutputData openWatchlistOutputData = new OpenWatchlistOutputData(movieIDsList, titlesList, false);
            this.openWatchlistPresenter.prepareSuccessView(openWatchlistOutputData);
        }
        else {
            this.openWatchlistPresenter.prepareFailView("Your watchlist is empty.");
            }
        }

    @Override
    public void switchToMovieSearchView() {
        openWatchlistPresenter.switchToMovieSearchView();
    }
}


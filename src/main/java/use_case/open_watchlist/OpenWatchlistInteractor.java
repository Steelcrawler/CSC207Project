package use_case.open_watchlist;

import data_access.TMDBDataAccessObject;
import entity.Movie;

import java.util.ArrayList;
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
        if (!movieIDsList.isEmpty()) {
            List<String> titlesList = new ArrayList<>();
            List<String> posterPathsList = new ArrayList<>();
            for (Integer movieID : movieIDsList) {
                Movie movie = tmdbDataAccessObject.getMovieByID(movieID);
                titlesList.add(movie.getTitle());
                posterPathsList.add(movie.getPosterPath());
            }

            final OpenWatchlistOutputData openWatchlistOutputData = new OpenWatchlistOutputData(movieIDsList, titlesList, posterPathsList, false);
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
    public void switchToMovieInfoView(int movieID) {
        openWatchlistPresenter.switchToMovieInfoView(movieID);
    }
}


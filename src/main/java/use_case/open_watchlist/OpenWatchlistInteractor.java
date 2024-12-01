package use_case.open_watchlist;

import java.util.ArrayList;
import java.util.List;

import data_access.TMDBDataAccessObject;
import entity.Movie;

/**
 * The Open Watchlist Interactor.
 */
public class OpenWatchlistInteractor implements OpenWatchlistInputBoundary {
    private final OpenWatchlistDataAccessInterface mongoDBDataAccessObject;
    private final TMDBDataAccessObject tmdbDataAccessObject;
    private final OpenWatchlistOutputBoundary openWatchlistPresenter;

    public OpenWatchlistInteractor(OpenWatchlistDataAccessInterface mongoDBDataAccessObject,
                                   TMDBDataAccessObject tmdbDataAccessObject,
                                   OpenWatchlistOutputBoundary openWatchlistOutputBoundary) {
        this.mongoDBDataAccessObject = mongoDBDataAccessObject;
        this.tmdbDataAccessObject = tmdbDataAccessObject;
        this.openWatchlistPresenter = openWatchlistOutputBoundary;
    }

    @Override
    public void execute() {
        String currentUsername = mongoDBDataAccessObject.getCurrentUsername();
        List<Integer> movieIDsList = mongoDBDataAccessObject.getWatchlist(currentUsername);
        if (!movieIDsList.isEmpty()) {
            List<String> titlesList = new ArrayList<>();
            List<String> posterPathsList = new ArrayList<>();
            for (Integer movieID : movieIDsList) {
                Movie movie = tmdbDataAccessObject.getMovieByID(movieID);
                titlesList.add(movie.getTitle());
                posterPathsList.add(movie.getPosterPath());
            }

            final OpenWatchlistOutputData openWatchlistOutputData = new OpenWatchlistOutputData(movieIDsList,
                    titlesList, posterPathsList, false);
            this.openWatchlistPresenter.prepareSuccessView(openWatchlistOutputData);
        }
        else {
            this.openWatchlistPresenter.prepareFailView("Your watchlist is empty.");
        }
    }

    /**
     * Switches from the WatchlistView to the MovieSearchView.
     */
    public void switchToMovieSearchView() {
        openWatchlistPresenter.switchToMovieSearchView();
    }

    /**
     * Switches from the WatchlistView to the MovieInfoView.
     * @param movieID the ID of the movie to display in the MovieInfoView
     */
    public void switchToMovieInfoView(int movieID) {
        openWatchlistPresenter.switchToMovieInfoView(movieID);
    }
}


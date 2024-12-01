package use_case.delete_from_watchlist;

import java.util.ArrayList;
import java.util.List;

import data_access.TMDBDataAccessObject;
import entity.Movie;

/**
 * The Delete From Watchlist Interactor.
 */
public class DeleteFromWatchlistInteractor implements DeleteFromWatchlistInputBoundary {
    private final DeleteFromWatchlistDataAccessInterface mongoDBDataAccessObject;
    private final TMDBDataAccessObject tmdbDataAccessObject;
    private final DeleteFromWatchlistOutputBoundary deleteFromWatchlistPresenter;

    public DeleteFromWatchlistInteractor(DeleteFromWatchlistDataAccessInterface mongoDBDataAccessObject,
                                         TMDBDataAccessObject tmdbDataAccessObject,
                                         DeleteFromWatchlistOutputBoundary deleteFromWatchlistOutputBoundary) {
        this.mongoDBDataAccessObject = mongoDBDataAccessObject;
        this.tmdbDataAccessObject = tmdbDataAccessObject;
        this.deleteFromWatchlistPresenter = deleteFromWatchlistOutputBoundary;
    }

    @Override
    public void execute(DeleteFromWatchlistInputData deleteFromWatchlistInputData) {
        List<Integer> selectedMoviesList = deleteFromWatchlistInputData.getSelectedMovies();
        if (!selectedMoviesList.isEmpty()) {
            String currentUsername = mongoDBDataAccessObject.getCurrentUsername();

            // remove all movies that are selected from the watchlist
            for (Integer movieID : selectedMoviesList) {
                mongoDBDataAccessObject.removeFromWatchlist(currentUsername, movieID);
            }

            List<Integer> newWatchlist = mongoDBDataAccessObject.getWatchlist(currentUsername);
            List<String> titlesList = new ArrayList<>();
            List<String> posterPathsList = new ArrayList<>();
            for (Integer movieID : newWatchlist) {
                Movie movie = tmdbDataAccessObject.getMovieByID(movieID);
                titlesList.add(movie.getTitle());
                posterPathsList.add(movie.getPosterPath());
            }

            final DeleteFromWatchlistOutputData deleteFromWatchlistOutputData = new DeleteFromWatchlistOutputData(
                    newWatchlist, titlesList, posterPathsList, false);
            this.deleteFromWatchlistPresenter.prepareSuccessView(deleteFromWatchlistOutputData);
        }
        else {
            this.deleteFromWatchlistPresenter.prepareFailView("You did not select any movie to delete.");
        }
    }
}


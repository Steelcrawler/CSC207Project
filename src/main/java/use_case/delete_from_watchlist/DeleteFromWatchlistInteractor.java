package use_case.delete_from_watchlist;

import data_access.MongoDBUserDataAccessObject;
import data_access.TMDBDataAccessObject;
import entity.Movie;
import use_case.open_watchlist.OpenWatchlistOutputData;

import java.util.ArrayList;
import java.util.List;

/**
 * The Delete From Watchlist Interactor.
 */
public class DeleteFromWatchlistInteractor implements DeleteFromWatchlistInputBoundary {
    private final DeleteFromWatchlistDataAccessInterface MongoDBDataAccessObject;
    private final TMDBDataAccessObject tmdbDataAccessObject;
    private final DeleteFromWatchlistOutputBoundary deleteFromWatchlistPresenter;

    public DeleteFromWatchlistInteractor(DeleteFromWatchlistDataAccessInterface MongoDBDataAccessObject, TMDBDataAccessObject tmdbDataAccessObject,
                                         DeleteFromWatchlistOutputBoundary deleteFromWatchlistOutputBoundary) {
        this.MongoDBDataAccessObject = MongoDBDataAccessObject;
        this.tmdbDataAccessObject = tmdbDataAccessObject;
        this.deleteFromWatchlistPresenter = deleteFromWatchlistOutputBoundary;
    }

    @Override
    public void execute(DeleteFromWatchlistInputData deleteFromWatchlistInputData) {
        if (!selectedMoviesList.isEmpty()) {
            String currentUsername = MongoDBDataAccessObject.getCurrentUsername();

            // remove all movies that are selected from the watchlist
            for (Integer movieID : deleteFromWatchlistInputData.getMovieIDs()) {
            MongoDBDataAccessObject.removeFromWatchlist(currentUsername, movieID);
            }

            List<Integer> newWatchlist = MongoDBUserDataAccessObject.getWatchlist(currentUsername);
            if (newWatchlist != null) {
                List<String> titlesList = new ArrayList<>();
                List<String> posterPathsList = new ArrayList<>();
                for (Integer movieID : newWatchlist) {
                    Movie movie = tmdbDataAccessObject.getMovieByID(movieID);
                    titlesList.add(movie.getTitle());
                    posterPathsList.add(movie.getPosterPath());
                }

                final DeleteFromWatchlistOutputData deleteFromWatchlistOutputData = new DeleteFromWatchlistOutputData(newWatchlist, titlesList, posterPathsList, false);
                this.deleteFromWatchlistPresenter.prepareSuccessView(deleteFromWatchlistOutputData);
            } else {
                // everything has been deleted from watchlist. the watchlist is now empty
                this.deleteFromWatchlistPresenter.prepareFailView("Your watchlist is empty.");
            }
        } else {
            this.deleteFromWatchlistPresenter.prepareFailView("You did not select any movie to delete.");
            }
        }
    }


package use_case.watchliststorage;

import java.util.List;

/**
 * Interface for a User Watchlist DAO.
 */
public interface UserWatchlistDataAccessInterface {
    /**
     * Adds a movie to the user's watchlist.
     *
     * @param username the username of the user
     * @param movieID the movie to add to the watchlist
     */
    void addToWatchlist(String username, Integer movieID);

    /**
     * Retrieves the user's watchlist.
     *
     * @param username the username of the user
     * @return the list of movies in the user's watchlist
     */
    List<Integer> getWatchlist(String username);

    /**
     * Removes a movie from the user's watchlist.
     *
     * @param username the username of the user
     * @param movieID the movie to remove from the watchlist
     */
    void removeFromWatchlist(String username, Integer movieID);
}

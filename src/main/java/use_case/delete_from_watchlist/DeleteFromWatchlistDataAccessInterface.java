package use_case.delete_from_watchlist;

import java.util.List;

/**
 * DAO for the Delete From Watchlist Use Case.
 */
public interface DeleteFromWatchlistDataAccessInterface {

    /**
     * Retrieves the current username that is logged in.
     * @return the current username.
     */
    String getCurrentUsername();

    /**
     * Returns the watchlist of the current username
     * @param username The current username
     * @return the watchlist (a list of movieIDs) of the current user.
     */
    List<Integer> getWatchlist(String username);

    /**
     * Removes the movie with that ID from currentUsername's watchlist.
     */
    void removeFromWatchlist(String currentUsername, Integer movieID);
}


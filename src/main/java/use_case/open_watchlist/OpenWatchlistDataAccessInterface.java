package use_case.open_watchlist;

import java.util.List;

/**
 * DAO for the Open Watchlist Use Case.
 */
public interface OpenWatchlistDataAccessInterface {

    /**
    * Retrieves the current username that is logged in.
    * @return the current username.
    */
    String getCurrentUsername();

    /**
     * Returns the watchlist of the current username.
     * @param username The current username
     * @return the watchlist (a list of movieIDs) of the current user.
     */
    List<Integer> getWatchlist(String username);
}


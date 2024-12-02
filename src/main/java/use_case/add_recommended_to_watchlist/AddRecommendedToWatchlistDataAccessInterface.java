package use_case.add_recommended_to_watchlist;

/**
 * DAO for the Add Recommended To Watchlist Use Case.
 */
public interface AddRecommendedToWatchlistDataAccessInterface {

    /**
     * Checks if the given movie is already in the watchlist.
     * @param username the current username.
     * @param movieID the movie ID to look for.
     * @return true if a movie with the ID exists in the watchlist; false otherwise.
     */
    boolean existsInWatchlist(String username, Integer movieID);

    /**
     * Returns the current username.
     * @return the current username that's logged in.
     */
    String getCurrentUsername();

    /**
     * Add the movieID to the currentUsername's watchlist.
     * @param currentUsername the current user's username.
     * @param movieID the movie ID to add to the watchlist.
     */
    void addToWatchlist(String currentUsername, Integer movieID);
}


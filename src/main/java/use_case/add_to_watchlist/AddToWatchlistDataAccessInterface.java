package use_case.add_to_watchlist;

/**
 * DAO for the Add To Watchlist Use Case.
 */
public interface AddToWatchlistDataAccessInterface {

    /**
     * Checks if the given movie is already in the watchlist.
     * @param movieID the movie ID to look for.
     * @return true if a movie with the ID exists in the watchlist; false otherwise.
     */
    boolean existsInWatchlist(String username, Integer movieID);

    String getCurrentUsername();

    void addToWatchlist(String currentUsername, Integer movieID);
}

